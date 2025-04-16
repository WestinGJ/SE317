package models;

import java.io.*;

public class SavingAccount extends BankAccount {
    private static final String SAVINGS = "savings.txt";
    private double dailyDeposit = 0;
    private double dailyTransferToChecking = 0;
    private static final double MAX_DAILY_DEPOSIT = 5000;
    private static final double MAX_DAILY_TRANSFER = 100;

    public SavingAccount() {
        this.balance = readBalanceFromFile();
    }

    private double readBalanceFromFile() {
        File file = new File(SAVINGS);
        if (!file.exists()) {
            return 0.0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Double.parseDouble(line);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    private void writeBalanceToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVINGS))) {
            writer.write(String.valueOf(balance));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deposit(double amount) {
        if (dailyDeposit + amount <= MAX_DAILY_DEPOSIT) {
            balance += amount;
            dailyDeposit += amount;
            writeBalanceToFile();
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        return false; // Withdraw not allowed
    }

    public boolean transferToChecking(CheckingAccount checking, double amount) {
        if (dailyTransferToChecking + amount <= MAX_DAILY_TRANSFER && balance >= amount) {
            if (checking.deposit(amount)) {
                balance -= amount;
                dailyTransferToChecking += amount;
                writeBalanceToFile();
                return true;
            }
        }
        return false;
    }
}


