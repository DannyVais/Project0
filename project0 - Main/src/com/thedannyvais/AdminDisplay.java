package com.thedannyvais;

import com.thedannyvais.Accounts.Account;
import com.thedannyvais.Customers.Customer;
import com.thedannyvais.Employees.AccountRequest;
import com.thedannyvais.Employees.EmployeeDao;
import com.thedannyvais.Employees.EmployeeDaoFactory;
import com.thedannyvais.Transactions.Transactions;
import com.thedannyvais.Transactions.TransferRecord;

import javax.sound.midi.Soundbank;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class AdminDisplay {
    EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();

    public AdminDisplay() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("*****************************");
            System.out.println("Select from the options below");
            System.out.println("*****************************");
            System.out.println("PRESS 1: View All Banking Account Requests");
            System.out.println("PRESS 2: Approve Banking Account");
            System.out.println("PRESS 3: Deny Banking Account");
            System.out.println("PRESS 4: View All Customers");
            System.out.println("PRESS 5: View a Customer Accounts");
            System.out.println("PRESS 6: View All Transactions");
            System.out.println("PRESS 7: View All Transfers");
            System.out.println("PRESS 8: Logout");
            System.out.println("*****************************");

            int input = scanner.nextInt();
            switch (input) {
                // View all banking account requests
                case 1: {
                    List<AccountRequest> accountRequests = employeeDao.viewAccountRequests();
                    for (AccountRequest accountRequest : accountRequests) {
                        System.out.println(accountRequest);
                    }
                    break;
                }
                case 2: {
                    //Approve banking account
                    System.out.println("Enter Customer ID: ");
                    int cust_id = scanner.nextInt();
                    System.out.println("Enter Request ID: ");
                    int request_id = scanner.nextInt();
                    System.out.println("Enter Initial Deposit: ");
                    double initialDeposit = scanner.nextDouble();
                    employeeDao.createBankingAccount(initialDeposit, cust_id, request_id);
                    break;
                }
                case 3: {
                    //Deny banking account
                    System.out.println("Enter Request ID: ");
                    int request_id = scanner.nextInt();
                    employeeDao.denyBankingAccount(request_id);
                    break;
                }
                case 4: {
                    //view all customers
                    List<Customer> customers = employeeDao.viewAllCustomerInfo();
                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                    break;
                }
                case 5: {
                    //view a customers accounts
                    System.out.println("Enter Customer ID: ");
                    int cust_id = scanner.nextInt();
                    List<Account> accounts = employeeDao.viewCustomerAccounts(cust_id);
                    for (Account account : accounts) {
                        System.out.println(account);
                    }
                    break;
                }

                case 6: {
                    //view all transactions
                    List<Transactions> transactions = employeeDao.viewTransactions();
                    for (Transactions transaction : transactions) {
                        System.out.println(transaction);
                    }
                    break;
                }

                case 7:{
                    //view all transfers
                    List<TransferRecord> transferRecords = employeeDao.viewTransfers();
                    for (TransferRecord transferRecord: transferRecords){
                        System.out.println(transferRecord);
                    }
                    break;
                }

                case 8: {
                    System.out.println("Logging out...");
                    flag = false;
                    break;
                }
                default:
                    System.out.println("Choose a number between 1-4");
            }
        }

    }
}
