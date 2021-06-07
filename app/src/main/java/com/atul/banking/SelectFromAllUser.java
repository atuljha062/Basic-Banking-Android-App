package com.atul.banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.List;

public class SelectFromAllUser extends AppCompatActivity implements RecyclerAdapter.RecyclerClickListner {

    RecyclerView recyclerView;
    List<UserData> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_all_user);

        data  = AllUsers.data;

        recyclerView = (RecyclerView) findViewById(R.id.selectFromAllUserRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(data,this));
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getApplicationContext(),TransferMoney.class);

        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String formattedBalance = "Rs." + decimalFormat.format(data.get(position).getBalance());

        intent.putExtra("nameFromAddUser",data.get(position).getName());
        intent.putExtra("accountNoFromAddUser",data.get(position).getAccountNo());
        intent.putExtra("balanceFromAddUser",data.get(position).getBalance());
        intent.putExtra("mobileNoFromAddUser",data.get(position).getMobileNo());
        intent.putExtra("formattedBalanceFromAddUser",formattedBalance);
        intent.putExtra("statusOfIntent",true);

        startActivity(intent);
        finish();
    }
}