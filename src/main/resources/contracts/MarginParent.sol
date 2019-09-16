pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

contract MarginParent {
  bytes constant _margin_swap_compiled = hex"
  #include "../../../../contracts-compiled/Margin/MarginSwap.bin"
  ";

  address _comptroller_address;
  address _cEther_address;
  uint256[2**160] _white_listed_addresses;

  address public _last_create_address;

  constructor(address comptroller_address, address cEther_address) public {
    assembly {
      sstore(_comptroller_address_slot, comptroller_address)
      sstore(_cEther_address_slot, cEther_address)
    }
  }

  #define CONSTRUCT(OWNER) \
      mstore(cursor, OWNER) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, address) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, sload(_comptroller_address_slot)) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, sload(_cEther_address_slot)) \
      cursor := add(cursor, 0x20) \
\
      mstore(0x40, cursor)


  function setupMargin() external returns (address margin_contract) {
    bytes memory margin_swap_compiled = _margin_swap_compiled;

    assembly {
      let compiled_bytes := mload(margin_swap_compiled)
      let contract_start := add(margin_swap_compiled, 0x20)
      let cursor := add(contract_start, compiled_bytes)

      CONSTRUCT(caller)

      let contract_size := sub(cursor, contract_start)
      margin_contract := create2(
        0, contract_start, contract_size,
        caller
      )

      if iszero(margin_contract) {
        REVERT(1)
      }

      sstore(_last_create_address_slot, margin_contract)
      sstore(add(_white_listed_addresses_slot, margin_contract), 1)
    }
  }

  function isMarginSetup(address owner) public view returns (address margin_contract, bool enabled) {
    margin_contract = getMarginAddress(owner);

    assembly {
      enabled := sload(add(_white_listed_addresses_slot, margin_contract))
    }
  }

  function getMarginAddress(address owner) public view returns (address margin_contract) {
    bytes memory margin_swap_compiled = _margin_swap_compiled;

    assembly {
      let compiled_bytes := mload(margin_swap_compiled)
      let contract_start := add(margin_swap_compiled, 0x20)
      let cursor := add(contract_start, compiled_bytes)

      CONSTRUCT(owner)
      
      let contract_size := sub(cursor, contract_start)
      let contract_hash := keccak256(contract_start, contract_size)

      mstore(margin_swap_compiled, or(shl(0xa0, 0xff), address))
      mstore(add(margin_swap_compiled, 0x20), owner)
      mstore(add(margin_swap_compiled, 0x40), contract_hash)

      let address_hash := keccak256(add(margin_swap_compiled, const_sub(32, 21)), const_add(0x40, 21))
      margin_contract := and(address_hash, 0xffffffffffffffffffffffffffffffffffffffff)
    }
  }

  function getCapital(address asset, uint256 amount) external {
    uint256[3] memory m_in;
    uint256[1] memory m_out;

    assembly {
      if iszero(sload(add(_white_listed_addresses_slot, caller))) {
        REVERT(1)
      }

      let m_in_size := 0
      let wei_to_send := amount
      let dest := caller

      if asset {
        mstore(m_in, fn_hash("transfer(address,uint256)"))
        mstore(add(m_in, 4), caller)
        mstore(add(m_in, 0x24), amount)
        dest := asset
        m_in_size := 0x44
        wei_to_send := 0
      }

      let result := call(
        gas, dest, wei_to_send,
        m_in, m_in_size,
        m_out, 32
      )

      if or(iszero(result), iszero(mload(m_out))) {
        REVERT(2)
      }
    }
  }
}


