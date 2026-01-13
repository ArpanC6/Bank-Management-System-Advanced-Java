package service;

import dao.AccountDAO;
import model.*;
import java.util.List;

public class BankService {

    private AccountDAO accountDAO;

    public BankService() {
        this.accountDAO = new AccountDAO();
    }

    public void createSavingsAccount(String holderName, double initialBalance, String phoneNumber, String email, String address) {

        if (initialBalance < SavingsAccount.getMinimumBalance()) {
            System.out.println("\nCannot create account..");
            System.out.println("Minimum initial deposit for Savings Account: Rs." + SavingsAccount.getMinimumBalance());
            return;
        }

        long accountNumber = accountDAO.generateAccountNumber();

        SavingsAccount account = new SavingsAccount(accountNumber, holderName, initialBalance);
        account.setPhoneNumber(phoneNumber);
        account.setEmail(email);
        account.setAddress(address);

        if (accountDAO.createAccount(account)) {
            System.out.println("\n================================");
            System.out.println("Savings Account Created..");
            System.out.println("================================");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Account Holder: " + holderName);
            System.out.println("Initial Balance: Rs." + initialBalance);
            System.out.println("Interest Rate: " + SavingsAccount.getInterestRate() + "%");
            System.out.println("\nPlease save your account number..");
        }
    }

    public void createCurrentAccount(String holderName, double initialBalance, String phoneNumber, String email, String address) {

        long accountNumber = accountDAO.generateAccountNumber();

        CurrentAccount account = new CurrentAccount(accountNumber, holderName, initialBalance);
        account.setPhoneNumber(phoneNumber);
        account.setEmail(email);
        account.setAddress(address);

        if (accountDAO.createAccount(account)) {
            System.out.println("\n================================");
            System.out.println("Current Account Created!");
            System.out.println("================================");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Account Holder: " + holderName);
            System.out.println("Initial Balance: Rs." + initialBalance);
            System.out.println("Overdraft Limit: Rs." + CurrentAccount.getOverdraftLimit());
            System.out.println("\nPlease save your account number..");
        }
    }

    public Account findAccount(long accountNumber) {
        return accountDAO.findAccountByNumber(accountNumber);
    }

    public void depositMoney(long accountNumber, double amount) {
        Account account = findAccount(accountNumber);

        if (account != null) {
            account.deposit(amount);
        }
    }

    public void withdrawMoney(long accountNumber, double amount) {
        Account account = findAccount(accountNumber);

        if (account != null) {
            account.withdraw(amount);
        }
    }

    public void showAllAccounts() {
        List<Account> accounts = accountDAO.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("\nNo accounts found in the system..");
            return;
        }

        System.out.println("\n================================");
        System.out.println("ALL ACCOUNTS");
        System.out.println("================================");
        System.out.println("\nTotal Accounts: " + accounts.size());
        System.out.println("--------------------------------");

        for (Account account : accounts) {
            System.out.println(account.getAccountNumber() + " | " +
                    account.getHolderName() + " | " +
                    account.getAccountType() + " | Rs." +
                    account.getBalance());
        }
        System.out.println("--------------------------------");
    }

    public void viewAccountDetails(long accountNumber) {
        Account account = findAccount(accountNumber);

        if (account != null) {
            account.displayDetails();
        }
    }

    public void viewTransactionHistory(long accountNumber) {
        Account account = findAccount(accountNumber);

        if (account != null) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).showTransactionHistory();
            } else if (account instanceof CurrentAccount) {
                ((CurrentAccount) account).showTransactionHistory();
            }
        }
    }

    public void transferMoney(long fromAccount, long toAccount, double amount) {
        Account sourceAcc = findAccount(fromAccount);
        Account destAcc = findAccount(toAccount);

        if (sourceAcc == null || destAcc == null) {
            System.out.println("One or both accounts not found..");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid transfer amount..");
            return;
        }

        double availableBalance = sourceAcc.getBalance();

        if (sourceAcc instanceof CurrentAccount) {
            availableBalance += CurrentAccount.getOverdraftLimit();
        } else if (sourceAcc instanceof SavingsAccount) {
            availableBalance -= SavingsAccount.getMinimumBalance();
        }

        if (amount > availableBalance) {
            System.out.println("Insufficient funds for transfer..");
            return;
        }

        double srcBalanceBefore = sourceAcc.getBalance();
        double destBalanceBefore = destAcc.getBalance();

        sourceAcc.setBalance(sourceAcc.getBalance() - amount);
        destAcc.setBalance(destAcc.getBalance() + amount);

        accountDAO.updateBalance(fromAccount, sourceAcc.getBalance());
        accountDAO.updateBalance(toAccount, destAcc.getBalance());

        accountDAO.logTransaction(fromAccount, "TRANSFER_OUT", amount,
                srcBalanceBefore, sourceAcc.getBalance(),
                "Transfer to " + toAccount);
        accountDAO.logTransaction(toAccount, "TRANSFER_IN", amount,
                destBalanceBefore, destAcc.getBalance(),
                "Transfer from " + fromAccount);

        System.out.println("\n================================");
        System.out.println("Transfer Successful!");
        System.out.println("From Account: " + fromAccount);
        System.out.println("To Account: " + toAccount);
        System.out.println("Amount: Rs." + amount);
        System.out.println("New Balance (Source): Rs." + sourceAcc.getBalance());
        System.out.println("New Balance (Destination): Rs." + destAcc.getBalance());
        System.out.println("================================");
    }

    public void deleteAccount(long accountNumber) {
        Account account = findAccount(accountNumber);

        if (account == null) {
            return;
        }

        if (account.getBalance() > 0) {
            System.out.println("Cannot delete account with remaining balance..");
            System.out.println("Please withdraw all funds before closing the account.");
            return;
        }

        if (accountDAO.deleteAccount(accountNumber)) {
            System.out.println("Account closed successfully..");
        }
    }

    public void calculateInterestForAll() {
        List<Account> accounts = accountDAO.getAllAccounts();
        int count = 0;

        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).addInterest();
                count++;
            }
        }

        System.out.println("\nInterest calculated for " + count + " savings accounts");
    }
}