# Bank Management System - Advanced Java

An advanced Bank Management System built using **Core Java**, **JDBC**, and **MySQL**, featuring real time database connectivity, clean OOP based architecture, and a modern web interface powered by Java HTTP Server.

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
[![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)



## Key Features

### Account Management
**Dual Account Types**: 
1) Savings Account (4.5% interest, Rs. 1000 minimum balance)
2) Current Account (Rs. 10,000 overdraft facility)
**Complete Account Operations**: Create, View, Close accounts
**Automatic Account Number Generation**

### Banking Operations
1) **Deposit Money**: Add funds to any account
2) **Withdraw Money**: Withdraw with balance validation
3) **Transfer Money**: Seamless inter-account transfers
4) **Interest Calculation**: Automatic interest @ 4.5% for savings accounts

### Tracking & Reports
1) **Transaction History**: Complete audit trail of all operations
2) **Account Details**: View comprehensive account information
3) **All Accounts View**: System-wide account management
4) **Real-time Balance Updates**

### Security & Configuration
**Secure Database Configuration**: Password protection with `.gitignore`
**Transaction Logging**: All operations recorded in database
**Input Validation**: Comprehensive error handling

### Dual Interface
**Web Interface**: Modern, responsive HTML/CSS/JS interface
**Console Interface**: Traditional command-line access
**Java HTTP Server**: Built-in web server (Port 8080)


## Technologies Used

**Programming Language** -> Core Java (JDK 8+) 
**Database** -> MySQL 8.0 
**Database Connectivity** -> JDBC (Java Database Connectivity) 
**Web Server** -> Java HTTP Server (com.sun.net.httpserver) 
**Frontend** -> HTML5, CSS3, JavaScript (Vanilla) 
**Architecture** -> DAO Pattern, MVC, OOP Principles 
**Design Patterns** -> Singleton (Database Connection), Factory (Account Creation) 


## Prerequisites

Before running this application, ensure you have:

**Java Development Kit (JDK)** 8 or higher
**MySQL Server** 8.0 or higher
**MySQL Connector/J** (JDBC Driver) - Already included in project
**Any IDE** (IntelliJ IDEA, Eclipse, NetBeans) or Command Line


## Installation & Setup

### Step 1: Clone the Repository
```bash
git clone https://github.com/ArpanC6/Bank-Management-System-Advanced-Java.git
cd Bank-Management-System-Advanced-Java
```

### Step 2: Database Setup

1. **Start MySQL Server**

2. **Create Database:**
```sql
CREATE DATABASE bank_management_system;
```

3. **The application will automatically create tables on first run:**
   - `accounts` - Stores account information
   - `transactions` - Stores transaction history

### Step 3: Configure Database Connection

1. **Copy the example properties file:**
```bash
cp resources/db.properties.example resources/db.properties
```

2. **Edit `resources/db.properties` with your credentials:**
```properties
db.url=jdbc:mysql://localhost:3306/bank_management_system
db.user=your_mysql_username
db.password=your_mysql_password
```

**Important:** Never commit `db.properties` to version control!

### Step 4: Add External Libraries

Add **MySQL Connector/J** to your project:

#### For IntelliJ IDEA:
1. File → Project Structure → Libraries
2. Add → Java → Select `mysql-connector-j-9.5.0.jar`
3. Apply → OK

#### For Eclipse:
1. Right-click project → Build Path → Configure Build Path
2. Libraries → Add External JARs
3. Select `mysql-connector-j-9.5.0.jar`
4. Apply → OK

### Step 5: Run the Application

#### Using IDE:
1. Open `management/Main.java`
2. Run the `main()` method

#### Using Command Line:
```bash
# Compile
javac -cp ".:mysql-connector-j-9.5.0.jar" src/**/*.java -d bin

# Run
java -cp "bin:mysql-connector-j-9.5.0.jar" management.Main
```

### Step 6: Access the Application

**Web Interface**: Open browser → `http://localhost:8080`
**Console Interface**: Use the terminal menu


## Usage Guide

### Creating an Account

1. Navigate to **Create Savings Account** or **Create Current Account**
2. Fill in:
1) Account holder name
2) Initial deposit (Min Rs. 1000 for savings)
3) Phone number
4) Email
5) Address
4. Click **Create Account**
**Save the generated account number!**

### Performing Transactions

#### Deposit:
1. Go to **Deposit Money**
2. Enter account number and amount
3. Click **Deposit**

#### Withdraw:
1. Go to **Withdraw Money**
2. Enter account number and amount
3. System validates minimum balance (savings) or overdraft limit (current)
4. Click **Withdraw**

#### Transfer:
1. Go to **Transfer Money**
2. Enter source account, destination account, and amount
3. Click **Transfer**

### Viewing Information

**Account Details**: View complete account information
**Transaction History**: See last 10 transactions
**All Accounts**: View all active accounts in system

### Interest Calculation

1. Go to **Calculate Interest**
2. Click **Calculate Interest for All**
3. System adds 4.5% interest to all savings accounts


## Security Features

**Password Protection**: Database credentials stored securely
**Git Ignore**: Sensitive files excluded from version control
**Input Validation**: All inputs validated before processing
**Transaction Logging**: Complete audit trail
**Balance Validation**: Prevents overdrafts and insufficient balance withdrawals


## Known Issues & Limitations

1) Web interface is currently frontend-only (no backend API integration)
2) No user authentication system
3) Single-user application (no concurrent user management)
4) Interest calculation is manual (not automated/scheduled)


## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## Author

**Arpan Chakraborty**

- GitHub: [@ArpanC6](https://github.com/ArpanC6)
- LinkedIn: [Arpan Chakraborty](https://www.linkedin.com/in/arpan-chakraborty-63251227b/)

