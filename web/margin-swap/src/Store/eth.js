import Eth from 'ethjs';
import EthContract from 'ethjs-contract';

const MARGIN_PARENT_ABI = require('abi/MarginParent.json');
const MARGIN_ABI = require('abi/MarginSwap.json');
const COMPTROLLER_ABI = require('abi/Comptroller.json');
const ERC20_ABI = require('abi/ERC20.json');
const CTOKEN_ABI = require('abi/cToken.json');
const ORACLE_ABI = require('abi/PriceOracle.json');

// const MARGIN_PARENT_ADDRESS = '0x9e667a53dd12155fbffe061527d0395b39f27ecc'; // mainnet
const MARGIN_PARENT_ADDRESS = '0xe8eb3c65c2de1b7b7244abb691e92c19001c282c'; // ropsten
// const COMPTROLLER_ADDRESS = '0x3d9819210a31b4961b30ef54be2aed79b9c9cd3b'; // mainnet
const COMPTROLLER_ADDRESS = '0xb081cf57b1e422b3e627544ec95992cbe8eaf9cb'; // ropsten


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

  _refreshMargin() {
    const state = this._store.getState();
    const margin_address = state.wallet.margin_address;

    console.log(this._comptroller.getAccountLiquidity);
    return this._comptroller.getAccountLiquidity(margin_address)
      .then(res => {
        const err = +res[0];
        let liquidity = res[1];
        let shortfall = res[2];

        if (err) {
          const e = new Error('failed to get account liquidity');
          e.code = err;
          throw e;
        }

        liquidity = Big(liquidity);
        shortfall = Big(shortfall);

        if (liquidity.eq(0)) {
          return Big(0).sub(shortfall);
        }

        return liquidity;
      })
      .then(liquidity => {
        Big.DP = 18;
        liquidity = liquidity.div(Big(10).pow(Big.DP)).toFixed(Big.DP);
        
        this._store.dispatch({
          type: 'set-liquidity',
          liquidity,
        });
      });
  }

  _refreshAssets() {
    const state = this._store.getState();
    const margin_address = state.wallet.margin_address;
    const new_assets = {};

    return this._comptroller.getAssetsIn(margin_address).then(res => res[0]).then(assets_in => {
      const assets_in_map = {};
      assets_in.forEach(asset => {
        assets_in_map[asset] = true;
      });

      const loads = Object.keys(ASSETS).map(symbol => {
        const compound_address = ASSETS[symbol].compound_address;
        const cToken = this._cToken(compound_address);

        let asset_address;
        let asset_price;
        let supply_rate;
        let borrow_rate;
        let collateral_factor;
        let decimals;

        return cToken.underlying().then(r => r[0])
          .then(_asset_address => {
            asset_address = _asset_address;

            if (symbol === 'ETH') {
              return 18;
            }

            return this._erc20(asset_address).decimals().then(r => r[0]);
          })
          .then(_decimals => {
            decimals = +_decimals;
            return this._oracle.getUnderlyingPrice(compound_address).then(r => r[0]);
          })
          .then(_asset_price => {
            asset_price = _asset_price;
            return cToken.borrowRatePerBlock().then(r => r[0]);
          })
          .then(_borrow_rate => {
            borrow_rate = _borrow_rate;
            return cToken.supplyRatePerBlock().then(r => r[0]);
          })
          .then(_supply_rate => {
            supply_rate = _supply_rate;
            return this._comptroller.markets(compound_address).then(r => r[1]);
          })
          .then(_collateral_factor => {
            collateral_factor = _collateral_factor;
            return null;
          })
          .then(() => {
            Big.DP = 18;
            console.log(symbol,asset_price + '');
            asset_price = Big(asset_price).div(Big(10).pow(Big.DP + 18 - decimals)).toFixed(Big.DP);

            Big.DP = 18;
            supply_rate = Big(supply_rate).mul(2102400)
              .div(Big(10).pow(Big.DP)).toFixed(Big.DP);

            Big.DP = 18;
            borrow_rate = Big(borrow_rate).mul(2102400)
              .div(Big(10).pow(Big.DP)).toFixed(Big.DP);

            Big.DP = 18;
            collateral_factor = Big(collateral_factor)
              .div(Big(10).pow(Big.DP)).toFixed(Big.DP);

            new_assets[symbol] = {
              in_market: !!assets_in_map[compound_address],
              compound_address,
              asset_address,
              asset_price,
              supply_rate,
              borrow_rate,
              collateral_factor,
              decimals,
            };
          });
      });

      return Promise.all(loads).then(() => {
        this._store.dispatch({
          type: 'set-assets',
          assets: new_assets,
        });

        return new_assets;
      });
    });
  }

  _refreshBalances(assets) {
    const state = this._store.getState();
    const margin_address = state.wallet.margin_address;

    const new_balances = {};

    const loads = Object.keys(assets).map(symbol => {
      const asset = assets[symbol];
      const cToken = this._cToken(asset.compound_address);

      let compound_balance;
      let compound_rate;
      let borrow_balance;

      return cToken.balanceOf(margin_address).then(r => r[0])
        .then(_compound_balance => {
          compound_balance = _compound_balance;
          return cToken.exchangeRateStored().then(r => r[0]);
        })
        .then(_compound_rate => {
          compound_rate = _compound_rate;
          return cToken.borrowBalanceStored(margin_address).then(r => r[0]);
        })
        .then(_borrow_balance => {
          borrow_balance = _borrow_balance;
          if (symbol === 'ETH') {
            return Big(10).pow(18).toFixed(0);
          }
          return null;
        })
        .then(() => {
          Big.DP = asset.decimals;
          borrow_balance = Big(borrow_balance).div(Big(10).pow(Big.DP)).toFixed(Big.DP);

          Big.DP = asset.decimals;
          compound_balance = Big(compound_balance).mul(compound_rate)
            .div(Big(10).pow(Big.DP + 18)).toFixed(Big.DP);

          new_balances[symbol] = {
            borrow_balance,
            compound_balance,
          };
        });
    });

    return Promise.all(loads).then(() => {
      this._store.dispatch({
        type: 'set-balances',
        balances: new_balances,
      });
      console.log(new_balances);
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

          this._margin = this._contract(MARGIN_ABI).at(res.margin_contract);
          this._comptroller = this._contract(COMPTROLLER_ABI).at(COMPTROLLER_ADDRESS);

          this._comptroller.oracle().then(r => r[0]).then(oracle_address => {
            this._oracle = this._contract(ORACLE_ABI).at(oracle_address);

            setTimeout(() => {
              this._refreshAssets()
                .then(assets => this._refreshBalances(assets))
                .then(() => this._refreshMargin());
            }, 100);
          });
        });
        break;
      }
    }
  }
}
