package com.thedannyvais.Employees;

import com.thedannyvais.Customers.Customer;
import com.thedannyvais.Accounts.Account;
import com.thedannyvais.Transactions.Transactions;
import com.thedannyvais.Transactions.TransferRecord;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {
    List<Account> viewCustomerAccounts(int cust_id) throws SQLException;

    List<Customer> viewAllCustomerInfo() throws SQLException;

    List<Transactions> viewTransactions() throws SQLException;

    List<AccountRequest> viewAccountRequests() throws SQLException;

    List<TransferRecord> viewTransfers() throws SQLException;

    void employeeLogin(String email, String password) throws SQLException;

    void createBankingAccount(double initialDeposit, int cust_id, int request_id) throws SQLException;

    void denyBankingAccount(int cust_id) throws SQLException;

}
