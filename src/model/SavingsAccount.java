package model;

import dao.AccountDAO;

public class SavingsAccount extends Account {

    private static final double MINIMUM_BALANCE = 1000.00;
    private static final double INTEREST_RATE = 4.5;
    private AccountDAO accountDAO;

    public SavingsAccount(long accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
        this.accountType = "SAVINGS";
        this.accountDAO = new AccountDAO();
    }

    public SavingsAccount() {
        super();
        this.accountType = "SAVINGS";
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

        if (this.balance - amount < MINIMUM_BALANCE) {
            System.out.println("\n================================");
            System.out.println("Withdrawal Failed!");
            System.out.println("Minimum balance of Rs." + MINIMUM_BALANCE + " must be maintained");
            System.out.println("Current Balance: Rs." + this.balance);
            System.out.println("Available for Withdrawal: Rs." + (this.balance - MINIMUM_BALANCE));
            System.out.println("================================");
            return;
        }

        double balanceBefore = this.balance;
        this.balance -= amount;

        System.out.println("\n================================");
        System.out.println("Withdrawal Successful!");
        System.out.println("Amount Withdrawn: Rs." + amount);
        System.out.println("Previous Balance: Rs." + balanceBefore);
        System.out.println("Current Balance: Rs." + this.balance);
        System.out.println("================================");

        accountDAO.updateBalance(this.accountNumber, this.balance);
        accountDAO.logTransaction(this.accountNumber, "WITHDRAW", amount,
                balanceBefore, this.balance, "Cash withdrawal");
    }

    public void addInterest() {
        double interest = (this.balance * INTEREST_RATE) / 100;
        double balanceBefore = this.balance;
        this.balance += interest;

        System.out.println("\n================================");
        System.out.println("Interest Added!");
        System.out.println("Interest Rate: " + INTEREST_RATE + "%");
        System.out.println("Interest Amount: Rs." + interest);
        System.out.println("Previous Balance: Rs." + balanceBefore);
        System.out.println("New Balance: Rs." + this.balance);
        System.out.println("================================");

        accountDAO.updateBalance(this.accountNumber, this.balance);
        accountDAO.logTransaction(this.accountNumber, "INTEREST", interest,
                balanceBefore, this.balance,
                "Annual interest @ " + INTEREST_RATE + "%");
    }

    public void displayDetails() {
        super.displayDetails();
        System.out.println("Account Type: Savings Account");
        System.out.println("Minimum Balance: Rs." + MINIMUM_BALANCE);
        System.out.println("Interest Rate: " + INTEREST_RATE + "%");
        System.out.println("Available for Withdrawal: Rs." + (this.balance - MINIMUM_BALANCE));
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

    public static double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    public static double getInterestRate() {
        return INTEREST_RATE;
    }
}