import { createStore, applyMiddleware } from 'redux'
import thunk from 'redux-thunk';

const INIT_STATE = {
  wallet: {
    has_web3: false,
    connected: false,
    address: null,
    processing: null,
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
          connected: !!action.address,
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
    default: {
      return state;
    }
  }
};

/* setting up */
 /*
  * Load margin contract details from parent (is deployed, address)
  * Load all asset prices from oracle (cAsset => comptroller => oracle => price)
  * Attempt different calls to Uniswap with varying min outputs till it works
  * Load all events on margin contract
  * Load all liquidation events from margin contract
  * Get margin contract state
  */

const web3_middle = store => {
  const { dispatch } = store;

  const getAddress = () => store.getState().wallet.address;

  return next => {
    const ethereum = window.ethereum;
    const has_web3 = typeof ethereum !== 'undefined';

    setTimeout(() => {
      if (has_web3) {
        dispatch({ type: 'wallet-has3' });

        if (ethereum.selectedAddress) {
          dispatch({
            type: 'wallet-address',
            address: ethereum.selectedAddress,
          });
        }

        ethereum.on('accountsChanged', accounts => {
          dispatch({
            type: 'wallet-address',
            address: accounts[0],
          });
        });
      }
    }, 0);

    return action => {
      switch(action.type) {
        case 'wallet-connect': {
          if (ethereum) {
            dispatch({ type: 'wallet-processing', processing: 'connect' });

            ethereum.enable().catch(() => {}).then(() => {
              dispatch({ type: 'wallet-processing', processing: null });
            });
          }
          break;
        }
        case 'wallet-address': {
          const address = action.address;
          break;
        }
      }

      next(action);
    };
  };
};

const middleware = applyMiddleware(
  thunk,
  web3_middle,
);

const store = createStore(
  reducer,
  middleware
);

export default store;
