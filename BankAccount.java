import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final Lock lock = new ReentrantLock();
    private final int accountId;
    private int balance;

    public BankAccount(int accountId, int initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Сумма пополнения должна быть положительной");
            }
            balance += amount;
            System.out.println("Пополнен счет " + accountId + ": +" + amount);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Сумма снятия должна быть положительной");
            }
            if (amount > balance) {
                throw new RuntimeException("Недостаточно средств на счете " + accountId);
            }
            balance -= amount;
            System.out.println("Снят со счета " + accountId + ": -" + amount);
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public int getAccountId() {
        return accountId;
    }

    public Lock getLock() {
        return lock;
    }
}

