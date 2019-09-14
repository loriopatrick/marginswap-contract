package com.marginswap;

import com.marginswap.contracts.CompoundMock;
import com.marginswap.contracts.ComptrollerMock;
import com.marginswap.contracts.MarginParentMock;
import com.marginswap.contracts.MarginSwap;
import dev.dcn.test.Accounts;
import dev.dcn.test.StaticNetwork;
import dev.dcn.web3.EtherTransactions;

import java.math.BigInteger;

import static com.greghaskins.spectrum.Spectrum.*;

public class Network {
    public static final String Comptroller;
    public static final String CEther;
    public static final String CToken;
    public static final String MarginParent;
    public static final String Margin;
    public static final EtherTransactions owner;

    static {
        StaticNetwork.Start();

        owner = Accounts.getTx(0);

        try {
            Comptroller = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    ComptrollerMock.BINARY,
                    BigInteger.ZERO
            );

            MarginParent = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    MarginParentMock.BINARY,
                    BigInteger.ZERO
            );

            CEther = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    CompoundMock.DeployData(
                            new BigInteger("99999999999999999999999999999"),
                            "cEther", 8, "cEther"
                    ),
                    BigInteger.ZERO
            );

            CToken = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    CompoundMock.DeployData(
                            new BigInteger("99999999999999999999999999999"),
                            "cToken", 8, "cToken"
                    ),
                    BigInteger.ZERO
            );

            Margin = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    MarginSwap.DeployData(owner.getAddress(), MarginParent, Comptroller, CEther),
                    BigInteger.ZERO
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void DescribeCheckpoint() {
        beforeAll(StaticNetwork::Checkpoint);
        afterAll(StaticNetwork::Revert);
    }

    public static void DescribeCheckpointForEach() {
        beforeEach(StaticNetwork::Checkpoint);
        afterEach(StaticNetwork::Revert);
    }
}
