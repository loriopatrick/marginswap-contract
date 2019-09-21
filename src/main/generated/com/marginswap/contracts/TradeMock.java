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
public class TradeMock {
    public static final String BINARY = "6080604052610208806100136000396000f3fe60806040526004361061001e5760003560e01c80634849b6c814610023575b600080fd5b6100996004803603608081101561003957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019092919050505061009b565b005b6100a361018f565b6100ab6101b1565b851561010d577f23b872dd0000000000000000000000000000000000000000000000000000000082523360048301523060248301528360448301526000815260208160648460008a5af18151158115171561010b5760036020526001603ffd5b505b600083338715610152577fa9059cbb00000000000000000000000000000000000000000000000000000000855233600486015285602486015287905060449250600091505b602084848785855af18061016b5760046020526001603ffd5b88156101835784516101825760056020526001603ffd5b5b50505050505050505050565b6040518060800160405280600490602082028038833980820191505090505090565b604051806020016040528060019060208202803883398082019150509050509056fea265627a7a723058201889b444128700dc47830f6c88dde9ae9329c12078d3424fb257c4e2652c2bf764736f6c634300050a0032";
    public static Function trade(String from_asset, String to_asset, BigInteger input, BigInteger output) {
        return new Function(
            "trade",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(from_asset)
                , new org.web3j.abi.datatypes.Address(to_asset)
                , new UnsignedNumberType(256, input)
                , new UnsignedNumberType(256, output)
            ),
            Collections.emptyList()
        );
    }
    public static Function trade(String from_asset, String to_asset, long input, long output) {
        return trade(
            from_asset
            , to_asset
            , new BigInteger(Long.toUnsignedString(input))
            , new BigInteger(Long.toUnsignedString(output))
        );
    }
    public static String DeployData() {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Collections.emptyList()
        );
        return BINARY + encodedConstructor;
    }
}
