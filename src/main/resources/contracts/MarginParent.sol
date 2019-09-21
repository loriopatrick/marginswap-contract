pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

contract MarginParent {
  bytes constant _margin_swap_compiled = hex"
  #include "../../../../contracts-compiled/MarginProxy/MarginProxy.bin"
  ";

  address _manager_address;
  address _manager_proposed;
  address _default_code;

  uint256[2**160] _white_listed_addresses;
  uint256[2**160] _approved_margin_code;

  event MarginSetup(
    address indexed owner,
    address margin_address
  );

  constructor(address default_code) public payable {
    assembly {
      sstore(_manager_address_slot, caller)
      sstore(_default_code_slot, default_code)
      sstore(add(_white_listed_addresses_slot, caller), 1)
    }
  }

  function() external payable {
  }

  /* allows manager to approve new margin code */
  function approveMarginCode(address margin_code_address, bool approved) external {
    assembly {
      /* ensure caller is manager */
      if xor(caller, sload(_manager_address_slot)) {
        REVERT(1)
      }

      sstore(add(_approved_margin_code_slot, margin_code_address), approved)
    }
  }

  function setDefautlMarginCode(address default_code) external {
    assembly {
      /* ensure caller is manager */
      if xor(caller, sload(_manager_address_slot)) {
        REVERT(1)
      }

      sstore(add(_approved_margin_code_slot, default_code), 1)
      sstore(_default_code_slot, default_code)
    }
  }

  /* allows user to update to margin code */
  function setMarginCode(address margin_code_address) external {
    address margin_contract = getMarginAddress(address(msg.sender));

    uint256[2] memory m_in;

    assembly {
      /* margin contract must be deployed */
      if iszero(extcodesize(margin_contract)) {
        REVERT(1)
      }

      /* if not approved, remove from whitelist */
      let approved := sload(add(_approved_margin_code_slot, margin_code_address))
      sstore(add(_white_listed_addresses_slot, margin_contract), approved)

      /* call margin contract to update code */
      {
        mstore(m_in, fn_hash("setCode(address)"))
        mstore(add(m_in, 0x04), margin_code_address)

        let res := call(
          gas, margin_contract, 0,
          m_in, 0x24,
          0x00, 0x00
        )

        if iszero(res) {
          REVERT(2)
        }
      }
    }
  }

  function managerPropose(address new_manager) external {
    assembly {
      if xor(caller, sload(_manager_address_slot)) {
        REVERT(1)
      }

      sstore(_manager_proposed_slot, new_manager)
    }
  }

  function managerSet() external {
    assembly {
      let proposed := sload(_manager_proposed_slot)
      if xor(caller, proposed) {
        REVERT(1)
      }

      sstore(add(_white_listed_addresses_slot, sload(_manager_address_slot)), 0)
      sstore(add(_white_listed_addresses_slot, proposed), 1)
      sstore(_manager_address_slot, proposed)
    }
  }

  #define CONSTRUCT(OWNER) \
      mstore(cursor, OWNER) \
      cursor := add(cursor, 0x20) \
\
      mstore(cursor, address) \
      cursor := add(cursor, 0x20) \
\
      mstore(0x40, cursor)


  function setupMargin() external returns (address margin_contract) {
    bytes memory margin_swap_compiled = _margin_swap_compiled;

    uint256[2] memory m_in;

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

      sstore(add(_white_listed_addresses_slot, margin_contract), 1)

      {
        mstore(m_in, fn_hash("setCode(address)"))
        mstore(add(m_in, 0x04), sload(_default_code_slot))

        let res := call(
          gas, margin_contract, 0,
          m_in, 0x24,
          0x0, 0x0
        )
        
        if iszero(res) {
          REVERT(2)
        }
      }

      log_event(
        MarginSetup, m_in,
        caller,
        margin_contract
      )
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

      if iszero(result) {
        REVERT(2)
      }

      if asset {
        if iszero(mload(m_out)) {
          REVERT(3)
        }
      }
    }
  }
}


