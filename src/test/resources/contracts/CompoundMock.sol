pragma solidity ^0.5.7;

contract CompoundMock {
    event Transfer(address indexed _from, address indexed _to, uint256 _value);
    event Approval(address indexed _owner, address indexed _spender, uint256 _value);

    uint256 constant private MAX_UINT256 = 2 ** 256 - 1;
    mapping(address => uint256) public balances;
    mapping(address => mapping(address => uint256)) public allowed;
    mapping(address => uint256) public borrowBalanceCurrent;
    mapping(address => uint256) public balanceOfUnderlying;

    uint256 public totalSupply;
    string public name;
    uint8 public decimals;
    string public symbol;

    uint256 public exchangeRateStored;
    address public underlying;

    constructor(
        uint256 _initialAmount,
        string memory _tokenName,
        uint8 _decimalUnits,
        string memory _tokenSymbol,
        address _underlying
    ) public {
        balances[msg.sender] = _initialAmount;
        totalSupply = _initialAmount;
        name = _tokenName;
        decimals = _decimalUnits;
        symbol = _tokenSymbol;
        underlying = _underlying;
    }

    function setBorrowCurrent(address _account, uint256 _value) public {
      borrowBalanceCurrent[_account] = _value;
    }

    function repayBorrow() public payable {
      require(borrowBalanceCurrent[msg.sender] >= msg.value);

      borrowBalanceCurrent[msg.sender] -= msg.value;
    }

    function repayBorrow(uint256 amount) public returns (uint) {
      require(borrowBalanceCurrent[msg.sender] >= amount);

      CompoundMock erc20 = CompoundMock(underlying);
      require(erc20.transferFrom(msg.sender, address(this), amount));
      borrowBalanceCurrent[msg.sender] -= amount;
      return 0;
    }

    function mint() public payable {
      balances[msg.sender] += (msg.value * (10 ** 18)) / exchangeRateStored;
      balanceOfUnderlying[msg.sender] += msg.value;
    }

    function mint(uint256 amount) public returns (uint) {
      CompoundMock erc20 = CompoundMock(underlying);
      require(erc20.transferFrom(msg.sender, address(this), amount));
      balances[msg.sender] += (amount * (10 ** 18)) / exchangeRateStored;
      balanceOfUnderlying[msg.sender] += amount;
      return 0;
    }

    function redeem(uint256 cAmount) public returns (uint) {
      require(balances[msg.sender] >= cAmount);

      uint amount = (cAmount * exchangeRateStored) / (10 ** 18);

      balances[msg.sender] -= cAmount;
      balanceOfUnderlying[msg.sender] -= amount;

      if (underlying == address(0x0)) {
        require(msg.sender.send(amount));
      }
      else {
        CompoundMock erc20 = CompoundMock(underlying);
        require(erc20.transfer(msg.sender, amount));
      }

      return 0;
    }

    function redeemUnderlying(uint256 amount) public returns (uint) {
      require(balanceOfUnderlying[msg.sender] >= amount);

      balanceOfUnderlying[msg.sender] -= amount;
      balances[msg.sender] -= (amount * (10 ** 18)) / exchangeRateStored;

      if (underlying == address(0x0)) {
        require(msg.sender.send(amount));
      }
      else {
        CompoundMock erc20 = CompoundMock(underlying);
        require(erc20.transfer(msg.sender, amount));
      }

      return 0;
    }

    function borrow(uint256 amount) public returns (uint) {
      borrowBalanceCurrent[msg.sender] += amount;

      if (underlying == address(0x0)) {
        msg.sender.transfer(amount);
      }
      else {
        CompoundMock erc20 = CompoundMock(underlying);
        require(erc20.transfer(msg.sender, amount));
      }
    }

    function transfer(address _to, uint256 _value) public returns (bool success) {
        require(balances[msg.sender] >= _value);
        balances[msg.sender] -= _value;
        balances[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
        return true;
    }

    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {
        uint256 allowance = allowed[_from][msg.sender];
        require(balances[_from] >= _value && allowance >= _value);
        balances[_to] += _value;
        balances[_from] -= _value;
        if (allowance < MAX_UINT256) {
            allowed[_from][msg.sender] -= _value;
        }
        emit Transfer(_from, _to, _value);
        return true;
    }

    function setRate(uint256 rate) public {
      exchangeRateStored = rate;
    }

    function exchangeRateCurrent() external returns (uint256 balance) {
      return exchangeRateStored;
    }

    function balanceOf(address _owner) public view returns (uint256 balance) {
        return balances[_owner];
    }

    function approve(address _spender, uint256 _value) public returns (bool success) {
        allowed[msg.sender][_spender] = _value;
        emit Approval(msg.sender, _spender, _value);
        return true;
    }

    function allowance(address _owner, address _spender) public view returns (uint256 remaining) {
        return allowed[_owner][_spender];
    }
}

