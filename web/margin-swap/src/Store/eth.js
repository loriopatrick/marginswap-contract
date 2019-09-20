import Eth from 'ethjs';
import EthABI from 'ethjs-abi';
import EthContract from 'ethjs-contract';
import tradeCalcKey from './trade_calc_key';

const MARGIN_PARENT_ABI = require('abi/MarginParent.json');
const MARGIN_ABI = require('abi/MarginSwap.json');
const COMPTROLLER_ABI = require('abi/Comptroller.json');
const ERC20_ABI = require('abi/ERC20.json');
const CTOKEN_ABI = require('abi/cToken.json');
const ORACLE_ABI = require('abi/PriceOracle.json');
const UNISWAP_FACTORY_ABI = require('abi/UniswapFactory.json');
const UNISWAP_EXCHANGE_ABI = require('abi/UniswapExchange.json');

const MARGIN_PARENT_ADDRESS = '0x520ac2c0001f8be421951227b86ebd4e89a8c34c'; // mainnet
const COMPTROLLER_ADDRESS = '0x3d9819210a31b4961b30ef54be2aed79b9c9cd3b'; // mainnet

// const MARGIN_PARENT_ADDRESS = '0xe8eb3c65c2de1b7b7244abb691e92c19001c282c'; // ropsten
// const COMPTROLLER_ADDRESS = '0xb081cf57b1e422b3e627544ec95992cbe8eaf9cb'; // ropsten

