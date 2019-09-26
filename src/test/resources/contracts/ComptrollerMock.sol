pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

#define LOAD_NEXT(SIZE) \
 shr(const_sub(32, SIZE), calldataload(cursor)) \
 cursor := add(cursor, SIZE)

contract ComptrollerMock {
  uint256[2**160] _has_entered;

  uint256 _should_return_error;

  function setShouldReturnError(uint256 value) external {
    _should_return_error = value;
  }

  function hasEntered(address addr) public view returns (bool result) {
    assembly {
      result := sload(add(_has_entered_slot, addr))
    }
  }

  /* note, return signature is invalid but code-gen doesn't have support for uint256[] yet */
  function enterMarkets(address[] calldata cTokens) external returns (bytes memory results) {
    assembly {
      let cursor := add(4, calldataload(4))

      let cToken_length := calldataload(cursor)
      cursor := add(cursor, 0x20)
      let cursor_end := add(cursor, mul(0x20, cToken_length))

      let m_start := mload(0x40)
      let m_ptr := m_start

      mstore(m_ptr, 0x20)
      m_ptr := add(m_ptr, 0x20)

      mstore(m_ptr, cToken_length)
      m_ptr := add(m_ptr, 0x20)

      let should_error := sload(_should_return_error_slot)

      for { let i := 0 } lt(cursor, cursor_end) { i := add(i, 1) } {
        let addr := LOAD_NEXT(0x20)

        switch and(shr(i, should_error), 0x01)
        case 0 {
          sstore(add(_has_entered_slot, addr), 1)
        }
        case 1 {
          mstore(m_ptr, 1)
        }

        m_ptr := add(m_ptr, 0x20)
      }

      return(m_start, sub(m_ptr, m_start))
    }
  }
}

