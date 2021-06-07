package com.atul.banking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.RecyclerViewHolder> {

    List<TransactionData> data;
    Context context;

    public TransactionRecyclerAdapter(List<TransactionData> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.transaction_history_list_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.fromUserName.setText(data.get(position).getFromName());
        holder.toUserName.setText(data.get(position).getToName());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String formattedBalanceToShow = "Rs." + decimalFormat.format(data.get(position).getAmount()) + "/-";
        holder.balance.setText(formattedBalanceToShow);
        holder.date.setText(data.get(position).getDate());
        if(data.get(position).getStatus().equals("Failed")){
            holder.status.setTextColor(ContextCompat.getColor(context,R.color.FailedRed));
        }
        else{
            holder.status.setTextColor(ContextCompat.getColor(context,R.color.SuccessGreen));

        }
        holder.status.setText(data.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView fromUserName, toUserName, balance, date, status;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            fromUserName = (TextView) itemView.findViewById(R.id.fromUserName);
            toUserName = (TextView) itemView.findViewById(R.id.toUserName);
            balance = (TextView) itemView.findViewById(R.id.balanceTransactionHistory);
            date = (TextView) itemView.findViewById(R.id.dateTransactionHistory);
            status = (TextView) itemView.findViewById(R.id.statusTransactionHistory);
        }
    }
}
