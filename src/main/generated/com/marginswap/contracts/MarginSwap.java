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
    public static final String BINARY = "608060405234801561001057600080fd5b50611083806100206000396000f3fe6080604052600436106100865760003560e01c80635ccbf176116100595780635ccbf176146101ec57806368bde41f1461032857806369328dec1461037f578063a0985ba1146103fa578063c29982381461044b57610086565b80632d891fba14610088578063439370b11461010357806347e7ef241461010d57806357aee3661461015b575b005b34801561009457600080fd5b50610101600480360360608110156100ab57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d1565b005b61010b610577565b005b6101596004803603604081101561012357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610584565b005b34801561016757600080fd5b506101aa6004803603602081101561017e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061062e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101f857600080fd5b50610326600480360360c081101561020f57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001906401000000008111156102a057600080fd5b8201836020820111156102b257600080fd5b803590602001918460018302840111640100000000831117156102d457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929050505061063c565b005b34801561033457600080fd5b5061033d61090f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561038b57600080fd5b506103f8600480360360608110156103a257600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610936565b005b34801561040657600080fd5b506104496004803603602081101561041d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061095b565b005b34801561045757600080fd5b506104cf6004803603602081101561046e57600080fd5b810190808035906020019064010000000081111561048b57600080fd5b82018360208201111561049d57600080fd5b803590602001918460208302840111640100000000831117156104bf57600080fd5b9091929391929390505050610977565b005b6104d9610fc6565b6104e1610fe8565b6001543318156104f65760016020526001603ffd5b60008484871561053b577fa9059cbb00000000000000000000000000000000000000000000000000000000855285600486015286602486015287905060449250600091505b602084848785855af1806105545760026020526001603ffd5b881561056c57845161056b5760036020526001603ffd5b5b505050505050505050565b610582600034610584565b565b61058c61100a565b610594610fe8565b34831415841516156105ab5760016020526001603ffd5b831561061e5734156105c25760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af18151158115171561061c5760036020526001603ffd5b505b6106288484610b86565b50505050565b600081600501549050919050565b610644610fc6565b61064c610fe8565b60006001543318156106635760006020526001603ffd5b600160035418156106795760016020526001603ffd5b60026003556002547f0a681c590000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af1806106cb5760026020526001603ffd5b508915610728577f095ea7b30000000000000000000000000000000000000000000000000000000084528560048501528860248501526000835260208360448660008e5af1835115811517156107265760046020526001603ffd5b505b3031881561077c577f70a08231000000000000000000000000000000000000000000000000000000008552306004860152600084526020846024878c5afa806107765760056020526001603ffd5b50835190505b863b61078d5760056020526001603ffd5b898b1561079957600090505b600080885160208a01848c5af1806107b65760076020526001603ffd5b50508a15610815577f095ea7b3000000000000000000000000000000000000000000000000000000008552866004860152600060248601526000845260208460448760008f5af1845115811517156108135760086020526001603ffd5b505b30318915610869577f70a08231000000000000000000000000000000000000000000000000000000008652306004870152600085526020856024888d5afa806108635760096020526001603ffd5b50845190505b8181101561087c57600a6020526001603ffd5b81810393508884101561089457600b6020526001603ffd5b5050506108a18782610b86565b60008060c88a04915089820190506108bc8b82600254610d9b565b60016003558a8552886020860152896040860152826060860152816080860152867f4a2af5744adbfadba82ab831aea212bad92f5a70fef2079562044f423e99985160a087a25050505050505050505050565b600060045490508061093357733d9819210a31b4961b30ef54be2aed79b9c9cd3b90505b90565b60015433181561094b5760016020526001603ffd5b610956838383610d9b565b505050565b6001543318156109705760016020526001603ffd5b8060045550565b60015433181561098c5760006020526001603ffd5b600435602018156109a25760016020526001603ffd5b6024356044368260200260440118156109c05760026020526001603ffd5b604051368060008337600454806109e957733d9819210a31b4961b30ef54be2aed79b9c9cd3b90505b600482038383856000855af180610a055760036020526001603ffd5b835160201815610a1a5760046020526001603ffd5b6020840151861815610a315760056020526001603ffd5b6000805b87811015610a585760208102604087010151808317925050600181019050610a35565b508015610a6a5760066020526001603ffd5b5050505050602082028101815b81811015610b7e5780356040516004810160008152734ddc2d193948926d02f9b1fe9e1daa0718270ed5831815610ae6577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa80610ae45760076020526001603ffd5b505b80518381600501558015610b6f577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af181511581151715610b6c5760086020526001603ffd5b50505b50505050602081019050610a77565b505050505050565b610b8e61102c565b610b96610fe8565b836005015480610bab5760646020526001603ffd5b7f17bfdfbc0000000000000000000000000000000000000000000000000000000083523060048401526020826024856000855af180610bef5760656020526001603ffd5b5081518080861015610bff578590505b8015610ccb577f4e4d9fea0000000000000000000000000000000000000000000000000000000085526004818815610c63577f0e75270200000000000000000000000000000000000000000000000000000000875282600488015260249150600090505b602086838984895af180610c7c5760666020526001603ffd5b3d60008114610c985760208114610cae5760696020526001603ffd5b8a15610ca95760676020526001603ffd5b610cc1565b875115610cc05760686020526001603ffd5b5b5083890398505050505b50508315610d94577f1249c58b0000000000000000000000000000000000000000000000000000000083526004848615610d31577fa0712d6800000000000000000000000000000000000000000000000000000000855285600486015260249150600090505b602084838784875af180610d4a57606a6020526001603ffd5b3d60008114610d665760208114610d7c57606d6020526001603ffd5b8815610d7757606b6020526001603ffd5b610d8f565b855115610d8e57606c6020526001603ffd5b5b505050505b5050505050565b610da361102c565b610dab610fe8565b846005015480610dc05760c86020526001603ffd5b847f70a082310000000000000000000000000000000000000000000000000000000084523060048501526020836024866000865af180610e055760c96020526001603ffd5b5082518015610ef1577fbd6d894d0000000000000000000000000000000000000000000000000000000085526020846004876000875af180610e4c5760ca6020526001603ffd5b50835180610e5f5760cb6020526001603ffd5b80670de0b6b3a7640000840260018303010482811115610e7d578290505b8015610eee577fdb006a750000000000000000000000000000000000000000000000000000000087528060048801526020866024896000895af180610ec75760cc6020526001603ffd5b865115610ed95760cd6020526001603ffd5b670de0b6b3a764000083830204808603955050505b50505b508015610f43577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af1835181151715610f415760cc6020526001603ffd5b505b600086868915610f88577fa9059cbb00000000000000000000000000000000000000000000000000000000875287600488015288602488015289905060449250600091505b602086848985855af180610fa15760cd6020526001603ffd5b8a15610fb9578651610fb85760ce6020526001603ffd5b5b5050505050505050505050565b6040518060600160405280600390602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b6040518060800160405280600490602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea265627a7a72305820b808d6307979b734bb305ccbbb030ad8bf6bcde0492f9b75505b8f3c46fd313164736f6c634300050a0032";
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
    public static Function enterMarkets(List<String> cTokens) {
        return new Function(
            "enterMarkets",
            Collections.singletonList(
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(org.web3j.abi.datatypes.Address.class, cTokens.stream().map(org.web3j.abi.datatypes.Address::new).collect(Collectors.toList()))
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
