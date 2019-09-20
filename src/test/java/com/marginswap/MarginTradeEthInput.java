package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.CompoundMock;
import com.marginswap.contracts.ERC20;
import com.marginswap.contracts.MarginSwap;
import com.marginswap.contracts.TradeMock;
import dev.dcn.test.StaticNetwork;
import org.junit.runner.RunWith;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;
import java.util.Arrays;

import static com.greghaskins.spectrum.Spectrum.it;
import static dev.dcn.test.AssertHelpers.assertSuccess;

@RunWith(Spectrum.class)
public class MarginTradeEthInput {
    {
        Network.DescribeCheckpoint();

        it("should be able to trade ETH for asset", () -> {
            /* setup market */
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Arrays.asList(Network.CEther, Network.CToken))));

            long tradeInput = 2_100000000000000000L;
            long tradeOutput = 6_300000000000000000L;

            /* setup compound */
            assertSuccess(Network.owner.sendCall(Network.CToken,
                    CompoundMock.setRate(BigInteger.valueOf(50))));
            assertSuccess(Network.owner.sendCall(Network.CEther,
                    CompoundMock.setRate(BigInteger.valueOf(50))));

            assertSuccess(Network.owner.sendCall(Network.CEther, CompoundMock.mint(),
                    BigInteger.valueOf(tradeInput + tradeInput / 200)));

            /* deposit eth to parent */
            assertSuccess(Network.owner.sendCall(BigInteger.ZERO, StaticNetwork.GAS_LIMIT, Network.Parent,
                    "0x", BigInteger.valueOf(tradeInput)));

            /* setup trade contract */
            String tradeAddress = Network.owner.deployContract(BigInteger.ZERO, StaticNetwork.GAS_LIMIT,
                    TradeMock.BINARY, BigInteger.valueOf(0));
            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.transfer(tradeAddress, BigInteger.valueOf(tradeOutput))));

            String encodedTrade = FunctionEncoder.encode(TradeMock.trade(
                    "0x0", Network.Token, tradeInput, tradeOutput
            ));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.trade(
                            "0x0",
                            tradeInput,
                            Network.Token,
                            tradeOutput,
                            tradeAddress,
                            encodedTrade
                    )
            ));
        });
    }
}
