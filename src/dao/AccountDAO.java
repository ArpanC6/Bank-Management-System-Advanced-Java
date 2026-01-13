package dao;

import config.DatabaseConfig;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private Connection connection;

    public AccountDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (account_number, holder_name, account_type, balance, phone_number, email, address, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setString(2, account.getHolderName());
            pstmt.setString(3, account.getAccountType());
            pstmt.setDouble(4, account.getBalance());
            pstmt.setString(5, account.getPhoneNumber());
            pstmt.setString(6, account.getEmail());
            pstmt.setString(7, account.getAddress());
            pstmt.setString(8, "ACTIVE");

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Account created successfully in database..");

                if (account.getBalance() > 0) {
                    logTransaction(account.getAccountNumber(), "DEPOSIT",
                            account.getBalance(), 0, account.getBalance(),
                            "Initial deposit");
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Account findAccountByNumber(long accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            } else {
                System.out.println("Account not found..");
            }

        } catch (SQLException e) {
            System.out.println("Error finding account: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(long accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ?, updated_at = CURRENT_TIMESTAMP WHERE account_number = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, newBalance);
            pstmt.setLong(2, accountNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE status = 'ACTIVE' ORDER BY account_number";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                accounts.add(extractAccountFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean deleteAccount(long accountNumber) {
        String sql = "UPDATE accounts SET status = 'INACTIVE' WHERE account_number = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account deactivated successfully..");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean logTransaction(long accountNumber, String type, double amount, double balanceBefore, double balanceAfter, String description) {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, balance_before, balance_after, description, reference_number) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setDouble(4, balanceBefore);
            pstmt.setDouble(5, balanceAfter);
            pstmt.setString(6, description);
            pstmt.setString(7, "TXN" + System.currentTimeMillis());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getTransactionHistory(long accountNumber) {
        List<String> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC LIMIT 10";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String transaction = rs.getTimestamp("transaction_date") + " | " +
                        rs.getString("transaction_type") + " | Rs." +
                        rs.getDouble("amount") + " | Balance: Rs." +
                        rs.getDouble("balance_after") + " | " +
                        rs.getString("description");
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }

    public long generateAccountNumber() {
        String sql = "SELECT MAX(account_number) as max_acc FROM accounts";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                long maxAcc = rs.getLong("max_acc");
                return maxAcc > 0 ? maxAcc + 1 : 1001001001L;
            }

        } catch (SQLException e) {
            System.out.println("Error generating account number: " + e.getMessage());
        }
        return 1001001001L;
    }

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        String accountType = rs.getString("account_type");
        Account account;

        if ("SAVINGS".equals(accountType)) {
            account = new SavingsAccount();
        } else {
            account = new CurrentAccount();
        }

        account.setAccountNumber(rs.getLong("account_number"));
        account.setHolderName(rs.getString("holder_name"));
        account.setBalance(rs.getDouble("balance"));
        account.setAccountType(accountType);
        account.setStatus(rs.getString("status"));
        account.setPhoneNumber(rs.getString("phone_number"));
        account.setEmail(rs.getString("email"));
        account.setAddress(rs.getString("address"));

        return account;
    }

    public boolean accountExists(long accountNumber) {
        String sql = "SELECT COUNT(*) as count FROM accounts WHERE account_number = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking account: " + e.getMessage());
        }
        return false;
    }
}