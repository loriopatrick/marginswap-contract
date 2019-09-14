pragma solidity ^0.5.7;
contract MarginParentMock {
  
  function getCapital(address asset, uint256 amount) external  {
    
    uint256[2] memory m_in;
    
    uint256[1] memory m_out;
    assembly {
      let m_in_size := 0
      let wei_to_sent := amount
      if asset {
        mstore(m_in, /* fn_hash("transfer(address,uint256)") */ 0xa9059cbb00000000000000000000000000000000000000000000000000000000)
        mstore(add(m_in, 0x04), caller)
        mstore(add(m_in, 0x20), amount)
        m_in_size := 0x24
        wei_to_sent := 0
      }
      let res := call(gas, asset, wei_to_sent, m_in, m_in_size, m_out, 0x20)
      if iszero(res) {
        mstore(32, 1)
        revert(63, 1)
      }
      switch returndatasize
        case 0 {}
        case 32 {
          if mload(m_out) {
            mstore(32, 2)
            revert(63, 1)
          }
        }
        default {
          mstore(32, 3)
          revert(63, 1)
        }
    }
  }
}
