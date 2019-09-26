package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.ERC20;
import com.marginswap.contracts.MarginSwap;
import com.marginswap.contracts.mock.CompoundMock;
import com.marginswap.contracts.mock.ComptrollerMock;
import com.marginswap.contracts.mock.TradeMock;
import dev.dcn.test.Accounts;
import dev.dcn.test.StaticNetwork;
import dev.dcn.web3.EtherTransactions;
import org.junit.runner.RunWith;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static com.greghaskins.spectrum.Spectrum.it;
import static dev.dcn.test.AssertHelpers.assertSuccess;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(Spectrum.class)
public class MarginSwapFlowTest {
    {
        Network.DescribeCheckpoint();

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

        it("CToken: deposit should mint without loan", () -> {
            assertSuccess(Network.owner.sendCall(Network.CToken,
                    CompoundMock.setRate(200000000000000000L)));
            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, 3_000000000000000000L)));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 3_000000000000000000L)));

            /* ensure balance moved to CToken, not margin swap */
            ERC20.BalanceofReturnValue result;

            result = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin));
            assertEquals(BigInteger.ZERO, result.balance);

            result = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(Network.CToken));
            assertEquals(BigInteger.valueOf(3_000000000000000000L), result.balance);

            /* should have called mint, which will credit cToken to the margin contract */
            result = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin));
            assertEquals(new BigInteger("15000000000000000000"), result.balance);
        });


        it("CEth: deposit should mint without loan", () -> {
            assertSuccess(Network.owner.sendCall(Network.CEther,
                    CompoundMock.setRate(200000000000000000L)));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.depositEth(), BigInteger.valueOf(3_000000000000000000L)));

            /* ensure balance moved to CToken, not margin swap */
            assertEquals(
                    BigInteger.ZERO,
                    Network.owner.getWeb3().ethGetBalance(Network.Margin, DefaultBlockParameterName.LATEST)
                            .send().getBalance()
            );

            assertEquals(
                    BigInteger.valueOf(3_000000000000000000L),
                    Network.owner.getWeb3().ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST)
                            .send().getBalance()
            );

            /* should have called mint, which will credit cToken to the margin contract */
            ERC20.BalanceofReturnValue result;
            result = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin));
            assertEquals(new BigInteger("15000000000000000000"), result.balance);
        });

        EtherTransactions dest = Accounts.getTx(5);

        it("CToken: withdraw should redeem funds", () -> {

            BigInteger cBalanceBefore = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin)).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw(Network.Token, 1000000L, dest.getAddress())));

            BigInteger cBalanceAfter = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin)).balance;
            assertEquals(cBalanceBefore.subtract(BigInteger.valueOf(1000000L * 5)), cBalanceAfter);

            assertEquals(
                    BigInteger.valueOf(1000000L),
                    ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(dest.getAddress())).balance
            );

            assertEquals(
                    BigInteger.ZERO,
                    CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                            CompoundMock.borrowBalanceCurrent(Network.Margin)).value
            );
        });

        it("CToken: withdraw should borrow funds", () -> {
            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.transfer(Network.CToken, new BigInteger("10000000000000000000"))));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw(Network.Token, 3_000000000000000000L, dest.getAddress())));

            BigInteger cBalanceAfter = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.Margin)).balance;
            assertEquals(BigInteger.ZERO, cBalanceAfter);

            assertEquals(
                    BigInteger.valueOf(3_000000000000000000L + 1000000L),
                    ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                            ERC20.balanceOf(dest.getAddress())).balance
            );

            assertEquals(
                    BigInteger.valueOf(1000000L),
                    CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                            CompoundMock.borrowBalanceCurrent(Network.Margin)).value
            );
        });

        it("Should be able to trade with margin", () -> {
            ERC20.BalanceofReturnValue balanceOf;

            String tradeAddress = Network.owner.deployContract(BigInteger.ZERO, StaticNetwork.GAS_LIMIT,
                    TradeMock.BINARY, BigInteger.valueOf(7_000000000000000000L));

            long tradeInput = 2_100000000000000000L;
            long tradeOutput = 6_300000000000000000L;

            String encodedTrade = FunctionEncoder.encode(TradeMock.trade(
                    Network.Token, "0x0", tradeInput, tradeOutput
            ));

            assertSuccess(Network.owner.sendCall(Network.Token, ERC20.transfer(
                    Network.Parent,
                    5_000000000000000000L
            )));


            balanceOf = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin));
            assertEquals(BigInteger.valueOf(0), balanceOf.balance);

            TransactionReceipt transactionReceipt = assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.trade(
                            Network.Token,
                            tradeInput,
                            "0x0",
                            tradeOutput,
                            tradeAddress,
                            encodedTrade
                    )
            ));

            List<Log> logs = transactionReceipt.getLogs();
            assertEquals(7, logs.size());

            MarginSwap.Trade tradeLog = MarginSwap.ExtractTrade(logs.get(6));
            assertNotNull(tradeLog);
            assertEquals(Network.Token, tradeLog.from_asset);
            assertEquals("0x0000000000000000000000000000000000000000", tradeLog.to_asset);
            assertEquals(BigInteger.valueOf(tradeInput), tradeLog.input);
            assertEquals(BigInteger.valueOf(tradeOutput), tradeLog.output);
            assertEquals(BigInteger.valueOf(10500000000000000L), tradeLog.input_fee);
            assertEquals(tradeAddress, tradeLog.trade_contract);

            /* balance of parent should include fee */
            balanceOf = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(Network.Parent));
            assertEquals(BigInteger.valueOf(5_010500000000000000L), balanceOf.balance);

            /* margin should not have any balance */
            balanceOf = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(Network.Margin));
            assertEquals(BigInteger.valueOf(0), balanceOf.balance);

            /* balance of trade contract should have input */
            balanceOf = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(), ERC20.balanceOf(tradeAddress));
            assertEquals(BigInteger.valueOf(tradeInput), balanceOf.balance);
        });

    }
}
