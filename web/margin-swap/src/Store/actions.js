export function walletConnect() {
  return { type: 'trigger-wallet-connect' };
}

export function enableMargin() {
  return { type: 'trigger-margin-enable' };
}

export function enterMarkets() {
  return { type: 'trigger-enter-markets' };
}

export function marginDeposit(symbol, amount) {
  return { type: 'trigger-margin-deposit', symbol, amount };
}
