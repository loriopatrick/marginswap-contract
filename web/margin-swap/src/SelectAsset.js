import './SelectAsset.scss';

import React, { Component } from 'react';
import { connect } from 'react-redux';

import ASSETS from 'assets';
import asset_images from 'asset_images';

class SelectAsset extends Component {
  selectAsset(symbol) {
    this.props.onSelect && this.props.onSelect(symbol);
  }

  render() {
    const asset_els = Object.keys(ASSETS).map(symbol => {
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

    return (
      <React.Fragment>
        <div className="spacer"></div>
        <div className="SelectAsset">
          <div className="title">{ this.props.title || 'Select an asset' }</div>
          { asset_els }
        </div>
      </React.Fragment>
    );
  }
}

export default connect(() => ({}))(SelectAsset);

