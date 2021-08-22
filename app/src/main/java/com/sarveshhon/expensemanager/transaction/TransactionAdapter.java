package com.sarveshhon.expensemanager.transaction;

import static com.sarveshhon.expensemanager.MainActivity.ExpenseTransactionDB;
import static com.sarveshhon.expensemanager.MainActivity.IncomeTransactionDB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sarveshhon.expensemanager.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    List<TransactionModel> list;
    Context context;
    View view;

    public TransactionAdapter(List<TransactionModel> list, Context context) {

        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Sets the Values of Items
        holder.tvCategory.setText(list.get(position).getCategory());
        holder.tvAmount.setText("\u20B9 " + list.get(position).getAmount());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvType.setText(list.get(position).getType());

        // Check the Item type Expense -> Green Color & Income -> Red Color
        if (list.get(position).getType().equals("Expense")) {
            holder.tvType.setTextColor(context.getResources().getColor(R.color.carrot_red));
        } else if (list.get(position).getType().equals("Income")) {
            holder.tvType.setTextColor(context.getResources().getColor(R.color.green_2));
        }


        // This will check if user really wanted to Delete a Transaction
        // by alert
        holder.ivRemove.setOnClickListener(v -> {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to Remove it ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        if (list.get(position).getType().equals("Expense")) {
                            ExpenseTransactionDB.deleteExpenseTransaction(list.get(position));
                            Toast.makeText(context, "Removed Successful", Toast.LENGTH_SHORT).show();
                            holder.itemView.setVisibility(View.GONE);
                        } else if (list.get(position).getType().equals("Income")) {
                            IncomeTransactionDB.deleteIncomeTransaction(list.get(position));
                            Toast.makeText(context, "Removed Successful", Toast.LENGTH_SHORT).show();
                            holder.itemView.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory, tvDate, tvAmount, tvType;
        ImageView ivRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvType = itemView.findViewById(R.id.tvType);
            ivRemove = itemView.findViewById(R.id.ivRemove);

        }

    }

}
