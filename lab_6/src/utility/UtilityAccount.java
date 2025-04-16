package utility;

import utils.AccountNumberGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UtilityAccount {
    private String username;
    private String password;
    private String accountNumber;
    private List<Bill> billHistory;
    private Bill nextBill;

    public UtilityAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.billHistory = new ArrayList<>();
        this.accountNumber = AccountNumberGenerator.generate();
        this.nextBill = Bill.generateRandom();
        saveToFile(); // Save newly created account
        
    }
    
    public UtilityAccount(String username, String password, String accountNumber, List<Bill> billHistory, Bill nextBill) {
    	this.username = username;
    	this.password = password;
    	this.billHistory = billHistory;
    	this.accountNumber = accountNumber;
    	this.nextBill = nextBill;
    }

    public boolean authenticate(String inputUser, String inputPass) {
        return (inputUser.equals(username) || inputUser.equals(accountNumber)) && inputPass.equals(password);
    }

    public void payBill() {
        billHistory.add(nextBill);
        nextBill = Bill.generateRandom();
        saveToFile();
    }

    public List<Bill> getLast3Bills() {
        return billHistory.stream()
                .skip(Math.max(0, billHistory.size() - 3))
                .collect(Collectors.toList());
    }

    public Bill getNextBill() {
        return nextBill;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("utility.txt"))) {
            writer.write(username);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write(accountNumber);
            writer.newLine();
            for (Bill bill : billHistory) {
                writer.write(bill.toDataString()); // custom format like: amount,duedate,isPaid
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
