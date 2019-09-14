package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.ComptrollerMock;
import com.marginswap.contracts.MarginSwap;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static com.greghaskins.spectrum.Spectrum.it;
import static dev.dcn.test.AssertHelpers.assertSuccess;
import static org.junit.Assert.*;

@RunWith(Spectrum.class)
public class MarginSwapTest {
    {
        Network.DescribeCheckpoint();

        it("should enter markets", () -> {
            ComptrollerMock.HasenteredReturnValue result;

            result = ComptrollerMock.query_hasEntered(Network.Comptroller, Network.owner.getWeb3(),
                    ComptrollerMock.hasEntered(Network.CToken));
            assertFalse(result.result);

            result = ComptrollerMock.query_hasEntered(Network.Comptroller, Network.owner.getWeb3(),
                    ComptrollerMock.hasEntered(Network.CEther));
            assertFalse(result.result);


            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Arrays.asList(Network.CEther, Network.CToken))));


            result = ComptrollerMock.query_hasEntered(Network.Comptroller, Network.owner.getWeb3(),
                    ComptrollerMock.hasEntered(Network.CToken));
            assertTrue(result.result);

            result = ComptrollerMock.query_hasEntered(Network.Comptroller, Network.owner.getWeb3(),
                    ComptrollerMock.hasEntered(Network.CEther));
            assertTrue(result.result);
        });
    }
}
