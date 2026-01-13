package model;

import dao.AccountDAO;

public class CurrentAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 10000.00;
    private AccountDAO accountDAO;

    public CurrentAccount(long accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
        this.accountType = "CURRENT";
        this.accountDAO = new AccountDAO();
    }

    public CurrentAccount() {
        super();
        this.accountType = "CURRENT";
        this.accountDAO = new AccountDAO();
    }

    public void deposit(double amount) {
        if (!validateBalance(amount)) {
            return;
        }

        double balanceBefore = this.balance;
        this.balance += amount;

        System.out.println("\n================================");
        System.out.println("Deposit Successful..");
        System.out.println("Amount Deposited: Rs." + amount);
        System.out.println("Previous Balance: Rs." + balanceBefore);
        System.out.println("Current Balance: Rs." + this.balance);
        System.out.println("================================");

        accountDAO.updateBalance(this.accountNumber, this.balance);
        accountDAO.logTransaction(this.accountNumber, "DEPOSIT", amount,
                balanceBefore, this.balance, "Cash deposit");
    }

    public void withdraw(double amount) {
        if (!validateBalance(amount)) {
            return;
        }

        double availableAmount = this.balance + OVERDRAFT_LIMIT;

        if (amount > availableAmount) {
            System.out.println("\n================================");
            System.out.println("Withdrawal Failed..");
            System.out.println("Overdraft limit exceeded");
            System.out.println("Current Balance: Rs." + this.balance);
            System.out.println("Overdraft Limit: Rs." + OVERDRAFT_LIMIT);
            System.out.println("Total Available: Rs." + availableAmount);
            System.out.println("================================");
            return;
        }

        double balanceBefore = this.balance;
        this.balance -= amount;

        System.out.println("\n================================");
        System.out.println("Withdrawal Successful..");
        System.out.println("Amount Withdrawn: Rs." + amount);
        System.out.println("Previous Balance: Rs." + balanceBefore);
        System.out.println("Current Balance: Rs." + this.balance);

        if (this.balance < 0) {
            System.out.println("Overdraft Used: Rs." + Math.abs(this.balance));
            System.out.println("Remaining Overdraft: Rs." + (OVERDRAFT_LIMIT - Math.abs(this.balance)));
        }
        System.out.println("================================");

        accountDAO.updateBalance(this.accountNumber, this.balance);
        accountDAO.logTransaction(this.accountNumber, "WITHDRAW", amount,
                balanceBefore, this.balance, "Cash withdrawal");
    }

    public void checkOverdraftStatus() {
        System.out.println("\n================================");
        System.out.println("Overdraft Status");
        System.out.println("================================");
        System.out.println("Current Balance: Rs." + this.balance);
        System.out.println("Overdraft Limit: Rs." + OVERDRAFT_LIMIT);

        if (this.balance < 0) {
            System.out.println("Overdraft Used: Rs." + Math.abs(this.balance));
            System.out.println("Remaining Limit: Rs." + (OVERDRAFT_LIMIT - Math.abs(this.balance)));
        } else {
            System.out.println("Overdraft Available: Rs." + OVERDRAFT_LIMIT);
        }
        System.out.println("Total Available: Rs." + (this.balance + OVERDRAFT_LIMIT));
        System.out.println("================================");
    }

    public void displayDetails() {
        super.displayDetails();
        System.out.println("Account Type: Current Account");
        System.out.println("Overdraft Facility: Rs." + OVERDRAFT_LIMIT);

        if (this.balance < 0) {
            System.out.println("Overdraft Used: Rs." + Math.abs(this.balance));
        }
    }

    public void showTransactionHistory() {
        System.out.println("\n================================");
        System.out.println("Transaction History (Last 10)");
        System.out.println("================================");

        java.util.List<String> transactions = accountDAO.getTransactionHistory(this.accountNumber);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
        System.out.println("================================");
    }

    public static double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}