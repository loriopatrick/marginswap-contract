pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

contract TradeMock {
  constructor() public payable {
  }

  function trade(address from_asset, address to_asset, uint256 input, uint256 output) external payable {
    uint256[4] memory m_in;
    uint256[1] memory m_out;

    assembly {
      /* Step 1: Take funds from caller */
      if from_asset {
        mstore(m_in, fn_hash("transferFrom(address,address,uint256)"))
        mstore(add(m_in, 4), caller)
        mstore(add(m_in, 0x24), address)
        mstore(add(m_in, 0x44), input)
        mstore(m_out, 0)

        let res := call(
          gas, from_asset, 0,
          m_in, 0x64,
          m_out, 0x20
        )

        if or(iszero(res), iszero(mload(m_out))) {
          REVERT(3)
        }
      }

      /* Step 2: Send funds to caller */
      {
        let m_in_size := 0
        let wei_to_send := output
        let dest := caller

        if to_asset {
          mstore(m_in, fn_hash("transfer(address,uint256)"))
          mstore(add(m_in, 4), caller)
          mstore(add(m_in, 0x24), output)
          dest := to_asset
          m_in_size := 0x44
          wei_to_send := 0
        }

        let res := call(
          gas, dest, wei_to_send,
          m_in, m_in_size,
          m_out, 32
        )

        if iszero(res) {
          REVERT(4)
        }

        if to_asset {
          if iszero(mload(m_out)) {
            REVERT(5)
          }
        }
      }
    }
  }
}

