pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

contract MarginParentMock {
  function getCapital(address asset, uint256 amount) external {
    uint256[2] memory m_in;
    uint256[1] memory m_out;

    assembly {
      let m_in_size := 0
      let wei_to_sent := amount

      if asset {
        mstore(m_in, fn_hash("transfer(address,uint256)"))
        mstore(add(m_in, 0x04), caller)
        mstore(add(m_in, 0x20), amount)
        
        m_in_size := 0x24
        wei_to_sent := 0
      }

      let res := call(
        gas, asset, wei_to_sent,
        m_in, m_in_size,
        m_out, 0x20
      )

      if iszero(res) {
        REVERT(1)
      }

      switch returndatasize()
      /* sent ether */
      case 0 {
      }
      /* called standard ERC20 */
      case 32 {
        if mload(m_out) {
          REVERT(2)
        }
      }
      /* called Unknown */
      default {
        REVERT(3)
      }
    }
  }
}

