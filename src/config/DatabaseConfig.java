package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static Connection connection = null;

    static {
        loadProperties();
    }

    private DatabaseConfig() {}

    private static void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                System.out.println("Using default configuration...");
                URL = "jdbc:mysql://localhost:3306/bank_management_system";
                USER = "root";
                PASSWORD = "";
                return;
            }

            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

            System.out.println("Database configuration loaded from properties file..");

        } catch (IOException e) {
            System.out.println("Error loading database properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully..");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found..");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed..");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed..");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection..");
            e.printStackTrace();
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}