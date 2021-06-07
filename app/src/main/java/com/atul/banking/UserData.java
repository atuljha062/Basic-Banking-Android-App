package com.atul.banking;

public class UserData {

    private String name;
    private String mobileNo;
    private String email;
    private String accountNo;
    private String ifsc;
    private double balance;

    public UserData(String name, String mobileNo, String email, String accountNo, String ifsc, double balance) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.accountNo = accountNo;
        this.ifsc = ifsc;
        this.balance = balance;
    }


    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public double getBalance() {
        return balance;
    }
}
