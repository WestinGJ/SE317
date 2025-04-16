package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CheckingAccount extends BankAccount {
	private static final String CHECKING = "checking.txt";
    private double dailyDeposit = 0;
    private double dailyWithdraw = 0;
    private static final double MAX_DAILY_DEPOSIT = 5000;
    private static final double MAX_DAILY_WITHDRAW = 500;
    
    public CheckingAccount() {
        this.balance = readBalanceFromFile();
    }

    private double readBalanceFromFile() {
        File file = new File(CHECKING);
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHECKING))) {
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
        if (dailyWithdraw + amount <= MAX_DAILY_WITHDRAW && balance >= amount) {
            balance -= amount;
            dailyWithdraw += amount;
            writeBalanceToFile();
            return true;
        }
        return false;
    }

    public boolean payBill(double amount) {
        return withdraw(amount);
    }

    public boolean transferToSaving(SavingAccount savings, double amount) {
        if (balance >= amount) {
            if (savings.deposit(amount)) {
                balance -= amount;
                writeBalanceToFile();
                return true;
            }
        }
        return false;
    }
}

