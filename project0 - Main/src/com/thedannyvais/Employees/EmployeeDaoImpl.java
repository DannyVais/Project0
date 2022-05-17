package com.thedannyvais.Employees;

import com.thedannyvais.Accounts.Account;
import com.thedannyvais.AdminDisplay;
import com.thedannyvais.Connection.ConnectionFactory;
import com.thedannyvais.Customers.Customer;
import com.thedannyvais.Transactions.Transactions;
import com.thedannyvais.Transactions.TransferRecord;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    Connection connection;

    public EmployeeDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }


    @Override
    public List<Account> viewCustomerAccounts(int cust_id) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from accounts where cust_id =" + cust_id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int account_id = resultSet.getInt(1);
            double balance = resultSet.getDouble(2);
            int cust_ID = resultSet.getInt(3);
            Account account = new Account(account_id, df.format(balance), cust_ID);
            accounts.add(account);
        }
        if (resultSet.next() == false) {
            System.out.println("No Accounts Found");
        }
        return accounts;
    }

    @Override
    public List<Customer> viewAllCustomerInfo() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "select * from customers";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int cust_id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            Customer customer = new Customer(cust_id, firstName, lastName, email, password);
            customers.add(customer);
        }
        return customers;
    }


    @Override
    public List<Transactions> viewTransactions() throws SQLException {
        List<Transactions> transactions = new ArrayList<>();
        String sql = "select * from transactions";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int transaction_id = resultSet.getInt(1);
            String transaction_type = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            int account_id = resultSet.getInt(4);
            Transactions transaction = new Transactions(transaction_id, transaction_type, amount, account_id);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<AccountRequest> viewAccountRequests() throws SQLException {
        List<AccountRequest> accountRequests = new ArrayList<>();
        String sql = "select * from accountRequests";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int cust_id = resultSet.getInt(1);
            String accountStatus = resultSet.getString(2);
            double initialDeposit = resultSet.getDouble(3);
            int request_id = resultSet.getInt(4);
            AccountRequest accountRequest = new AccountRequest(cust_id, accountStatus, initialDeposit, request_id);
            accountRequests.add(accountRequest);
        }
        return accountRequests;
    }

    @Override
    public List<TransferRecord> viewTransfers() throws SQLException {
        List<TransferRecord> transferRecords = new ArrayList<>();
        String sql = "select * from transfers";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int send_accountID = resultSet.getInt(1);
            int receive_accountID = resultSet.getInt(2);
            double amount = resultSet.getDouble(3);
            String transfer_status = resultSet.getString(4);
            int transfer_id = resultSet.getInt(5);
            TransferRecord transferRecord = new TransferRecord(send_accountID, receive_accountID, amount, transfer_status, transfer_id);
            transferRecords.add(transferRecord);
        }
        return transferRecords;
    }


    @Override
    public void createBankingAccount(double initialDeposit, int cust_id, int request_id) throws SQLException {
        try {
            String sql = "call createAccount(?, ?, ?)";
            // SQL stored procedure code

            //delimiter $$
            //create procedure createAccount(IN balanceP double, IN cust_idP int, IN request_idP int)
            //begin
            //insert into accounts (balance, cust_id) values (balanceP, cust_idP);
            //update accountRequests set accountStatus = 'Approved' where request_id = request_idP;
            //end $$
            //delimiter ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, initialDeposit);
            preparedStatement.setInt(2, cust_id);
            preparedStatement.setInt(3, request_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                System.out.println("Banking Account approved and created");
            } else {
                System.out.println("Oops something went wrong...");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Creation failed, banking account already exists for customer");
        }
    }

    @Override
    public void denyBankingAccount(int request_id) throws SQLException {
        String sql = "update accountRequests set accountStatus = 'Denied' where request_id = " + request_id;
        Statement statement = connection.createStatement();
        int count = statement.executeUpdate(sql);
        if (count > 0) {
            System.out.println("Banking Account Denied");
        } else {
            System.out.println("Oops something went wrong...");
        }
    }


    @Override
    public void employeeLogin(String email, String password) throws SQLException {
        String sql = "select email, password from employees where exists (select email, password from employees where email = ? and password = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultset = preparedStatement.executeQuery();
        if (resultset.next() == false) {
            System.out.println("login failed");

        } else {
            System.out.println("login successful");
            new AdminDisplay();
        }
    }
}
