export default function tradeCalcKey(trade) {
  return [
      trade.from_asset,
      trade.to_asset,
      trade.amount,
      trade.is_input_active,
  ].join('-');
}

