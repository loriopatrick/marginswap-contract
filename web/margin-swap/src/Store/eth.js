import Eth from 'ethjs';
import EthContract from 'ethjs-contract';

const MARGIN_PARENT_ABI = require('abi/MarginParent.json');
const MARGIN_ABI = require('abi/MarginSwap.json');
const ERC20_ABI = require('abi/ERC20.json');
const CTOKEN_ABI = require('abi/cToken.json');

// const MARGIN_PARENT_ADDRESS = '0x9e667a53dd12155fbffe061527d0395b39f27ecc'; // mainnet
const MARGIN_PARENT_ADDRESS = '0xfe84e8d7e87a179704f8711473fce5ba84b10522'; // ropsten


const Big = require('big.js');

const ASSETS = require('assets');

export default class EthManager {
  constructor(store) {
    this._store = store;

    const ethereum = this._ethereum = window.ethereum;
    const has_web3 = typeof ethereum !== 'undefined';

    this._erc20_assets = {};
    this._ctoken_assets = {};

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

  _erc20(address) {
    const c = this._erc20_assets[address];
    if (c) {
      return c;
    }
    return this._erc20_assets[address] = this._contract(ERC20_ABI).at(address);
  }

  _cToken(address) {
    const c = this._ctoken_assets[address];
    if (c) {
      return c;
    }
    return this._ctoken_assets[address] = this._contract(CTOKEN_ABI).at(address);
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

      case 'trigger-enter-markets': {
        console.log('here');
        this._run('Enter markets', address => this._margin.enterMarkets(
          Object.keys(ASSETS).map(s => ASSETS[s]).map(a => a.compound_address),
          { from: address }
        ));
        break;
      }

      case 'trigger-margin-deposit': {
        let { symbol, amount } = action;
        const cToken = this._cToken(ASSETS[symbol].compound_address);

        let wei_amount = '0';
        let asset_address = null;

        cToken.underlying().then(_asset_address => {
          asset_address = _asset_address[0];

          if (symbol === 'ETH') {
            asset_address = '0x0000000000000000000000000000000000000000';
            amount = Big(amount).mul(Big(10).pow(18)).toFixed(0);
            wei_amount = amount;
          }
          else {
            return this._erc20(asset_address).decimals().then(res => {
              amount = Big(amount).mul(Big(10).pow(+res[0])).toFixed(0);
            });
          }
        }).then(() => {
          return this._margin.deposit(asset_address, amount,
            { from: this._store.getState().wallet.address, value: wei_amount }
          );
        }).then(res => {
          console.log(res);
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

          console.log(res.margin_contract);

          this._margin = this._contract(MARGIN_ABI).at(res.margin_contract);
          this._comptroller = this._contract(MARGIN_ABI).at('0x3d9819210a31b4961b30ef54be2aed79b9c9cd3b');
        });
        break;
      }
    }
  }
}
