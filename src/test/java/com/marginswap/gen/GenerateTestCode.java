package com.marginswap.gen;

import java.io.File;
import java.io.IOException;

import static dev.dcn.web3.gen.GenerateContractCode.CompileContract;
import static dev.dcn.web3.gen.GenerateContractCode.ContractToJava;

public class GenerateTestCode {
    public static void main(String[] args) throws IOException, InterruptedException {
        ContractToJava(
                new File("src/test/resources/contracts/CompoundMock.sol"),
                new File("src/test/generated"),
                "com.marginswap.contracts.mock"
        );

        ContractToJava(
                new File("src/test/resources/contracts/ComptrollerMock.sol"),
                new File("src/test/generated"),
                "com.marginswap.contracts.mock"
        );

        ContractToJava(
                new File("src/test/resources/contracts/TradeMock.sol"),
                new File("src/test/generated"),
                "com.marginswap.contracts.mock"
        );
    }
}
