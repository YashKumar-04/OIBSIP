
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ATM {

    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
    }

    public void start() {

        System.out.println("--------------------------------");
        System.out.println("       WELCOME TO JAVA ATM      ");
        System.out.println("--------------------------------");

        Account loggedInAccount = authenticate();

        if (loggedInAccount == null) {

            System.out.println("\nAccess denied. Too many incorrect attempts.");
            System.out.println("Please contact your bank.");

            return;
        }

        System.out.println("\nLogin successful!");
        System.out.println("Welcome, account "
                + loggedInAccount.getAccountId());

        showMenu(loggedInAccount);
    }

    private Account authenticate() {

        final int maximumAttempts = 3;

        for (int attempt = 1; attempt <= maximumAttempts; attempt++) {

            System.out.print("\nEnter User ID: ");
            String userId = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String enteredPin = scanner.nextLine().trim();

            Account account = bank.findAccount(userId);

            if (account != null && account.validatePin(enteredPin)) {
                return account;
            }

            int attemptsLeft = maximumAttempts - attempt;

            System.out.println("Incorrect User ID or PIN.");

            if (attemptsLeft > 0) {
                System.out.println("Attempts remaining: " + attemptsLeft);
            }
        }

        return null;
    }

    private void showMenu(Account account) {

        boolean running = true;

        while (running) {

            System.out.println("\n========== ATM MAIN MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    showTransactionHistory(account);
                    break;

                case "2":
                    withdraw(account);
                    break;

                case "3":
                    deposit(account);
                    break;

                case "4":
                    transfer(account);
                    break;

                case "5":
                    System.out.println(
                            "\nThank you for using Java ATM. Goodbye!"
                    );
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please select 1 to 5."
                    );
            }
        }
    }

    private void showTransactionHistory(Account account) {

        List<Transaction> history = account.getTransactionHistory();

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (history.isEmpty()) {
            System.out.println("No transactions in this session.");
            return;
        }

        for (int i = 0; i < history.size(); i++) {

            System.out.println(
                    (i + 1) + ". " + history.get(i)
            );
        }

        System.out.println("-----------------------------------------");

        System.out.println(
                "Current balance: Rs. " + account.getBalance()
        );
    }

    private void withdraw(Account account) {

        BigDecimal amount = readAmount("Enter withdrawal amount: ");

        if (amount == null) {
            return;
        }

        if (!account.withdraw(amount)) {

            System.out.println("Insufficient Funds.");
            return;
        }

        System.out.println("Withdrawal successful!");

        System.out.println(
                "Remaining balance: Rs. " + account.getBalance()
        );
    }

    private void deposit(Account account) {

        BigDecimal amount = readAmount("Enter deposit amount: ");

        if (amount == null) {
            return;
        }

        account.deposit(amount);

        System.out.println("Deposit successful!");

        System.out.println(
                "Updated balance: Rs. " + account.getBalance()
        );
    }

    private void transfer(Account sender) {

        System.out.print("Enter recipient account ID: ");

        String recipientId = scanner.nextLine().trim();

        Account recipient = bank.findAccount(recipientId);

        if (recipient == null) {

            System.out.println("Recipient account not found.");
            return;
        }

        if (recipientId.equals(sender.getAccountId())) {

            System.out.println(
                    "You cannot transfer money to your own account."
            );

            return;
        }

        BigDecimal amount = readAmount("Enter transfer amount: ");

        if (amount == null) {
            return;
        }

        if (sender.getBalance().compareTo(amount) < 0) {

            System.out.println("Insufficient Funds.");
            return;
        }

        boolean successful = bank.transfer(
                sender.getAccountId(),
                recipientId,
                amount
        );

        if (successful) {

            System.out.println("Transfer successful!");

            System.out.println(
                    "Transferred Rs. " + amount
                            + " to account " + recipientId
            );

            System.out.println(
                    "Remaining balance: Rs. " + sender.getBalance()
            );

        } else {

            System.out.println("Transfer could not be completed.");
        }
    }

    private BigDecimal readAmount(String prompt) {

        System.out.print(prompt);

        String input = scanner.nextLine().trim();

        try {

            BigDecimal amount = new BigDecimal(input);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {

                System.out.println(
                        "Invalid amount. Enter a value greater than zero."
                );

                return null;
            }

            return amount;

        } catch (NumberFormatException exception) {

            System.out.println(
                    "Invalid input. Please enter a valid number."
            );

            return null;
        }
    }
}

