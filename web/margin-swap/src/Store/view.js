const Big = require('big.js');
const ASSETS = require('assets');
const ASSET_KEYS = Object.keys(ASSETS);

const CACHE = {};

function viewCache(key, state, fn) {
  let line = CACHE[key]
  if (line) {
    let i;
    for (i = 0; i < line.state.length; ++i) {
      if (line.state[i] !== state[i]) {
        break;
      }
    }

    if (i === line.state.length) {
      return line.result;
    }
  }

  if (!line) {
    line = { };
    CACHE[key] = line;
  }

  line.state = state;
  line.result = fn(state);
  return line.result;
}

export function assetsView(state) {
  return viewCache('assets', [ state.assets ], ([ assets ]) => {
    return Object.keys(assets).map(s => assets[s]);
  });
}

export function walletView(state) {
  return viewCache('wallet', [ state.wallet ], ([ wallet ]) => {
    if (wallet.loading) {
      return {
        processing: 'Loading',
      };
    }

    return {
      has_web3: wallet.has_web3,
      address: wallet.address,
      connected: !!wallet.address,
      processing: wallet.processing,
      margin_address: wallet.margin_address,
      margin_setup: wallet.margin_setup,
      loading: wallet.address && !wallet.margin_address,
    };
  });
}

export function isReadyView(state) {
  return state.wallet.address && state.wallet.margin_setup;
}

function balances_loading(balances, assets) {
  const balance_keys = Object.keys(balances);
  const asset_keys = Object.keys(assets);
  return balance_keys.length != asset_keys.length || asset_keys.length !== ASSET_KEYS.length;
}

export function balancesView(state) {
  return viewCache('balances',
    [ state.balances, state.assets, state.eth_price ],
    ([ balances, assets, eth_price ]) => {
      const items = [];

      if (balances_loading(balances, assets)) {
        return {
          loading: true,
          items,
        };
      }

      Object.keys(balances).forEach(symbol => {
        const b = balances[symbol];
        const a = assets[symbol];
        const net_balance = Big(b.compound_balance).sub(b.borrow_balance).toFixed(a.decimals);

        items.push({
          symbol,
          net_balance: net_balance,
          net_value: Big(net_balance).mul(assets[symbol].asset_price).mul(eth_price).toFixed(2),
          in_market: a.in_market,
        });
      });

      items.sort((a, b) => +b.net_value - +a.net_value);

      return {
        loading: false,
        items,
      };
    });
}

export function marginView(state) {
  return viewCache('margin',
    [ state.liquidity, state.eth_price ],
    ([ liquidity, eth_price ]) => {
      if (liquidity === null) {
        return {
          loading: true,
        };
      }

      return {
        loading: false,
        value: Big(liquidity).mul(state.eth_price).toFixed(2),
        eth_value: liquidity,
      };
    });
}

export function interestView(state) {
  return viewCache('interest',
    [ state.assets, state.balances, state.eth_price ],
    ([ assets, balances, eth_price]) => {
      if (balances_loading(balances, assets)) {
        return {
          loading: true,
          paying: false,
          amount: '0',
          rate: '0',
        };
      }

      let year_revenue = Big(0);
      let net_borrow = Big(0);
      let net_supply = Big(0);

      Object.keys(balances).forEach(symbol => {
        const {
          borrow_balance,
          compound_balance,
        } = balances[symbol];

        const {
          supply_rate,
          borrow_rate,
          asset_price,
        } = assets[symbol];

        const pay = Big(borrow_balance).mul(borrow_rate).mul(asset_price);
        const receive = Big(compound_balance).mul(supply_rate).mul(asset_price);
        const value = Big(compound_balance).sub(borrow_balance).mul(asset_price);

        if (value.gt(0)) {
          net_supply = net_supply.add(value);
        }
        else {
          net_borrow = net_borrow.add(value);
        }

        year_revenue = year_revenue.add(receive).sub(pay);
      });

      if (year_revenue.lt(0)) {
        Big.DP = 18;
        return {
          loading: false,
          paying: true,
          amount: net_borrow.mul(eth_price).toFixed(2),
          rate: year_revenue.div(net_borrow).mul(100).toFixed(2),
        };
      }

      return {
        loading: false,
        paying: false,
        amount: net_supply.mul(eth_price).toFixed(2),
        rate: net_supply.eq(0) ? '0.00' : year_revenue.div(net_supply).mul(100).toFixed(2),
      };
    });
}

