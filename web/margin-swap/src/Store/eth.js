import Eth from 'ethjs';
import EthContract from 'ethjs-contract';

const MARGIN_PARENT_ADDRESS = '0x9e667a53dd12155fbffe061527d0395b39f27ecc';
const MARGIN_PARENT_ABI = require('abi/MarginParent.json');

export default class EthManager {
  constructor(store) {
    this._store = store;

    const ethereum = this._ethereum = window.ethereum;
    const has_web3 = typeof ethereum !== 'undefined';

    setTimeout(() => {
      const { dispatch } = store;
      if (has_web3) {
        this._eth = new Eth(ethereum);
        this._contract = new EthContract(this._eth);
        this._margin_parent = this._contract(MARGIN_PARENT_ABI).at(MARGIN_PARENT_ADDRESS);

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
  }

  _run(type, promise_fn) {
    if (!this._ethereum) {
      return Promise.reject(new Error('ethereum not setup'));
    }

    const current = this._store.getState().wallet.processing;
    if (this._store.getState().wallet.processing) {
      return Promise.reject(new Error(current + ', currently processing'));
    }

    this._store.dispatch({ type: 'wallet-processing', processing: type });

    let _err;
    return promise_fn(this._store.getState().wallet.address)
      .catch(err => {
        console.error(err);
        _err = err;
      })
      .then(res => {
        this._store.dispatch({ type: 'wallet-processing', processing: null });
        if (_err) {
          throw _err;
        }
        return res;
      });
  }

  _waitForTx(run_promise) {
    // TODO: wait for tx to confirm
    return run_promise.then(res => {
      return res;
    });
  }

  _retry(fn) {
    return fn();
  }

  handle(action) {
    switch(action.type) {
      case 'trigger-wallet-connect': {
        this._run('Connecting', () => {
          return this._ethereum.enable();
        });
        break;
      }

      case 'trigger-margin-enable': {
        this._run('Enable margin', address => {
          return this._margin_parent.setupMargin({ from: address });
        });
        break;
      }

      case 'wallet-address': {
        this._retry(() => this._margin_parent.isMarginSetup(action.address)).then(res => {
          this._store.dispatch({
            type: 'wallet-margin',
            address: action.address,
            margin_address: res.margin_contract,
            margin_setup: res.enabled,
          });
          console.log(res);
        });
        break;
      }
    }
  }
}
