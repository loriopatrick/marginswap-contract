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
public class MarginSwap {
    public static final String BINARY = "608060405234801561001057600080fd5b506109c9806100206000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee3661461014557806368bde41f146101d657806369328dec1461022d578063a0985ba1146102a857610070565b80632d891fba14610072578063439370b1146100ed57806347e7ef24146100f7575b005b34801561007e57600080fd5b506100eb6004803603606081101561009557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102f9565b005b6100f561039f565b005b6101436004803603604081101561010d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506103ac565b005b34801561015157600080fd5b506101946004803603602081101561016857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610456565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101e257600080fd5b506101eb610464565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561023957600080fd5b506102a66004803603606081101561025057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061048b565b005b3480156102b457600080fd5b506102f7600480360360208110156102cb57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104b0565b005b61030161090c565b61030961092e565b60015433181561031e5760016020526001603ffd5b600084848715610363577fa9059cbb00000000000000000000000000000000000000000000000000000000855285600486015286602486015287905060449250600091505b602084848785855af18061037c5760026020526001603ffd5b88156103945784516103935760036020526001603ffd5b5b505050505050505050565b6103aa6000346103ac565b565b6103b4610950565b6103bc61092e565b34831415841516156103d35760016020526001603ffd5b83156104465734156103ea5760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af1815115811517156104445760036020526001603ffd5b505b61045084846104cc565b50505050565b600081600501549050919050565b600060045490508061048857733d9819210a31b4961b30ef54be2aed79b9c9cd3b90505b90565b6001543318156104a05760016020526001603ffd5b6104ab8383836106e1565b505050565b6001543318156104c55760016020526001603ffd5b8060045550565b6104d4610972565b6104dc61092e565b8360050154806104f15760646020526001603ffd5b7f17bfdfbc0000000000000000000000000000000000000000000000000000000083523060048401526020826024856000855af1806105355760656020526001603ffd5b5081518080861015610545578590505b8015610611577f4e4d9fea00000000000000000000000000000000000000000000000000000000855260048188156105a9577f0e75270200000000000000000000000000000000000000000000000000000000875282600488015260249150600090505b602086838984895af1806105c25760666020526001603ffd5b3d600081146105de57602081146105f45760696020526001603ffd5b8a156105ef5760676020526001603ffd5b610607565b8751156106065760686020526001603ffd5b5b5083890398505050505b505083156106da577f1249c58b0000000000000000000000000000000000000000000000000000000083526004848615610677577fa0712d6800000000000000000000000000000000000000000000000000000000855285600486015260249150600090505b602084838784875af18061069057606a6020526001603ffd5b3d600081146106ac57602081146106c257606d6020526001603ffd5b88156106bd57606b6020526001603ffd5b6106d5565b8551156106d457606c6020526001603ffd5b5b505050505b5050505050565b6106e9610972565b6106f161092e565b8460050154806107065760c86020526001603ffd5b847f70a082310000000000000000000000000000000000000000000000000000000084523060048501526020836024866000865af18061074b5760c96020526001603ffd5b5082518015610837577fbd6d894d0000000000000000000000000000000000000000000000000000000085526020846004876000875af1806107925760ca6020526001603ffd5b508351806107a55760cb6020526001603ffd5b80670de0b6b3a76400008402600183030104828111156107c3578290505b8015610834577fdb006a750000000000000000000000000000000000000000000000000000000087528060048801526020866024896000895af18061080d5760cc6020526001603ffd5b86511561081f5760cd6020526001603ffd5b670de0b6b3a764000083830204808603955050505b50505b508015610889577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af18351811517156108875760cc6020526001603ffd5b505b6000868689156108ce577fa9059cbb00000000000000000000000000000000000000000000000000000000875287600488015288602488015289905060449250600091505b602086848985855af1806108e75760cd6020526001603ffd5b8a156108ff5786516108fe5760ce6020526001603ffd5b5b5050505050505050505050565b6040518060600160405280600390602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b6040518060800160405280600490602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea265627a7a72305820292a5736a8ba79b05f4123ebeb17fb0ab391b2e3125dae5f29edace4fe5bee8564736f6c634300050a0032";
    public static Function transferOut(String asset, BigInteger amount, String destination) {
        return new Function(
            "transferOut",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(asset)
                , new UnsignedNumberType(256, amount)
                , new org.web3j.abi.datatypes.Address(destination)
            ),
            Collections.emptyList()
        );
    }
    public static Function transferOut(String asset, long amount, String destination) {
        return transferOut(
            asset
            , new BigInteger(Long.toUnsignedString(amount))
            , destination
        );
    }
    public static Function depositEth() {
        return new Function(
            "depositEth",
            Collections.emptyList(),
            Collections.emptyList()
        );
    }
    public static Function deposit(String asset_address, BigInteger amount) {
        return new Function(
            "deposit",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(asset_address)
                , new UnsignedNumberType(256, amount)
            ),
            Collections.emptyList()
        );
    }
    public static Function deposit(String asset_address, long amount) {
        return deposit(
            asset_address
            , new BigInteger(Long.toUnsignedString(amount))
        );
    }
    public static Function lookupUnderlying(String cToken) {
        return new Function(
            "lookupUnderlying",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(cToken)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static LookupunderlyingReturnValue query_lookupUnderlying(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        LookupunderlyingReturnValue returnValue = new LookupunderlyingReturnValue();
        returnValue.result = (String) values.get(0).getValue();
        return returnValue;
    }
    public static LookupunderlyingReturnValue query_lookupUnderlying(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_lookupUnderlying(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function comptrollerAddress() {
        return new Function(
            "comptrollerAddress",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static ComptrolleraddressReturnValue query_comptrollerAddress(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        ComptrolleraddressReturnValue returnValue = new ComptrolleraddressReturnValue();
        returnValue.comptroller = (String) values.get(0).getValue();
        return returnValue;
    }
    public static ComptrolleraddressReturnValue query_comptrollerAddress(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_comptrollerAddress(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function withdraw(String asset, BigInteger amount, String destination) {
        return new Function(
            "withdraw",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(asset)
                , new UnsignedNumberType(256, amount)
                , new org.web3j.abi.datatypes.Address(destination)
            ),
            Collections.emptyList()
        );
    }
    public static Function withdraw(String asset, long amount, String destination) {
        return withdraw(
            asset
            , new BigInteger(Long.toUnsignedString(amount))
            , destination
        );
    }
    public static Function setComptrollerAddress(String comptroller) {
        return new Function(
            "setComptrollerAddress",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(comptroller)
            ),
            Collections.emptyList()
        );
    }
    public static class Trade {
        public String trade_contract;
        public String from_asset;
        public String to_asset;
        public BigInteger input;
        public BigInteger output;
        public BigInteger input_fee;
    }
    public static final Event Trade_EVENT = new Event("Trade",
        Arrays.asList(
            new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.Address>() {}
            , new TypeReference<org.web3j.abi.datatypes.Address>() {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
        )
    );
    public static final String Trade_EVENT_HASH = EventEncoder.encode(Trade_EVENT);
    public static Trade ExtractTrade(Log log) {
        List<String> topics = log.getTopics();
        if (topics.size() == 0 || !Trade_EVENT_HASH.equals(topics.get(0))) {
            return null;
        }
        EventValues values = Contract.staticExtractEventParameters(Trade_EVENT, log);
        Trade event = new Trade();
        event.trade_contract = (String) values.getIndexedValues().get(0).getValue();
        event.from_asset = (String) values.getNonIndexedValues().get(0).getValue();
        event.to_asset = (String) values.getNonIndexedValues().get(1).getValue();
        event.input = (BigInteger) values.getNonIndexedValues().get(2).getValue();
        event.output = (BigInteger) values.getNonIndexedValues().get(3).getValue();
        event.input_fee = (BigInteger) values.getNonIndexedValues().get(4).getValue();
        return event;
    }
    public static class LookupunderlyingReturnValue {
        public String result;
    }
    public static class ComptrolleraddressReturnValue {
        public String comptroller;
    }
}
