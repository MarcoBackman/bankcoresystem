package org.demo.bankdemocore.processor;

import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.domain.Account;
import org.demo.bankdemocore.domain.Transaction;
import org.demo.bankdemocore.task.CallableTask;
import org.demo.bankdemocore.task.RunnableTask;
import org.demo.bankdemocore.task.ThreadTask;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * Runs sequentially based on each account
 */
@Service
@Slf4j
public class TransactionProcessor implements AutoCloseable {

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
    ExecutorService forkJoinPool = Executors.newWorkStealingPool(); //forkJoinPool

    private Map<String, List<Transaction>> convertToMap(Stream<Transaction> transactions) {
        Map<String, List<Transaction>> transactionMap = new LinkedHashMap<>();
        transactions.forEach(transaction -> {
            Account account = transaction.getAccount();
            String key = account.getAccountId();
            List<Transaction> transactionList = transactionMap.get(key);
            if (transactionList == null) {
                transactionList = new ArrayList<>();
            }
            transactionList.add(transaction);
            transactionMap.put(account.getAccountId(), transactionList);
        });
        return transactionMap;
    }

    /**
     * This will not guarantee ordered execution of the transaction.
     * Main thread will 'not' wait until this process completes. Runs independently
     */
    public void processRunnableWithThreadPoolExecutor(Stream<Transaction> transactions) {
        transactions.forEach(transaction -> {
            RunnableTask task = new RunnableTask(transaction);
            fixedThreadPool.submit(task);
        });
    }

    /**
     * This will not guarantee ordered execution of the transaction.
     * Main thread will wait until the entire process completes.
     */
    public void processCallableWithThreadPoolExecutor(Stream<Transaction> transactions) throws InterruptedException {
        List<CallableTask> tasks = new ArrayList<>();

        transactions.forEach(transaction -> {
            CallableTask task = new CallableTask(transaction);
            tasks.add(task);
        });

        fixedThreadPool.invokeAll(tasks);
    }

    /**
     * Fast because it uses all available worker threads.
     * However, this does not guarantee the order of the transactions
     *
     * ForkJoinPool may not work well with Thread.sleep()
     *
     * Main thread will 'not' wait until this process completes. Runs independently
     */
    public void processTransactionsWithForkJoin(Stream<Transaction> transactions) {
        transactions.forEach(transaction -> {
            RunnableTask task = new RunnableTask(transaction);
            forkJoinPool.submit(task);
        });
    }

    /**
     * This will guarantee ordered execution of the transaction and process orders by each account concurrently
     * Main thread will 'not' wait until this process completes. Runs independently
     */
    public void processWithTransactionExecutor(Stream<Transaction> transactions) {
        Map<String, List<Transaction>> transactionMap = convertToMap(transactions);
        fixedThreadPool = Executors.newFixedThreadPool(transactionMap.size());

        transactionMap.forEach((s, transactionList) -> {
            log.info("Thread pool(Account) size={}", transactionMap.size());
            fixedThreadPool.execute(() -> {
                ThreadTask<Transaction> task = new ThreadTask<>(transactionList);
                task.start();
            });
        });
    }

    @Override
    public void close() {
        log.info("Ordered pool shut down. This will shutdown after all working threads are finished");
        fixedThreadPool.shutdown();
        forkJoinPool.shutdown();
    }
}
