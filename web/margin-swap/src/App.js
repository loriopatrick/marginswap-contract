import './App.scss';

import React, { Component } from 'react';
import { connect } from 'react-redux';

import WalletStatus from 'Components/WalletStatus';
import Spinner from 'Components/Spinner';
import Modal from './Modal';

import {
  marginDeposit,
  enterMarkets,
} from 'Store/actions';

import {
  balancesView,
  marginView,
  interestView,
} from 'Store/view';

import assets from 'assets';
import asset_images from './asset_images';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      active_side: 'input',
      input_amount: '',
      output_amount: '',
      input_asset: '',
      output_asset: '',
      value_loading: true,
    };
  }

  selectSide(side) {
    this.setState({ active_side: side });
  }

  updateInput(side, event) {
    const value = event.target.value;
  }

  deposit(symbol) {
    const asset = assets[symbol];
    console.log(asset);

    this.props.dispatch(marginDeposit(symbol, '0.1'));
  }

  render() {
    const dict = {
      input: {
        title: 'Input',
        value: this.state.input_amount,
        placeholder: '0.0',
        cls: 'input',
      },
      output: {
        title: 'Output',
        value: this.state.output_amount,
        placeholder: '0.0',
        cls: 'output',
      }
    };

    if (this.state.active_side === 'input') {
      dict.output.cls += ' auto';

      if (this.state.value_loading) {
        dict.output.placeholder = 'loading...';
      }
    }
    else {
      dict.input.cls += ' auto';

      if (this.state.value_loading) {
        dict.input.placeholder = 'loading...';
      }
    }

    let side = side => {
      return (
        <div className={side + (side !== this.state.active_side ? ' auto' : '')}>
          <div className="amount">
            <label className="title" htmlFor={side}>{dict[side].title}</label>
            <input type="text" id={side}
              placeholder={dict[side].placeholder}
              onFocus={this.selectSide.bind(this, side)}
              onChange={this.updateInput.bind(this, side)}
              value={dict[side].value}
            />
            <div className="asset-select">
              <img src={asset_images.ETH} /> ETH
            </div>
          </div>
        </div>
      );
    };

    let position = () => {
      return (
        <div className="position">
          <div className="from">
            1.321 <div className="asset"><img src={asset_images.ETH} /> ETH</div>
          </div>
          <div className="to">
            1.321 <div className="asset"><img src={asset_images.ETH} /> ETH</div>
          </div>
          <div className="profit">
            + 2.3%
          </div>
          <div className="state">
            unrealized
          </div>
        </div>
      );
    };

    return (
      <div className="App">
        <div className="container">
          <header className="header">
            <span className="title">Margin Swap</span>
            <WalletStatus />
          </header>
          <div className="body">
            <div className="trade">
              { side('input') }
              { side('output') }
            </div>
            <div className="btn red" onClick={() => this.props.dispatch(enterMarkets())}>trade</div>
            <div className="data">
              { this.renderAccounts() }
              <div className="positions">
                { position() }
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }

  renderAccounts() {
    const { balances, margin, interest } = this.props;

    const amount_cls = up => {
      return 'amount' + (up ? ' green' : ' red');
    };

    const accountEl = asset => {
      if (!asset.in_market) {
      return (
        <div className="account" key={asset.symbol}>
          <div className="asset"><img src={asset_images[asset.symbol]} /> {asset.symbol}</div>
          <div className="amount">
          </div>
          <div className="actions">
            <div>enable</div>
          </div>
        </div>
      );
      }

      return (
        <div className="account" key={asset.symbol}>
          <div className="asset"><img src={asset_images[asset.symbol]} /> {asset.symbol}</div>
          <div className={amount_cls(+asset.net_balance >= 0)}>
            ${ asset.net_value }
          </div>
          <div className="actions">
            <div onClick={this.deposit.bind(this, asset.symbol)}>deposit</div>
            <div>withdraw</div>
          </div>
        </div>
      );
    };

    let accounts_el = null;
    if (balances.loading) {
      accounts_el = <Spinner />;
    }
    else {
      accounts_el = balances.items.map(accountEl);
      console.log(balances.items);
    }

    let margin_el = null;
    if (margin.loading) {
      margin_el = (
        <div className="account">
          <div className="title">Margin</div>
          <div className="amount"><Spinner /></div>
        </div>
      );
    }
    else {
      margin_el = (
        <div className="account">
          <div className="title">Margin</div>
          <div className={amount_cls(+margin.value >= 0)}>${ margin.value }</div>
        </div>
      );
    }

    let interest_el = null;
    if (interest.loading) {
      interest_el = (
        <div className="account">
          <div className="title">Interest</div>
          <div className="amount"><Spinner /></div>
        </div>
      );
    }
    else {
      interest_el = (
        <div className="account">
          <div className="title">Interest</div>
          <div className={amount_cls(!interest.paying)}>
          { interest.paying ? 'Paying: ' : 'Receiving: ' }
          { interest.rate }% on ${ interest.amount }
          </div>
        </div>
      );
    }

    return (
      <div className="accounts">
        { margin_el }
        { interest_el }
        { accounts_el }
      </div>
    );
  }
}

export default connect(state => ({
  balances: balancesView(state),
  margin: marginView(state),
  interest: interestView(state),
}))(App);
