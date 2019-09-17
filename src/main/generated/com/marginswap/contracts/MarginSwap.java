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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610e27380380610e278339818101604052608081101561003357600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291905050508360005582600155816002558060035550505050610da2806100856000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee366146101455780635ccbf176146101d657806369328dec14610305578063c29982381461038057610070565b80632d891fba14610072578063439370b1146100ed57806347e7ef24146100f7575b005b34801561007e57600080fd5b506100eb6004803603606081101561009557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610406565b005b6100f5610420565b005b6101436004803603604081101561010d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061042d565b005b34801561015157600080fd5b506101946004803603602081101561016857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d6565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610303600480360360c08110156101ec57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561027d57600080fd5b82018360208201111561028f57600080fd5b803590602001918460018302840111640100000000831117156102b157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e4565b005b34801561031157600080fd5b5061037e6004803603606081101561032857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610742565b005b34801561038c57600080fd5b50610404600480360360208110156103a357600080fd5b81019080803590602001906401000000008111156103c057600080fd5b8201836020820111156103d257600080fd5b803590602001918460208302840111640100000000831117156103f457600080fd5b9091929391929390505050610767565b005b60005433181561041b5760016020526001603ffd5b505050565b61042b60003461042d565b565b610435610d07565b61043d610d29565b348318841516156104535760016020526001603ffd5b83156104c657341561046a5760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af1815115811517156104c45760036020526001603ffd5b505b6104d08484610947565b50505050565b600081600401549050919050565b6104ec610d07565b6104f4610d29565b6000805433181561050a5760016020526001603ffd5b6001547f0a681c590000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af1806105575760026020526001603ffd5b5089156105c557341561056f5760036020526001603ffd5b7f095ea7b30000000000000000000000000000000000000000000000000000000084528560048501528860248501526000835260208360448660008e5af1835115811517156105c35760046020526001603ffd5b505b30318815610619577f70a08231000000000000000000000000000000000000000000000000000000008552336004860152600084526020846024878c5afa806106135760056020526001603ffd5b50835190505b863b61062a5760056020526001603ffd5b600080875160208901348b5af1806106475760076020526001603ffd5b507f095ea7b3000000000000000000000000000000000000000000000000000000008552866004860152600060248601526000845260208460448760008f5af18451158115171561069d5760086020526001603ffd5b50303189156106f2577f70a08231000000000000000000000000000000000000000000000000000000008652336004870152600085526020856024888d5afa806106ec5760096020526001603ffd5b50845190505b8181101561070557600a6020526001603ffd5b81810393508884101561071d57600b6020526001603ffd5b50505061072a8782610947565b6107378989600154610b56565b505050505050505050565b6000543318156107575760016020526001603ffd5b610762838383610b56565b505050565b60005433181561077c5760006020526001603ffd5b600435602018156107925760016020526001603ffd5b6024356044368260200260440118156107b05760026020526001603ffd5b6040513680600083376004810382828460006002545af1806107d75760036020526001603ffd5b8251602018156107ec5760046020526001603ffd5b60208301518518156108035760056020526001603ffd5b6000805b8681101561082a5760208102604086010151808317925050600181019050610807565b50801561083c5760066020526001603ffd5b50505050600354602083028201825b8181101561093e5780356040516004810160008252858318156108a6577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa806108a45760076020526001603ffd5b505b8051838160040155801561092f577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af18151158115171561092c5760086020526001603ffd5b50505b5050505060208101905061084b565b50505050505050565b61094f610d4b565b610957610d29565b83600401548061096c5760646020526001603ffd5b7f17bfdfbc000000000000000000000000000000000000000000000000000000008352306004840152602082602485845afa806109ae5760656020526001603ffd5b50600354825180808710156109c1578690505b8015610a91577f4e4d9fea00000000000000000000000000000000000000000000000000000000865260048184861815610a27577f0e75270200000000000000000000000000000000000000000000000000000000885282600489015260249150600090505b602087838a848a5af180610a405760666020526001603ffd5b3d60008114610a5c5760208114610a745760696020526001603ffd5b86881815610a6f5760676020526001603ffd5b610a87565b885115610a865760686020526001603ffd5b5b50838a0399505050505b50508415610b4e577f1249c58b00000000000000000000000000000000000000000000000000000000845260048582841815610af9577fa0712d6800000000000000000000000000000000000000000000000000000000865286600487015260249150600090505b602085838884885af13d60008114610b1e5760208114610b3657606c6020526001603ffd5b84861815610b3157606a6020526001603ffd5b610b49565b865115610b4857606b6020526001603ffd5b5b505050505b505050505050565b610b5e610d4b565b610b66610d29565b846004015480610b7b5760c86020526001603ffd5b847f3af9e669000000000000000000000000000000000000000000000000000000008452306004850152602083602486855afa80610bbe5760c96020526001603ffd5b5082518080831015610bce578290505b8015610c31577f852a12e30000000000000000000000000000000000000000000000000000000086528060048701526020856024886000885af180610c185760ca6020526001603ffd5b855115610c2a5760cb6020526001603ffd5b8184039350505b50508015610c84577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af1835181151715610c825760cc6020526001603ffd5b505b600086868915610cc9577fa9059cbb00000000000000000000000000000000000000000000000000000000875287600488015288602488015289905060449250600091505b602086848985855af180610ce25760cd6020526001603ffd5b8a15610cfa578651610cf95760ce6020526001603ffd5b5b5050505050505050505050565b6040518060800160405280600490602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea265627a7a7231582044e6bd6e9f0e6b41f7481818dfe363dca971d5ba61bd5bedca5016f8db234b5664736f6c634300050b0032";
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
    public static class LookupunderlyingReturnValue {
        public String result;
    }
}
