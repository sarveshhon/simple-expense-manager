package com.sarveshhon.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.sarveshhon.expensemanager.databinding.ActivityMainBinding;
import com.sarveshhon.expensemanager.expense_category.DBCategoryExpenseHelper;
import com.sarveshhon.expensemanager.expense_category.ExpenseActivity;
import com.sarveshhon.expensemanager.income_category.DBCategoryIncomeHelper;
import com.sarveshhon.expensemanager.income_category.IncomeActivity;
import com.sarveshhon.expensemanager.transaction.DBExpenseTransactionHelper;
import com.sarveshhon.expensemanager.transaction.DBIncomeTransactionHelper;
import com.sarveshhon.expensemanager.transaction.TransactionAdapter;
import com.sarveshhon.expensemanager.transaction.TransactionModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaration of Binding
    private ActivityMainBinding mBinding;

    // Declaration Database Helpers
    public static DBCategoryExpenseHelper ExpenseDB;
    public static DBCategoryIncomeHelper IncomeDB;
    public static DBExpenseTransactionHelper ExpenseTransactionDB;
    public static DBIncomeTransactionHelper IncomeTransactionDB;

    // Declaration of Adapters
    private ChipsCategoryAdapter chips_adapter;
    private TransactionAdapter transactionAdapter;

    // Initialisation of Transaction List for Transaction Entry
    public static List<String> Transactionlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Its helps to Linking View without using findViewById method
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Its set the color of Status Bar
        Helper.blackIconStatusBar(this, R.color.carrot_red);

        // Database Helper Object Creation
        ExpenseDB = new DBCategoryExpenseHelper(this);
        IncomeDB = new DBCategoryIncomeHelper(this);
        ExpenseTransactionDB = new DBExpenseTransactionHelper(this);
        IncomeTransactionDB = new DBIncomeTransactionHelper(this);

        // Its used for Displaying Current
        mBinding.tvDate.setText(DateFormat.format("d MMM yyyy", new Date().getTime()));

        // This method is called for Showing Total of Income & Expense on Screen
        showIncomeExpenseTV();

        // Its shows the Bottom Navigation to Modify Categories
        mBinding.bottomAppBar.setNavigationOnClickListener(v -> {
            showBSDCategoryModify();

        });

        // Its shows the Bottom Navigation to Add new Transaction
        mBinding.btnAddTrasaction.setOnClickListener(v -> {
            showBSDAddTransaction();
        });

        // Its set the type of Layout for RecyclerView to Display Its Items
        mBinding.rvTransactions.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // This shows all the Transaction related to Expense
        mBinding.cvExpenseDisplay.setOnClickListener(v -> {

            showExpenseTransaction();
        });

        // This shows all the Transactions related to Income
        mBinding.cvIncomeDisplay.setOnClickListener(v -> {
            showIncomeTransaction();
        });

    }

    // This method Lists the Expense Transaction to Recyclerview
    public void showExpenseTransaction() {

        transactionAdapter = new TransactionAdapter(ExpenseTransactionDB.getAllExpenseTransaction(), this);
        mBinding.rvTransactions.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();

    }

    // This method Lists the Income Transaction to Recyclerview
    public void showIncomeTransaction() {

        transactionAdapter = new TransactionAdapter(IncomeTransactionDB.getAllIncomeTransaction(), this);
        mBinding.rvTransactions.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();

    }

    // This method shows BottomSheetDialog for Adding Transaction
    private void showBSDAddTransaction() {

        // Creates BottomSheetDialog of Adding new Transactions
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_transaction);

        // Linking of Views contains in BSD Adding Transaction Layout
        RadioButton rbExpense = bottomSheetDialog.findViewById(R.id.rbExpense);
        RadioButton rbIncome = bottomSheetDialog.findViewById(R.id.rbIncome);
        RecyclerView rvCategory = bottomSheetDialog.findViewById(R.id.rvCategory);
        TextInputEditText etAmount = bottomSheetDialog.findViewById(R.id.etAmount);
        CardView cvAddCategory = bottomSheetDialog.findViewById(R.id.cvAddCategory);

        // Its set the type of Layout for RecyclerView to Display Its Items
        rvCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        // Setting Default Checked to Expense
        rbExpense.setChecked(true);

        // Its List all the Category of Expense on BSD Add New Transaction Layout
        chips_adapter = new ChipsCategoryAdapter(ExpenseDB.getAllCategory(), this);
        rvCategory.setAdapter(chips_adapter);
        chips_adapter.notifyDataSetChanged();

        // Checking if Expense RadioButton is Checked if Yes then list all Expense Category to
        // Add Transaction Layout
        rbExpense.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chips_adapter = new ChipsCategoryAdapter(ExpenseDB.getAllCategory(), this);
                rvCategory.setAdapter(chips_adapter);
                chips_adapter.notifyDataSetChanged();
            }
        });

        // Checking if Income RadioButton is Checked if Yes then list all Income Category to
        // Add Transaction Layout
        rbIncome.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chips_adapter = new ChipsCategoryAdapter(IncomeDB.getAllCategory(), this);
                rvCategory.setAdapter(chips_adapter);
                chips_adapter.notifyDataSetChanged();
            }
        });

        // This will add new Transaction Based on Given Conditions
        cvAddCategory.setOnClickListener(v -> {
            if (rbExpense.isChecked()) {
                if (Transactionlist.size() > 0) {
                    if (etAmount.getText().toString() != null && !etAmount.getText().toString().equals("")) {

                        ExpenseTransactionDB.addExpenseTransaction(new TransactionModel(1, etAmount.getText().toString(), "Expense", Transactionlist.get(0), DateFormat.format("d MMM yyyy", new Date().getTime()).toString()));
                        Toast.makeText(this, "Transaction Added Successfully", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.cancel();
                        Transactionlist.clear();
                        showIncomeExpenseTV();
                        showExpenseTransaction();
                    } else {
                        etAmount.setError("Enter Amount");
                    }
                } else {
                    Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
                }
            } else if (rbIncome.isChecked()) {
                if (Transactionlist.size() > 0) {
                    if (etAmount.getText().toString() != null && !etAmount.getText().toString().equals("")) {

                        IncomeTransactionDB.addIncomeTransaction(new TransactionModel(1, etAmount.getText().toString(), "Income", Transactionlist.get(0), DateFormat.format("d MMM yyyy", new Date().getTime()).toString()));
                        Toast.makeText(this, "Transaction Added Successfully", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.cancel();
                        Transactionlist.clear();
                        showIncomeExpenseTV();
                        showIncomeTransaction();
                    } else {
                        etAmount.setError("Enter Amount");
                    }
                } else {
                    Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.cancel();
            }
        });

        // This Shows the BSD
        bottomSheetDialog.show();

    }

    private void showBSDCategoryModify() {

        // Creates BottomSheetDialog of Modify Expense & Income Categories
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_category);

        // Linking of Views contains in BSD of Modify Expense & Income Categories
        CardView expense_category = bottomSheetDialog.findViewById(R.id.expense_category);
        CardView income_category = bottomSheetDialog.findViewById(R.id.income_category);

        // It will open ExpenseActivity where all Expense Categories are listed to Modify
        expense_category.setOnClickListener(v -> {
            startActivity(new Intent(this, ExpenseActivity.class).putExtra("type", "expense"));
            bottomSheetDialog.cancel();
        });

        // It will open IncomeActivity where all Income Categories are listed to Modify
        income_category.setOnClickListener(v -> {
            startActivity(new Intent(this, IncomeActivity.class).putExtra("type", "income"));
            bottomSheetDialog.cancel();
        });

        // This Shows the BSD
        bottomSheetDialog.show();

    }

    // This method Calculate Income & Expense
    // Displays on Screen
    private void showIncomeExpenseTV() {

        mBinding.cvExpenseDisplay.setText("\u20B9 " + ExpenseTransactionDB.getTotal());
        mBinding.cvIncomeDisplay.setText("\u20B9 " + String.valueOf(Integer.parseInt(IncomeTransactionDB.getTotal()) - Integer.parseInt(ExpenseTransactionDB.getTotal())));
        if (Integer.parseInt(IncomeTransactionDB.getTotal()) - Integer.parseInt(ExpenseTransactionDB.getTotal()) > 0) {
            mBinding.cvIncomeDisplay.setChipBackgroundColorResource(R.color.green_2);
        } else if (Integer.parseInt(IncomeTransactionDB.getTotal()) - Integer.parseInt(ExpenseTransactionDB.getTotal()) <= 0) {
            mBinding.cvIncomeDisplay.setChipBackgroundColorResource(R.color.carrot_red);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        // This will list all the Income Transactions
        showIncomeTransaction();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // This will Display Details of Total Income & Expense
        showIncomeExpenseTV();

    }
}