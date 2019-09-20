import { createStore, applyMiddleware } from 'redux'
import thunk from 'redux-thunk';

import EthManager from './eth';
import reducer from './reducer';

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
  return next => {
    const eth_manger = new EthManager(store);

    return action => {
      eth_manger && eth_manger.handle(action);
      const before_state = store.getState();
      next(action);
      eth_manger && eth_manger.postHandle(action, before_state);
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
