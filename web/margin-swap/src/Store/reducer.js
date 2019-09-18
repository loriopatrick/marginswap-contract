const INIT_STATE = {
  wallet: {
    has_web3: false,
    address: null,
    processing: null,
    margin_address: null,
    margin_setup: false,
  },
};

const reducer = (state = INIT_STATE, action) => {
  switch (action.type) {
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
