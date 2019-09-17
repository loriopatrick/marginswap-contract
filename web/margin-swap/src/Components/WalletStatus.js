import React from 'react';
import './WalletStatus.scss';

import { connect } from 'react-redux';

import {
  walletConnect,
  walletAuthenticate,
} from 'Store/actions';

const WalletStatus = ({ wallet, dispatch }) => {
  const cls = ['WalletStatus', 'btn'];
  let msg = null;

  let action = undefined;
  let link_to = null;

  if (!wallet.connected) {
    msg = 'Connect Wallet'
    cls.push('disconnected');
    cls.push('red');

    action = () => dispatch(walletConnect());
  }
  else {
    msg = 'Connected: ' + wallet.address;
    cls.push('connected');
    cls.push('green');
  }

  return (
    <div className={cls.join(' ')} onClick={action}>
      <div className="indicator"></div>
      <div className="status">{ msg }</div>
    </div>
  );
}

export default connect(state => ({
  wallet: state.wallet,
}))(WalletStatus);

