import React, { Component } from 'react';
import logo from './logo.svg';
import './App.scss';

import WalletStatus from 'Components/WalletStatus';

import eth_img from 'img/assets/eth.svg';
import assets from 'assets';

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

  render() {
    const dict = {
      input: {
        title: 'Send',
        value: this.state.input_amount,
        placeholder: '0.0',
        cls: 'input',
      },
      output: {
        title: 'Receive',
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
            <div className="btn red">trade</div>

            <div className="positions">
              { position() }
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
