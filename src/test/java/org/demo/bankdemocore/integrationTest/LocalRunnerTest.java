package org.demo.bankdemocore.integrationTest;

import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.Side;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.processor.TransactionProcessor;
import org.demo.bankdemocore.util.RandomGenerator;
import org.jetbrains.annotations.TestOnly;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class LocalRunnerTest {

    Account testAccount1 = Account.builder().build();
    Account testAccount2 = Account.builder().build();
    Account testAccount3 = Account.builder().build();

    LocalRunnerTest() {
        testAccount1.createNewAccount(1);
        testAccount1.createNewAccount(2);
        testAccount1.createNewAccount(3);
    }

    private List<Transaction> generateRandomTransactions(BigDecimal minTranAmount,
                                                         BigDecimal maxTranAmount,
                                                         Account targetAccount,
                                                         int numberOfTransactions) {
        List<Transaction> list = new ArrayList<>();

        Side side;

        for (int i = 0; i < numberOfTransactions; i++) {
            BigDecimal randomDecimal = RandomGenerator.generateRandomBigDecimalFromRange(minTranAmount, maxTranAmount);
            if (randomDecimal.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ONE) > 0) {
                side = Side.DEPOSIT;
            } else {
                side = Side.WITHDRAW;
            }

            list.add(Transaction.builder()
                    .account(targetAccount)
                    .transactionId(UUID.randomUUID().toString())
                    .tranAmount(randomDecimal)
                    .side(side)
                    .build());
        }
        return list;
    }

    private List<Transaction> getBulkTransactionList() {
        List<Transaction> allTransactions = new ArrayList<>();

        //Generate test transaction sets for account
        List<Transaction> firstAccountTransactions = generateRandomTransactions(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2000),
                testAccount1,
                8
        );

        List<Transaction> secondAccountTransactions = generateRandomTransactions(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2000),
                testAccount2,
                9
        );

        List<Transaction> thirdAccountTransactions = generateRandomTransactions(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2000),
                testAccount3,
                4
        );

        allTransactions.addAll(firstAccountTransactions);
        allTransactions.addAll(secondAccountTransactions);
        allTransactions.addAll(thirdAccountTransactions);
        return allTransactions;
    }

    @TestOnly
    private List<Transaction> getManualTransactionListForSingleAccount() {
        List<Transaction> allTransactions = new ArrayList<>();

        allTransactions.add(Transaction.builder()
                .account(testAccount1)
                .receivedWhen(LocalDateTime.now())
                .side(Side.DEPOSIT)
                .tranAmount(BigDecimal.valueOf(20))
                .transactionId(UUID.randomUUID().toString())
                .build());

        allTransactions.add(Transaction.builder()
                .account(testAccount1)
                .receivedWhen(LocalDateTime.now())
                .side(Side.WITHDRAW)
                .tranAmount(BigDecimal.valueOf(120))
                .transactionId(UUID.randomUUID().toString())
                .build());

        allTransactions.add(Transaction.builder()
                .account(testAccount1)
                .receivedWhen(LocalDateTime.now())
                .side(Side.DEPOSIT)
                .tranAmount(BigDecimal.valueOf(120))
                .transactionId(UUID.randomUUID().toString())
                .build());

        allTransactions.add(Transaction.builder()
                .account(testAccount1)
                .receivedWhen(LocalDateTime.now())
                .side(Side.WITHDRAW)
                .tranAmount(BigDecimal.valueOf(140))
                .transactionId(UUID.randomUUID().toString())
                .build());

        return allTransactions;
    }

    private void runTestSets() {
        //List<Transaction> allTransactions = getManualTransactionListForSingleAccount();

//        try(TransactionProcessor transactionProcessor = new TransactionProcessor()) {
//            //runnable process  with thread pool executor
//            List<Transaction> allTransactions = getBulkTransactionList();
//            transactionProcessor.processRunnableWithThreadPoolExecutor(allTransactions.stream());
//        } catch (Exception exception) {
//            log.warn("Process went wrong", exception);
//        }
//
        try(TransactionProcessor transactionProcessor = new TransactionProcessor()) {
            //callable process  with thread pool executor
            List<Transaction> allTransactions = getBulkTransactionList();
            transactionProcessor.processCallableWithThreadPoolExecutor(allTransactions.stream());
        } catch (Exception exception) {
            log.warn("Process went wrong", exception);
        }
//
         //this will not wait the main thread
//        try(TransactionProcessor transactionProcessor = new TransactionProcessor()) {
//            //runnable process  with fork join pool
//            List<Transaction> allTransactions = getBulkTransactionList();
//            transactionProcessor.processTransactionsWithForkJoin(allTransactions.stream());
//        } catch (Exception exception) {
//            log.warn("Process went wrong", exception);
//        }

        //this will not wait the main thread
//        try(TransactionProcessor transactionProcessor = new TransactionProcessor()) {
//            //Custom thread process with thread pool executor
//            List<Transaction> allTransactions = getBulkTransactionList();
//            transactionProcessor.processWithTransactionExecutor(allTransactions.stream());
//        } catch (Exception exception) {
//            log.warn("Process went wrong", exception);
//        }
    }

    public static void main(String[] args) {
        LocalRunnerTest inst = new LocalRunnerTest();
        inst.runTestSets();
    }
}
