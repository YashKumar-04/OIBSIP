
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();

        Account account1 = new Account(
                "1001",
                "1234",
                new BigDecimal("25000.00")
        );

        Account account2 = new Account(
                "1002",
                "5678",
                new BigDecimal("10000.00")
        );


        bank.addAccount(account1);
        bank.addAccount(account2);

        System.out.println("DEMO LOGIN DETAILS");

        System.out.println("Account 1: User ID = 1001, PIN = 1234");
        System.out.println("Account 2: User ID = 1002, PIN = 5678");

        Scanner scanner = new Scanner(System.in);

        ATM atm = new ATM(bank, scanner);

        atm.start();

        scanner.close();
    }
}
