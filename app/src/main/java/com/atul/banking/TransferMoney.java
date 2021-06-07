package com.atul.banking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransferMoney extends AppCompatActivity {

    ImageView addUser;
    EditText amountEditText;
    Button transferButton;
    DBHelper db;
    CardView cardView;
    TextView toNameTextView;
    TextView toAccountNoTextView;
    TextView toBalanceTextView;
    TextView availableBalanceTextView;

    double balanceFromSharedPref;
    String fromNameFromSharedPref;
    String mobileNoFromSharedPref;
    String formattedBalanceFromSharedPref;

    String date;
    String toName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        date = format.format(calendar.getTime());

        Intent intent = getIntent();

        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.atul.banking", Context.MODE_PRIVATE);
        boolean statusOfIntentFromIndividualUser = intent.getBooleanExtra("status",false);

        if(statusOfIntentFromIndividualUser) {
            double balance = intent.getDoubleExtra("balanceFromIndividual", -1);
            String fromName = intent.getStringExtra("nameFromIndividual");
            String mobileNo = intent.getStringExtra("mobileNoFromIndividual");
            String formattedBalance = intent.getStringExtra("formattedBalance");


            sharedPreferences.edit().putString("balance",Double.toString(balance)).apply();
            sharedPreferences.edit().putString("fromName",fromName).apply();
            sharedPreferences.edit().putString("mobileNo",mobileNo).apply();
            sharedPreferences.edit().putString("formattedBalance",formattedBalance).apply();
        }

        final String nameFromAddUser = intent.getStringExtra("nameFromAddUser");
        final String accountNoFromAddUser = intent.getStringExtra("accountNoFromAddUser");
        final double balanceFromAddUser = intent.getDoubleExtra("balanceFromAddUser",-1);
        final String mobileNoFromAddUser = intent.getStringExtra("mobileNoFromAddUser");
        String formattedBalanceFromAddUser = intent.getStringExtra("formattedBalanceFromAddUser");
        boolean statusOfIntentFromAddUser = intent.getBooleanExtra("statusOfIntent",false);

        balanceFromSharedPref = Double.valueOf(sharedPreferences.getString("balance","-1"));
        fromNameFromSharedPref = sharedPreferences.getString("fromName","");
        mobileNoFromSharedPref = sharedPreferences.getString("mobileNo","");
        formattedBalanceFromSharedPref = sharedPreferences.getString("formattedBalance","");


        addUser = (ImageView) findViewById(R.id.addUserImageView);
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        transferButton = (Button) findViewById(R.id.finalTransferButton);
        db = new DBHelper(this);
        cardView = (CardView) findViewById(R.id.transferMoneyCardView);
        toNameTextView = (TextView) findViewById(R.id.nameTransferMoney);
        toAccountNoTextView = (TextView) findViewById(R.id.accountNoTransferMoney);
        toBalanceTextView = (TextView) findViewById(R.id.balanceTransferMoney);
        availableBalanceTextView = (TextView) findViewById(R.id.availableBalanceAmount);
        availableBalanceTextView.setText(formattedBalanceFromSharedPref);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),SelectFromAllUser.class);
                startActivity(intent1);
                finish();
            }
        });

        if(statusOfIntentFromAddUser){
            statusOfIntentFromAddUser = false;

            addUser.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);

            toNameTextView.setText(nameFromAddUser);
            toAccountNoTextView.setText(accountNoFromAddUser);
            toBalanceTextView.setText(formattedBalanceFromAddUser);
        }

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addUser.getVisibility() == View.VISIBLE){
                    Toast.makeText(TransferMoney.this, "Add User to Send Money!", Toast.LENGTH_SHORT).show();
                }
                else if(amountEditText.getText().toString().isEmpty()) {
                    Toast.makeText(TransferMoney.this, "Amount cannot be Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(balanceFromSharedPref < Double.valueOf(amountEditText.getText().toString())){
                    Toast.makeText(TransferMoney.this, "Insufficent Balance in your Account!", Toast.LENGTH_SHORT).show();
                }
                else {

                    toName = toNameTextView.getText().toString();
                    String amountSent = amountEditText.getText().toString();
                    db.insertTransaction(date,fromNameFromSharedPref,toName,amountSent,"Success");

                    double updatedSenderBalance = balanceFromSharedPref - Double.valueOf(amountSent);
                    double updatedReceiverBalance = balanceFromAddUser + Double.valueOf(amountSent);
                    db.updateBalance(mobileNoFromSharedPref,updatedSenderBalance);
                    db.updateBalance(mobileNoFromAddUser,updatedReceiverBalance);
                    AllUsers.data.clear();
                    AllUsers.storeDataInArrayList(TransferMoney.this);
                    AllUsers.recyclerAdapter.notifyDataSetChanged();

                    sharedPreferences.edit().clear().apply();

                    Toast.makeText(TransferMoney.this, "Transaction Successful", Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(getApplicationContext(),AllUsers.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Transaction?")
                .setMessage("Do you want to cancel the transaction?")
                .setNegativeButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String amountSent;
                        if(toNameTextView.getText().toString().isEmpty()){
                            toName = "Not Selected";
                        }
                        else{
                            toName = toNameTextView.getText().toString();
                        }
                        if(amountEditText.getText().toString().isEmpty()){
                            amountSent = "0";
                        }
                        else {
                            amountSent = amountEditText.getText().toString();
                        }
                        db.insertTransaction(date,fromNameFromSharedPref,toName,amountSent,"Failed");
                        Toast.makeText(TransferMoney.this, "Transaction Cancelled!", Toast.LENGTH_SHORT).show();
                        TransferMoney.super.onBackPressed();
                    }
                }).create().show();

    }
}