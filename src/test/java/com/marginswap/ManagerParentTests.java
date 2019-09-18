package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.ERC20;
import com.marginswap.contracts.MarginParent;
import dev.dcn.test.Accounts;
import dev.dcn.test.StaticNetwork;
import org.junit.runner.RunWith;

import java.math.BigInteger;

import static com.greghaskins.spectrum.Spectrum.it;
import static dev.dcn.test.AssertHelpers.assertRevert;
import static dev.dcn.test.AssertHelpers.assertSuccess;

@RunWith(Spectrum.class)
public class ManagerParentTests {
    {
        Network.DescribeCheckpoint();

        it("Owner should be able to get ETH from parent", () -> {
            assertSuccess(Network.owner.sendCall(BigInteger.ZERO, StaticNetwork.GAS_LIMIT, Network.Parent, "0x", BigInteger.valueOf(100_000)));
            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Parent, MarginParent.getCapital("0x0", 50_000)));
            assertSuccess(Network.owner.sendCall(Network.Parent, MarginParent.getCapital("0x0", 50_000)));
        });

        it("Owner should be able to get ERC20 from parent", () -> {
            assertSuccess(Network.owner.sendCall(Network.Token, ERC20.transfer(
                    Network.Parent,
                    5_000000000000000000L
            )));

            assertSuccess(Network.owner.sendCall(Network.Parent, MarginParent.getCapital(Network.Token, 3_000000000000000000L)));
        });

        it("should be able to update manager", () -> {
            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Parent, MarginParent.managerPropose(Accounts.getTx(3).getAddress())));
            assertSuccess(Network.owner.sendCall(Network.Parent, MarginParent.managerPropose(Accounts.getTx(3).getAddress())));
            assertRevert("0x01", Network.owner.sendCall(Network.Parent, MarginParent.managerSet()));
            assertSuccess(Accounts.getTx(3).sendCall(Network.Parent, MarginParent.managerSet()));

            assertRevert("0x01", Network.owner.sendCall(Network.Parent, MarginParent.getCapital("0x0", 50_000)));
            assertSuccess(Accounts.getTx(3).sendCall(Network.Parent, MarginParent.getCapital("0x0", 50_000)));
        });
    }
}
