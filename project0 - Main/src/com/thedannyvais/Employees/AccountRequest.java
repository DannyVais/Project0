package com.thedannyvais.Employees;

public class AccountRequest {
    public int cust_id;
    public String accountStatus;
    public double initialDeposit;
    public int request_id;

    public AccountRequest() {
    }

    public AccountRequest(int cust_id, String accountStatus, double initialDeposit, int request_id) {
        this.cust_id = cust_id;
        this.accountStatus = accountStatus;
        this.initialDeposit = initialDeposit;
        this.request_id = request_id;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "cust_id=" + cust_id +
                ", accountStatus='" + accountStatus + '\'' +
                ", initialDeposit=" + initialDeposit +
                ", request_id=" + request_id +
                '}';
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setInitialDeposit(double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public int getCust_id() {
        return cust_id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public double getInitialDeposit() {
        return initialDeposit;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
