package com.thedannyvais.Transactions;

public class TransferRecord {
    int send_accountID;
    int receive_accountID;
    double amount;
    String transfer_status;
    int transfer_id;

    public TransferRecord(){

    }

    public TransferRecord(int send_accountID, int receive_accountID, double amount, String transfer_status, int transfer_id) {
        this.send_accountID = send_accountID;
        this.receive_accountID = receive_accountID;
        this.amount = amount;
        this.transfer_status = transfer_status;
        this.transfer_id = transfer_id;
    }

    public int getSend_accountID() {
        return send_accountID;
    }

    public int getReceive_accountID() {
        return receive_accountID;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransfer_status() {
        return transfer_status;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setSend_accountID(int send_accountID) {
        this.send_accountID = send_accountID;
    }

    public void setReceive_accountID(int receive_accountID) {
        this.receive_accountID = receive_accountID;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransfer_status(String transfer_status) {
        this.transfer_status = transfer_status;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    @Override
    public String toString() {
        return "TransferRecord{" +
                "send_accountID=" + send_accountID +
                ", receive_accountID=" + receive_accountID +
                ", amount=" + amount +
                ", transfer_status='" + transfer_status + '\'' +
                ", transfer_id=" + transfer_id +
                '}';
    }
}
