package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.ComptrollerMock;
import com.marginswap.contracts.ERC20;
import com.marginswap.contracts.MarginSwap;
import dev.dcn.test.Accounts;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.util.Arrays;

import static com.greghaskins.spectrum.Spectrum.it;
import static dev.dcn.test.AssertHelpers.assertRevert;
import static dev.dcn.test.AssertHelpers.assertSuccess;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Spectrum.class)
public class MarginSwapTest {
    {
        Network.DescribeCheckpoint();

        it("should not be able to enter markets with non owner account", () -> {
            assertRevert("0x00", Accounts.getTx(10).sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Arrays.asList(Network.CEther, Network.CToken))));
        });

        it("should enter markets", () -> {

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Arrays.asList(Network.CEther, Network.CToken))));

            /* should add underlying lookup */
            {
                MarginSwap.LookupunderlyingReturnValue result;

                result = MarginSwap.query_lookupUnderlying(Network.Margin, Network.owner.getWeb3(),
                        MarginSwap.lookupUnderlying("0x000"));
                assertEquals(Network.CEther, result.result);

                result = MarginSwap.query_lookupUnderlying(Network.Margin, Network.owner.getWeb3(),
                        MarginSwap.lookupUnderlying(Network.Token));
                assertEquals(Network.CToken, result.result);
            }

            /* should approve transfer to compound */
            {
                ERC20.AllowanceReturnValue result;
                result = ERC20.query_allowance(Network.Token, Network.owner.getWeb3(),
                        ERC20.allowance(Network.Margin, Network.CToken));

                assertEquals(BigInteger.valueOf(2).pow(256).subtract(BigInteger.ONE), result.remaining);
            }

            /* comptroller should flag markets entered */
            {
                ComptrollerMock.HasenteredReturnValue result;

                result = ComptrollerMock.query_hasEntered(Network.Comptroller, Network.owner.getWeb3(),
                        ComptrollerMock.hasEntered(Network.CToken));
                assertTrue(result.result);

                result = ComptrollerMock.query_hasEntered(Network.Comptroller, Network.owner.getWeb3(),
                        ComptrollerMock.hasEntered(Network.CEther));
                assertTrue(result.result);
            }
        });
    }
}
