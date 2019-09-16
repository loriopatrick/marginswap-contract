pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

/* AUDIT NOTE:

  If you call to a non-contract address, the call will be all good even though nothing happens!
  If you are unsure the address exists, you need to execute extcodesize to check if there's a contract.

  Note: should probably return affirmative success so caller has something to check and doesn't need to
  verify that the contract exists.

  - CHECK that we verify all call results!
*/

/* TODOs:
  
   - Need to add transferOut so user can move out funds.
   - Restrict caller to be owner where needed


   PICKUP:
   - mock out compound stuff for deposit
   - test deposit
   - ensure return value format of returns (uint[]) is as expected (try in MainNet?)
*/

contract MarginSwap {
  uint256 _owner;
  uint256 _parent_address;
  uint256 _comptroller_address;
  uint256 _cEther_address;

  uint256[2**160] _compound_lookup;

  constructor(address owner, address parent_address, address comptroller_address, address cEther_address) public {
    assembly {
      sstore(_owner_slot, owner)
      sstore(_parent_address_slot, parent_address)
      sstore(_comptroller_address_slot, comptroller_address)
      sstore(_cEther_address_slot, cEther_address)
    }
  }

  function lookupUnderlying(address cToken) public view returns (address result) {
    assembly {
      result := sload(add(_compound_lookup_slot, cToken))
    }
  }

  function enterMarkets(address[] calldata cTokens) external {
    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(0)
      }

      /* assert: array position is standard */
      if xor(0x20, calldataload(4)) {
        REVERT(1)
      }

      let array_length := calldataload(0x24)
      let array_start := 0x44

      /* assert: calldatasize fits data */
      if xor(add(0x44, mul(0x20, array_length)), calldatasize) {
        REVERT(2)
      }

      /* Step 1: enter all markets */
      {
        let call_input := mload(0x40)
        let call_input_size := calldatasize

        /* function signature is the same, so relay calldata */
        calldatacopy(
          /* free memory pointer */ call_input,
          /* start */ 0,
          /* size */ call_input_size
        )

        /* NOTE: using input memory to store output, safe? */
        let res := call(
          gas, sload(_comptroller_address_slot), 0,
          call_input,
          call_input_size,
          call_input,
          sub(call_input_size, 4)
        )

        if iszero(res) {
          REVERT(3)
        }

        /* assert: output array is standard */
        if xor(0x20, mload(call_input)) {
          REVERT(4)
        }

        /* assert: output array length matches input */
        if xor(array_length, mload(add(call_input, 0x20))) {
          REVERT(5)
        }

        let has_error := 0
        for { let i := 0 } lt(i, array_length) { i := add(i, 1) } {
          let value := mload(
            add(add(call_input, 0x40), mul(i, 0x20))
          )
          has_error := or(has_error, value)
        }

        if has_error {
          REVERT(6)
        }
      }

      /* -------------------------------- */
      /*   Entered the compound markets   */
      /* -------------------------------- */

      let cEther_addr := sload(_cEther_address_slot)

      /* Step 2+3 */
      let array_end := add(array_start, mul(array_length, 0x20))
      for { let i := array_start } lt(i, array_end) { i := add(i, 0x20) } {
        let cToken_addr := calldataload(i)

        let mem_ptr := mload(0x40)
        let m_out := add(mem_ptr, 4)

        /* Step 2: register to lookup */
        {
          /* default to ETH address (0) */
          mstore(mem_ptr, 0)

          if xor(cToken_addr, cEther_addr) {
            mstore(mem_ptr, fn_hash("underlying()"))

            let res := staticcall(
              gas, cToken_addr,
              mem_ptr, 4,
              m_out, 32
            )

            if iszero(res) {
              REVERT(7)
            }
          }
        }

        let underlying_addr := mload(m_out)
        sstore(add(_compound_lookup_slot, underlying_addr), cToken_addr)

        /* Step 3: approve transfers from here to cToken */
        if underlying_addr {
          mstore(mem_ptr, fn_hash("approve(address,uint256)"))
          mstore(add(mem_ptr, 4), cToken_addr)
          mstore(add(mem_ptr, 0x24), 0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff)

          let mem_out := add(mem_ptr, 0x44)
          mstore(mem_out, 0)

          let res := call(
            gas, underlying_addr, 0,
            mem_ptr, 0x44,
            mem_out, 0x20
          )

          if or(iszero(res), iszero(mload(mem_out))) {
            REVERT(8)
          }
        }
      }
    }
  }

  #define APPROVE(TOKEN, CONTRACT, AMOUNT, REVERT_1) \
    { \
      mstore(m_in, fn_hash("approve(uint256)")) \
      mstore(add(m_in, 4), AMOUNT) \
      mstore(m_out, 0) \
      let res := call( \
        gas, CONTRACT, 0, \
        m_in, 36, \
        m_out, 32 \
      ) \
      if or(iszero(res), iszero(mload(m_out))) { \
        REVERT(REVERT_1) \
      } \
    }

  function depositEth() external payable {
    deposit(address(0x0), msg.value);
  }

  function deposit(address asset_address, uint256 amount) public payable {
    uint256[4] memory m_in;
    uint256[1] memory m_out;

    assembly {
      /* if ETH, ensure call value matches amount */
      if and(iszero(asset_address), xor(amount, callvalue)) {
        REVERT(1)
      }

      /* transfer amount to this contract */
      if asset_address {
        if callvalue {
          REVERT(2)
        }

        mstore(m_in, fn_hash("transferFrom(address,address,uint256)"))
        mstore(add(m_in, 4), caller)
        mstore(add(m_in, 0x24), address)
        mstore(add(m_in, 0x44), amount)

        mstore(m_out, 0)
        let res := call(
          gas, asset_address, 0,
          m_in, 0x64,
          m_out, 0x20
        )

        if or(iszero(res), iszero(mload(m_out))) {
          REVERT(3)
        }
      }
    }

    depositToCompound(asset_address, amount);
  }

  /* assumes amount exists in contract's wallet */
  function depositToCompound(address asset_address, uint256 amount) internal {
    uint256[2] memory m_in;
    uint256[1] memory m_out;

    assembly {
      let c_address := sload(add(_compound_lookup_slot, asset_address))
      if iszero(c_address) {
        REVERT(100)
      }

      /* Step 1. Get borrow amount */
      {
        mstore(m_in, fn_hash("borrowBalanceCurrent(address)"))
        mstore(add(m_in, 4), address)
        
        let res := staticcall(
          gas, c_address,
          m_in, 36,
          m_out, 32
        )
        
        if iszero(res) {
          REVERT(101)
        }
      }

      let cEther_addr := sload(_cEther_address_slot)

      /* Step 2. Repay max possible borrow */
      {
        let borrow_amount := mload(m_out)

        let to_repay := borrow_amount
        if lt(amount, to_repay) {
          to_repay := amount
        }

        if to_repay {
          mstore(m_in, fn_hash("repayBorrow()"))
          let m_in_size := 4
          let wei_to_send := to_repay

          if xor(c_address, cEther_addr) {
            mstore(m_in, fn_hash("repayBorrow(uint256)"))
            mstore(add(m_in, 4), to_repay)
            m_in_size := 36
            wei_to_send := 0
          }

          let result := call(
            gas, c_address, wei_to_send,
            m_in, m_in_size,
            m_out, 32
          )

          if iszero(result) {
            REVERT(102)
          }

          switch returndatasize()
          /* called cEther */
          case 0 {
            /* note: assuming compound assets return a value */
            if xor(c_address, cEther_addr) {
              REVERT(103)
            }
          }
          /* called CErc20 */
          case 32 {
            if mload(m_out) {
              REVERT(104)
            }
          }
          /* called Unknown */
          default {
            REVERT(105)
          }

          amount := sub(amount, to_repay)
        }
      }

      /* Step 3. Mint remaining amount */
      {
        if amount {
          mstore(m_in, fn_hash("mint()"))
          let m_in_size := 4
          let wei_to_send := amount

          if xor(c_address, cEther_addr) {
            mstore(m_in, fn_hash("mint(uint256)"))
            mstore(add(m_in, 4), amount)
            m_in_size := 36
            wei_to_send := 0
          }

          let result := call(
            gas, c_address, wei_to_send,
            m_in, m_in_size,
            m_out, 32
          )

          switch returndatasize()
          /* called cEther */
          case 0 {
            if xor(c_address, cEther_addr) {
              REVERT(106)
            }
          }
          /* called CErc20 */
          case 32 {
            if mload(m_out) {
              REVERT(107)
            }
          }
          /* called Unknown */
          default {
            REVERT(108)
          }
        }
      }
    }
  }

  function withdraw(address asset, uint256 amount, address destination) public {
    uint256[2] memory m_in;
    uint256[1] memory m_out;

    assembly {
      let c_address := sload(add(_compound_lookup_slot, asset))
      if iszero(c_address) {
        REVERT(200)
      }

      let remaining := amount

      /* Step 1. Get avaiable balance */
      {
        mstore(m_in, fn_hash("balanceOfUnderlying(address)"))
        mstore(add(m_in, 4), address)
        
        let res := staticcall(
          gas, c_address,
          m_in, 36,
          m_out, 32
        )
        
        if iszero(res) {
          REVERT(201)
        }
      }

      /* Step 2. Reedeem */
      {
        let available := mload(m_out)

        let to_redeem := available
        if lt(remaining, to_redeem) {
          to_redeem := remaining
        }

        if to_redeem {
          mstore(m_in, fn_hash("redeemUnderlying(uint256)"))
          mstore(add(m_in, 4), to_redeem)

          let result := call(
            gas, c_address, 0,
            m_in, 36,
            m_out, 32
          )

          if iszero(result) {
            REVERT(202)
          }

          if mload(m_out) {
            REVERT(203)
          }

          remaining := sub(remaining, to_redeem)
        }
      }

      /* Step 3. Borrow Remaining */
      {
        if remaining {
          mstore(m_in, fn_hash("borrow(uint256)"))
          mstore(add(m_in, 4), remaining)

          let result := call(
            gas, c_address, 0,
            m_in, 36,
            m_out, 32
          )

          if or(iszero(result), mload(m_out)) {
            REVERT(204)
          }
        }
      }

      /* Step 4. Send acquired funds */
      {
        let m_in_size := 0
        let wei_to_send := amount
        let dest := destination

        if asset {
          mstore(m_in, fn_hash("transfer(address,uint256)"))
          mstore(add(m_in, 4), destination)
          mstore(add(m_in, 0x24), amount)
          dest := asset
          m_in_size := 0x44
          wei_to_send := 0
        }

        /* TODO, test withdraw ETH */

        let result := call(
          gas, dest, wei_to_send,
          m_in, m_in_size,
          m_out, 32
        )

        if or(iszero(result), iszero(mload(m_out))) {
          REVERT(205)
        }
      }
    }
  }

  function transferOut(address asset, uint256 amount, address destination) external {
    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(1)
      }
    }
  }

  #define BALANCE_OF(TOKEN, OWNER, REVERT_1) \
    { \
      mstore(m_in, fn_hash("balanceOf(address)")) \
      mstore(add(m_in, 4), OWNER) \
      mstore(m_out, 0) \
      let res := staticcall( \
        gas, TOKEN, \
        m_in, 0x24, \
        m_out, 32 \
      ) \
      if iszero(res) { \
        REVERT(REVERT_1) \
      } \
    }

  function trade(address input_asset,
                 uint256 input_amount,
                 address output_asset,
                 uint256 min_output_amount,
                 address trade_contract,
                 bytes memory trade_data) public payable {

    uint256[4] memory m_in;
    uint256[1] memory m_out;
    uint256 output_amount;

    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(1)
      }

      let capital_source := sload(_parent_address_slot)

      /* Step 1: Get source capital from parent contract */
      {
        mstore(m_in, fn_hash("getCapital(address,address,uint256)"))
        mstore(add(m_in, 0x04), input_asset)
        mstore(add(m_in, 0x24), input_amount)

        let res := call(
          gas, capital_source, 0,
          m_in, 0x44,
          0, 0
        )

        if iszero(res) {
          REVERT(2)
        }
      }

      /* Step 2: Allow trade contract to use capital for trade */
      if input_asset {
        /* only accept payment if using ETH as from */
        if callvalue {
          REVERT(3)
        }

        APPROVE(input_asset, trade_contract, input_amount, /* REVERT(4) */ 4)
      }

      BALANCE_OF(caller, output_asset, /* REVERT(5) */ 5)
      let before_balance := mload(m_out)

      /* Step 3: Execute trade */
      {
        /* TODO: what happens if signature returns memory? */
        /* TODO: test with Uniswap, Kyber, 0x */
        let res := call(
          gas, trade_contract, callvalue,
          add(trade_data, 0x20), mload(trade_data),
          0, 0
        )

        if iszero(res) {
          REVERT(6)
        }
      }

      APPROVE(input_asset, trade_contract, 0, /* REVERT(7) */ 7)

      BALANCE_OF(caller, output_asset, /* REVERT(8) */ 8)
      let after_balance := mload(m_out)

      if lt(before_balance, after_balance) {
        REVERT(9)
      }

      output_amount := sub(after_balance, before_balance)
      if lt(output_amount, min_output_amount) {
        REVERT(10)
      }
    }

    /* Step 4: Deposit trade output into money market */
    depositToCompound(output_asset, output_amount);

    /* Step 5: Borrow funds to repay parent */
    withdraw(input_asset, input_amount, address(_parent_address));
  }
}

