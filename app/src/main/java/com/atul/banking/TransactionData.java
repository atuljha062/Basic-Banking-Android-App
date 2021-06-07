package com.atul.banking;

public class TransactionData {
    private int transactionID;
    private String date;
    private String fromName;
    private String toName;
    private String status;
    private double amount;

    public TransactionData(int transactionID, String date, String fromName, String toName, double amount, String status) {
        this.transactionID = transactionID;
        this.date = date;
        this.fromName = fromName;
        this.toName = toName;
        this.status = status;
        this.amount = amount;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getDate() {
        return date;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToName() {
        return toName;
    }

    public String getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }
}
