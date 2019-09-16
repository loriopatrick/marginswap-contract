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

  #define CONSTRUCT \
      mstore(cursor, caller) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, address) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, sload(_comptroller_address_slot)) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, sload(_cEther_address_slot)) \
      cursor := add(cursor, 0x20)


  function buildMargin() external returns (address margin_contract) {
    bytes memory margin_swap_compiled = _margin_swap_compiled;

    assembly {
      let compiled_bytes := mload(margin_swap_compiled)
      let contract_start := add(margin_swap_compiled, 0x20)
      let cursor := add(contract_start, compiled_bytes)

      CONSTRUCT

      margin_contract := create2(
        0, contract_start, sub(cursor, contract_start),
        caller
      )

      if iszero(margin_contract) {
        REVERT(1)
      }

      sstore(add(_white_listed_addresses_slot, margin_contract), 1)
    }
  }

  function getMarginAddress(address owner) public returns (address margin_contract) {
    bytes memory margin_swap_compiled = _margin_swap_compiled;

    assembly {
      let compiled_bytes := mload(margin_swap_compiled)
      let contract_start := add(margin_swap_compiled, 0x20)
      let cursor := add(contract_start, compiled_bytes)

      CONSTRUCT
      
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
    assembly {
      if iszero(sload(_white_listed_addresses_slot, caller)) {
        REVERT(1)
      }

      /* TODO: send $$ to caller */
    }
  }
}


