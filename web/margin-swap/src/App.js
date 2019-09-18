import './App.scss';

import React, { Component } from 'react';
import logo from './logo.svg';

import { connect } from 'react-redux';

import WalletStatus from 'Components/WalletStatus';

import {
  marginDeposit,
  enterMarkets,
} from 'Store/actions';

import assets from 'assets';
import bat_img from 'img/assets/bat.svg';
import dai_img from 'img/assets/dai.svg';
import eth_img from 'img/assets/eth.svg';
import rep_img from 'img/assets/rep.svg';
import usdc_img from 'img/assets/usdc.svg';
import wbtc_img from 'img/assets/wbtc.svg';

const asset_images = {
  BAT: bat_img,
  DAI: dai_img,
  ETH: eth_img,
  REP: rep_img,
  USDC: usdc_img,
  WBTC: wbtc_img,
};


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

    this.props.dispatch(marginDeposit(symbol, '0.01'));
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
              <img src={eth_img} /> ETH
            </div>
          </div>
        </div>
      );
    };

    let position = () => {
      return (
        <div className="position">
          <div className="from">
            1.321 <div className="asset"><img src={eth_img} /> ETH</div>
          </div>
          <div className="to">
            1.321 <div className="asset"><img src={eth_img} /> ETH</div>
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

    let account = asset => {
      return (
        <div className="account" key={asset.symbol}>
          <div className="asset"><img src={asset_images[asset.symbol]} /> {asset.symbol}</div>
          <div className="amount green">+1.32</div>
          <div className="actions">
            <div onClick={this.deposit.bind(this, asset.symbol)}>deposit</div>
            <div>withdraw</div>
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
              <div className="accounts">
                <div className="account">
                  <div className="title">Margin</div>
                  <div className="amount">$3,000</div>
                </div>
                <div className="account">
                  <div className="title">Interest</div>
                  <div className="amount red">Paying: 15% on $12,213</div>
                </div>
                { Object.keys(assets).map(k => assets[k]).map(account) }
              </div>
              <div className="positions">
                { position() }
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }
}

export default connect(() => {})(App);
