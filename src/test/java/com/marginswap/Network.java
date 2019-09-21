package com.marginswap;

import com.marginswap.contracts.*;
import dev.dcn.test.Accounts;
import dev.dcn.test.StaticNetwork;
import dev.dcn.web3.EtherTransactions;

import java.math.BigInteger;

import static com.greghaskins.spectrum.Spectrum.*;
import static dev.dcn.test.AssertHelpers.assertSuccess;
import static junit.framework.TestCase.assertTrue;

public class Network {
    public static final String Comptroller;
    public static final String CEther;
    public static final String CToken;
    public static final String Token;
    public static final String Impl;
    public static final String Parent;
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

            CEther = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    CompoundMock.DeployData(
                            new BigInteger("99999999999999999999999999999"),
                            "cEther", 8, "cEther", "0x0"

                    ),
                    BigInteger.ZERO
            );

            Impl = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    MarginSwap.BINARY,
                    BigInteger.ZERO
            );

            Parent = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    MarginParent.DeployData(Impl),
                    BigInteger.ZERO
            );

            Token = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    ERC20.DeployData(
                            new BigInteger("999999999999999999999999999999999999"),
                            "token", 18, "token"
                    ),
                    BigInteger.ZERO
            );

            CToken = owner.deployContract(
                    BigInteger.ZERO,
                    StaticNetwork.GAS_LIMIT,
                    CompoundMock.DeployData(
                            new BigInteger("99999999999999999999999999999"),
                            "cToken", 8, "cToken", Token
                    ),
                    BigInteger.ZERO
            );

            assertSuccess(owner.sendCall(Parent, MarginParent.setupMargin()));

            MarginParent.IsmarginsetupReturnValue isSetup = MarginParent.query_isMarginSetup(
                    Parent, owner.getWeb3(),
                    MarginParent.isMarginSetup(owner.getAddress())
            );

            assertTrue(isSetup.enabled);
            Margin = isSetup.margin_contract;

            assertSuccess(owner.sendCall(Margin, MarginSwap.setComptrollerAddress(Comptroller)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void DescribeCheckpoint() {
        try {
            owner.reloadNonce();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        beforeAll(StaticNetwork::Checkpoint);
        afterAll(StaticNetwork::Revert);
    }

    public static void DescribeCheckpointForEach() {
        beforeEach(StaticNetwork::Checkpoint);
        afterEach(StaticNetwork::Revert);
    }
}
