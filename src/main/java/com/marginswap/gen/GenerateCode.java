package com.marginswap.gen;

import java.io.File;
import java.io.IOException;

import static dev.dcn.web3.gen.GenerateContractCode.CompileContract;
import static dev.dcn.web3.gen.GenerateContractCode.ContractToJava;

public class GenerateCode {
    public static void main(String[] args) throws IOException, InterruptedException {
        ContractToJava(
                new File("src/main/resources/contracts/Margin.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        ContractToJava(
                new File("src/main/resources/contracts/CompoundMock.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        ContractToJava(
                new File("src/main/resources/contracts/ERC20.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        ContractToJava(
                new File("src/main/resources/contracts/ComptrollerMock.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        ContractToJava(
                new File("src/main/resources/contracts/TradeMock.sol"),
                new File("src/main/generated"),
                "com.marginswap.contracts"
        );

        CompileContract(
                new File("src/main/resources/contracts/Margin.sol"),
                new File("contracts-compiled/Margin")
        );

        CompileContract(
                new File("src/main/resources/contracts/ComptrollerMock.sol"),
                new File("contracts-compiled/ComptrollerMock")
        );

        CompileContract(
                new File("src/main/resources/contracts/CompoundMock.sol"),
                new File("contracts-compiled/CompoundMock")
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
