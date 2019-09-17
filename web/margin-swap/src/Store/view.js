export function accountLoading(state) {
  return state.wallet.processing === 'auth' || state.account.loading;
}
