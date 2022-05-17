package com.thedannyvais.Transactions;

public class Transactions {
    int transaction_id;
    String transaction_type;
    double amount;
    int account_ID;

    public Transactions(int transaction_id, String transaction_type, double amount, int account_ID) {
        this.transaction_id = transaction_id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.account_ID = account_ID;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public double getAmount() {
        return amount;
    }

    public int getAccount_ID() {
        return account_ID;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAccount_ID(int account_ID) {
        this.account_ID = account_ID;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transaction_id=" + transaction_id +
                ", transaction_type='" + transaction_type + '\'' +
                ", amount=" + amount +
                ", account_ID=" + account_ID +
                '}';
    }
}
