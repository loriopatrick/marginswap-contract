import './App.scss';

import React, { Component } from 'react';
import { connect } from 'react-redux';

import WalletStatus from 'Components/WalletStatus';
import Spinner from 'Components/Spinner';
import Modal from './Modal';
import SelectAsset from './SelectAsset';

import {
  marginDeposit,
  enterMarkets,
} from 'Store/actions';

import {
  balancesView,
  marginView,
  interestView,
  tradeView,
} from 'Store/view';

import assets from 'assets';
import asset_images from './asset_images';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      select_asset_modal: false,
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

    let modal = null;
    if (this.state.select_asset_modal) {
      const is_input = this.state.select_asset_is_input;
      const { trade } = this.props;
      const asset_symbol = is_input ? trade.from_asset : trade.to_asset;

      modal = (
        <Modal onClose={() => this.setState({ select_asset_modal: false })} >
          <SelectAsset
            title={is_input ? 'Select input asset' : 'Select output asset'}
            selected={asset_symbol}
            onSelect={this.selectAsset.bind(this, is_input)}
          />
        </Modal>
      );
    }

    return (
      <div className="App">
        { modal }
        <div className="container">
          <header className="header">
            <span className="title">Margin Swap</span>
            <WalletStatus />
          </header>
          <div className="body">
            <div className="trade">
              { this.renderInput(true) }
              { this.renderInput(false) }
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

  selectAssetModal(is_input) {
    this.setState({
      select_asset_modal: true,
      select_asset_is_input: is_input,
    });
  }

  selectAsset(is_input, symbol) {
    this.setState({
      select_asset_modal: false,
    });

    this.props.dispatch({
      type: 'set-trade',
      data: {
        [ is_input ? 'from_asset' : 'to_asset' ]: symbol,
      },
    });
  }

  renderInput(is_input) {
    const { trade, dispatch } = this.props;

    const id = 'input-' + is_input;
    const title = is_input ? 'Input' : 'Output';
    const asset_symbol = is_input ? trade.from_asset : trade.to_asset;

    const set = fn => e => dispatch({ type: 'set-trade', data: fn(e) });

    let max_input = null;
    if (is_input && !trade.loading) {
      max_input = (
        <div className="max-input"
          onClick={set(() => ({ is_input_active: true, amount: trade.max_input }))}>
          max: { trade.max_input }
        </div>
      );
    }

    return (
      <div className={'input' + (is_input !== trade.is_input_active ? ' auto' : '')}>
        <div className="amount">
          <label className="title" htmlFor={id}>{title}</label>
          <input type="text" id={id}
            placeholder={"0.0"}
            onFocus={set(() => ({ is_input_active: is_input }))}
            onChange={set(e => ({ amount: e.target.value }))}
            value={trade.amount}
          />
          { max_input }
          <div className="asset-select" onClick={this.selectAssetModal.bind(this, is_input)}>
            <img src={asset_images[asset_symbol]} /> { asset_symbol }
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
  trade: tradeView(state),
}))(App);
