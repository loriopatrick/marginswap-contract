import './Modal.scss';

import React, { Component } from 'react';

class Modal extends Component {
  componentDidMount() {
    const root_el = document.getElementById('root');
    root_el.classList.add('modal-open');
  }

  componentWillUnmount() {
    const root_el = document.getElementById('root');
    root_el.classList.remove('modal-open');
  }

  closeModal(evt) {
    if (evt.target !== this._ref) {
      return;
    }
    this.props.onClose && this.props.onClose();
  }

  render() {
    return (
      <div className="Modal" onClick={this.closeModal.bind(this)} ref={ref => this._ref = ref}>
        <div className="body">
          { this.props.children }
        </div>
      </div>
    );
  }
}

export default Modal;
