package bank;

import java.util.Arrays;

public class SynchronizedBank implements IBank {

    private final int[] accounts;

    public SynchronizedBank(int accountsNum, int initialBalance) {
        this.accounts = new int[accountsNum];
        Arrays.fill(this.accounts, initialBalance);
    }

    @Override
    public synchronized void transfer(int from, int to, int amount) {

        while (this.accounts[from] < amount) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread());
        this.accounts[from] -= amount;
        this.accounts[to] += amount;
        System.out.printf("Transfer from %3d to %3d: %5d\n", from, to, amount);
        System.out.println("Total balance: " + this.getTotalBalance());

        this.notifyAll();
    }

    @Override
    public int getTotalBalance() {
        return Arrays.stream(accounts).sum();
    }

    @Override
    public int count() {
        return this.accounts.length;
    }
}
