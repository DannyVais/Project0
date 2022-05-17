package com.thedannyvais.Accounts;

import com.thedannyvais.Connection.ConnectionFactory;
import com.thedannyvais.Employees.AccountRequest;
import com.thedannyvais.Transactions.TransferRecord;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AccountDaoImpl implements AccountDao {
    private static final DecimalFormat df = new DecimalFormat("0.00");


    Connection connection;

    public AccountDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void accountRequest(AccountRequest accountRequest) throws SQLException {
        try {

            String sql = "insert into accountRequests (cust_id, initialDeposit) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountRequest.getCust_id());
            preparedStatement.setDouble(2, accountRequest.getInitialDeposit());
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                System.out.println("Request submitted, pending approval");
            else
                System.out.println("Oops, something went wrong");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Request failed, duplicate requests not allowed");
        }
    }

    @Override
    public List<AccountRequest> accountStatus(int customerID) throws SQLException {
        List<AccountRequest> accountRequests = new ArrayList<>();
        String sql = "select * from accountRequests where cust_id = " + customerID;
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
        if (resultSet.next() == false){
            System.out.println("No Requests found");
        }
        return accountRequests;
    }

    @Override
    public List<Account> viewAccountInfo(int customerID) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from accounts where cust_id = " + customerID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int accountID = resultSet.getInt(1);
            double balance = resultSet.getDouble(2);
            int cust_id = resultSet.getInt(3);
            Account account = new Account(accountID, df.format(balance), cust_id);
            accounts.add(account);
        }
        if (resultSet.next() == false){
            System.out.println("No Accounts found");
        }
        return accounts;
    }

    @Override
    public void withdraw(int accountID, double amount) throws SQLException {
        String sql = "call withdrawFunds(?, ?)";
        // SQL stored procedure code

        //delimiter $$
        //create procedure withdrawFunds(IN amountP double, IN accountIDP int)
        //begin
        //update accounts set balance = balance - amountP where account_id = accountIDP;
        //insert into transactions (transaction_type, amount, account_id) values ('Withdraw', amountP, accountIDP);
        //end $$
        //delimiter ;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, amount);
        preparedStatement.setInt(2, accountID);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Withdraw Successful");
        } else {
            System.out.println("Withdraw Failed");
        }
    }

    @Override
    public void deposit(int accountID, double amount) throws SQLException {
        String sql = "call depositFunds(?, ?)";
        // SQL stored procedure code

        //delimiter $$
        //create procedure depositFunds(IN amountP double, IN accountIDP int)
        //begin
        //update accounts set balance = balance + amountP where account_id = accountIDP;
        //insert into transactions (transaction_type, amount, account_id) values ('Deposit', amountP, accountIDP);
        //end $$
        //delimiter ;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, amount);
        preparedStatement.setInt(2, accountID);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Deposit Successful");
        } else {
            System.out.println("Deposit Failed");
        }
    }

    @Override
    public double getBalance(int accountId) throws SQLException {
        String sql = "select balance from accounts where account_id = " + accountId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        double balance = 0;
        if (resultSet.next() == false) {
            System.out.println("No Account found");
        } else {
            balance = resultSet.getDouble(1);
        }
        return balance;
    }

    @Override
    public double getTransferBalance(int transferID) throws SQLException {
        String sql = "select amount from transfers where transfer_id = " + transferID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        double balance = 0;
        if (resultSet.next() == false) {
            System.out.println("No Transfer record found");
        } else {
            balance = resultSet.getDouble(1);
        }
        return balance;
    }

    @Override
    public void transferFunds(int sendAccountId, int receiveAccountId, double amount) throws SQLException {
        String sql = "call transferFunds(?, ?, ?)";
        // SQL stored procedure code

        //delimiter $$
        //create procedure transferFunds(IN send_accountIDP int, IN receive_accountIDP int, IN amountP double)
        //begin
        //insert into transfers (send_accountID, receive_accountID, amount) values (send_accountIDP, receive_accountIDP, amountP);
        //update accounts set balance = balance - amountP where account_id = send_accountIDP;
        //end $$
        //delimiter ;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sendAccountId);
        preparedStatement.setInt(2, receiveAccountId);
        preparedStatement.setDouble(3, amount);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Transfer sent pending approval");
        } else {
            System.out.println("Transfer Failed");
        }
    }

    @Override
    public TransferRecord viewPendingTransfers(int receive_accountID) throws SQLException {
        TransferRecord transferRecord = new TransferRecord();
        String sql = "select * from transfers where receive_accountID =" + receive_accountID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next() == true) {
            int send_accountId = resultSet.getInt(1);
            int receive_accountId = resultSet.getInt(2);
            double amount = resultSet.getDouble(3);
            String transferStatus = resultSet.getString(4);
            int transfer_Id = resultSet.getInt(5);
            transferRecord = new TransferRecord(send_accountId, receive_accountId, amount, transferStatus, transfer_Id);
        } else {
            System.out.println("No transfer record found");
        }
        return transferRecord;
    }

    @Override
    public void acceptTransfer(int receive_accountID, int transfer_ID, double amount) throws SQLException {
        String sql = "call approveTransfer(?, ?, ?)";
        // SQL stored procedure code

        //delimiter $$
        //create procedure approveTransfer(IN receive_accountIDP int, IN transfer_IDP int, IN amountP double)
        //begin
        //update transfers set transfer_status = 'Approved' where transfer_id = transfer_IDP;
        //update accounts set balance = balance + amountP where account_id = receive_accountIDP;
        //end $$
        //delimiter ;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, receive_accountID);
        preparedStatement.setInt(2, transfer_ID);
        preparedStatement.setDouble(3, amount);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Transfer Approved");
        } else {
            System.out.println("Oops something went wrong, try again");
        }
    }

    @Override
    public void denyTransfer(int send_AccountId, int transfer_ID, double amount) throws SQLException {
        String sql = "call denyTransfer(?, ?, ?)";
        // SQL stored procedure code

        //delimiter $$
        //create procedure denyTransfer(IN send_accountIDP int, IN transfer_IDP int, IN amountP double)
        //begin
        //update transfers set transfer_status = 'Denied' where transfer_id = transfer_IDP;
        //update accounts set balance = balance + amountP where account_id = send_accountIDP;
        //end $$
        //delimiter ;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, send_AccountId);
        preparedStatement.setInt(2, transfer_ID);
        preparedStatement.setDouble(3, amount);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Transfer Denied");
        } else {
            System.out.println("Oops something went wrong, try again");
        }
    }
}
