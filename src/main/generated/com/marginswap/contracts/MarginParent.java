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
public class MarginParent {
    public static final String BINARY = "60806040526040516113993803806113998339818101604052604081101561002657600080fd5b8101908080519060200190929190805190602001909291905050508160005580600155505061133f8061005a6000396000f3fe60806040526004361061004a5760003560e01c8063047acfaf1461004c578063058d7c97146100e85780630a681c591461013f57806325765f821461019a57806337286752146101f1575b005b34801561005857600080fd5b5061009b6004803603602081101561006f57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610282565b604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001821515151581526020019250505060405180910390f35b3480156100f457600080fd5b506100fd61029c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561014b57600080fd5b506101986004803603604081101561016257600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610335565b005b3480156101a657600080fd5b506101af6103c7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101fd57600080fd5b506102406004803603602081101561021457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610401565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008061028e83610401565b915081600201549050915091565b6000606060405180610e600160405280610e2781526020016104e4610e279139905080516020820181810133815260208101905030815260208101905060005481526020810190506001548152602081019050806040528181033381846000f595508561030e5760016020526001603ffd5b85740100000000000000000000000000000000000000025560018660020155505050505090565b61033d61049f565b6103456104c1565b33600201546103595760016020526001603ffd5b60008333861561039e577fa9059cbb00000000000000000000000000000000000000000000000000000000855233600486015285602486015286905060449250600091505b602084848785855af1845115811517156103bd5760026020526001603ffd5b5050505050505050565b7401000000000000000000000000000000000000000260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000606060405180610e600160405280610e2781526020016104e4610e279139905080516020820181810185815260208101905030815260208101905060005481526020810190506001548152602081019050806040528181038083203060ff60a01b1786528760208701528060408701526055600b87012073ffffffffffffffffffffffffffffffffffffffff8116975050505050505050919050565b6040518060600160405280600390602082028038833980820191505090505090565b604051806020016040528060019060208202803883398082019150509050509056fe608060405234801561001057600080fd5b50604051610e27380380610e278339818101604052608081101561003357600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291905050508360005582600155816002558060035550505050610da2806100856000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee366146101455780635ccbf176146101d657806369328dec14610305578063c29982381461038057610070565b80632d891fba14610072578063439370b1146100ed57806347e7ef24146100f7575b005b34801561007e57600080fd5b506100eb6004803603606081101561009557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610406565b005b6100f5610420565b005b6101436004803603604081101561010d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061042d565b005b34801561015157600080fd5b506101946004803603602081101561016857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d6565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610303600480360360c08110156101ec57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561027d57600080fd5b82018360208201111561028f57600080fd5b803590602001918460018302840111640100000000831117156102b157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e4565b005b34801561031157600080fd5b5061037e6004803603606081101561032857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610742565b005b34801561038c57600080fd5b50610404600480360360208110156103a357600080fd5b81019080803590602001906401000000008111156103c057600080fd5b8201836020820111156103d257600080fd5b803590602001918460208302840111640100000000831117156103f457600080fd5b9091929391929390505050610767565b005b60005433181561041b5760016020526001603ffd5b505050565b61042b60003461042d565b565b610435610d07565b61043d610d29565b348318841516156104535760016020526001603ffd5b83156104c657341561046a5760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af1815115811517156104c45760036020526001603ffd5b505b6104d08484610947565b50505050565b600081600401549050919050565b6104ec610d07565b6104f4610d29565b6000805433181561050a5760016020526001603ffd5b6001547f0a681c590000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af1806105575760026020526001603ffd5b5089156105c557341561056f5760036020526001603ffd5b7f095ea7b30000000000000000000000000000000000000000000000000000000084528560048501528860248501526000835260208360448660008e5af1835115811517156105c35760046020526001603ffd5b505b30318815610619577f70a08231000000000000000000000000000000000000000000000000000000008552336004860152600084526020846024878c5afa806106135760056020526001603ffd5b50835190505b863b61062a5760056020526001603ffd5b600080875160208901348b5af1806106475760076020526001603ffd5b507f095ea7b3000000000000000000000000000000000000000000000000000000008552866004860152600060248601526000845260208460448760008f5af18451158115171561069d5760086020526001603ffd5b50303189156106f2577f70a08231000000000000000000000000000000000000000000000000000000008652336004870152600085526020856024888d5afa806106ec5760096020526001603ffd5b50845190505b8181101561070557600a6020526001603ffd5b81810393508884101561071d57600b6020526001603ffd5b50505061072a8782610947565b6107378989600154610b56565b505050505050505050565b6000543318156107575760016020526001603ffd5b610762838383610b56565b505050565b60005433181561077c5760006020526001603ffd5b600435602018156107925760016020526001603ffd5b6024356044368260200260440118156107b05760026020526001603ffd5b6040513680600083376004810382828460006002545af1806107d75760036020526001603ffd5b8251602018156107ec5760046020526001603ffd5b60208301518518156108035760056020526001603ffd5b6000805b8681101561082a5760208102604086010151808317925050600181019050610807565b50801561083c5760066020526001603ffd5b50505050600354602083028201825b8181101561093e5780356040516004810160008252858318156108a6577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa806108a45760076020526001603ffd5b505b8051838160040155801561092f577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af18151158115171561092c5760086020526001603ffd5b50505b5050505060208101905061084b565b50505050505050565b61094f610d4b565b610957610d29565b83600401548061096c5760646020526001603ffd5b7f17bfdfbc000000000000000000000000000000000000000000000000000000008352306004840152602082602485845afa806109ae5760656020526001603ffd5b50600354825180808710156109c1578690505b8015610a91577f4e4d9fea00000000000000000000000000000000000000000000000000000000865260048184861815610a27577f0e75270200000000000000000000000000000000000000000000000000000000885282600489015260249150600090505b602087838a848a5af180610a405760666020526001603ffd5b3d60008114610a5c5760208114610a745760696020526001603ffd5b86881815610a6f5760676020526001603ffd5b610a87565b885115610a865760686020526001603ffd5b5b50838a0399505050505b50508415610b4e577f1249c58b00000000000000000000000000000000000000000000000000000000845260048582841815610af9577fa0712d6800000000000000000000000000000000000000000000000000000000865286600487015260249150600090505b602085838884885af13d60008114610b1e5760208114610b3657606c6020526001603ffd5b84861815610b3157606a6020526001603ffd5b610b49565b865115610b4857606b6020526001603ffd5b5b505050505b505050505050565b610b5e610d4b565b610b66610d29565b846004015480610b7b5760c86020526001603ffd5b847f3af9e669000000000000000000000000000000000000000000000000000000008452306004850152602083602486855afa80610bbe5760c96020526001603ffd5b5082518080831015610bce578290505b8015610c31577f852a12e30000000000000000000000000000000000000000000000000000000086528060048701526020856024886000885af180610c185760ca6020526001603ffd5b855115610c2a5760cb6020526001603ffd5b8184039350505b50508015610c84577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af1835181151715610c825760cc6020526001603ffd5b505b600086868915610cc9577fa9059cbb00000000000000000000000000000000000000000000000000000000875287600488015288602488015289905060449250600091505b602086848985855af180610ce25760cd6020526001603ffd5b8a15610cfa578651610cf95760ce6020526001603ffd5b5b5050505050505050505050565b6040518060800160405280600490602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea265627a7a72315820451466ca06df5e83743afdd41d19e06a8cb9d69adb99774f632fa794870456ec64736f6c634300050b0032a265627a7a72315820d67d5698a3fcbce78bbdae44871f7f6d7027b62b1bb411f26e46f6bc73328a1f64736f6c634300050b0032";
    public static Function isMarginSetup(String owner) {
        return new Function(
            "isMarginSetup",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(owner)
            ),
            Arrays.asList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
                , new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static IsmarginsetupReturnValue query_isMarginSetup(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        IsmarginsetupReturnValue returnValue = new IsmarginsetupReturnValue();
        returnValue.margin_contract = (String) values.get(0).getValue();
        returnValue.enabled = (Boolean) values.get(1).getValue();
        return returnValue;
    }
    public static IsmarginsetupReturnValue query_isMarginSetup(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_isMarginSetup(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function setupMargin() {
        return new Function(
            "setupMargin",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static Function getCapital(String asset, BigInteger amount) {
        return new Function(
            "getCapital",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(asset)
                , new UnsignedNumberType(256, amount)
            ),
            Collections.emptyList()
        );
    }
    public static Function getCapital(String asset, long amount) {
        return getCapital(
            asset
            , new BigInteger(Long.toUnsignedString(amount))
        );
    }
    public static Function _last_create_address() {
        return new Function(
            "_last_create_address",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static LastCreateAddressReturnValue query__last_create_address(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        LastCreateAddressReturnValue returnValue = new LastCreateAddressReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static LastCreateAddressReturnValue query__last_create_address(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query__last_create_address(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function getMarginAddress(String owner) {
        return new Function(
            "getMarginAddress",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(owner)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static GetmarginaddressReturnValue query_getMarginAddress(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        GetmarginaddressReturnValue returnValue = new GetmarginaddressReturnValue();
        returnValue.margin_contract = (String) values.get(0).getValue();
        return returnValue;
    }
    public static GetmarginaddressReturnValue query_getMarginAddress(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_getMarginAddress(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static String DeployData(String comptroller_address, String cEther_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(comptroller_address)
                , new org.web3j.abi.datatypes.Address(cEther_address)
            )
        );
        return BINARY + encodedConstructor;
    }
    public static class IsmarginsetupReturnValue {
        public String margin_contract;
        public boolean enabled;
    }
    public static class LastCreateAddressReturnValue {
        public String value;
    }
    public static class GetmarginaddressReturnValue {
        public String margin_contract;
    }
}
