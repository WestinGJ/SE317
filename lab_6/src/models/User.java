package models;

public class User {
    private String name;
    private CheckingAccount checking;
    private SavingAccount saving;

    public User(String name) {
        this.name = name;
        this.checking = new CheckingAccount();
        this.saving = new SavingAccount();
    }

    public String getName() {
        return name;
    }

    public CheckingAccount getChecking() {
        return checking;
    }

    public SavingAccount getSaving() {
        return saving;
    }
}


