package com.thedannyvais;

import com.thedannyvais.Accounts.Account;
import com.thedannyvais.Accounts.AccountDao;
import com.thedannyvais.Accounts.AccountDaoFactory;
import com.thedannyvais.Transactions.TransferRecord;
import com.thedannyvais.Customers.Customer;
import com.thedannyvais.Customers.CustomerDao;
import com.thedannyvais.Customers.CustomerDaoFactory;
import com.thedannyvais.Employees.AccountRequest;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BankingDisplay {
    AccountDao accountDao = AccountDaoFactory.getAccountDao();
    CustomerDao customerDao = CustomerDaoFactory.getCustomerDao();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public BankingDisplay() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("*****************************");
            System.out.println("Select from the options below");
            System.out.println("*****************************");
            System.out.println("PRESS 1: View your info");
            System.out.println("PRESS 2: Request Banking Account");
            System.out.println("PRESS 3: View Account Requests");
            System.out.println("PRESS 4: View Accounts Info");
            System.out.println("PRESS 5: Deposit");
            System.out.println("PRESS 6: Withdraw");
            System.out.println("PRESS 7: Send Money Transfer");
            System.out.println("PRESS 8: View Pending Transfers");
            System.out.println("PRESS 9: Accept Transfer");
            System.out.println("PRESS 10: Deny Transfer");
            System.out.println("PRESS 11: Logout");
            System.out.println("*****************************");

            int input = scanner.nextInt();
            try {
                switch (input) {
                    case 1: {
                        //get customer info
                        System.out.println("Enter Email Address: ");
                        String email = scanner.next();
                        System.out.println("Enter Password: ");
                        String password = scanner.next();
                        Customer customer = customerDao.customerInfoRequest(email, password);
                        System.out.println(customer);
                        break;
                    }

                    case 2: {
                        //request account
                        System.out.println("Enter Customer ID: ");
                        int cust_id = scanner.nextInt();
                        System.out.println("Enter Initial Deposit");
                        double inDeposit = scanner.nextDouble();
                        AccountRequest newAccountRequest = new AccountRequest();
                        newAccountRequest.setCust_id(cust_id);
                        newAccountRequest.setInitialDeposit(inDeposit);
                        accountDao.accountRequest(newAccountRequest);
                        break;
                    }

                    case 3: {
                        //View Account Requests
                        System.out.println("Enter Customer ID: ");
                        int cust_id = scanner.nextInt();
                        List<AccountRequest> accountRequests = accountDao.accountStatus(cust_id);
                        for (AccountRequest accountRequest: accountRequests){
                            System.out.println(accountRequest);
                        }
                        break;
                    }

                    case 4: {
                        //view accounts info
                        System.out.println("Enter Customer ID");
                        int cust_id = scanner.nextInt();
                        List<Account> accounts = accountDao.viewAccountInfo(cust_id);
                        for (Account account: accounts){
                            System.out.println(account);
                        }
                        break;
                    }

                    case 5: {
                        //deposit
                        System.out.println("Enter Account ID: ");
                        int accountID = scanner.nextInt();
                        System.out.println("Enter Deposit amount: ");
                        double amount = scanner.nextDouble();
                        if (amount <= 0) {
                            System.out.println("Incorrect amount, must be positive number");
                        } else {
                            accountDao.deposit(accountID, amount);
                        }
                        break;
                    }

                    case 6: {
                        //withdraw
                        System.out.println("Enter Account ID: ");
                        int accountID = scanner.nextInt();
                        System.out.println("Enter Withdraw Amount: ");
                        double amount = scanner.nextDouble();
                        double currentBalance = accountDao.getBalance(accountID);
                        if (amount > currentBalance) {
                            System.out.println("Insufficient Funds");
                        } else if (amount <= 0) {
                            System.out.println("Incorrect amount, must be positive number");
                        } else {
                            accountDao.withdraw(accountID, amount);
                        }
                        break;
                    }
                    case 7: {
                        //Transfer
                        System.out.println("Enter your Account ID: ");
                        int accountID = scanner.nextInt();
                        System.out.println("Enter Transfer Amount: ");
                        double amount = scanner.nextDouble();
                        System.out.println("Enter Transfer Account ID: ");
                        int transferID = scanner.nextInt();
                        double currentBalance = accountDao.getBalance(accountID);
                        if (amount <= 0) {
                            System.out.println("Incorrect amount, must be positive number");
                        } else if (amount > currentBalance) {
                            System.out.println("Insufficient Funds");
                        } else {
                            accountDao.transferFunds(accountID, transferID, amount);
                        }
                        break;
                    }

                    case 8: {
                        //View Pending Transfers
                        System.out.println("Enter your Account ID: ");
                        int accountID = scanner.nextInt();
                        TransferRecord transferRecord = accountDao.viewPendingTransfers(accountID);
                        System.out.println(transferRecord);
                        break;


                    }
                    case 9: {
                        //Accept Transfer
                        System.out.println("Enter your Account ID: ");
                        int receiveAccount_id = scanner.nextInt();
                        System.out.println("Enter Transfer ID: ");
                        int transfer_id = scanner.nextInt();
                        System.out.println("Enter Transfer amount: ");
                        double amount = scanner.nextDouble();
                        double transferAmount = accountDao.getTransferBalance(transfer_id);

                        if (amount == transferAmount) {
                            accountDao.acceptTransfer(receiveAccount_id, transfer_id, amount);
                        } else {
                            System.out.println("Transfer amount incorrect");
                        }
                        break;
                    }

                    case 10: {
                        //Deny Transfer
                        System.out.println("Enter the Sender Account ID: ");
                        int senderAccount_id = scanner.nextInt();
                        System.out.println("Enter Transfer ID: ");
                        int transfer_id = scanner.nextInt();
                        System.out.println("Enter Transfer Amount: ");
                        double amount = scanner.nextDouble();
                        double transferAmount = accountDao.getTransferBalance(transfer_id);

                        if (amount == transferAmount) {
                            accountDao.denyTransfer(senderAccount_id, transfer_id, amount);
                        } else {
                            System.out.println("Transfer amount incorrect");
                        }
                        break;
                    }

                    case 11: {
                        //logout
                        System.out.println("Logging out...");
                        flag = false;
                        break;
                    }

                    default:
                        System.out.println("Please enter number between 1-6");
                }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect Input");
            }
        }
    }

}
