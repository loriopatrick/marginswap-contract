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

export function walletView(state) {
  return viewCache('wallet', [ state.wallet ], ([ wallet ]) => ({
    has_web3: wallet.has_web3,
    address: wallet.address,
    connected: !!wallet.address,
    processing: wallet.processing,
    margin_address: wallet.margin_address,
    margin_setup: wallet.margin_setup,
    loading: wallet.address && !wallet.margin_address,
  }));
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
        } = assets[symbol];

        const pay = Big(borrow_balance).mul(borrow_rate);
        const receive = Big(compound_balance).mul(supply_rate);

        const value = Big(compound_balance).sub(borrow_balance).mul(eth_price);

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
          amount: net_borrow.toFixed(2),
          rate: year_revenue.div(net_borrow).mul(100).toFixed(2),
        };
      }

      return {
        loading: false,
        paying: false,
        amount: net_supply.toFixed(2),
        rate: year_revenue.div(net_supply).mul(100).toFixed(2),
      };
    });
}
