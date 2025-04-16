package models;

public abstract class BankAccount {
    protected double balance;
    protected String accountNumber;

    public double getBalance() {
        return balance;
    }

    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);
}

