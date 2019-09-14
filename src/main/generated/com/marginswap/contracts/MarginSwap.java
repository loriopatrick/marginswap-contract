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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051608080610d958339810180604052608081101561003057600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291905050508360005582600155816002558060035550505050610d13806100826000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee366146101485780635ccbf176146101d957806369328dec14610308578063c29982381461038357610070565b80632d891fba14610075578063439370b1146100f057806347e7ef24146100fa575b600080fd5b34801561008157600080fd5b506100ee6004803603606081101561009857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610409565b005b6100f8610423565b005b6101466004803603604081101561011057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610430565b005b34801561015457600080fd5b506101976004803603602081101561016b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610306600480360360c08110156101ef57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561028057600080fd5b82018360208201111561029257600080fd5b803590602001918460018302840111640100000000831117156102b457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e7565b005b34801561031457600080fd5b506103816004803603606081101561032b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610712565b005b34801561038f57600080fd5b50610407600480360360208110156103a657600080fd5b81019080803590602001906401000000008111156103c357600080fd5b8201836020820111156103d557600080fd5b803590602001918460208302840111640100000000831117156103f757600080fd5b9091929391929390505050610892565b005b60005433181561041e5760016020526001603ffd5b505050565b61042e600034610430565b565b610438610c81565b610440610ca3565b348318841516156104565760016020526001603ffd5b83156104c957341561046d5760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af1815115811517156104c75760036020526001603ffd5b505b6104d38484610a72565b50505050565b600081600401549050919050565b6104ef610c81565b6104f7610ca3565b6000805433181561050d5760016020526001603ffd5b6001547fab9567040000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af18061055a5760026020526001603ffd5b5089156105c25734156105725760036020526001603ffd5b7fb759f9540000000000000000000000000000000000000000000000000000000084528860048501526000835260208360248660008a5af1835115811517156105c05760046020526001603ffd5b505b7f70a0823100000000000000000000000000000000000000000000000000000000845287600485015260008352602083602486335afa806106085760056020526001603ffd5b508251600080875160208901348b5af1806106285760066020526001603ffd5b507fb759f954000000000000000000000000000000000000000000000000000000008552600060048601526000845260208460248760008b5af1845115811517156106785760076020526001603ffd5b507f70a0823100000000000000000000000000000000000000000000000000000000855288600486015260008452602084602487335afa806106bf5760086020526001603ffd5b508351808210156106d55760096020526001603ffd5b8181039350888410156106ed57600a6020526001603ffd5b5050506106fa8782610a72565b6107078989600154610712565b505050505050505050565b61071a610cc5565b610722610ca3565b8460040154806107375760c86020526001603ffd5b7f3af9e669000000000000000000000000000000000000000000000000000000008352336004840152602082602485845afa806107795760c96020526001603ffd5b5081518080871015610789578690505b80156107ec577f852a12e30000000000000000000000000000000000000000000000000000000085528060048601526020846024876000875af1806107d35760ca6020526001603ffd5b8451156107e55760cb6020526001603ffd5b8188039750505b5050841561083c577fc5ebeaec0000000000000000000000000000000000000000000000000000000083528460048401526020826024856000855af182511561083a5760cc6020526001603ffd5b505b841561088a577fc5ebeaec0000000000000000000000000000000000000000000000000000000083528460048401526020826024856000855af18251156108885760cc6020526001603ffd5b505b505050505050565b6000543318156108a75760006020526001603ffd5b600435602018156108bd5760016020526001603ffd5b6024356044368260200260440118156108db5760026020526001603ffd5b6040513680600083376004810382828460006002545af1806109025760036020526001603ffd5b8251602018156109175760046020526001603ffd5b602083015185181561092e5760056020526001603ffd5b6000805b868110156109555760208102604086010151808317925050600181019050610932565b5080156109675760066020526001603ffd5b50505050600354602083028201825b81811015610a695780356040516004810160008252858318156109d1577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa806109cf5760076020526001603ffd5b505b80518381600401558015610a5a577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af181511581151715610a575760086020526001603ffd5b50505b50505050602081019050610976565b50505050505050565b610a7a610cc5565b610a82610ca3565b836004015480610a975760646020526001603ffd5b7f17bfdfbc000000000000000000000000000000000000000000000000000000008352336004840152602082602485845afa80610ad95760656020526001603ffd5b5060035482518080871015610aec578690505b8015610bbc577f4e4d9fea00000000000000000000000000000000000000000000000000000000865260048184861815610b52577f0e75270200000000000000000000000000000000000000000000000000000000885282600489015260249150600090505b602087838a848a5af180610b6b5760666020526001603ffd5b3d60008114610b875760208114610b9f5760696020526001603ffd5b86881815610b9a5760676020526001603ffd5b610bb2565b885115610bb15760686020526001603ffd5b5b50838a0399505050505b50508415610c79577f1249c58b00000000000000000000000000000000000000000000000000000000845260048582841815610c24577fa0712d6800000000000000000000000000000000000000000000000000000000865286600487015260249150600090505b602085838884885af13d60008114610c495760208114610c6157606c6020526001603ffd5b84861815610c5c57606a6020526001603ffd5b610c74565b865115610c7357606b6020526001603ffd5b5b505050505b505050505050565b6040518060800160405280600490602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea165627a7a72305820798b89a6e2fa4b204755843b97ff14dc3735d96d6bbdf7018e5033c9793a0e9c0029";
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
