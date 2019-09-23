import './SelectAsset.scss';

import React, { Component } from 'react';
import { connect } from 'react-redux';

import ASSETS from 'assets';
import asset_images from 'asset_images';

const PASS = () => true;

class SelectAsset extends Component {
  selectAsset(symbol) {
    this.props.onSelect && this.props.onSelect(symbol);
  }

  render() {
    const { assets } = this.props;

    const asset_els = assets.map(asset => {
      const { symbol } = asset;

      return (
        <div
          className={'option' + (this.props.selected === symbol ? ' active' : '')}
          key={symbol}
          onClick={this.selectAsset.bind(this, symbol)}
        >
          <img src={asset_images[symbol]} />
          { symbol }
        </div>
      );
    });

    let body = null;
    console.log(asset_els.length);
    if (asset_els.length) {
      body = (
        <React.Fragment>
          <div className="title">{ this.props.title || 'Select an asset' }</div>
          <div>
            { asset_els }
          </div>
        </React.Fragment>
      );
    }

    let post = null;
    if (this.props.children) {
      post = (
        <React.Fragment>
          { this.props.children }
        </React.Fragment>
      );
    }

    return (
      <div className="SelectAsset">
        { body }
        { post }
      </div>
    );
  }
}

export default SelectAsset;

