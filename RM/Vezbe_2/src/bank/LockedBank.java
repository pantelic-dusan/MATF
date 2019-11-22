package bank;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBank implements IBank {

    private final int[] accounts;

    private Lock lock;
    private Condition insufficientFunds;

    public LockedBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);

        this.lock = new ReentrantLock();
        this.insufficientFunds = this.lock.newCondition();
    }
    @Override
    public void transfer(int from, int to, int amount) {
        this.lock.lock();
        try {
            while (this.accounts[from] < amount) {
                try {
                    this.insufficientFunds.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread());
            this.accounts[from] -= amount;
            this.accounts[to] += amount;
            System.out.printf("Transfer from %3d to %3d: %5d\n", from, to, amount);
            System.out.println("Total balance: " + this.getTotalBalance());

            this.insufficientFunds.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int getTotalBalance() {
        return Arrays.stream(accounts).sum();
    }

    @Override
    public int count() {
        return accounts.length;
    }
}
