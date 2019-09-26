package com.marginswap.gen;

import java.io.File;
import java.io.IOException;

import static dev.dcn.web3.gen.GenerateContractCode.CompileContract;
import static dev.dcn.web3.gen.GenerateContractCode.ContractToJava;

public class GenerateCode {
    public static void main(String[] args) throws IOException, InterruptedException {
        ContractToJava(
                new File("src/main/resources/contracts/MarginSwap.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        ContractToJava(
                new File("src/main/resources/contracts/MarginProxy.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        ContractToJava(
                new File("src/main/resources/contracts/ERC20.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        CompileContract(
                new File("src/main/resources/contracts/MarginSwap.sol"),
                new File("contracts-compiled/MarginSwap")
        );

        CompileContract(
                new File("src/main/resources/contracts/MarginProxy.sol"),
                new File("contracts-compiled/MarginProxy")
        );

        ContractToJava(
                new File("src/main/resources/contracts/MarginParent.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        CompileContract(
                new File("src/main/resources/contracts/MarginParent.sol"),
                new File("contracts-compiled/MarginParent")
        );
    }
}
