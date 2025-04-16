package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UtilityCompany {

    public UtilityAccount register(String username, String password) {
        UtilityAccount account = new UtilityAccount(username, password);
        return account;
    }

    public UtilityAccount login(String id, String password) {
    	File file = new File("utility.txt");
        UtilityAccount acc = loadFromFile(file);
        if (acc != null && acc.authenticate(id, password)) {
            return acc;
        }
        return null;
    }
    
    private UtilityAccount loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String username = reader.readLine();
            String password = reader.readLine();
            String accountNumber = reader.readLine();

            String line;
            ArrayList<Bill> billHistory = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                billHistory.add(Bill.fromDataString(line));
            }

            // If no upcoming bill was saved, generate one
            Bill nextBill = Bill.generateRandom();
            return new UtilityAccount(username, password, accountNumber, billHistory, nextBill);

        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
    }
}

