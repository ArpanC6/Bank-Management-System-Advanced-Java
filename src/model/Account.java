package model;

public abstract class Account {

    protected long accountNumber;
    protected String holderName;
    protected double balance;
    protected String accountType;
    protected String status;
    protected String phoneNumber;
    protected String email;
    protected String address;

    public Account(long accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.status = "ACTIVE";
    }

    public Account() {}

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    public void displayDetails() {
        System.out.println("\n================================");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Account Type: " + accountType);
        System.out.println("Current Balance: Rs." + balance);
        System.out.println("Status: " + status);
        if (phoneNumber != null) {
            System.out.println("Phone: " + phoneNumber);
        }
        System.out.println("================================");
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected boolean validateBalance(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount Amount must be positive..");
            return false;
        }
        return true;
    }
}