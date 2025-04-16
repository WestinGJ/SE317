import models.User;
import utility.Bill;
import utility.UtilityAccount;
import utility.UtilityCompany;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UtilityCompany utilityCompany = new UtilityCompany();
    private static User user;
    private static UtilityAccount utilityAccount;

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM System!");

        // Create a bank user
        System.out.print("Enter your name to create a bank user: ");
        user = new User(scanner.nextLine());

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Deposit to Checking");
            System.out.println("2. Withdraw from Checking");
            System.out.println("3. Transfer from Checking to Saving");
            System.out.println("4. Transfer from Saving to Checking");
            System.out.println("5. Pay Utility Bill from Checking");
            System.out.println("6. View Bank Account Balances");
            System.out.println("7. Login to Utility Account");
            System.out.println("8. Create Utility Account");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> depositToChecking();
                case 2 -> withdrawFromChecking();
                case 3 -> transferToSaving();
                case 4 -> transferToChecking();
                case 5 -> payBill();
                case 6 -> showBalances();
                case 7 -> utilityAccountMenu();
                case 8 -> createUtilityAccount();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("Thanks for using the ATM. Goodbye!");
    }

    private static void depositToChecking() {
        System.out.print("Enter amount to deposit to Checking: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (user.getChecking().deposit(amount)) {
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Deposit failed. Check daily limit.");
        }
    }

    private static void withdrawFromChecking() {
        System.out.print("Enter amount to withdraw from Checking: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (user.getChecking().withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. Check daily limit or balance.");
        }
    }

    private static void transferToSaving() {
        System.out.print("Enter amount to transfer to Saving: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (user.getChecking().transferToSaving(user.getSaving(), amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    private static void transferToChecking() {
        System.out.print("Enter amount to transfer to Checking: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (user.getSaving().transferToChecking(user.getChecking(), amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Check daily limit or balance.");
        }
    }

    private static void payBill() {
        Bill bill = utilityAccount.getNextBill();
        double amount = bill.getAmount();
        System.out.println("Next utility bill: " + bill);
        System.out.print("Pay this bill from Checking? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            if (user.getChecking().payBill(amount)) {
                utilityAccount.payBill();
                System.out.println("Bill paid successfully.");
            } else {
                System.out.println("Payment failed. Check balance or daily limit.");
            }
        }
    }

    private static void showBalances() {
        System.out.printf("Checking Balance: $%.2f\n", user.getChecking().getBalance());
        System.out.printf("Saving Balance: $%.2f\n", user.getSaving().getBalance());
    }

    private static void utilityAccountMenu() {
        System.out.print("Enter username or account number: ");
        String id = scanner.nextLine();
        System.out.print("Enter password: ");
        String pw = scanner.nextLine();

        UtilityAccount acc = utilityCompany.login(id, pw);
        if (acc == null) {
            System.out.println("Login failed.");
            return;
        }

        System.out.println("Login successful!");
        utilityAccount = acc;
        boolean utilRunning = true;
        while (utilRunning) {
            System.out.println("\n--- Utility Account Menu ---");
            System.out.println("1. View Last 3 Paid Bills");
            System.out.println("2. View Next Bill");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            int opt = Integer.parseInt(scanner.nextLine());
            switch (opt) {
                case 1 -> {
                    List<Bill> bills = acc.getLast3Bills();
                    if (bills.isEmpty()) {
                        System.out.println("No bill history.");
                    } else {
                        System.out.println("Last 3 Paid Bills:");
                        for (Bill b : bills) {
                            System.out.println(b);
                        }
                    }
                }
                case 2 -> System.out.println("Next Bill: " + acc.getNextBill());
                case 0 -> utilRunning = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }
    
    private static void createUtilityAccount() {
    	System.out.println("Let's set up your Utility Company account.");
        System.out.print("Enter username: ");
        String uname = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();
        utilityAccount = utilityCompany.register(uname, pass);
        System.out.println("Utility account created! Your account number is: " + utilityAccount.getAccountNumber());
    }
}


