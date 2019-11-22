package Bank;

import java.util.Arrays;

public class DefaultBank implements IBank {

    private final int[] accounts;

    public DefaultBank(int accNum, int initialBalance) {
        this.accounts = new int[accNum];
        Arrays.fill(this.accounts, initialBalance);
    }

    @Override
    public void transfer(int from, int to, int amount) {
        if (this.accounts[from] < amount)
            return;

        System.out.println(Thread.currentThread());
        this.accounts[from] -= amount;
        this.accounts[to] += amount;

        System.out.printf("Transfer from %3d to %3d: %5d\n", from, to, amount);

        System.out.println("Total balance: " + this.getTotalBalance());
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
