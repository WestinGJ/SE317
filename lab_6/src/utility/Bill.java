package utility;

import java.time.LocalDate;

public class Bill {
    private double amount;
    private String dueDate;
    private boolean isPaid;

    public Bill(double amount, String dueDate, boolean isPaid) {
        this.amount = amount;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
    }

    public static Bill generateRandom() {
        double randomAmount = Math.round(((Math.random() * 100) + 20) * 100.0) / 100.0;
        String dueDate = LocalDate.now().plusDays((int) (Math.random() * 30)).toString();
        boolean isPaid = false;
        return new Bill(randomAmount, dueDate, isPaid);
    }

    public double getAmount() {
        return amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Amount: $" + amount + ", Due Date: " + dueDate;
    }
    
    public String toDataString() {
        return amount + "," + dueDate + "," + isPaid;
    }

    public static Bill fromDataString(String data) {
        String[] parts = data.split(",");
        double amount = Double.parseDouble(parts[0]);
        String dueDate = parts[1];
        boolean isPaid = Boolean.parseBoolean(parts[2]);
        return new Bill(amount, dueDate, isPaid);
    }

}


