pragma solidity ^0.5.7;

#define TRANSPILE

#include "./common.sol"

#define DEFAULT_COMPTROLLER_ADDRESS 0x3d9819210a31b4961b30ef54be2aed79b9c9cd3b
#define C_TOKEN_ADDR 0x4ddc2d193948926d02f9b1fe9e1daa0718270ed5

#define STATE_IDLE 1
#define STATE_TRADE 2

contract MarginSwap {
  /* proxy variables */
  uint256 _code;
  uint256 _owner;
  uint256 _parent_address;
  uint256 _run_state;

  /* impl variables */
  uint256 _comptroller_address;
  uint256[2**160] _compound_lookup;

  event Trade(
    address indexed trade_contract,
    address from_asset,
    address to_asset,
    uint256 input,
    uint256 output,
    uint256 input_fee
  );

  /* needed to received ETH */
  function() external payable {
  }

  function setComptrollerAddress(address comptroller) external {
    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(1)
      }

      sstore(_comptroller_address_slot, comptroller)
    }
  }

  function comptrollerAddress() public view returns (address comptroller) {
    assembly {
      comptroller := sload(_comptroller_address_slot)
      if iszero(comptroller) {
        comptroller := DEFAULT_COMPTROLLER_ADDRESS
      }
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

        /* allow owner to update default comptroller address */
        let comptroller_address := sload(_comptroller_address_slot)
        if iszero(comptroller_address) {
          comptroller_address := DEFAULT_COMPTROLLER_ADDRESS
        }

        let res := call(
          gas, comptroller_address, 0,
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

      /* Step 2+3 */
      let array_end := add(array_start, mul(array_length, 0x20))
      for { let i := array_start } lt(i, array_end) { i := add(i, 0x20) } {
        let cToken_addr := calldataload(i)

        let mem_ptr := mload(0x40)
        let m_out := add(mem_ptr, 4)

        /* Step 2: register to lookup */
        {
          /* default to ETH address (0) */
          mstore(m_out, 0)

          if xor(cToken_addr, C_TOKEN_ADDR) {
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

  #define APPROVE(TOKEN, SPENDER, AMOUNT, REVERT_1) \
    { \
      mstore(m_in, fn_hash("approve(address,uint256)")) \
      mstore(add(m_in, 4), SPENDER) \
      mstore(add(m_in, 0x24), AMOUNT) \
      mstore(m_out, 0) \
      let res := call( \
        gas, TOKEN, 0, \
        m_in, 0x44, \
        m_out, 0x20 \
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
      if and(iszero(asset_address), iszero(eq(amount, callvalue))) {
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
        
        let res := call(
          gas, c_address, 0,
          m_in, 36,
          m_out, 32
        )
        
        if iszero(res) {
          REVERT(101)
        }
      }

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

          if asset_address {
            mstore(m_in, fn_hash("repayBorrow(uint256)"))
            mstore(add(m_in, 4), to_repay)
            m_in_size := 36
            wei_to_send := 0
          }

          let res := call(
            gas, c_address, wei_to_send,
            m_in, m_in_size,
            m_out, 32
          )

          if iszero(res) {
            REVERT(102)
          }

          switch returndatasize()
          /* called cEther */
          case 0 {
            /* error if called a cToken */
            if asset_address {
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

          if asset_address {
            mstore(m_in, fn_hash("mint(uint256)"))
            mstore(add(m_in, 4), amount)
            m_in_size := 0x24
            wei_to_send := 0
          }

          let res := call(
            gas, c_address, wei_to_send,
            m_in, m_in_size,
            m_out, 32
          )

          if iszero(res) {
            REVERT(106)
          }

          switch returndatasize()
          /* called cEther */
          case 0 {
            if asset_address {
              REVERT(107)
            }
          }
          /* called CErc20 */
          case 32 {
            if mload(m_out) {
              REVERT(108)
            }
          }
          /* called Unknown */
          default {
            REVERT(109)
          }
        }
      }
    }
  }

  function withdraw(address asset, uint256 amount, address destination) external {
    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(1)
      }
    }

    _withdraw(asset, amount, destination);
  }

  function _withdraw(address asset, uint256 amount, address destination) internal {
    uint256[2] memory m_in;
    uint256[1] memory m_out;

    assembly {
      let c_address := sload(add(_compound_lookup_slot, asset))
      if iszero(c_address) {
        REVERT(200)
      }

      let remaining := amount

      /* Step 1. Get balanceOf */
      {
        mstore(m_in, fn_hash("balanceOf(address)"))
        mstore(add(m_in, 0x04), address)
        
        let res := call(
          gas, c_address, 0,
          m_in, 0x24,
          m_out, 0x20
        )
        
        if iszero(res) {
          REVERT(201)
        }
      }

      {
        let c_balance := mload(m_out)

        if c_balance {
          /* Step 2.1 Determine how much token we can redeem  */
          {
            mstore(m_in, fn_hash("exchangeRateCurrent()"))

            let res := call(
              gas, c_address, 0,
              m_in, 0x04,
              m_out, 0x20
            )

            if iszero(res) {
              REVERT(202)
            }
          }

          let rate := mload(m_out)
          if iszero(rate) {
            REVERT(203)
          }

          /* roud up */
          let c_redeem := div(add(sub(rate, 1), mul(remaining, const_pow(10, 18))), rate)
          if gt(c_redeem, c_balance) {
            c_redeem := c_balance
          }

          /* Step 2.2 Redeem */
          if c_redeem {
            mstore(m_in, fn_hash("redeem(uint256)"))
            mstore(add(m_in, 4), c_redeem)

            let res := call(
              gas, c_address, 0,
              m_in, 0x24,
              m_out, 0x20
            )

            if iszero(res) {
              REVERT(204)
            }

            if mload(m_out) {
              REVERT(205)
            }

            let t_redeem := div(mul(c_redeem, rate), const_pow(10, 18))
            remaining := sub(remaining, t_redeem)
          }
        }
      }

      /* Step 3. Borrow Remaining */
      {
        if remaining {
          mstore(m_in, fn_hash("borrow(uint256)"))
          mstore(add(m_in, 4), remaining)

          let res := call(
            gas, c_address, 0,
            m_in, 0x24,
            m_out, 0x20
          )

          if or(iszero(res), mload(m_out)) {
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

        let res := call(
          gas, dest, wei_to_send,
          m_in, m_in_size,
          m_out, 32
        )

        if iszero(res) {
          REVERT(205)
        }

        if asset {
          if iszero(mload(m_out)) {
            REVERT(206)
          }
        }
      }
    }
  }

  function transferOut(address asset, uint256 amount, address destination) external {
    uint256[3] memory m_in;
    uint256[1] memory m_out;

    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(1)
      }

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

      let res := call(
        gas, dest, wei_to_send,
        m_in, m_in_size,
        m_out, 32
      )

      if iszero(res) {
        REVERT(2)
      }

      if asset {
        if iszero(mload(m_out)) {
          REVERT(3)
        }
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
        m_out, 0x20 \
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
                 bytes memory trade_data) public {
    uint256[3] memory m_in;
    uint256[1] memory m_out;
    uint256 output_amount;

    assembly {
      if xor(caller, sload(_owner_slot)) {
        REVERT(0)
      }

      /* protect against re-entry */
      {
        if xor(sload(_run_state_slot), STATE_IDLE) {
          REVERT(1)
        }
        sstore(_run_state_slot, STATE_TRADE)
      }

      let capital_source := sload(_parent_address_slot)

      /* Step 1: Get source capital from parent contract */
      {
        mstore(m_in, fn_hash("getCapital(address,uint256)"))
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
        APPROVE(input_asset, trade_contract, input_amount, /* REVERT(4) */ 4)
      }

      let before_balance := balance(address)

      if output_asset {
        BALANCE_OF(output_asset, address, /* REVERT(5) */ 5)
        before_balance := mload(m_out)
      }

      /* Step 3: Execute trade */
      {
        if iszero(extcodesize(trade_contract)) {
          REVERT(5)
        }

        let wei_to_send := input_amount
        if input_asset {
          wei_to_send := 0
        }

        let res := call(
          gas, trade_contract, wei_to_send,
          add(trade_data, 0x20), mload(trade_data),
          0, 0
        )

        if iszero(res) {
          REVERT(7)
        }
      }

      if input_asset {
        APPROVE(input_asset, trade_contract, 0, /* REVERT(8) */ 8)
      }

      let after_balance := balance(address)
      if output_asset {
        BALANCE_OF(output_asset, address, /* REVERT(9) */ 9)
        after_balance := mload(m_out)
      }

      if lt(after_balance, before_balance) {
        REVERT(10)
      }

      output_amount := sub(after_balance, before_balance)
      if lt(output_amount, min_output_amount) {
        REVERT(11)
      }
    }

    /* Step 4: Deposit trade output into money market */
    depositToCompound(output_asset, output_amount);

    uint256 fee;
    uint256 return_amount;

    assembly {
      fee := div(input_amount, 200)
      return_amount := add(fee, input_amount)
    }

    /* Step 5: Borrow funds to repay parent */
    _withdraw(input_asset, return_amount, address(_parent_address));

    assembly {
      sstore(_run_state_slot, STATE_IDLE)

      log_event(
        Trade, m_in,
        trade_contract,
        input_asset,
        output_asset,
        input_amount,
        output_amount,
        fee
      )
    }
  }
}


