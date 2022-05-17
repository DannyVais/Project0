package com.thedannyvais.Accounts;

import com.thedannyvais.Employees.AccountRequest;
import com.thedannyvais.Transactions.TransferRecord;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    void accountRequest(AccountRequest accountRequest) throws SQLException;

    List<AccountRequest> accountStatus(int customerID) throws SQLException;

    List<Account> viewAccountInfo(int customerID) throws SQLException;

    void withdraw(int accountID, double amount) throws SQLException;

    void deposit(int accountID, double amount) throws SQLException;

    double getBalance(int accountId) throws SQLException;

    double getTransferBalance(int transferID) throws SQLException;

    void transferFunds(int sendAccountId, int receiveAccountId, double amount) throws SQLException;

    TransferRecord viewPendingTransfers(int sendAccountId) throws SQLException;

    void acceptTransfer(int receive_accountID, int transfer_ID, double amount) throws SQLException;

    void denyTransfer(int send_AccountId, int transfer_ID, double amount) throws SQLException;

}
