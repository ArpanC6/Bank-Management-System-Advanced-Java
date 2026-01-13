package management;

import service.BankService;
import config.DatabaseConfig;
import model.*;
import web.WebServer;
import java.util.Scanner;

public class Main {

    private static BankService bankService = new BankService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        if (!DatabaseConfig.testConnection()) {
            System.out.println("\n================================");
            System.out.println("DATABASE CONNECTION FAILED..");
            System.out.println("================================");
            System.out.println("Please check:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database exists");
            System.out.println("3. Username and password are correct");
            System.out.println("================================\n");
            return;
        }

        System.out.println("\n================================");
        System.out.println("BANK MANAGEMENT SYSTEM");
        System.out.println("With MySQL Database & Web Interface");
        System.out.println("================================");
        System.out.println("Database Connected Successfully..");

        // Start Web Server
        WebServer.start();

        System.out.println("\nYou can now:");
        System.out.println("1. Open http://localhost:8080 in your browser for Web Interface");
        System.out.println("2. Use the Console Menu below for Command Line Interface");
        System.out.println("");

        boolean running = true;

        while (running) {
            displayMenu();

            System.out.print("\nEnter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createSavingsAccount();
                    break;
                case 2:
                    createCurrentAccount();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    viewAccountDetails();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    transferMoney();
                    break;
                case 8:
                    bankService.showAllAccounts();
                    break;
                case 9:
                    deleteAccount();
                    break;
                case 10:
                    calculateInterest();
                    break;
                case 0:
                    exitProgram();
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice Please try again..");
            }

            if (running) {
                System.out.println("\nPress Enter to continue..");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n================================");
        System.out.println("CONSOLE MENU");
        System.out.println("================================");
        System.out.println("1. Create Savings Account");
        System.out.println("2. Create Current Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. View Account Details");
        System.out.println("6. View Transaction History");
        System.out.println("7. Transfer Money");
        System.out.println("8. View All Accounts");
        System.out.println("9. Close Account");
        System.out.println("10. Calculate Interest");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    private static void createSavingsAccount() {
        System.out.println("\n================================");
        System.out.println("CREATE SAVINGS ACCOUNT");
        System.out.println("================================");

        scanner.nextLine();

        System.out.print("Enter account holder name: ");
        String holderName = scanner.nextLine();

        System.out.print("Enter initial deposit (Min Rs." +
                SavingsAccount.getMinimumBalance() + "): ");
        double initialBalance = getDoubleInput();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();

        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        bankService.createSavingsAccount(holderName, initialBalance,
                phoneNumber, email, address);
    }

    private static void createCurrentAccount() {
        System.out.println("\n================================");
        System.out.println("CREATE CURRENT ACCOUNT");
        System.out.println("================================");

        scanner.nextLine();

        System.out.print("Enter account holder name: ");
        String holderName = scanner.nextLine();

        System.out.print("Enter initial deposit: ");
        double initialBalance = getDoubleInput();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();

        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        bankService.createCurrentAccount(holderName, initialBalance,
                phoneNumber, email, address);
    }

    private static void depositMoney() {
        System.out.println("\n================================");
        System.out.println("DEPOSIT MONEY");
        System.out.println("================================");

        System.out.print("Enter account number: ");
        long accountNumber = getLongInput();

        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();

        bankService.depositMoney(accountNumber, amount);
    }

    private static void withdrawMoney() {
        System.out.println("\n================================");
        System.out.println("WITHDRAW MONEY");
        System.out.println("================================");

        System.out.print("Enter account number: ");
        long accountNumber = getLongInput();

        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();

        bankService.withdrawMoney(accountNumber, amount);
    }

    private static void viewAccountDetails() {
        System.out.println("\n================================");
        System.out.println("VIEW ACCOUNT DETAILS");
        System.out.println("================================");

        System.out.print("Enter account number: ");
        long accountNumber = getLongInput();

        bankService.viewAccountDetails(accountNumber);
    }

    private static void viewTransactionHistory() {
        System.out.println("\n================================");
        System.out.println("TRANSACTION HISTORY");
        System.out.println("================================");

        System.out.print("Enter account number: ");
        long accountNumber = getLongInput();

        bankService.viewTransactionHistory(accountNumber);
    }

    private static void transferMoney() {
        System.out.println("\n================================");
        System.out.println("TRANSFER MONEY");
        System.out.println("================================");

        System.out.print("Enter source account number: ");
        long fromAccount = getLongInput();

        System.out.print("Enter destination account number: ");
        long toAccount = getLongInput();

        System.out.print("Enter amount to transfer: ");
        double amount = getDoubleInput();

        bankService.transferMoney(fromAccount, toAccount, amount);
    }

    private static void deleteAccount() {
        System.out.println("\n================================");
        System.out.println("CLOSE ACCOUNT");
        System.out.println("================================");

        System.out.print("Enter account number to close: ");
        long accountNumber = getLongInput();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.next().toLowerCase();

        if (confirm.equals("yes")) {
            bankService.deleteAccount(accountNumber);
        } else {
            System.out.println("Account closure cancelled.");
        }
    }

    private static void calculateInterest() {
        System.out.println("\n================================");
        System.out.println("CALCULATE INTEREST");
        System.out.println("================================");

        System.out.print("Calculate interest for all savings accounts? (yes/no): ");
        String confirm = scanner.next().toLowerCase();

        if (confirm.equals("yes")) {
            bankService.calculateInterestForAll();
        }
    }

    private static void exitProgram() {
        System.out.println("\n================================");
        System.out.println("Thank you for using our services..");
        System.out.println("Closing database...");
        System.out.println("Stopping web server...");
        System.out.println("================================");

        DatabaseConfig.closeConnection();
        WebServer.stop();
        scanner.close();

        System.out.println("\nProgram terminated successfully..\n");
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static long getLongInput() {
        while (!scanner.hasNextLong()) {
            System.out.print("Invalid input Please enter a valid account number: ");
            scanner.next();
        }
        return scanner.nextLong();
    }

    private static double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input Please enter a valid amount: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}