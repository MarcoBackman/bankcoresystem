package org.demo.bankdemo.processor;

import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemo.domain.Account;
import org.demo.bankdemo.domain.Transaction;
import org.demo.bankdemo.task.CallableTask;
import org.demo.bankdemo.task.RunnableTask;
import org.demo.bankdemo.task.ThreadTask;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Runs sequentially based on each account
 */
@Service
@Slf4j
public class TransactionProcessor implements AutoCloseable {

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

    ExecutorService forkJoinPool = Executors.newWorkStealingPool();

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

    /*
     * This will not guarantee ordered execution of the transaction
     */
    public void processRunnableWithThreadPoolExecutor(Stream<Transaction> transactions) {
        transactions.forEach(transaction -> {
            RunnableTask task = new RunnableTask(transaction);
            fixedThreadPool.submit(task);
        });
    }

    /*
     * This will not guarantee ordered execution of the transaction
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
     * Fast and utilize all available worker threads.
     * However, this does not guarantee the order of the transactions
     *
     * ForkJoinPool may not work well with Thread.sleep()
     * @param transactions
     * @throws InterruptedException
     */
    public void processTransactionsWithForkJoin(Stream<Transaction> transactions) throws InterruptedException {
        transactions.forEach(transaction -> {
            RunnableTask task = new RunnableTask(transaction);
            forkJoinPool.execute(task);
        });
        //This is needed, otherwise application process will end immediately
        //Because forkjoinpool needs some time to get initialized before main thread considers there's no thread work to do
        Thread.sleep(50);
    }

    /*
     * This will guarantee ordered execution of the transaction and process orders by each account concurrently
     */
    public void processWithTransactionExecutor(Stream<Transaction> transactions) {
        Map<String, List<Transaction>> transactionMap = convertToMap(transactions);
        transactionMap.forEach((s, transactionList) -> {
            fixedThreadPool = Executors.newFixedThreadPool(transactionMap.size());
            log.info("Thread pool(Account) size={}", transactionMap.size());
            fixedThreadPool.execute(() -> {
                ThreadTask<Transaction> task = new ThreadTask<>(transactionList);
                task.start();
            });
        });
    }

    @Override
    public void close() {
        log.info("Terminating threadPool.... had active pool size={}", Thread.activeCount());
        fixedThreadPool.shutdown();
        forkJoinPool.shutdown();
    }
}
