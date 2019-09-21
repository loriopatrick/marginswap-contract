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
public class MarginProxy {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516102963803806102968339818101604052604081101561003357600080fd5b81019080805190602001909291908051906020019092919050505081600155806002556001600355505061022a8061006c6000396000f3fe60806040526004361061003f5760003560e01c80633b1ca3b51461006657806380f76021146100b7578063893d20e81461010e578063ea87963414610165575b366000803760008036600080545af43d6000803e8060008114610061573d6000f35b3d6000fd5b34801561007257600080fd5b506100b56004803603602081101561008957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506101bc565b005b3480156100c357600080fd5b506100cc6101d8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561011a57600080fd5b506101236101e2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561017157600080fd5b5061017a6101ec565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6002543318156101d15760016020526001603ffd5b8060005550565b6000600254905090565b6000600154905090565b6000805490509056fea265627a7a72305820351285e6eca15cd358c6a6796537ffd73d521130fe4b498f1970940b9e6c02a664736f6c634300050a0032";
    public static Function setCode(String code_address) {
        return new Function(
            "setCode",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(code_address)
            ),
            Collections.emptyList()
        );
    }
    public static Function getParent() {
        return new Function(
            "getParent",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static GetparentReturnValue query_getParent(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        GetparentReturnValue returnValue = new GetparentReturnValue();
        returnValue.parent_address = (String) values.get(0).getValue();
        return returnValue;
    }
    public static GetparentReturnValue query_getParent(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_getParent(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function getOwner() {
        return new Function(
            "getOwner",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static GetownerReturnValue query_getOwner(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        GetownerReturnValue returnValue = new GetownerReturnValue();
        returnValue.owner_address = (String) values.get(0).getValue();
        return returnValue;
    }
    public static GetownerReturnValue query_getOwner(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_getOwner(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function getCode() {
        return new Function(
            "getCode",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static GetcodeReturnValue query_getCode(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        GetcodeReturnValue returnValue = new GetcodeReturnValue();
        returnValue.code_address = (String) values.get(0).getValue();
        return returnValue;
    }
    public static GetcodeReturnValue query_getCode(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_getCode(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static String DeployData(String owner, String parent_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(owner)
                , new org.web3j.abi.datatypes.Address(parent_address)
            )
        );
        return BINARY + encodedConstructor;
    }
    public static class GetparentReturnValue {
        public String parent_address;
    }
    public static class GetownerReturnValue {
        public String owner_address;
    }
    public static class GetcodeReturnValue {
        public String code_address;
    }
}
