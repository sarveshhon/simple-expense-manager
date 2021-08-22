package com.sarveshhon.expensemanager.expense_category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.sarveshhon.expensemanager.CategoryModel;
import com.sarveshhon.expensemanager.Helper;
import com.sarveshhon.expensemanager.R;
import com.sarveshhon.expensemanager.databinding.ActivityExpenseBinding;

public class ExpenseActivity extends AppCompatActivity {

    // Declaration of Binding
    private ActivityExpenseBinding mBinding;

    // Declaration of Adapters
    private ExpenseAdapter adapter;

    // Declaration Database Helpers
    public static DBCategoryExpenseHelper ExpenseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Its helps to Linking View without using findViewById method
        mBinding = ActivityExpenseBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Its set the color of Status Bar
        Helper.blackIconStatusBar(this, R.color.carrot_red);

        // Database Helper Object Creation
        ExpenseDB = new DBCategoryExpenseHelper(this);

        // Fetches the list of all Expense Categories
        fetch_and_list();

        // Shows the BottomSheetDialog to Add new Category
        mBinding.btnAdd.setOnClickListener(v -> {

            showBottomSheetDialog();
            fetch_and_list();

        });

    }

    // This method Lists all the Expense Categories from Database
    void fetch_and_list() {

        adapter = new ExpenseAdapter(ExpenseDB.getAllCategory(), this);
        mBinding.rvExpenseCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvExpenseCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    // This method shows BottomSheetDialog for Adding New Category
    private void showBottomSheetDialog() {

        // Creates BottomSheetDialog of Adding New Category
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_category_input);

        // Linking of Views contains in BSD Adding New Category Layout
        TextInputEditText etTitleInput = bottomSheetDialog.findViewById(R.id.etTitleInput);
        CardView cvAddCategory = bottomSheetDialog.findViewById(R.id.cvAddCategory);

        // This method will add New Expense Category based on given conditions
        cvAddCategory.setOnClickListener(v -> {
            if (!etTitleInput.getText().toString().equals("")) {
                ExpenseDB.addCategory(new CategoryModel(1, etTitleInput.getText().toString()));
                bottomSheetDialog.cancel();
                Toast.makeText(this, "Category Added", Toast.LENGTH_SHORT).show();
                fetch_and_list();
            } else {
                etTitleInput.setError("Enter Category Name");
                fetch_and_list();
            }
        });

        // This Shows the BSD
        bottomSheetDialog.show();

    }

}