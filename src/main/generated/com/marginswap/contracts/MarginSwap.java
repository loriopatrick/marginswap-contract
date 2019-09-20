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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610fbf380380610fbf8339818101604052608081101561003357600080fd5b8101908080519060200190929190805190602001909291908051906020019092919080519060200190929190505050836000558260015581600255806003556001740100000000000000000000000000000000000000045550505050610f218061009e6000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee366146101455780635ccbf176146101d657806369328dec14610312578063c29982381461038d57610070565b80632d891fba14610072578063439370b1146100ed57806347e7ef24146100f7575b005b34801561007e57600080fd5b506100eb6004803603606081101561009557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610413565b005b6100f56104b9565b005b6101436004803603604081101561010d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506104c6565b005b34801561015157600080fd5b506101946004803603602081101561016857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061056f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101e257600080fd5b50610310600480360360c08110156101f957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561028a57600080fd5b82018360208201111561029c57600080fd5b803590602001918460018302840111640100000000831117156102be57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929050505061057d565b005b34801561031e57600080fd5b5061038b6004803603606081101561033557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061088b565b005b34801561039957600080fd5b50610411600480360360208110156103b057600080fd5b81019080803590602001906401000000008111156103cd57600080fd5b8201836020820111156103df57600080fd5b8035906020019184602083028401116401000000008311171561040157600080fd5b90919293919293905050506108b0565b005b61041b610e64565b610423610e86565b6000543318156104385760016020526001603ffd5b60008484871561047d577fa9059cbb00000000000000000000000000000000000000000000000000000000855285600486015286602486015287905060449250600091505b602084848785855af1806104965760026020526001603ffd5b88156104ae5784516104ad5760036020526001603ffd5b5b505050505050505050565b6104c46000346104c6565b565b6104ce610ea8565b6104d6610e86565b348318841516156104ec5760016020526001603ffd5b831561055f5734156105035760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af18151158115171561055d5760036020526001603ffd5b505b6105698484610a90565b50505050565b600081600401549050919050565b610585610e64565b61058d610e86565b600080543318156105a35760006020526001603ffd5b6002740100000000000000000000000000000000000000045414156105cd5760016020526001603ffd5b600274010000000000000000000000000000000000000004556001547f0a681c590000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af1806106335760026020526001603ffd5b508915610690577f095ea7b30000000000000000000000000000000000000000000000000000000084528560048501528860248501526000835260208360448660008e5af18351158115171561068e5760046020526001603ffd5b505b303188156106e4577f70a08231000000000000000000000000000000000000000000000000000000008552306004860152600084526020846024878c5afa806106de5760056020526001603ffd5b50835190505b863b6106f55760056020526001603ffd5b898b1561070157600090505b600080885160208a01848c5af18061071e5760076020526001603ffd5b50508a1561077d577f095ea7b3000000000000000000000000000000000000000000000000000000008552866004860152600060248601526000845260208460448760008f5af18451158115171561077b5760086020526001603ffd5b505b303189156107d1577f70a08231000000000000000000000000000000000000000000000000000000008652306004870152600085526020856024888d5afa806107cb5760096020526001603ffd5b50845190505b818110156107e457600a6020526001603ffd5b8181039350888410156107fc57600b6020526001603ffd5b5050506108098782610a90565b60008060c88a04915089820190506108248b82600154610cb1565b600174010000000000000000000000000000000000000004558a8552886020860152896040860152826060860152816080860152867f4a2af5744adbfadba82ab831aea212bad92f5a70fef2079562044f423e99985160a087a25050505050505050505050565b6000543318156108a05760016020526001603ffd5b6108ab838383610cb1565b505050565b6000543318156108c55760006020526001603ffd5b600435602018156108db5760016020526001603ffd5b6024356044368260200260440118156108f95760026020526001603ffd5b6040513680600083376004810382828460006002545af1806109205760036020526001603ffd5b8251602018156109355760046020526001603ffd5b602083015185181561094c5760056020526001603ffd5b6000805b868110156109735760208102604086010151808317925050600181019050610950565b5080156109855760066020526001603ffd5b50505050600354602083028201825b81811015610a875780356040516004810160008152858318156109ef577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa806109ed5760076020526001603ffd5b505b80518381600401558015610a78577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af181511581151715610a755760086020526001603ffd5b50505b50505050602081019050610994565b50505050505050565b610a98610eca565b610aa0610e86565b836004015480610ab55760646020526001603ffd5b7f17bfdfbc0000000000000000000000000000000000000000000000000000000083523060048401526020826024856000855af180610af95760656020526001603ffd5b5060035482518080871015610b0c578690505b8015610bdc577f4e4d9fea00000000000000000000000000000000000000000000000000000000865260048184861815610b72577f0e75270200000000000000000000000000000000000000000000000000000000885282600489015260249150600090505b602087838a848a5af180610b8b5760666020526001603ffd5b3d60008114610ba75760208114610bbf5760696020526001603ffd5b86881815610bba5760676020526001603ffd5b610bd2565b885115610bd15760686020526001603ffd5b5b50838a0399505050505b50508415610ca9577f1249c58b00000000000000000000000000000000000000000000000000000000845260048582841815610c44577fa0712d6800000000000000000000000000000000000000000000000000000000865286600487015260249150600090505b602085838884885af180610c5d57606a6020526001603ffd5b3d60008114610c795760208114610c9157606d6020526001603ffd5b84861815610c8c57606b6020526001603ffd5b610ca4565b865115610ca357606c6020526001603ffd5b5b505050505b505050505050565b610cb9610eca565b610cc1610e86565b846004015480610cd65760c86020526001603ffd5b847f3af9e6690000000000000000000000000000000000000000000000000000000084523060048501526020836024866000865af180610d1b5760c96020526001603ffd5b5082518080831015610d2b578290505b8015610d8e577f852a12e30000000000000000000000000000000000000000000000000000000086528060048701526020856024886000885af180610d755760ca6020526001603ffd5b855115610d875760cb6020526001603ffd5b8184039350505b50508015610de1577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af1835181151715610ddf5760cc6020526001603ffd5b505b600086868915610e26577fa9059cbb00000000000000000000000000000000000000000000000000000000875287600488015288602488015289905060449250600091505b602086848985855af180610e3f5760cd6020526001603ffd5b8a15610e57578651610e565760ce6020526001603ffd5b5b5050505050505050505050565b6040518060600160405280600390602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b6040518060800160405280600490602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea265627a7a72305820c38358d28f603e3de86039d4b756ef54c6b04660c40b13c97493e6baabdfe84264736f6c634300050a0032";
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
    public static Function trade(String input_asset, BigInteger input_amount, String output_asset, BigInteger min_output_amount, String trade_contract, String trade_data) {
        return new Function(
            "trade",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(input_asset)
                , new UnsignedNumberType(256, input_amount)
                , new org.web3j.abi.datatypes.Address(output_asset)
                , new UnsignedNumberType(256, min_output_amount)
                , new org.web3j.abi.datatypes.Address(trade_contract)
                , new org.web3j.abi.datatypes.DynamicBytes(Numeric.hexStringToByteArray(trade_data))
            ),
            Collections.emptyList()
        );
    }
    public static Function trade(String input_asset, long input_amount, String output_asset, long min_output_amount, String trade_contract, String trade_data) {
        return trade(
            input_asset
            , new BigInteger(Long.toUnsignedString(input_amount))
            , output_asset
            , new BigInteger(Long.toUnsignedString(min_output_amount))
            , trade_contract
            , trade_data
        );
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
    public static Function enterMarkets(List<String> cTokens) {
        return new Function(
            "enterMarkets",
            Collections.singletonList(
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(org.web3j.abi.datatypes.Address.class, cTokens.stream().map(org.web3j.abi.datatypes.Address::new).collect(Collectors.toList()))
            ),
            Collections.emptyList()
        );
    }
    public static String DeployData(String owner, String parent_address, String comptroller_address, String cEther_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(owner)
                , new org.web3j.abi.datatypes.Address(parent_address)
                , new org.web3j.abi.datatypes.Address(comptroller_address)
                , new org.web3j.abi.datatypes.Address(cEther_address)
            )
        );
        return BINARY + encodedConstructor;
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
}
