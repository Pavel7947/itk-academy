import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class ConcurrentBank {
    private final ConcurrentHashMap<Integer, BankAccount> accounts = new ConcurrentHashMap<>();
    private int nextAccountId = 1;

    public int createAccount(int initialBalance) {
        BankAccount account = new BankAccount(nextAccountId++, initialBalance);
        accounts.put(account.getAccountId(), account);
        System.out.println("Создан счет " + account.getAccountId() + " с начальным балансом " + initialBalance);
        return account.getAccountId();
    }

    public void transfer(int fromId, int toId, int amount) {
        BankAccount fromAccount = accounts.get(fromId);
        BankAccount toAccount = accounts.get(toId);
        if (fromAccount == null || toAccount == null) {
            throw new RuntimeException("Неверный номер счета");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
        Lock firstLock = fromAccount.getLock();
        Lock secondLock = toAccount.getLock();
        if (fromId > toId) {
            Lock tmp = firstLock;
            firstLock = secondLock;
            secondLock = tmp;
        }
        firstLock.lock();
        try {
            secondLock.lock();
            try {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.println("Переведен " + amount + " с счета " + fromId + " на счет " + toId);
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }

    public int getTotalBalance() {
        return accounts.values().stream()
                .mapToInt(BankAccount::getBalance)
                .sum();
    }
}
