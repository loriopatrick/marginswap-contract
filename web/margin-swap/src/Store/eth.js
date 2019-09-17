export function personalSign(eth, message) {
  const address = eth.selectedAddress;

  return new Promise((resolve, reject) => {
    eth.sendAsync({
      method: 'personal_sign',
      params: [
        new Buffer(message, 'utf-8').toString('hex'),
        address,
        null
      ],
      from: address,
    }, (err, res) => {
      if (err) {
        return reject(err);
      }
      resolve(res.result);
    });
  })
}
