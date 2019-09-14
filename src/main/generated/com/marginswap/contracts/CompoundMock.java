package com.marginswap.contracts;

import org.web3j.abi.*;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.utils.Numeric;
import java.util.stream.Collectors;

@javax.annotation.Generated(value="merklex-code-gen")
public class CompoundMock {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620010ff380380620010ff833981018060405260a08110156200003757600080fd5b810190808051906020019092919080516401000000008111156200005a57600080fd5b828101905060208101848111156200007157600080fd5b81518560018202830111640100000000821117156200008f57600080fd5b5050929190602001805190602001909291908051640100000000811115620000b657600080fd5b82810190506020810184811115620000cd57600080fd5b8151856001820283011164010000000082111715620000eb57600080fd5b505092919060200180519060200190929190505050846000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555084600281905550836003908051906020019062000162929190620001e3565b5082600460006101000a81548160ff021916908360ff160217905550816005908051906020019062000196929190620001e3565b5080600760006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050505062000292565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200022657805160ff191683800117855562000257565b8280016001018555821562000257579182015b828111156200025657825182559160200191906001019062000239565b5b5090506200026691906200026a565b5090565b6200028f91905b808211156200028b57600081600090555060010162000271565b5090565b90565b610e5d80620002a26000396000f3fe6080604052600436106100f35760003560e01c806334fcf4371161008a57806395d89b411161005957806395d89b4114610500578063a9059cbb14610590578063bd6d894d14610603578063dd62ed3e1461062e576100f3565b806334fcf437146103845780635c658165146103bf5780636f307dc31461044457806370a082311461049b576100f3565b8063182df0f5116100c6578063182df0f51461023057806323b872dd1461025b57806327e235e3146102ee578063313ce56714610353576100f3565b806306fdde03146100f8578063095ea7b3146101885780631249c58b146101fb57806318160ddd14610205575b600080fd5b34801561010457600080fd5b5061010d6106b3565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561014d578082015181840152602081019050610132565b50505050905090810190601f16801561017a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561019457600080fd5b506101e1600480360360408110156101ab57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610751565b604051808215151515815260200191505060405180910390f35b610203610843565b005b34801561021157600080fd5b5061021a610845565b6040518082815260200191505060405180910390f35b34801561023c57600080fd5b5061024561084b565b6040518082815260200191505060405180910390f35b34801561026757600080fd5b506102d46004803603606081101561027e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610851565b604051808215151515815260200191505060405180910390f35b3480156102fa57600080fd5b5061033d6004803603602081101561031157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610ae6565b6040518082815260200191505060405180910390f35b34801561035f57600080fd5b50610368610afe565b604051808260ff1660ff16815260200191505060405180910390f35b34801561039057600080fd5b506103bd600480360360208110156103a757600080fd5b8101908080359060200190929190505050610b11565b005b3480156103cb57600080fd5b5061042e600480360360408110156103e257600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610b1b565b6040518082815260200191505060405180910390f35b34801561045057600080fd5b50610459610b40565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156104a757600080fd5b506104ea600480360360208110156104be57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610b66565b6040518082815260200191505060405180910390f35b34801561050c57600080fd5b50610515610bae565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561055557808201518184015260208101905061053a565b50505050905090810190601f1680156105825780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561059c57600080fd5b506105e9600480360360408110156105b357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610c4c565b604051808215151515815260200191505060405180910390f35b34801561060f57600080fd5b50610618610da0565b6040518082815260200191505060405180910390f35b34801561063a57600080fd5b5061069d6004803603604081101561065157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610daa565b6040518082815260200191505060405180910390f35b60038054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107495780601f1061071e57610100808354040283529160200191610749565b820191906000526020600020905b81548152906001019060200180831161072c57829003601f168201915b505050505081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b565b60025481565b60065481565b600080600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050826000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156109215750828110155b61092a57600080fd5b826000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550826000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff811015610a755782600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b8373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a360019150509392505050565b60006020528060005260406000206000915090505481565b600460009054906101000a900460ff1681565b8060068190555050565b6001602052816000526040600020602052806000526040600020600091509150505481565b600760009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60058054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c445780601f10610c1957610100808354040283529160200191610c44565b820191906000526020600020905b815481529060010190602001808311610c2757829003601f168201915b505050505081565b6000816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610c9957600080fd5b816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550816000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a36001905092915050565b6000600654905090565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490509291505056fea165627a7a72305820f694eea5d444cbc6b263622b9b75a6bc43bd6c9c00bb9fe567b482b6fc0711510029";
    public static Function name() {
        return new Function(
            "name",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Utf8String>() {}
            )
        );
    }
    public static NameReturnValue query_name(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        NameReturnValue returnValue = new NameReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static NameReturnValue query_name(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_name(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function approve(String _spender, BigInteger _value) {
        return new Function(
            "approve",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_spender)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static Function approve(String _spender, long _value) {
        return approve(
            _spender
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function mint() {
        return new Function(
            "mint",
            Collections.emptyList(),
            Collections.emptyList()
        );
    }
    public static Function totalSupply() {
        return new Function(
            "totalSupply",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static TotalsupplyReturnValue query_totalSupply(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        TotalsupplyReturnValue returnValue = new TotalsupplyReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static TotalsupplyReturnValue query_totalSupply(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_totalSupply(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function exchangeRateStored() {
        return new Function(
            "exchangeRateStored",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static ExchangeratestoredReturnValue query_exchangeRateStored(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        ExchangeratestoredReturnValue returnValue = new ExchangeratestoredReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static ExchangeratestoredReturnValue query_exchangeRateStored(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_exchangeRateStored(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function transferFrom(String _from, String _to, BigInteger _value) {
        return new Function(
            "transferFrom",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_from)
                , new org.web3j.abi.datatypes.Address(_to)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static Function transferFrom(String _from, String _to, long _value) {
        return transferFrom(
            _from
            , _to
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function balances(String arg0) {
        return new Function(
            "balances",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(arg0)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static BalancesReturnValue query_balances(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        BalancesReturnValue returnValue = new BalancesReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static BalancesReturnValue query_balances(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_balances(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function decimals() {
        return new Function(
            "decimals",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint8>() {}
            )
        );
    }
    public static DecimalsReturnValue query_decimals(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        DecimalsReturnValue returnValue = new DecimalsReturnValue();
        returnValue.value = ((BigInteger) values.get(0).getValue()).intValue();
        return returnValue;
    }
    public static DecimalsReturnValue query_decimals(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_decimals(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function setRate(BigInteger rate) {
        return new Function(
            "setRate",
            Collections.singletonList(
                new UnsignedNumberType(256, rate)
            ),
            Collections.emptyList()
        );
    }
    public static Function setRate(long rate) {
        return setRate(
            new BigInteger(Long.toUnsignedString(rate))
        );
    }
    public static Function allowed(String arg0, String arg1) {
        return new Function(
            "allowed",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(arg0)
                , new org.web3j.abi.datatypes.Address(arg1)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static AllowedReturnValue query_allowed(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        AllowedReturnValue returnValue = new AllowedReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static AllowedReturnValue query_allowed(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_allowed(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function underlying() {
        return new Function(
            "underlying",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static UnderlyingReturnValue query_underlying(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        UnderlyingReturnValue returnValue = new UnderlyingReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static UnderlyingReturnValue query_underlying(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_underlying(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function balanceOf(String _owner) {
        return new Function(
            "balanceOf",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(_owner)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static BalanceofReturnValue query_balanceOf(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        BalanceofReturnValue returnValue = new BalanceofReturnValue();
        returnValue.balance = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static BalanceofReturnValue query_balanceOf(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_balanceOf(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function symbol() {
        return new Function(
            "symbol",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Utf8String>() {}
            )
        );
    }
    public static SymbolReturnValue query_symbol(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        SymbolReturnValue returnValue = new SymbolReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static SymbolReturnValue query_symbol(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_symbol(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function transfer(String _to, BigInteger _value) {
        return new Function(
            "transfer",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_to)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static Function transfer(String _to, long _value) {
        return transfer(
            _to
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function exchangeRateCurrent() {
        return new Function(
            "exchangeRateCurrent",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static Function allowance(String _owner, String _spender) {
        return new Function(
            "allowance",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_owner)
                , new org.web3j.abi.datatypes.Address(_spender)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static AllowanceReturnValue query_allowance(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        AllowanceReturnValue returnValue = new AllowanceReturnValue();
        returnValue.remaining = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static AllowanceReturnValue query_allowance(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_allowance(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static String DeployData(BigInteger _initialAmount, String _tokenName, int _decimalUnits, String _tokenSymbol, String _underlying) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Arrays.asList(
                new UnsignedNumberType(256, _initialAmount)
                , new org.web3j.abi.datatypes.Utf8String(_tokenName)
                , new UnsignedNumberType(8, _decimalUnits)
                , new org.web3j.abi.datatypes.Utf8String(_tokenSymbol)
                , new org.web3j.abi.datatypes.Address(_underlying)
            )
        );
        return BINARY + encodedConstructor;
    }
    public static class Transfer {
        public String _from;
        public String _to;
        public BigInteger _value;
    }
    public static final Event Transfer_EVENT = new Event("Transfer",
        Arrays.asList(
            new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
        )
    );
    public static final String Transfer_EVENT_HASH = EventEncoder.encode(Transfer_EVENT);
    public static Transfer ExtractTransfer(Log log) {
        List<String> topics = log.getTopics();
        if (topics.size() == 0 || !Transfer_EVENT_HASH.equals(topics.get(0))) {
            return null;
        }
        EventValues values = Contract.staticExtractEventParameters(Transfer_EVENT, log);
        Transfer event = new Transfer();
        event._from = (String) values.getIndexedValues().get(0).getValue();
        event._to = (String) values.getIndexedValues().get(1).getValue();
        event._value = (BigInteger) values.getNonIndexedValues().get(0).getValue();
        return event;
    }
    public static class Approval {
        public String _owner;
        public String _spender;
        public BigInteger _value;
    }
    public static final Event Approval_EVENT = new Event("Approval",
        Arrays.asList(
            new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
        )
    );
    public static final String Approval_EVENT_HASH = EventEncoder.encode(Approval_EVENT);
    public static Approval ExtractApproval(Log log) {
        List<String> topics = log.getTopics();
        if (topics.size() == 0 || !Approval_EVENT_HASH.equals(topics.get(0))) {
            return null;
        }
        EventValues values = Contract.staticExtractEventParameters(Approval_EVENT, log);
        Approval event = new Approval();
        event._owner = (String) values.getIndexedValues().get(0).getValue();
        event._spender = (String) values.getIndexedValues().get(1).getValue();
        event._value = (BigInteger) values.getNonIndexedValues().get(0).getValue();
        return event;
    }
    public static class NameReturnValue {
        public String value;
    }
    public static class TotalsupplyReturnValue {
        public BigInteger value;
    }
    public static class ExchangeratestoredReturnValue {
        public BigInteger value;
    }
    public static class BalancesReturnValue {
        public BigInteger value;
    }
    public static class DecimalsReturnValue {
        public int value;
    }
    public static class AllowedReturnValue {
        public BigInteger value;
    }
    public static class UnderlyingReturnValue {
        public String value;
    }
    public static class BalanceofReturnValue {
        public BigInteger balance;
    }
    public static class SymbolReturnValue {
        public String value;
    }
    public static class AllowanceReturnValue {
        public BigInteger remaining;
    }
}
