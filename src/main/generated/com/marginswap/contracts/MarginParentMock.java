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
public class MarginParentMock {
    public static final String BINARY = "608060405234801561001057600080fd5b50610196806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80630a681c5914610030575b600080fd5b61007c6004803603604081101561004657600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061007e565b005b610086610126565b61008e610148565b60008385156100cf577fa9059cbb00000000000000000000000000000000000000000000000000000000845233600485015284602085015260249150600090505b6020838386848a5af1806100e85760016020526001603ffd5b3d6000811461010457602081146101095760036020526001603ffd5b61011c565b84511561011b5760026020526001603ffd5b5b5050505050505050565b6040518060400160405280600290602082028038833980820191505090505090565b604051806020016040528060019060208202803883398082019150509050509056fea165627a7a723058203ef35b6c0abc3e001974d13690e89296acee47afa170557de32ac15bf915148a0029";
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
}
