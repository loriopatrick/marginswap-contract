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
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161130e38038061130e8339818101604052604081101561003357600080fd5b810190808051906020019092919080519060200190929190505050816000558060015550506112a7806100676000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c8063047acfaf1461005c578063058d7c97146100eb5780630a681c591461013557806325765f821461018357806337286752146101cd575b600080fd5b61009e6004803603602081101561007257600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610251565b604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001821515151581526020019250505060405180910390f35b6100f361026b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101816004803603604081101561014b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610304565b005b61018b610396565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61020f600480360360208110156101e357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103d0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008061025d836103d0565b915081600201549050915091565b6000606060405180610de00160405280610dc081526020016104b3610dc09139905080516020820181810133815260208101905030815260208101905060005481526020810190506001548152602081019050806040528181033381846000f59550856102dd5760016020526001603ffd5b85740100000000000000000000000000000000000000025560018660020155505050505090565b61030c61046e565b610314610490565b33600201546103285760016020526001603ffd5b60008333861561036d577fa9059cbb00000000000000000000000000000000000000000000000000000000855233600486015285602486015286905060449250600091505b602084848785855af18451158115171561038c5760026020526001603ffd5b5050505050505050565b7401000000000000000000000000000000000000000260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000606060405180610de00160405280610dc081526020016104b3610dc09139905080516020820181810185815260208101905030815260208101905060005481526020810190506001548152602081019050806040528181038083203060ff60a01b1786528760208701528060408701526055600b87012073ffffffffffffffffffffffffffffffffffffffff8116975050505050505050919050565b6040518060600160405280600390602082028038833980820191505090505090565b604051806020016040528060019060208202803883398082019150509050509056fe608060405234801561001057600080fd5b50604051610dc0380380610dc08339818101604052608081101561003357600080fd5b81019080805190602001909291908051906020019092919080519060200190929190805190602001909291905050508360005582600155816002558060035550505050610d3b806100856000396000f3fe6080604052600436106100705760003560e01c806357aee3661161004e57806357aee366146101485780635ccbf176146101d957806369328dec14610308578063c29982381461038357610070565b80632d891fba14610075578063439370b1146100f057806347e7ef24146100fa575b600080fd5b34801561008157600080fd5b506100ee6004803603606081101561009857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610409565b005b6100f8610423565b005b6101466004803603604081101561011057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610430565b005b34801561015457600080fd5b506101976004803603602081101561016b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610306600480360360c08110156101ef57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561028057600080fd5b82018360208201111561029257600080fd5b803590602001918460018302840111640100000000831117156102b457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e7565b005b34801561031457600080fd5b506103816004803603606081101561032b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610712565b005b34801561038f57600080fd5b50610407600480360360208110156103a657600080fd5b81019080803590602001906401000000008111156103c357600080fd5b8201836020820111156103d557600080fd5b803590602001918460208302840111640100000000831117156103f757600080fd5b90919293919293905050506108b1565b005b60005433181561041e5760016020526001603ffd5b505050565b61042e600034610430565b565b610438610ca0565b610440610cc2565b348318841516156104565760016020526001603ffd5b83156104c957341561046d5760026020526001603ffd5b7f23b872dd000000000000000000000000000000000000000000000000000000008252336004830152306024830152826044830152600081526020816064846000885af1815115811517156104c75760036020526001603ffd5b505b6104d38484610a91565b50505050565b600081600401549050919050565b6104ef610ca0565b6104f7610cc2565b6000805433181561050d5760016020526001603ffd5b6001547fab9567040000000000000000000000000000000000000000000000000000000084528960048501528860248501526000806044866000855af18061055a5760026020526001603ffd5b5089156105c25734156105725760036020526001603ffd5b7fb759f9540000000000000000000000000000000000000000000000000000000084528860048501526000835260208360248660008a5af1835115811517156105c05760046020526001603ffd5b505b7f70a0823100000000000000000000000000000000000000000000000000000000845287600485015260008352602083602486335afa806106085760056020526001603ffd5b508251600080875160208901348b5af1806106285760066020526001603ffd5b507fb759f954000000000000000000000000000000000000000000000000000000008552600060048601526000845260208460248760008b5af1845115811517156106785760076020526001603ffd5b507f70a0823100000000000000000000000000000000000000000000000000000000855288600486015260008452602084602487335afa806106bf5760086020526001603ffd5b508351808210156106d55760096020526001603ffd5b8181039350888410156106ed57600a6020526001603ffd5b5050506106fa8782610a91565b6107078989600154610712565b505050505050505050565b61071a610ce4565b610722610cc2565b8460040154806107375760c86020526001603ffd5b847f3af9e669000000000000000000000000000000000000000000000000000000008452306004850152602083602486855afa8061077a5760c96020526001603ffd5b508251808083101561078a578290505b80156107ed577f852a12e30000000000000000000000000000000000000000000000000000000086528060048701526020856024886000885af1806107d45760ca6020526001603ffd5b8551156107e65760cb6020526001603ffd5b8184039350505b50508015610840577fc5ebeaec0000000000000000000000000000000000000000000000000000000084528060048501526020836024866000865af183518115171561083e5760cc6020526001603ffd5b505b600086868915610885577fa9059cbb00000000000000000000000000000000000000000000000000000000875287600488015288602488015289905060449250600091505b602086848985855af1865115811517156108a45760cd6020526001603ffd5b5050505050505050505050565b6000543318156108c65760006020526001603ffd5b600435602018156108dc5760016020526001603ffd5b6024356044368260200260440118156108fa5760026020526001603ffd5b6040513680600083376004810382828460006002545af1806109215760036020526001603ffd5b8251602018156109365760046020526001603ffd5b602083015185181561094d5760056020526001603ffd5b6000805b868110156109745760208102604086010151808317925050600181019050610951565b5080156109865760066020526001603ffd5b50505050600354602083028201825b81811015610a885780356040516004810160008252858318156109f0577f6f307dc3000000000000000000000000000000000000000000000000000000008252602081600484865afa806109ee5760076020526001603ffd5b505b80518381600401558015610a79577f095ea7b30000000000000000000000000000000000000000000000000000000083528360048401527fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff602484015260448301600081526020816044866000865af181511581151715610a765760086020526001603ffd5b50505b50505050602081019050610995565b50505050505050565b610a99610ce4565b610aa1610cc2565b836004015480610ab65760646020526001603ffd5b7f17bfdfbc000000000000000000000000000000000000000000000000000000008352306004840152602082602485845afa80610af85760656020526001603ffd5b5060035482518080871015610b0b578690505b8015610bdb577f4e4d9fea00000000000000000000000000000000000000000000000000000000865260048184861815610b71577f0e75270200000000000000000000000000000000000000000000000000000000885282600489015260249150600090505b602087838a848a5af180610b8a5760666020526001603ffd5b3d60008114610ba65760208114610bbe5760696020526001603ffd5b86881815610bb95760676020526001603ffd5b610bd1565b885115610bd05760686020526001603ffd5b5b50838a0399505050505b50508415610c98577f1249c58b00000000000000000000000000000000000000000000000000000000845260048582841815610c43577fa0712d6800000000000000000000000000000000000000000000000000000000865286600487015260249150600090505b602085838884885af13d60008114610c685760208114610c8057606c6020526001603ffd5b84861815610c7b57606a6020526001603ffd5b610c93565b865115610c9257606b6020526001603ffd5b5b505050505b505050505050565b6040518060800160405280600490602082028038833980820191505090505090565b6040518060200160405280600190602082028038833980820191505090505090565b604051806040016040528060029060208202803883398082019150509050509056fea265627a7a72315820c16568607605cb048511fce5714273e12bfb5164a2e129b35cd31d98c72bab3164736f6c634300050b0032a265627a7a723158202c646e5e2ce4de105e81509311dc86a2960c2513b030015858808686f821e3eb64736f6c634300050b0032";
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
