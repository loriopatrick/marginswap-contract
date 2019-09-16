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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051608080610daf8339810180604052608081101561003057600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291905050508360005582600155816002558060035550505050610d2d806100826000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee366146101485780635ccbf176146101d957806369328dec14610308578063c29982381461038357610070565b80632d891fba14610075578063439370b1146100f057806347e7ef24146100fa575b600080fd5b34801561008157600080fd5b506100ee6004803603606081101561009857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610409565b005b6100f8610423565b005b6101466004803603604081101561011057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610430565b005b34801561015457600080fd5b506101976004803603602081101561016b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610306600480360360c08110156101ef57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561028057600080fd5b82018360208201111561029257600080fd5b803590602001918460018302840111640100000000831117156102b457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e7565b005b34801561031457600080fd5b506103816004803603606081101561032b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610712565b005b34801561038f57600080fd5b50610407600480360360208110156103a657600080fd5b81019080803590602001906401000000008111156103c357600080fd5b8201836020820111156103d557600080fd5b803590602001918460208302840111640100000000831117156103f757600080fd5b90919293919293905050506108ac565b005b60005433181561041e5760016020526001603ffd5b505050565b61042e600034610430565b565b610438610c9b565b610440610cbd565b348318841516156104565760016020526001603ffd5b83156104c957341561046d5760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af1815115811517156104c75760036020526001603ffd5b505b6104d38484610a8c565b50505050565b600081600401549050919050565b6104ef610c9b565b6104f7610cbd565b6000805433181561050d5760016020526001603ffd5b6001547fab9567040000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af18061055a5760026020526001603ffd5b5089156105c25734156105725760036020526001603ffd5b7fb759f9540000000000000000000000000000000000000000000000000000000084528860048501526000835260208360248660008a5af1835115811517156105c05760046020526001603ffd5b505b7f70a0823100000000000000000000000000000000000000000000000000000000845287600485015260008352602083602486335afa806106085760056020526001603ffd5b508251600080875160208901348b5af1806106285760066020526001603ffd5b507fb759f954000000000000000000000000000000000000000000000000000000008552600060048601526000845260208460248760008b5af1845115811517156106785760076020526001603ffd5b507f70a0823100000000000000000000000000000000000000000000000000000000855288600486015260008452602084602487335afa806106bf5760086020526001603ffd5b508351808210156106d55760096020526001603ffd5b8181039350888410156106ed57600a6020526001603ffd5b5050506106fa8782610a8c565b6107078989600154610712565b505050505050505050565b61071a610cdf565b610722610cbd565b8460040154806107375760c86020526001603ffd5b847f3af9e669000000000000000000000000000000000000000000000000000000008452306004850152602083602486855afa8061077a5760c96020526001603ffd5b508251808083101561078a578290505b80156107ed577f852a12e30000000000000000000000000000000000000000000000000000000086528060048701526020856024886000885af1806107d45760ca6020526001603ffd5b8551156107e65760cb6020526001603ffd5b8184039350505b50508015610840577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af183518115171561083e5760cc6020526001603ffd5b505b6000868815610881577fa9059cbb00000000000000000000000000000000000000000000000000000000865286600487015287602487015260449150600090505b6020858388848d5af1855115811517156108a05760cd6020526001603ffd5b50505050505050505050565b6000543318156108c15760006020526001603ffd5b600435602018156108d75760016020526001603ffd5b6024356044368260200260440118156108f55760026020526001603ffd5b6040513680600083376004810382828460006002545af18061091c5760036020526001603ffd5b8251602018156109315760046020526001603ffd5b60208301518518156109485760056020526001603ffd5b6000805b8681101561096f576020810260408601015180831792505060018101905061094c565b5080156109815760066020526001603ffd5b50505050600354602083028201825b81811015610a835780356040516004810160008252858318156109eb577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa806109e95760076020526001603ffd5b505b80518381600401558015610a74577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af181511581151715610a715760086020526001603ffd5b50505b50505050602081019050610990565b50505050505050565b610a94610cdf565b610a9c610cbd565b836004015480610ab15760646020526001603ffd5b7f17bfdfbc000000000000000000000000000000000000000000000000000000008352306004840152602082602485845afa80610af35760656020526001603ffd5b5060035482518080871015610b06578690505b8015610bd6577f4e4d9fea00000000000000000000000000000000000000000000000000000000865260048184861815610b6c577f0e75270200000000000000000000000000000000000000000000000000000000885282600489015260249150600090505b602087838a848a5af180610b855760666020526001603ffd5b3d60008114610ba15760208114610bb95760696020526001603ffd5b86881815610bb45760676020526001603ffd5b610bcc565b885115610bcb5760686020526001603ffd5b5b50838a0399505050505b50508415610c93577f1249c58b00000000000000000000000000000000000000000000000000000000845260048582841815610c3e577fa0712d6800000000000000000000000000000000000000000000000000000000865286600487015260249150600090505b602085838884885af13d60008114610c635760208114610c7b57606c6020526001603ffd5b84861815610c7657606a6020526001603ffd5b610c8e565b865115610c8d57606b6020526001603ffd5b5b505050505b505050505050565b6040518060800160405280600490602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea165627a7a723058201bb44523582a446744819810e62dd5646106cf449c34c4a61ad57724f5d966be0029";
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
