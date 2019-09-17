const dummy_services = [
  {
    id: 1,
    name: 'Netflix',
    monthly_rate: '9.99',
    styles: {
      backgroundColor: 'red',
    }
  },
  {
    id: 2,
    name: 'Spotify',
    monthly_rate: '7.99',
    styles: {
      backgroundColor: 'green',
    }
  },
];

export function loadServices() {
  return (dispatch, getState) => {
    setTimeout(() => {
      dispatch({ type: 'services-set', services: dummy_services });
    }, 1000);
  };
}

export function selectService(id) {
  return {
    type: 'services-toggle',
    id,
  };
}

export function walletConnect() {
  return { type: 'wallet-connect' };
}

export function walletAuthenticate() {
  return { type: 'wallet-authenticate' };
}

export function setEmail(email) {
  return { type: 'account-set-email', email };
}

export function saveEmail() {
  return { type: 'account-save-email' };
}
