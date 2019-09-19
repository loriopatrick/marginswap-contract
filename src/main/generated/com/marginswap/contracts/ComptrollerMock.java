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
public class ComptrollerMock {
    public static final String BINARY = "608060405234801561001057600080fd5b506102c0806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806324d70dea146100465780633dc86a63146100a2578063c2998238146100d0575b600080fd5b6100886004803603602081101561005c57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506101c2565b604051808215151515815260200191505060405180910390f35b6100ce600480360360208110156100b857600080fd5b81019080803590602001909291905050506101d0565b005b610147600480360360208110156100e657600080fd5b810190808035906020019064010000000081111561010357600080fd5b82018360208201111561011557600080fd5b8035906020019184602083028401116401000000008311171561013757600080fd5b90919293919293905050506101ee565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561018757808201518184015260208101905061016c565b50505050905090810190601f1680156101b45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b600081600001549050919050565b80740100000000000000000000000000000000000000008190555050565b606060043560040180356020820191508060200282016040518060208152602081019050838152602081019050740100000000000000000000000000000000000000005460005b84871015610284578635602088019750600183831c1660008114610260576001811461026c57610271565b60018260000155610271565b600185525b5060208401935050600181019050610235565b5082820383f3fea265627a7a72305820b6594fc2eca01333180799a525a4112aeafe5c13fe26dab60f35d9777954a08a64736f6c634300050a0032";
    public static Function hasEntered(String addr) {
        return new Function(
            "hasEntered",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(addr)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static HasenteredReturnValue query_hasEntered(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        HasenteredReturnValue returnValue = new HasenteredReturnValue();
        returnValue.result = (Boolean) values.get(0).getValue();
        return returnValue;
    }
    public static HasenteredReturnValue query_hasEntered(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_hasEntered(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function setShouldReturnError(BigInteger value) {
        return new Function(
            "setShouldReturnError",
            Collections.singletonList(
                new UnsignedNumberType(256, value)
            ),
            Collections.emptyList()
        );
    }
    public static Function setShouldReturnError(long value) {
        return setShouldReturnError(
            new BigInteger(Long.toUnsignedString(value))
        );
    }
    public static Function enterMarkets(List<String> cTokens) {
        return new Function(
            "enterMarkets",
            Collections.singletonList(
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(org.web3j.abi.datatypes.Address.class, cTokens.stream().map(org.web3j.abi.datatypes.Address::new).collect(Collectors.toList()))
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.DynamicBytes>() {}
            )
        );
    }
    public static class HasenteredReturnValue {
        public boolean result;
    }
}
