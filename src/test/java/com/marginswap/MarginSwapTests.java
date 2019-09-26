package com.marginswap;

import com.greghaskins.spectrum.Spectrum;
import com.marginswap.contracts.ERC20;
import com.marginswap.contracts.MarginSwap;
import com.marginswap.contracts.mock.CompoundMock;
import com.marginswap.contracts.mock.TradeMock;
import com.marginswap.utils.Box;
import dev.dcn.test.Accounts;
import dev.dcn.test.StaticNetwork;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.greghaskins.spectrum.Spectrum.*;
import static dev.dcn.test.AssertHelpers.assertRevert;
import static dev.dcn.test.AssertHelpers.assertSuccess;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        it("only owner should be able to call transfer out", () -> {
            assertSuccess(Network.owner.sendCall(BigInteger.ZERO, StaticNetwork.GAS_LIMIT, Network.Margin,
                    "0x", BigInteger.valueOf(1000)));
            assertSuccess(Network.owner.sendCall(Network.Token, ERC20.transfer(Network.Margin, 1000)));

            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Margin,
                    MarginSwap.transferOut("0x0", 1, Network.owner.getAddress())));
            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Margin,
                    MarginSwap.transferOut("0x0", 1, Accounts.getTx(3).getAddress())));

            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Margin,
                    MarginSwap.transferOut(Network.Token, 1, Network.owner.getAddress())));
            assertRevert("0x01", Accounts.getTx(3).sendCall(Network.Margin,
                    MarginSwap.transferOut(Network.Token, 1, Accounts.getTx(3).getAddress())));

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.transferOut("0x0", 1, Network.owner.getAddress())));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.transferOut("0x0", 1, Accounts.getTx(3).getAddress())));
        });

        it("transfer out should send funds [ETH]", () -> {
            assertSuccess(Network.owner.sendCall(BigInteger.ZERO, StaticNetwork.GAS_LIMIT, Network.Margin,
                    "0x", BigInteger.valueOf(1000)));

            BigInteger walletFundsBefore = Network.owner.getWeb3()
                    .ethGetBalance(Network.owner.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger contractFundsBefore = Network.owner.getWeb3()
                    .ethGetBalance(Network.Margin, DefaultBlockParameterName.LATEST).send().getBalance();

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.transferOut("0x0", 23, Network.owner.getAddress())));

            BigInteger walletFundsAfter = Network.owner.getWeb3()
                    .ethGetBalance(Network.owner.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
            BigInteger contractFundsAfter = Network.owner.getWeb3()
                    .ethGetBalance(Network.Margin, DefaultBlockParameterName.LATEST).send().getBalance();

            assertEquals(walletFundsAfter.subtract(walletFundsBefore).longValueExact(), 23);
            assertEquals(contractFundsBefore.subtract(contractFundsAfter).longValueExact(), 23);
        });

        it("transfer out should send funds [TKN]", () -> {
            assertSuccess(Network.owner.sendCall(Network.Token, ERC20.transfer(Network.Margin, 1000)));

            BigInteger walletFundsBefore = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;
            BigInteger contractFundsBefore = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.Margin)).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.transferOut(Network.Token, 23, Network.owner.getAddress())));

            BigInteger walletFundsAfter = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;
            BigInteger contractFundsAfter = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.Margin)).balance;

            assertEquals(walletFundsAfter.subtract(walletFundsBefore).longValueExact(), 23);
            assertEquals(contractFundsBefore.subtract(contractFundsAfter).longValueExact(), 23);
        });

        it("transfer out should send funds [cTKN]", () -> {
            assertSuccess(Network.owner.sendCall(Network.CToken, ERC20.transfer(Network.Margin, 1000)));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Collections.singletonList(Network.CToken))));

            BigInteger walletFundsBefore = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;
            BigInteger contractFundsBefore = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.Margin)).balance;

            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.transferOut(Network.CToken, 23, Network.owner.getAddress())));

            BigInteger walletFundsAfter = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.owner.getAddress())).balance;
            BigInteger contractFundsAfter = ERC20.query_balanceOf(Network.CToken, Network.owner.getWeb3(),
                    ERC20.balanceOf(Network.Margin)).balance;

            assertEquals(walletFundsAfter.subtract(walletFundsBefore).longValueExact(), 23);
            assertEquals(contractFundsBefore.subtract(contractFundsAfter).longValueExact(), 23);
        });

        it("only owner should be able to call trade", () -> {
            assertSuccess(Network.owner.sendCall(BigInteger.ZERO, StaticNetwork.GAS_LIMIT,
                    Network.Parent, "0x", BigInteger.valueOf(10000)));
            assertSuccess(Network.owner.sendCall(Network.Token, ERC20.transfer(Network.Trade, 10000)));
            assertSuccess(Network.owner.sendCall(Network.CEther, CompoundMock.mint(), BigInteger.valueOf(10000)));
            assertSuccess(Network.owner.sendCall(Network.Margin,
                    MarginSwap.enterMarkets(Arrays.asList(Network.CToken, Network.CEther))));

            String tradeData = FunctionEncoder
                    .encode(TradeMock.trade("0x00", Network.Token, 100, 30));

            Function trade = MarginSwap.trade("0x0", 100, Network.Token,
                    30, Network.Trade, tradeData);

            assertRevert("0x00", Accounts.getTx(3).sendCall(Network.Margin, trade));
            assertSuccess(Network.owner.sendCall(Network.Margin, trade));
        });

        class BalancePair {
            BigInteger eth;
            BigInteger tkn;

            void load(String wallet) throws IOException {
                eth = Network.owner.getWeb3().ethGetBalance(wallet, DefaultBlockParameterName.LATEST).send().getBalance();
                tkn = ERC20.query_balanceOf(Network.Token, Network.owner.getWeb3(),
                        ERC20.balanceOf(wallet)).balance;
            }

            void assertEquals(BalancePair other) {
                Assert.assertEquals(eth, other.eth);
                Assert.assertEquals(tkn, other.tkn);
            }
        }

        class TradeState {
            BalancePair wallet = new BalancePair();
            BalancePair parent = new BalancePair();
            BalancePair margin = new BalancePair();
            BalancePair trade = new BalancePair();
            BalancePair cToken = new BalancePair();
            BalancePair cEth = new BalancePair();
            BalancePair marginUnderlying = new BalancePair();
            BalancePair marginBorrow = new BalancePair();

            BigInteger tokenMarginToTrade;

            void load() throws IOException {
                wallet.load(Network.owner.getAddress());
                parent.load(Network.Parent);
                margin.load(Network.Margin);
                trade.load(Network.Trade);
                cToken.load(Network.CToken);
                cEth.load(Network.CEther);

                tokenMarginToTrade = ERC20.query_allowance(Network.Token, Network.owner.getWeb3(),
                        ERC20.allowance(Network.Margin, Network.Trade)).remaining;

                marginUnderlying.eth = CompoundMock.query_balanceOfUnderlying(Network.CEther, Network.owner.getWeb3(),
                        CompoundMock.balanceOfUnderlying(Network.Margin)).value;
                marginUnderlying.tkn = CompoundMock.query_balanceOfUnderlying(Network.CToken, Network.owner.getWeb3(),
                        CompoundMock.balanceOfUnderlying(Network.Margin)).value;

                marginBorrow.eth = CompoundMock.query_borrowBalanceCurrent(Network.CEther, Network.owner.getWeb3(),
                        CompoundMock.borrowBalanceCurrent(Network.Margin)).value;
                marginBorrow.tkn = CompoundMock.query_borrowBalanceCurrent(Network.CToken, Network.owner.getWeb3(),
                        CompoundMock.borrowBalanceCurrent(Network.Margin)).value;
            }
        }

        describe("trade (ETH => TKN)", () -> {
            TradeState before = new TradeState();
            TradeState after = new TradeState();
            Box<TransactionReceipt> tx = new Box<>();

            int tradeIn = 50;
            int tradeOut = 30;

            beforeAll(() -> {
                assertSuccess(Network.owner.sendCall(BigInteger.ZERO, StaticNetwork.GAS_LIMIT,
                        Network.Parent, "0x", BigInteger.valueOf(10000)));
                assertSuccess(Network.owner.sendCall(Network.Token, ERC20.transfer(Network.Trade, 10000)));
                assertSuccess(Network.owner.sendCall(Network.CEther, CompoundMock.mint(), BigInteger.valueOf(10000)));
                assertSuccess(Network.owner.sendCall(Network.Margin,
                        MarginSwap.enterMarkets(Arrays.asList(Network.CToken, Network.CEther))));

                String tradeData = FunctionEncoder
                        .encode(TradeMock.trade("0x00", Network.Token, tradeIn, tradeOut));

                before.load();
                tx.value = assertSuccess(Network.owner.sendCall(Network.Margin,
                        MarginSwap.trade("0x0", tradeIn, Network.Token, tradeOut, Network.Trade, tradeData)));
                after.load();
            });

            it("trade should pay parent 1/200 of input", () -> {
                assertEquals(before.parent.eth.add(BigInteger.valueOf(tradeIn / 200)), after.parent.eth);
                assertEquals(before.parent.tkn, after.parent.tkn);
            });

            it("trade should borrow input + fee", () -> {
                assertEquals(before.marginBorrow.tkn, after.marginBorrow.tkn);
                assertEquals(before.marginBorrow.eth.add(BigInteger.valueOf(tradeIn + tradeIn / 200)), after.marginBorrow.eth);
            });

            it("trade should deposit output", () -> {
                assertEquals(before.marginUnderlying.tkn.add(BigInteger.valueOf(tradeOut)), after.marginUnderlying.tkn);
                assertEquals(before.marginUnderlying.eth, after.marginUnderlying.eth);
            });

            it("trade should have moved fund in trade contract", () -> {
                assertEquals(before.trade.tkn.subtract(BigInteger.valueOf(tradeOut)), after.trade.tkn);
                assertEquals(before.trade.eth.add(BigInteger.valueOf(tradeIn)), after.trade.eth);
            });

            it("trade should have displaced funds in compound assets", () -> {
                assertEquals(before.cEth.eth.subtract(BigInteger.valueOf(tradeIn + tradeIn / 200)), after.cEth.eth);
                assertEquals(before.cToken.tkn.add(BigInteger.valueOf(tradeOut)), after.cToken.tkn);
            });

            it("other balances should be unchanged", () -> {
                before.wallet.assertEquals(after.wallet);
                before.parent.assertEquals(after.parent);
                before.margin.assertEquals(after.margin);
            });

            it("trade should emit trade event", () -> {
                List<Log> logs = tx.value.getLogs();

                MarginSwap.Trade trade = null;
                for (Log log : logs) {
                    trade = MarginSwap.ExtractTrade(log);
                    if (trade != null) {
                        break;
                    }
                }

                assertNotNull(trade);
                assertEquals("0x0000000000000000000000000000000000000000", trade.from_asset);
                assertEquals(Network.Token, trade.to_asset);
                assertEquals(tradeIn, trade.input.longValueExact());
                assertEquals(tradeOut, trade.output.longValueExact());
                assertEquals(tradeOut / 200, trade.input_fee.longValueExact());
            });
        });
    }
}
