package com.atul.banking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    List<UserData> data;
    RecyclerClickListner recyclerClickListner;

    public RecyclerAdapter(List<UserData> data, RecyclerClickListner recyclerClickListner) {
        this.data = data;
        this.recyclerClickListner = recyclerClickListner;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_list_item,parent,false);
        return new RecyclerViewHolder(view,recyclerClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.accountNo.setText(data.get(position).getAccountNo());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedBalance = "Rs." + formatter.format(data.get(position).getBalance()) + "/-";
        holder.balance.setText(formattedBalance);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, accountNo, balance;
        RecyclerClickListner recyclerClickListner;

        public RecyclerViewHolder(@NonNull View itemView, RecyclerClickListner recyclerClickListner) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nameAllUser);
            accountNo = (TextView) itemView.findViewById(R.id.accountNoAllUser);
            balance = (TextView) itemView.findViewById(R.id.balanceAllUser);

            this.recyclerClickListner = recyclerClickListner;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerClickListner.onClick(getAbsoluteAdapterPosition());
        }
    }

    public interface RecyclerClickListner{
        void onClick(int position);
    }
}
