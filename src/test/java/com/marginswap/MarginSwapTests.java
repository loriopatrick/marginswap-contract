package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.ERC20;
import com.marginswap.contracts.MarginSwap;
import com.marginswap.contracts.mock.CompoundMock;
import dev.dcn.test.Accounts;
import org.junit.runner.RunWith;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;
import java.util.Collections;

import static com.greghaskins.spectrum.Spectrum.it;
import static dev.dcn.test.AssertHelpers.assertRevert;
import static dev.dcn.test.AssertHelpers.assertSuccess;
import static org.junit.Assert.assertEquals;

@RunWith(Spectrum.class)
public class MarginSwapTests {
    {
        Network.DescribeCheckpointForEach();

        it("only owner should be able to setComptroller", () -> {
            Function function = MarginSwap.setComptrollerAddress("0x00");

            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Margin,
                    function));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    function));
        });

        it("only owner should be able to enterMarkets", () -> {
            Function function = MarginSwap.enterMarkets(Collections.singletonList(Network.CEther));

            assertRevert("0x00", Accounts.getTx(3).sendCall(Network.Margin,
                    function));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    function));
        });

        it("deposit should fail when asset is not enabled", () -> {
            Function depositEth = MarginSwap.deposit("0x000", BigInteger.ZERO);
            Function depositTkn = MarginSwap.deposit(Network.Token, BigInteger.ZERO);

            assertRevert("0x64", Network.owner.sendCall(Network.Margin, depositEth));
            assertRevert("0x64", Network.owner.sendCall(Network.Margin, depositTkn));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            assertRevert("0x64", Network.owner.sendCall(Network.Margin, depositEth));
            assertSuccess(Network.owner.sendCall(Network.Margin, depositTkn));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));

            assertSuccess(Network.owner.sendCall(Network.Margin, depositEth));
            assertSuccess(Network.owner.sendCall(Network.Margin, depositTkn));
        });

        it("deposit should fail if eth amount does not match callvalue", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));

            assertRevert("0x01", Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", BigInteger.ZERO),
                    BigInteger.valueOf(1)));

            assertRevert("0x01", Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", BigInteger.valueOf(10)),
                    BigInteger.valueOf(1)));

            assertRevert("0x01", Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", BigInteger.valueOf(10)),
                    BigInteger.valueOf(100)));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", BigInteger.valueOf(8)),
                    BigInteger.valueOf(8)));
        });

        it("deposit should fail if eth is sent in token deposit", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            assertRevert("0x02", Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, BigInteger.ZERO),
                    BigInteger.valueOf(1)));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, BigInteger.ZERO),
                    BigInteger.ZERO));
        });

        it("deposit should first repay borrow (ETH)", () -> {
            /* setup */
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));
            assertSuccess(Network.owner.sendCall(Network.CEther,
                    CompoundMock.setBorrowCurrent(Network.Margin, 1_000)));

            CompoundMock.BorrowbalancecurrentReturnValue value;
            value = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(1_000, value.value.longValueExact());

            BigInteger startCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 200), BigInteger.valueOf(200)));

            value = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(1_000 - 200, value.value.longValueExact());

            BigInteger endCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();

            assertEquals(endCompoundBalance.subtract(startCompoundBalance).longValueExact(), 200);
        });

        it("deposit should mint remaining after repaying (ETH)", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));

            assertSuccess(Network.owner.sendCall(Network.CEther,
                    CompoundMock.setBorrowCurrent(Network.Margin, 1_000)));

            CompoundMock.BorrowbalancecurrentReturnValue borrowValue;

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(1_000, borrowValue.value.longValueExact());


            BigInteger startCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 5_000), BigInteger.valueOf(5_000)));

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(0, borrowValue.value.longValueExact());

            CompoundMock.BalanceofunderlyingReturnValue underlyingValue;
            underlyingValue = CompoundMock.query_balanceOfUnderlying(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(5_000 - 1_000, underlyingValue.value.longValueExact());


            BigInteger endCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();

            assertEquals(endCompoundBalance.subtract(startCompoundBalance).longValueExact(), 5_000);
        });

        it("deposit should first repay borrow (TKN)", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, BigInteger.ONE.shiftLeft(255))));

            assertSuccess(Network.owner.sendCall(Network.CToken,
                    CompoundMock.setBorrowCurrent(Network.Margin, 1_000)));

            CompoundMock.BorrowbalancecurrentReturnValue value;

            value = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(1_000, value.value.longValueExact());

            BigInteger startCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 200), BigInteger.ZERO));

            value = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(1_000 - 200, value.value.longValueExact());

            BigInteger endCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;
            assertEquals(endCompoundBalance.subtract(startCompoundBalance).longValueExact(), 200);
        });

        it("deposit should mint remaining after repaying (TKN)", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, BigInteger.ONE.shiftLeft(255))));

            assertSuccess(Network.owner.sendCall(Network.CToken,
                    CompoundMock.setBorrowCurrent(Network.Margin, 1_000)));

            CompoundMock.BorrowbalancecurrentReturnValue borrowValue;

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(1_000, borrowValue.value.longValueExact());


            BigInteger startCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 5_000), BigInteger.ZERO));

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(0, borrowValue.value.longValueExact());

            CompoundMock.BalanceofunderlyingReturnValue underlyingValue;
            underlyingValue = CompoundMock.query_balanceOfUnderlying(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(5_000 - 1_000, underlyingValue.value.longValueExact());

            BigInteger endCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;
            assertEquals(endCompoundBalance.subtract(startCompoundBalance).longValueExact(), 5_000);
        });

        it("withdraw should only be callable by owner", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 200), BigInteger.valueOf(200)));

            Function function = MarginSwap.withdraw("0x0", 100, Network.owner.getAddress());

            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Margin, function));
            assertSuccess(Network.owner.sendCall(Network.Margin, function));
        });

        it("withdraw should redeem first [ETH]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 200), BigInteger.valueOf(200)));

            CompoundMock.BalanceofunderlyingReturnValue value;

            value = CompoundMock.query_balanceOfUnderlying(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(200, value.value.longValueExact());

            BigInteger startCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger startWalletBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.owner.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw("0x0", 100, Network.owner.getAddress())));

            value = CompoundMock.query_balanceOfUnderlying(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(200 - 100, value.value.longValueExact());

            BigInteger endCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger endWalletBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.owner.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();

            assertEquals(startCompoundBalance.subtract(endCompoundBalance).longValueExact(), 100);
            assertEquals(endWalletBalance.subtract(startWalletBalance).longValueExact(), 100);
        });

        it("withdraw should fail on redeem if funds are not available [ETH]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 200), BigInteger.valueOf(200)));

            assertSuccess(Network.owner.sendCall(Network.CEther,
                    CompoundMock.borrow(150)));

            assertRevert("0xca", Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw("0x0", 100, Network.owner.getAddress())));
        });

        it("withdraw should borrow after redeem [ETH]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 200), BigInteger.valueOf(200)));
            assertSuccess(Network.owner.sendCall(Network.CEther, CompoundMock.mint(), BigInteger.valueOf(10_000)));

            CompoundMock.BalanceofunderlyingReturnValue underlyingValue;
            CompoundMock.BorrowbalancecurrentReturnValue borrowValue;

            underlyingValue = CompoundMock.query_balanceOfUnderlying(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(200, underlyingValue.value.longValueExact());

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(0, borrowValue.value.longValueExact());

            BigInteger startCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger startWalletBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.owner.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw("0x0", 500, Network.owner.getAddress())));

            underlyingValue = CompoundMock.query_balanceOfUnderlying(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(0, underlyingValue.value.longValueExact());

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(500 - 200, borrowValue.value.longValueExact());

            BigInteger endCompoundBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.CEther, DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger endWalletBalance = Network.owner.getWeb3()
                    .ethGetBalance(Network.owner.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();

            assertEquals(startCompoundBalance.subtract(endCompoundBalance).longValueExact(), 500);
            assertEquals(endWalletBalance.subtract(startWalletBalance).longValueExact(), 500);
        });

        it("withdraw should fail to borrow if funds are not available [ETH]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CEther))));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit("0x0", 200), BigInteger.valueOf(200)));

            assertRevert("0xcc", Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw("0x0", 500, Network.owner.getAddress())));
        });

        it("withdraw should redeem first [TKN]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, 200)));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 200)));

            CompoundMock.BalanceofunderlyingReturnValue value;

            value = CompoundMock.query_balanceOfUnderlying(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(200, value.value.longValueExact());

            BigInteger startCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;
            BigInteger startWalletBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw(Network.Token, 100, Network.owner.getAddress())));

            value = CompoundMock.query_balanceOfUnderlying(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(200 - 100, value.value.longValueExact());


            BigInteger endCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;
            BigInteger endWalletBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;

            assertEquals(startCompoundBalance.subtract(endCompoundBalance).longValueExact(), 100);
            assertEquals(endWalletBalance.subtract(startWalletBalance).longValueExact(), 100);
        });

        it("withdraw should fail on redeem if funds are not available [TKN]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, 200)));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 200)));

            assertSuccess(Network.owner.sendCall(Network.CToken,
                    CompoundMock.borrow(150)));

            assertRevert("0xca", Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw(Network.Token, 100, Network.owner.getAddress())));
        });

        it("withdraw should borrow after redeem [TKN]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));
            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, 200)));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 200)));
            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.CToken, 10_000)));
            assertSuccess(Network.owner.sendCall(Network.CToken, CompoundMock.mint(10_000)));

            CompoundMock.BalanceofunderlyingReturnValue underlyingValue;
            CompoundMock.BorrowbalancecurrentReturnValue borrowValue;

            underlyingValue = CompoundMock.query_balanceOfUnderlying(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(200, underlyingValue.value.longValueExact());

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(0, borrowValue.value.longValueExact());

            BigInteger startCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;
            BigInteger startWalletBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw(Network.Token, 500, Network.owner.getAddress())));

            underlyingValue = CompoundMock.query_balanceOfUnderlying(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.balanceOfUnderlying(Network.Margin));
            assertEquals(0, underlyingValue.value.longValueExact());

            borrowValue = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                    CompoundMock.borrowBalanceCurrent(Network.Margin));
            assertEquals(500 - 200, borrowValue.value.longValueExact());

            BigInteger endCompoundBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.CToken)).balance;
            BigInteger endWalletBalance = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;

            assertEquals(startCompoundBalance.subtract(endCompoundBalance).longValueExact(), 500);
            assertEquals(endWalletBalance.subtract(startWalletBalance).longValueExact(), 500);
        });

        it("withdraw should fail to borrow if funds are not available [TKN]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));
            assertSuccess(Network.owner.sendCall(Network.Token,
                    ERC20.approve(Network.Margin, 200)));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.deposit(Network.Token, 200)));

            assertRevert("0xcc", Network.owner.sendCall(Network.Margin,
                    MarginSwap.withdraw(Network.Token, 500, Network.owner.getAddress())));
        });
    }
}