export function tradeView(state) {
  const { from_asset, to_asset } = state.trade;

  return viewCache('trade',
    [ state.trade, state.assets[from_asset], state.assets[to_asset], state.liquidity ],
    ([ trade, from, to, liquidity ]) => {

      if (!from || !to || !liquidity) {
        return {
          loading: true,
          input: {
            symbol: from_asset,
            value: '',
            placeholder: '0.0',
            loading: false,
          },
          output: {
            symbol: to_asset,
            value: '',
            placeholder: '0.0',
            loading: false,
          },
        };
      }

      if (!to.in_market) {
        let input_value = '';
        if (trade.is_input_active) {
          input_value = trade.amount;
        }

        return {
          loading: false,
          input: {
            symbol: from_asset,
            value: input_value,
            placeholder: '0.0',
            loading: false,
          },
          output: {
            symbol: '-',
            value: '',
            placeholder: '0.0',
            loading: false,
          },
          is_input_active: trade.is_input_active,
        };
      }

      /*
       * OUTPUT * O_eth_value * O_collateral_factor + liquidity - INPUT * I_eth_value = 0
       *
       * SLIP = 0.9
       *
       * OUTPUT = INPUT * OUT/IN * SLIP
       *
       * X * O_eth_value (ETH/OUT) = ETH
       *
       * O_eth_value (ETH/OUT)
       * I_eth_value (ETH/IN)
       *
       * OUT/IN => I_eth_value / O_eth_value
       * OUTPUT = INPUT * I_eth_value / O_eth_value * SLIP
       *
       * (INPUT * I_eth_value / O_eth_value * SLIP) * O_eth_value * O_collateral_factor
       *    + liquidity - INPUT * I_eth_value = 0
       *
       *
       * (INPUT * I_eth_value * SLIP) * O_collateral_factor
       *    + liquidity - INPUT * I_eth_value = 0
       *
       *
       * INPUT * I_eth_value - (INPUT * I_eth_value * SLIP) * O_collateral_factor
       *    = liquidity
       *
       *
       * INPUT * I_eth_value * (1 - SLIP * O_collateral_factor) = liquidity
       *
       * INPUT = liquidity / (I_eth_value * (1 - SLIP * O_collateral_factor))
       */

      Big.DP = 18;
      let max_input = Big(liquidity).div(
        Big(from.asset_price).mul(Big(1).sub(Big(0.9).mul(to.collateral_factor)))
      );

      if (max_input.gt(from.margin_parent_balance)) {
        max_input = Big(from.margin_parent_balance);
      }

      Big.RM = 0;
      Big.NE = -10000000000000;
      Big.PE = 10000000000000;
      max_input = max_input.toPrecision(5);

      const res = {
        from_asset,
        to_asset,
        is_input_active: trade.is_input_active,
      };

      if (trade.is_input_active) {
        res.input = {
          symbol: from_asset,
          value: trade.amount,
          placeholder: '0.0',
        };

        res.output = {
          symbol: to_asset,
          value: '',
          placeholder: +trade.amount && !trade.calculated ? 'loading' : (trade.calculated || '0.0'),
        };
      }
      else {
        res.output = {
          symbol: to_asset,
          value: trade.amount,
          placeholder: '0.0',
        };

        res.input = {
          symbol: from_asset,
          value: '',
          placeholder: +trade.amount && !trade.calculated ? 'loading' : (trade.calculated || '0.0'),
        };
      }

      res.input.max = max_input;
      res.input.max_selected = !Big(+res.input.value ? res.input.value : '0').lt(max_input);

      return res;
    });
}
