const Big = require('big.js');

const CACHE = {};

function viewCache(key, state, fn) {
  let line = CACHE[key]
  if (line) {
    if (line.state === state) {
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
  return viewCache('wallet', state.wallet, wallet => ({
    has_web3: wallet.has_web3,
    address: wallet.address,
    connected: !!wallet.address,
    processing: wallet.processing,
    margin_address: wallet.margin_address,
    margin_setup: wallet.margin_setup,
    loading: wallet.address && !wallet.margin_address,
  }));
}

export function balancesView(state) {
  return viewCache('balances', state.balances, balances => {
    const mapped = {};
    Object.keys(balances).forEach(symbol => {
      const b = balances[symbol];

      mapped[symbol] = {
        net_balance: Big(b.compound_balance).sub(b.borrow_balance).toFixed(b.decimals),
      };
    });

    return mapped;
  });
}