const UNISWAP_FACTORY_ADDRESS = '0xc0a47dFe034B400B47bDaD5FecDa2621de6c4d95';

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
        this._comptroller = this._contract(COMPTROLLER_ABI).at(COMPTROLLER_ADDRESS);
        this._uniswap_factory = this._contract(UNISWAP_FACTORY_ABI).at(UNISWAP_FACTORY_ADDRESS);
        this._comptroller.oracle().then(r => r[0]).then(oracle_address => {
          if (oracle_address === '0x') {
            throw new Error('got invalid oracle_address');
          }
          this._oracle = this._contract(ORACLE_ABI).at(oracle_address);
          return this._refreshAssets();
        }).then(() => {
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

  _getAssetsIn() {
    const state = this._store.getState();
    const margin_address = state.wallet.margin_address;

    if (!margin_address) {
      return Promise.resolve({});
    }

    return this._comptroller.getAssetsIn(margin_address).then(res => res[0]).then(assets_in => {
      const assets_in_map = {};
      assets_in.forEach(asset => {
        assets_in_map[asset] = true;
      });

      return assets_in_map;
    });
  }

  _refreshAssets() {
    const new_assets = {};

    return this._getAssetsIn().then(assets_in_map => {
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
      case 'trigger-trade': {
        this._uniswapTradeData(action.data).catch(e => {});
        break;
      }
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

            if (res.enabled) {
              setTimeout(() => {
                this._refreshAssets()
                  .then(assets => this._refreshBalances(assets))
                  .then(() => this._refreshMargin());
              }, 100);
            }
          });
        });
        break;
      }
    }
  }

  postHandle(action, before_state) {
    if (action.type !== 'set-trade') {
      return;
    }

    const state = this._store.getState();

    const calculate_key = tradeCalcKey(state.trade);
    if (calculate_key === this._trade_calc_key) {
      return;
    }
    this._trade_calc_key = calculate_key;

    if (+state.trade.amount === 0) {
      return this._store.dispatch({
        type: 'trade-calculated',
        key: calculate_key,
        calculated: null,
      });
    }

    clearTimeout(this._trade_calc_tid);

    this._trade_calc_tid = setTimeout(() => {
      this._calculateTrade(state.trade).then(calculated => {
        this._store.dispatch({
          type: 'trade-calculated',
          key: calculate_key,
          calculated,
        });
      });
    }, 500);
  }

  _uniswapExactInput(input_reserve, output_reserve, input_amount) {
    const numerator = Big(input_amount).mul(output_reserve).mul(997);
    const denominator = Big(input_reserve).mul(1000).add(Big(input_amount).mul(997));
    Big.DP = 0;
    Big.RM = 0;
    return numerator.div(denominator);
  }

  _uniswapExactOutput(input_reserve, output_reserve, output_amount) {
    const numerator = Big(output_amount).mul(input_reserve).mul(1000);
    const denominator = Big(output_reserve).sub(output_amount).mul(997);

    Big.DP = 0;
    Big.RM = 0;
    return numerator.div(denominator).add(1);
  }

  _uniswapTradeData(trade) {
    if (!trade.calculated) {
      return Promise.reject(new Error('calcualted not loaded yet'));
    }

    const state = this._store.getState();

    const from_symbol = trade.from_asset;
    const to_symbol = trade.to_asset;

    const from = state.assets[from_symbol];
    const to = state.assets[to_symbol];

    /* todo, load current block and add delta */
    const block_number = 10000000000;

    return new Promise(resolve => resolve(block_number))
      .then(deadline => {

        let input = null;
        let min_output = null;

        if (trade.is_input_active) {
          input = Big(trade.amount).mul(Big(10).pow(from.decimals)).toFixed(0);
          min_output = Big(trade.calculated).mul('0.95').mul(Big(10).pow(to.decimals)).toFixed(0);
        }
        else {
          input = Big(trade.calculated).mul(Big(10).pow(to.decimals)).toFixed(0);
          min_output = Big(trade.amount).mul('0.95').mul(Big(10).pow(from.decimals)).toFixed(0);
        }

        if (trade.is_input_active) {
          if (from_symbol === 'ETH') {
            return {
              exchange_address: to.asset_address,
              fn_name: 'ethToTokenSwapInput',
              eth_value: input,
              fn_args: [
                min_output,
                deadline,
              ],
            };
          }

          if (to_symbol === 'ETH') {
            return {
              exchange_address: from.asset_address,
              fn_name: 'tokenToEthSwapInput',
              eth_value: '0',
              fn_args: [
                input,
                min_output,
                deadline,
              ],
            }
          }
          else {
            return {
              exchange_address: from.asset_address,
              fn_name: 'tokenToTokenSwapInput',
              eth_value: '0',
              fn_args: [
                input,
                min_output,
                /* min_eth_bought */ '0',
                deadline,
                to.asset_address,
              ],
            };
          }
        }
      })
      .then(details => {
        const fn_abi = UNISWAP_EXCHANGE_ABI.find(a => a.name === details.fn_name);
        const encoded = EthABI.encodeMethod(fn_abi, details.fn_args);

        return {
          trade_address: details.exchange_address,
          trade_data: encoded,
          eth_value: details.eth_value,
        };
      })
      .then(res => {
        console.log(res);
        return res;
      });
  }

  _uniswapGetReserve(symbol) {
    if (symbol === 'ETH') {
      return Promise.resolve(null);
    }

    const state = this._store.getState();
    const { asset_address } = state.assets[symbol];

    return this._uniswap_factory
      .getExchange(asset_address).then(r => r[0])
      .then(exchange_address => {
        if (exchange_address === '0x') {
          throw new Error('exchange does not exist for ' + symbol);
        }

        return Promise.all([
          this._eth.getBalance(exchange_address),
          this._erc20(asset_address).balanceOf(exchange_address).then(r => r[0]),
        ]).then(([eth_balance, token_balance]) => {
          return {
            exchange_address,
            eth_balance,
            token_balance,
          };
        });
      });
  }


  _calculateTrade(trade) {
    const state = this._store.getState();
    const from = state.assets[trade.from_asset];
    const to = state.assets[trade.to_asset];

    return Promise.all([
      this._uniswapGetReserve(trade.from_asset),
      this._uniswapGetReserve(trade.to_asset),
    ]).then(([from_reserve, to_reserve]) => {

      if (trade.is_input_active) {
        let eth_input = null;

        /* FROM => eth_input */
        if (from_reserve === null) {
          /* from asset is eth, so just copy amount */
          eth_input = Big(trade.amount).mul(Big(10).pow(18));
        }
        else {
          const input_amount = Big(trade.amount).mul(Big(10).pow(from.decimals));
          eth_input = this._uniswapExactInput(
            from_reserve.token_balance,
            from_reserve.eth_balance,
            input_amount
          );
        }

        /* eth_input => TO */
        if (to_reserve === null) {
          Big.DP = 18;
          return eth_input.div(Big(10).pow(Big.DP)).toFixed(Big.DP);
        }

        const output_amount = this._uniswapExactInput(
          to_reserve.eth_balance,
          to_reserve.token_balance,
          eth_input
        );

        Big.DP = to.decimals;
        return output_amount.div(Big(10).pow(Big.DP)).toFixed(Big.DP);
      }
      else {
        /* TO <= eth_output */
        let eth_output = null;

        if (to_reserve === null) {
          eth_output = Big(trade.amount).mul(Big(10).pow(18));
        }
        else {
          const output_amount = Big(trade.amount).mul(Big(10).pow(to.decimals));

          eth_output = this._uniswapExactOutput(
            to_reserve.eth_balance,
            to_reserve.token_balance,
            output_amount
          );
        }

        /* FROM => eth_output */
        if (from_reserve === null) {
          Big.DP = 18;
          return eth_output.div(Big(10).pow(Big.DP)).toFixed(Big.DP);
        }

        const input_amount = this._uniswapExactOutput(
          from_reserve.token_balance,
          from_reserve.eth_balance,
          eth_output
        );

        Big.DP = from.decimals;
        return input_amount.div(Big(10).pow(Big.DP)).toFixed(Big.DP);
      }
    });
  }
}
