import './SelectAsset.scss';
import './EnableAssets.scss';

import React, { Component } from 'react';
import { connect } from 'react-redux';

import ASSETS from 'assets';
import asset_images from 'asset_images';

import {
  enableAssets,
} from 'Store/actions';

class EnableAssets extends Component {
  constructor(props) {
    super(props);

    this.state = {};

    Object.keys(props.assets).forEach(symbol => {
      if (!props.assets[symbol].in_market) {
        this.state[symbol] = true;
      }
    });
  }

  toggleAsset(symbol) {
    if (this.props.assets[symbol].in_market) {
      return;
    }

    this.setState({
      [symbol]: !this.state[symbol],
    });
  }

  enable() {
    const assets = [];
    Object.keys(this.state).forEach(symbol => {
      if (this.state[symbol]) {
        assets.push(symbol);
      }
    });

    this.props.dispatch(enableAssets(assets));
  }

  render() {
    const { assets } = this.props;

    let selected_count = 0;

    const asset_els = Object.keys(ASSETS).map(symbol => {
      const a = assets[symbol];
      if (!a) {
        return null;
      }

      const cls = ['option'];
      if (a.in_market) {
        cls.push('active');
      }
      else if (this.state[symbol]) {
        cls.push('selected');
        selected_count++;
      }

      return (
        <div
          className={cls.join(' ')}
          key={symbol}
          onClick={this.toggleAsset.bind(this, symbol)}
        >
          <img src={asset_images[symbol]} />
          { symbol }
        </div>
      );
    });

    return (
      <div className="EnableAssets">
        <div className="SelectAsset">
          <div className="title">Select assets to enable for margin</div>
          { asset_els }
        </div>
        <div className="btn blue" onClick={this.enable.bind(this)}>
          Enable { selected_count } assets for margin.
        </div>
      </div>
    );
  }
}

export default connect(state => ({
  assets: state.assets,
}))(EnableAssets);


