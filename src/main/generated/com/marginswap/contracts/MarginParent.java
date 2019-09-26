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
    public static final String BINARY = "6080604052604051610ac1380380610ac18339818101604052602081101561002657600080fd5b810190808051906020019092919050505033600055806002556001336003015550610a6b806100566000396000f3fe6080604052600436106100865760003560e01c8063372867521161005957806337286752146102275780633f843bae146102b85780634bb8596e1461030957806378ca76a314610320578063d48dd0491461037d57610086565b8063047acfaf14610088578063058d7c97146101245780630a681c591461017b5780630af13a24146101d6575b005b34801561009457600080fd5b506100d7600480360360208110156100ab57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103ce565b604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001821515151581526020019250505060405180910390f35b34801561013057600080fd5b506101396103e8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561018757600080fd5b506101d46004803603604081101561019e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506104cc565b005b3480156101e257600080fd5b50610225600480360360208110156101f957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610570565b005b34801561023357600080fd5b506102766004803603602081101561024a57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610600565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102c457600080fd5b50610307600480360360208110156102db57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610688565b005b34801561031557600080fd5b5061031e6106a4565b005b34801561032c57600080fd5b5061037b6004803603604081101561034357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035151590602001909291905050506106d0565b005b34801561038957600080fd5b506103cc600480360360208110156103a057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610703565b005b6000806103da83610600565b915081600301549050915091565b60006060604051806102c0016040528061029681526020016107a16102969139905061041261073a565b815160208301818101338152602081019050308152602081019050806040528181033381846000f596508661044c5760016020526001603ffd5b600187600301557f3b1ca3b5000000000000000000000000000000000000000000000000000000008552600254600486015260008060248760008b5af1806104995760026020526001603ffd5b50868552337fd1915076529a929900f0bed2467292f2d10fdeda6f13a14d8d793a45d7916eaf602087a250505050505090565b6104d461075c565b6104dc61077e565b33600301546104f05760016020526001603ffd5b600083338615610535577fa9059cbb00000000000000000000000000000000000000000000000000000000855233600486015285602486015286905060449250600091505b602084848785855af18061054e5760026020526001603ffd5b87156105665784516105655760036020526001603ffd5b5b5050505050505050565b600061057b33610600565b905061058561073a565b813b6105965760016020526001603ffd5b827401000000000000000000000000000000000000000301548083600301557f3b1ca3b50000000000000000000000000000000000000000000000000000000082528360048301526000806024846000875af1806105f95760026020526001603ffd5b5050505050565b60006060604051806102c0016040528061029681526020016107a161029691399050805160208201818101858152602081019050308152602081019050806040528181038083203060ff60a01b1786528760208701528060408701526055600b87012073ffffffffffffffffffffffffffffffffffffffff8116975050505050505050919050565b60005433181561069d5760016020526001603ffd5b8060015550565b600154803318156106ba5760016020526001603ffd5b6000805460030155600181600301558060005550565b6000543318156106e55760016020526001603ffd5b80827401000000000000000000000000000000000000000301555050565b6000543318156107185760016020526001603ffd5b6001817401000000000000000000000000000000000000000301558060025550565b6040518060400160405280600290602082028038833980820191505090505090565b6040518060600160405280600390602082028038833980820191505090505090565b604051806020016040528060019060208202803883398082019150509050509056fe608060405234801561001057600080fd5b506040516102963803806102968339818101604052604081101561003357600080fd5b81019080805190602001909291908051906020019092919050505081600155806002556001600355505061022a8061006c6000396000f3fe60806040526004361061003f5760003560e01c80633b1ca3b51461006657806380f76021146100b7578063893d20e81461010e578063ea87963414610165575b366000803760008036600080545af43d6000803e8060008114610061573d6000f35b3d6000fd5b34801561007257600080fd5b506100b56004803603602081101561008957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506101bc565b005b3480156100c357600080fd5b506100cc6101d8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561011a57600080fd5b506101236101e2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561017157600080fd5b5061017a6101ec565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6002543318156101d15760016020526001603ffd5b8060005550565b6000600254905090565b6000600154905090565b6000805490509056fea265627a7a72305820c1c7f4c7bd26890e7f00477b3ef68b6d45b2399e0b5eb13676dec4ad4737583e64736f6c634300050a0032a265627a7a723058208d21ce2ef9fe4e46d493fb63518938a4cf7ede0a0dc3890b1502e55f3d044a7364736f6c634300050a0032";
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
    public static Function setMarginCode(String margin_code_address) {
        return new Function(
            "setMarginCode",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(margin_code_address)
            ),
            Collections.emptyList()
        );
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
    public static Function managerPropose(String new_manager) {
        return new Function(
            "managerPropose",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(new_manager)
            ),
            Collections.emptyList()
        );
    }
    public static Function managerSet() {
        return new Function(
            "managerSet",
            Collections.emptyList(),
            Collections.emptyList()
        );
    }
    public static Function approveMarginCode(String margin_code_address, boolean approved) {
        return new Function(
            "approveMarginCode",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(margin_code_address)
                , new org.web3j.abi.datatypes.Bool(approved)
            ),
            Collections.emptyList()
        );
    }
    public static Function setDefautlMarginCode(String default_code) {
        return new Function(
            "setDefautlMarginCode",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(default_code)
            ),
            Collections.emptyList()
        );
    }
    public static String DeployData(String default_code) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(default_code)
            )
        );
        return BINARY + encodedConstructor;
    }
    public static class MarginSetup {
        public String owner;
        public String margin_address;
    }
    public static final Event MarginSetup_EVENT = new Event("MarginSetup",
        Arrays.asList(
            new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.Address>() {}
        )
    );
    public static final String MarginSetup_EVENT_HASH = EventEncoder.encode(MarginSetup_EVENT);
    public static MarginSetup ExtractMarginSetup(Log log) {
        List<String> topics = log.getTopics();
        if (topics.size() == 0 || !MarginSetup_EVENT_HASH.equals(topics.get(0))) {
            return null;
        }
        EventValues values = Contract.staticExtractEventParameters(MarginSetup_EVENT, log);
        MarginSetup event = new MarginSetup();
        event.owner = (String) values.getIndexedValues().get(0).getValue();
        event.margin_address = (String) values.getNonIndexedValues().get(0).getValue();
        return event;
    }
    public static class IsmarginsetupReturnValue {
        public String margin_contract;
        public boolean enabled;
    }
    public static class GetmarginaddressReturnValue {
        public String margin_contract;
    }
}
