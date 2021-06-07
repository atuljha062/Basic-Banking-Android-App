package com.atul.banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class IndividualUser extends AppCompatActivity {

    TextView nameTextView, mobileTextView, emailTextView,accountTextView, ifscTextView, balanceTextView;
    Button transferMoneyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_user);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String mobile = intent.getStringExtra("mobile");
        String email = intent.getStringExtra("email");
        String account = intent.getStringExtra("account");
        String ifsc = intent.getStringExtra("ifsc");
        final double balance = intent.getDoubleExtra("balance",-1);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        final String formattedBalance = "Rs." + formatter.format(balance);

        nameTextView = (TextView) findViewById(R.id.userDetailsNameData);
        nameTextView.setText(name);

        mobileTextView = (TextView) findViewById(R.id.userDetailsMobileNoData);
        mobileTextView.setText(mobile);

        emailTextView = (TextView) findViewById(R.id.userDetailsEmailData);
        emailTextView.setText(email);

        accountTextView = (TextView) findViewById(R.id.userDetailsAccountNoData);
        accountTextView.setText(account);

        ifscTextView = (TextView) findViewById(R.id.userDetailsIfscData);
        ifscTextView.setText(ifsc);

        balanceTextView =(TextView) findViewById(R.id.userDetailsBalanceData);
        balanceTextView.setText(formattedBalance);

        transferMoneyButton = (Button) findViewById(R.id.transferMoneyButton);

        transferMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),TransferMoney.class);
                intent1.putExtra("balanceFromIndividual",balance);
                intent1.putExtra("nameFromIndividual",name);
                intent1.putExtra("mobileNoFromIndividual",mobile);
                intent1.putExtra("status", true);
                intent1.putExtra("formattedBalance",formattedBalance);
                startActivity(intent1);
            }
        });

    }
}