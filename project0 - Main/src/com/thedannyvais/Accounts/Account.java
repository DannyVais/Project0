package com.thedannyvais.Accounts;

public class Account {

    int accountID;
    int customerID;
    String balance;

    public Account() {

    }

    public Account(int accountID, String balance, int customerID) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getBalance() {
        return balance;
    }


    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", customerID=" + customerID +
                ", balance=" + balance +
                '}';
    }
}

