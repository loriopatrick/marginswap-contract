const INIT_STATE = {
  eth_price: '200',
  wallet: {
    has_web3: false,
    address: null,
    processing: null,
    margin_address: null,
    margin_setup: false,
  },
  assets: {
    /*
     * [asset_symbol]: {
     *   compound_address,
     *   asset_address,
     *   decimals,
     *   borrow_rate,
     *   supply_rate,
     *   ether_price,
     *   collateral_factor,
     * }
     */
  },
  balances: {
    /*
     * [asset_symbol]: {
     *   compound_balance: x,
     *   borrow_balance: x,
     * }
     */
  },
  reserves: {
    /*
     * [asset_symbol]: TokenReservesNormalized
     */
  },
  liquidity: null,
  trade: {
    from_asset: 'DAI',
    to_asset: 'ETH',
    amount: '',
    is_input_active: true,
  }
};

const reducer = (state = INIT_STATE, action) => {
  switch (action.type) {
    case 'set-trade': {
      let { amount } = action.data;

      const updated_trade = {
        ...state.trade,
        ...action.data,
      };

      if (amount !== undefined) {
        amount = amount
          .replace(/[^0-9.]/g,'')
          .replace(/([0-9]*\.[0-9]*)\.+([0-9]*)/g, '$1$2');

        updated_trade.amount = amount;
      }

      /* flip assets so that they are not the same */
      if (updated_trade.from_asset === updated_trade.to_asset) {
        if (state.trade.from_asset !== updated_trade.from_asset) {
          updated_trade.to_asset = state.trade.from_asset;
        }
        else {
          updated_trade.from_asset = state.trade.to_asset;
        }
      }

      return {
        ...state,
        trade: updated_trade,
      };
    }
    case 'set-balances': {
      return {
        ...state,
        balances: action.balances,
      };
    }
    case 'set-assets': {
      return {
        ...state,
        assets: action.assets,
      };
    }
    case 'set-liquidity': {
      return {
        ...state,
        liquidity: action.liquidity,
      };
    }
    case 'wallet-has3': {
      return {
        ...state,
        wallet: {
          ...state.wallet,
          has_web3: true,
        },
      };
    }
    case 'wallet-address': {
      return {
        ...state,
        wallet: {
          ...state.wallet,
          address: action.address,
        },
        account: {
          ...INIT_STATE.account,
        },
      };
    }
    case 'wallet-processing': {
      return {
        ...state,
        wallet: {
          ...state.wallet,
          processing: action.processing,
        }
      };
    }
    case 'wallet-margin': {
      if (action.address !== state.wallet.address) {
        console.error('got margin data for a different wallet address');
        return state;
      }

      return {
        ...state,
        wallet: {
          ...state.wallet,
          margin_address: action.margin_address,
          margin_setup: action.margin_setup,
        }
      };
    }
    default: {
      return state;
    }
  }
};

export default reducer;
