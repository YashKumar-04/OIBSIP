
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private final String accountId;
    private final String pin;
    private BigDecimal balance;

    private final ArrayList<Transaction> transactionHistory;

    public Account(String accountId, String pin, BigDecimal openingBalance) {

        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be empty.");
        }

        if (pin == null || pin.trim().isEmpty()) {
            throw new IllegalArgumentException("PIN cannot be empty.");
        }

        if (openingBalance == null ||
                openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid opening balance.");
        }

        this.accountId = accountId;
        this.pin = pin;
        this.balance = openingBalance;

        this.transactionHistory = new ArrayList<>();
    }
    public String getAccountId() {
        return accountId;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }
    public void deposit(BigDecimal amount) {
        validatePositiveAmount(amount);

        balance = balance.add(amount);

        transactionHistory.add(
                new Transaction("DEPOSIT", amount, "Cash deposited")
        );
    }
    public boolean withdraw(BigDecimal amount) {
        validatePositiveAmount(amount);

        if (balance.compareTo(amount) < 0) {
            return false;
        }

        balance = balance.subtract(amount);

        transactionHistory.add(
                new Transaction("WITHDRAWAL", amount, "Cash withdrawn")
        );

        return true;
    }
    void recordTransferSent(BigDecimal amount, String recipientId) {

        balance = balance.subtract(amount);

        transactionHistory.add(
                new Transaction(
                        "TRANSFER SENT",
                        amount,
                        "Money sent to account " + recipientId
                )
        );
    }
    void recordTransferReceived(BigDecimal amount, String senderId) {

        balance = balance.add(amount);

        transactionHistory.add(
                new Transaction(
                        "TRANSFER RECEIVED",
                        amount,
                        "Money received from account " + senderId
                )
        );
    }
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    private void validatePositiveAmount(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Amount must be greater than zero."
            );
        }
    }
}
