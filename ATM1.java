package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;

    public User(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }
}

class Account {
    private String accountId;
    private double balance;
    private StringBuilder transactionHistory;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.transactionHistory = new StringBuilder();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        appendTransaction("Deposit", amount);
    }

    public void withdraw(double amount) {
        balance -= amount;
        appendTransaction("Withdraw", amount);
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            withdraw(amount);
            recipient.deposit(amount);
            appendTransaction("Transfer to " + recipient.getAccountId(), amount);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    public void appendTransaction(String transactionType, double amount) {
        transactionHistory.append(transactionType).append(": ").append(amount).append("\n");
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }
}

class Bank {
    private Map<String, User> users;
    private Map<String, Account> accounts;

    public Bank() {
        this.users = new HashMap<>();
        this.accounts = new HashMap<>();
    }

    public void addUser(String userId, String userPin) {
        users.put(userId, new User(userId, userPin));
        accounts.put(userId, new Account(userId, 0.0));
    }

    public boolean authenticateUser(String userId, String userPin) {
        User user = users.get(userId);
        return user != null && user.getUserPin().equals(userPin);
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}

public class ATM1 {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.addUser("user1", "1234");
        bank.addUser("user2", "5678");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        if (bank.authenticateUser(userId, userPin)) {
            System.out.println("Authentication successful!");
            Account account = bank.getAccount(userId);
            int choice;
            do {
                System.out.println("\n1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. View Transaction History");
                System.out.println("5. Transfer Balance");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Your Balance: " + account.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter the amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        System.out.println("Deposit successful!");
                        break;
                    case 3:
                        System.out.print("Enter the amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (account.getBalance() >= withdrawAmount) {
                            account.withdraw(withdrawAmount);
                            System.out.println("Withdrawal successful!");
                        } else {
                            System.out.println("Insufficient balance!");
                        }
                        break;
                    case 4:
                        String transactionHistory = account.getTransactionHistory();
                        System.out.println("Transaction History:");
                        System.out.println(transactionHistory);
                        break;
                    case 5:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientId = scanner.next();
                        Account recipientAccount = bank.getAccount(recipientId);
                        if (recipientAccount != null) {
                            System.out.print("Enter the amount to transfer: ");
                            double transferAmount = scanner.nextDouble();
                            account.transfer(recipientAccount, transferAmount);
                        } else {
                            System.out.println("Recipient's User ID not found.");
                        }
                        break;
                    case 6:
                        System.out.println("Exiting ATM. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 6);
        } else {
            System.out.println("Authentication failed. Invalid User ID or PIN.");
        }
    }
}
