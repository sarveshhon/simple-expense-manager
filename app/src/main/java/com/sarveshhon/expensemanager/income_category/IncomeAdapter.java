package com.sarveshhon.expensemanager.income_category;

import static com.sarveshhon.expensemanager.income_category.IncomeActivity.IncomeDB;

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

import com.sarveshhon.expensemanager.CategoryModel;
import com.sarveshhon.expensemanager.R;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    List<CategoryModel> list;
    Context context;

    public IncomeAdapter(List<CategoryModel> list, Context context) {

        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Sets the Title of Category to Item
        holder.tvTitle.setText(list.get(position).getTitle());

        // This will check if user really wanted to Delete a Category
        // by alert
        holder.ivRemove.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to Remove it ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        IncomeDB.deleteCategory(list.get(position));
                        Toast.makeText(context, "Removed Successful", Toast.LENGTH_SHORT).show();
                        holder.itemView.setVisibility(View.GONE);
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

        TextView tvTitle;
        ImageView ivRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvCategory);
            ivRemove = itemView.findViewById(R.id.ivRemove);

        }

    }

}
