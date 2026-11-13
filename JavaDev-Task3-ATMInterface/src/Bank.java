
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(Account account) {

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        if (accounts.containsKey(account.getAccountId())) {
            throw new IllegalArgumentException("Account ID already exists.");
        }

        accounts.put(account.getAccountId(), account);
    }

    public Account findAccount(String accountId) {
        return accounts.get(accountId);
    }

    public boolean transfer(
            String senderId,
            String recipientId,
            BigDecimal amount
    ) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Account sender = findAccount(senderId);
        Account recipient = findAccount(recipientId);

        if (sender == null || recipient == null ||
                senderId.equals(recipientId)) {
            return false;
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            return false;
        }

        sender.recordTransferSent(amount, recipientId);

        recipient.recordTransferReceived(amount, senderId);

        return true;
    }
}

