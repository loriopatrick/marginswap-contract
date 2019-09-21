package com.marginswap.contracts;

import org.web3j.abi.*;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.utils.Numeric;
import java.util.stream.Collectors;

@javax.annotation.Generated(value="merklex-code-gen")
public class CompoundMock {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001db438038062001db4833981810160405260a08110156200003757600080fd5b810190808051906020019092919080516401000000008111156200005a57600080fd5b828101905060208101848111156200007157600080fd5b81518560018202830111640100000000821117156200008f57600080fd5b5050929190602001805190602001909291908051640100000000811115620000b657600080fd5b82810190506020810184811115620000cd57600080fd5b8151856001820283011164010000000082111715620000eb57600080fd5b505092919060200180519060200190929190505050846000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555084600481905550836005908051906020019062000162929190620001e3565b5082600660006101000a81548160ff021916908360ff160217905550816007908051906020019062000196929190620001e3565b5080600960006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050505062000292565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200022657805160ff191683800117855562000257565b8280016001018555821562000257579182015b828111156200025657825182559160200191906001019062000239565b5b5090506200026691906200026a565b5090565b6200028f91905b808211156200028b57600081600090555060010162000271565b5090565b90565b611b1280620002a26000396000f3fe60806040526004361061014b5760003560e01c80633af9e669116100b657806395d89b411161006f57806395d89b4114610725578063a0712d68146107b5578063a9059cbb14610804578063bd6d894d14610877578063c5ebeaec146108a2578063dd62ed3e146108f15761014b565b80633af9e669146105265780634e4d9fea1461058b5780635c658165146105955780636f307dc31461061a57806370a0823114610671578063852a12e3146106d65761014b565b806318160ddd1161010857806318160ddd1461036c578063182df0f51461039757806323b872dd146103c257806327e235e314610455578063313ce567146104ba57806334fcf437146104eb5761014b565b806306a8ad4c1461015057806306fdde03146101ab578063095ea7b31461023b5780630e752702146102ae5780631249c58b146102fd57806317bfdfbc14610307575b600080fd5b34801561015c57600080fd5b506101a96004803603604081101561017357600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610976565b005b3480156101b757600080fd5b506101c06109be565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102005780820151818401526020810190506101e5565b50505050905090810190601f16801561022d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561024757600080fd5b506102946004803603604081101561025e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610a5c565b604051808215151515815260200191505060405180910390f35b3480156102ba57600080fd5b506102e7600480360360208110156102d157600080fd5b8101908080359060200190929190505050610b4e565b6040518082815260200191505060405180910390f35b610305610d19565b005b34801561031357600080fd5b506103566004803603602081101561032a57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610dc9565b6040518082815260200191505060405180910390f35b34801561037857600080fd5b50610381610de1565b6040518082815260200191505060405180910390f35b3480156103a357600080fd5b506103ac610de7565b6040518082815260200191505060405180910390f35b3480156103ce57600080fd5b5061043b600480360360608110156103e557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610ded565b604051808215151515815260200191505060405180910390f35b34801561046157600080fd5b506104a46004803603602081101561047857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611082565b6040518082815260200191505060405180910390f35b3480156104c657600080fd5b506104cf61109a565b604051808260ff1660ff16815260200191505060405180910390f35b3480156104f757600080fd5b506105246004803603602081101561050e57600080fd5b81019080803590602001909291905050506110ad565b005b34801561053257600080fd5b506105756004803603602081101561054957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506110b7565b6040518082815260200191505060405180910390f35b6105936110cf565b005b3480156105a157600080fd5b50610604600480360360408110156105b857600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061116a565b6040518082815260200191505060405180910390f35b34801561062657600080fd5b5061062f61118f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561067d57600080fd5b506106c06004803603602081101561069457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506111b5565b6040518082815260200191505060405180910390f35b3480156106e257600080fd5b5061070f600480360360208110156106f957600080fd5b81019080803590602001909291905050506111fd565b6040518082815260200191505060405180910390f35b34801561073157600080fd5b5061073a611490565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561077a57808201518184015260208101905061075f565b50505050905090810190601f1680156107a75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156107c157600080fd5b506107ee600480360360208110156107d857600080fd5b810190808035906020019092919050505061152e565b6040518082815260200191505060405180910390f35b34801561081057600080fd5b5061085d6004803603604081101561082757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061170d565b604051808215151515815260200191505060405180910390f35b34801561088357600080fd5b5061088c611861565b6040518082815260200191505060405180910390f35b3480156108ae57600080fd5b506108db600480360360208110156108c557600080fd5b810190808035906020019092919050505061186b565b6040518082815260200191505060405180910390f35b3480156108fd57600080fd5b506109606004803603604081101561091457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611a56565b6040518082815260200191505060405180910390f35b80600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b60058054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a545780601f10610a2957610100808354040283529160200191610a54565b820191906000526020600020905b815481529060010190602001808311610a3757829003601f168201915b505050505081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a36001905092915050565b600081600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610b9c57600080fd5b6000600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff166323b872dd3330866040518463ffffffff1660e01b8152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b158015610c7e57600080fd5b505af1158015610c92573d6000803e3d6000fd5b505050506040513d6020811015610ca857600080fd5b8101908080519060200190929190505050610cc257600080fd5b82600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055506000915050919050565b600854670de0b6b3a7640000340281610d2e57fe5b046000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555034600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550565b60026020528060005260406000206000915090505481565b60045481565b60085481565b600080600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050826000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410158015610ebd5750828110155b610ec657600080fd5b826000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550826000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8110156110115782600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b8373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a360019150509392505050565b60006020528060005260406000206000915090505481565b600660009054906101000a900460ff1681565b8060088190555050565b60036020528060005260406000206000915090505481565b34600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561111b57600080fd5b34600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550565b6001602052816000526040600020602052806000526040600020600091509150505481565b600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b600081600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561124b57600080fd5b81600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550600854670de0b6b3a76400008302816112ad57fe5b046000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550600073ffffffffffffffffffffffffffffffffffffffff16600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415611393573373ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f1935050505061138e57600080fd5b611487565b6000600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663a9059cbb33856040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b15801561144157600080fd5b505af1158015611455573d6000803e3d6000fd5b505050506040513d602081101561146b57600080fd5b810190808051906020019092919050505061148557600080fd5b505b60009050919050565b60078054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115265780601f106114fb57610100808354040283529160200191611526565b820191906000526020600020905b81548152906001019060200180831161150957829003601f168201915b505050505081565b600080600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff166323b872dd3330866040518463ffffffff1660e01b8152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b15801561161157600080fd5b505af1158015611625573d6000803e3d6000fd5b505050506040513d602081101561163b57600080fd5b810190808051906020019092919050505061165557600080fd5b600854670de0b6b3a764000084028161166a57fe5b046000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555082600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055506000915050919050565b6000816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561175a57600080fd5b816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550816000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a36001905092915050565b6000600854905090565b600081600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550600073ffffffffffffffffffffffffffffffffffffffff16600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141561195d573373ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f19350505050158015611957573d6000803e3d6000fd5b50611a51565b6000600960009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663a9059cbb33856040518363ffffffff1660e01b8152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b158015611a0b57600080fd5b505af1158015611a1f573d6000803e3d6000fd5b505050506040513d6020811015611a3557600080fd5b8101908080519060200190929190505050611a4f57600080fd5b505b919050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490509291505056fea265627a7a72305820f22ccb73f49b5e8ac51ea829c085533e65a334529e1c660dc895ee3f579bd53664736f6c634300050a0032";
    public static Function setBorrowCurrent(String _account, BigInteger _value) {
        return new Function(
            "setBorrowCurrent",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_account)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.emptyList()
        );
    }
    public static Function setBorrowCurrent(String _account, long _value) {
        return setBorrowCurrent(
            _account
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function name() {
        return new Function(
            "name",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Utf8String>() {}
            )
        );
    }
    public static NameReturnValue query_name(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        NameReturnValue returnValue = new NameReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static NameReturnValue query_name(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_name(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function approve(String _spender, BigInteger _value) {
        return new Function(
            "approve",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_spender)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static Function approve(String _spender, long _value) {
        return approve(
            _spender
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function repayBorrow(BigInteger amount) {
        return new Function(
            "repayBorrow",
            Collections.singletonList(
                new UnsignedNumberType(256, amount)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static Function repayBorrow(long amount) {
        return repayBorrow(
            new BigInteger(Long.toUnsignedString(amount))
        );
    }
    public static Function mint() {
        return new Function(
            "mint",
            Collections.emptyList(),
            Collections.emptyList()
        );
    }
    public static Function borrowBalanceCurrent(String arg0) {
        return new Function(
            "borrowBalanceCurrent",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(arg0)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static BorrowbalancecurrentReturnValue query_borrowBalanceCurrent(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        BorrowbalancecurrentReturnValue returnValue = new BorrowbalancecurrentReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static BorrowbalancecurrentReturnValue query_borrowBalanceCurrent(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_borrowBalanceCurrent(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function totalSupply() {
        return new Function(
            "totalSupply",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static TotalsupplyReturnValue query_totalSupply(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        TotalsupplyReturnValue returnValue = new TotalsupplyReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static TotalsupplyReturnValue query_totalSupply(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_totalSupply(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function exchangeRateStored() {
        return new Function(
            "exchangeRateStored",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static ExchangeratestoredReturnValue query_exchangeRateStored(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        ExchangeratestoredReturnValue returnValue = new ExchangeratestoredReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static ExchangeratestoredReturnValue query_exchangeRateStored(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_exchangeRateStored(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function transferFrom(String _from, String _to, BigInteger _value) {
        return new Function(
            "transferFrom",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_from)
                , new org.web3j.abi.datatypes.Address(_to)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static Function transferFrom(String _from, String _to, long _value) {
        return transferFrom(
            _from
            , _to
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function balances(String arg0) {
        return new Function(
            "balances",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(arg0)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static BalancesReturnValue query_balances(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        BalancesReturnValue returnValue = new BalancesReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static BalancesReturnValue query_balances(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_balances(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function decimals() {
        return new Function(
            "decimals",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint8>() {}
            )
        );
    }
    public static DecimalsReturnValue query_decimals(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        DecimalsReturnValue returnValue = new DecimalsReturnValue();
        returnValue.value = ((BigInteger) values.get(0).getValue()).intValue();
        return returnValue;
    }
    public static DecimalsReturnValue query_decimals(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_decimals(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function setRate(BigInteger rate) {
        return new Function(
            "setRate",
            Collections.singletonList(
                new UnsignedNumberType(256, rate)
            ),
            Collections.emptyList()
        );
    }
    public static Function setRate(long rate) {
        return setRate(
            new BigInteger(Long.toUnsignedString(rate))
        );
    }
    public static Function balanceOfUnderlying(String arg0) {
        return new Function(
            "balanceOfUnderlying",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(arg0)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static BalanceofunderlyingReturnValue query_balanceOfUnderlying(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        BalanceofunderlyingReturnValue returnValue = new BalanceofunderlyingReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static BalanceofunderlyingReturnValue query_balanceOfUnderlying(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_balanceOfUnderlying(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function repayBorrow() {
        return new Function(
            "repayBorrow",
            Collections.emptyList(),
            Collections.emptyList()
        );
    }
    public static Function allowed(String arg0, String arg1) {
        return new Function(
            "allowed",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(arg0)
                , new org.web3j.abi.datatypes.Address(arg1)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static AllowedReturnValue query_allowed(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        AllowedReturnValue returnValue = new AllowedReturnValue();
        returnValue.value = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static AllowedReturnValue query_allowed(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_allowed(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function underlying() {
        return new Function(
            "underlying",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Address>() {}
            )
        );
    }
    public static UnderlyingReturnValue query_underlying(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        UnderlyingReturnValue returnValue = new UnderlyingReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static UnderlyingReturnValue query_underlying(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_underlying(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function balanceOf(String _owner) {
        return new Function(
            "balanceOf",
            Collections.singletonList(
                new org.web3j.abi.datatypes.Address(_owner)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static BalanceofReturnValue query_balanceOf(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        BalanceofReturnValue returnValue = new BalanceofReturnValue();
        returnValue.balance = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static BalanceofReturnValue query_balanceOf(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_balanceOf(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function redeemUnderlying(BigInteger amount) {
        return new Function(
            "redeemUnderlying",
            Collections.singletonList(
                new UnsignedNumberType(256, amount)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static Function redeemUnderlying(long amount) {
        return redeemUnderlying(
            new BigInteger(Long.toUnsignedString(amount))
        );
    }
    public static Function symbol() {
        return new Function(
            "symbol",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Utf8String>() {}
            )
        );
    }
    public static SymbolReturnValue query_symbol(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        SymbolReturnValue returnValue = new SymbolReturnValue();
        returnValue.value = (String) values.get(0).getValue();
        return returnValue;
    }
    public static SymbolReturnValue query_symbol(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_symbol(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static Function mint(BigInteger amount) {
        return new Function(
            "mint",
            Collections.singletonList(
                new UnsignedNumberType(256, amount)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static Function mint(long amount) {
        return mint(
            new BigInteger(Long.toUnsignedString(amount))
        );
    }
    public static Function transfer(String _to, BigInteger _value) {
        return new Function(
            "transfer",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_to)
                , new UnsignedNumberType(256, _value)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.Bool>() {}
            )
        );
    }
    public static Function transfer(String _to, long _value) {
        return transfer(
            _to
            , new BigInteger(Long.toUnsignedString(_value))
        );
    }
    public static Function exchangeRateCurrent() {
        return new Function(
            "exchangeRateCurrent",
            Collections.emptyList(),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static Function borrow(BigInteger amount) {
        return new Function(
            "borrow",
            Collections.singletonList(
                new UnsignedNumberType(256, amount)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static Function borrow(long amount) {
        return borrow(
            new BigInteger(Long.toUnsignedString(amount))
        );
    }
    public static Function allowance(String _owner, String _spender) {
        return new Function(
            "allowance",
            Arrays.asList(
                new org.web3j.abi.datatypes.Address(_owner)
                , new org.web3j.abi.datatypes.Address(_spender)
            ),
            Collections.singletonList(
                new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
            )
        );
    }
    public static AllowanceReturnValue query_allowance(String contractAddress, Web3j web3j, Function function, DefaultBlockParameter block) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
            block
        ).send();
        String value = ethCall.getValue();
        List<Type> values = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        AllowanceReturnValue returnValue = new AllowanceReturnValue();
        returnValue.remaining = (BigInteger) values.get(0).getValue();
        return returnValue;
    }
    public static AllowanceReturnValue query_allowance(String contractAddress, Web3j web3j, Function function) throws IOException {
        return query_allowance(contractAddress, web3j, function, DefaultBlockParameterName.LATEST);
    }
    public static String DeployData(BigInteger _initialAmount, String _tokenName, int _decimalUnits, String _tokenSymbol, String _underlying) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
            Arrays.asList(
                new UnsignedNumberType(256, _initialAmount)
                , new org.web3j.abi.datatypes.Utf8String(_tokenName)
                , new UnsignedNumberType(8, _decimalUnits)
                , new org.web3j.abi.datatypes.Utf8String(_tokenSymbol)
                , new org.web3j.abi.datatypes.Address(_underlying)
            )
        );
        return BINARY + encodedConstructor;
    }
    public static class Transfer {
        public String _from;
        public String _to;
        public BigInteger _value;
    }
    public static final Event Transfer_EVENT = new Event("Transfer",
        Arrays.asList(
            new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
        )
    );
    public static final String Transfer_EVENT_HASH = EventEncoder.encode(Transfer_EVENT);
    public static Transfer ExtractTransfer(Log log) {
        List<String> topics = log.getTopics();
        if (topics.size() == 0 || !Transfer_EVENT_HASH.equals(topics.get(0))) {
            return null;
        }
        EventValues values = Contract.staticExtractEventParameters(Transfer_EVENT, log);
        Transfer event = new Transfer();
        event._from = (String) values.getIndexedValues().get(0).getValue();
        event._to = (String) values.getIndexedValues().get(1).getValue();
        event._value = (BigInteger) values.getNonIndexedValues().get(0).getValue();
        return event;
    }
    public static class Approval {
        public String _owner;
        public String _spender;
        public BigInteger _value;
    }
    public static final Event Approval_EVENT = new Event("Approval",
        Arrays.asList(
            new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.Address>(true) {}
            , new TypeReference<org.web3j.abi.datatypes.generated.Uint256>() {}
        )
    );
    public static final String Approval_EVENT_HASH = EventEncoder.encode(Approval_EVENT);
    public static Approval ExtractApproval(Log log) {
        List<String> topics = log.getTopics();
        if (topics.size() == 0 || !Approval_EVENT_HASH.equals(topics.get(0))) {
            return null;
        }
        EventValues values = Contract.staticExtractEventParameters(Approval_EVENT, log);
        Approval event = new Approval();
        event._owner = (String) values.getIndexedValues().get(0).getValue();
        event._spender = (String) values.getIndexedValues().get(1).getValue();
        event._value = (BigInteger) values.getNonIndexedValues().get(0).getValue();
        return event;
    }
    public static class NameReturnValue {
        public String value;
    }
    public static class BorrowbalancecurrentReturnValue {
        public BigInteger value;
    }
    public static class TotalsupplyReturnValue {
        public BigInteger value;
    }
    public static class ExchangeratestoredReturnValue {
        public BigInteger value;
    }
    public static class BalancesReturnValue {
        public BigInteger value;
    }
    public static class DecimalsReturnValue {
        public int value;
    }
    public static class BalanceofunderlyingReturnValue {
        public BigInteger value;
    }
    public static class AllowedReturnValue {
        public BigInteger value;
    }
    public static class UnderlyingReturnValue {
        public String value;
    }
    public static class BalanceofReturnValue {
        public BigInteger balance;
    }
    public static class SymbolReturnValue {
        public String value;
    }
    public static class AllowanceReturnValue {
        public BigInteger remaining;
    }
}
