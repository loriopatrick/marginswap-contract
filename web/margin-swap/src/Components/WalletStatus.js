import React from 'react';
import './WalletStatus.scss';

import { connect } from 'react-redux';
import { walletView } from 'Store/view';

import {
  walletConnect,
  enableMargin,
} from 'Store/actions';

import Spinner from 'Components/Spinner';

const WalletStatus = ({ wallet, dispatch }) => {
  const cls = ['WalletStatus', 'btn'];
  let msg = null;

  let action = undefined;
  let link_to = null;

  if (wallet.loading || wallet.processing) {
    msg = <React.Fragment><Spinner /> { wallet.processing || 'Loading' }</React.Fragment>;
    cls.push('loading');
  }
  else if (!wallet.connected) {
    msg = 'Connect Wallet'
    cls.push('disconnected');
    cls.push('red');

    action = () => dispatch(walletConnect());
  }
  else if (!wallet.margin_setup) {
    msg = 'Margin not enabled'
    cls.push('yellow', 'warn');

    action = () => dispatch(enableMargin());
  }
  else {
    msg = 'Connected: ' + wallet.margin_address;
    cls.push('connected');
    cls.push('green');

    action = () => {
      window.open('https://etherscan.io/address/' + wallet.margin_address);
    };
  }

  return (
    <div className={cls.join(' ')} onClick={action}>
      <div className="indicator"></div>
      <div className="status">{ msg }</div>
    </div>
  );
}

export default connect(state => ({
  wallet: walletView(state),
}))(WalletStatus);

