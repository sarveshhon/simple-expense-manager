package com.sarveshhon.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

public class ChipsCategoryAdapter extends RecyclerView.Adapter<ChipsCategoryAdapter.ViewHolder> {

    List<CategoryModel> list;
    Context context;

    public ChipsCategoryAdapter(List<CategoryModel> list, Context context) {

        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_chips_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Sets the Title of Category to Item
        holder.cTitle.setText(list.get(position).getTitle());

        // This is used to select the category of Transaction while Adding new Transaction to Database
        holder.itemView.setOnClickListener(v -> {
            if (MainActivity.Transactionlist.size() == 0) {
                MainActivity.Transactionlist.add(list.get(position).getTitle());
            } else {
                if (MainActivity.Transactionlist.contains(list.get(position).getTitle())) {
                    MainActivity.Transactionlist.remove(list.get(position).getTitle());
                }
                holder.cTitle.setChecked(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Chip cTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cTitle = itemView.findViewById(R.id.cTitle);

        }

    }
}
