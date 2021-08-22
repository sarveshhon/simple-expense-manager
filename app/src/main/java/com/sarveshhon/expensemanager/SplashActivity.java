package com.sarveshhon.expensemanager;

import static com.sarveshhon.expensemanager.Helper.blackIconStatusBar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.sarveshhon.expensemanager.expense_category.DBCategoryExpenseHelper;
import com.sarveshhon.expensemanager.income_category.DBCategoryIncomeHelper;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    DBCategoryExpenseHelper ExpenseDB;
    DBCategoryIncomeHelper IncomeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        blackIconStatusBar(this, R.color.carrot_red);

        // Database Helper Object
        ExpenseDB = new DBCategoryExpenseHelper(this);
        IncomeDB = new DBCategoryIncomeHelper(this);

        // Shared preference for storing data
        prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);

        // Handler of opening MainActivity from SplashActivity with specified Time in Milli Sec
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // This method will check if user has open this App for First time or Not
        if (prefs.getBoolean("isFirstRun", true)) {
            prefs.edit().putBoolean("isFirstRun", false).apply();
            // If user opens this app for first time then we stores some Sample Categories
            // for user to use
            addSampleExpenseCategory();
            addSampleIncomeCategory();
        }

    }

    // This methods adds Sample Categories to IncomeCategory Table
    void addSampleIncomeCategory() {

        IncomeDB.addCategory(new CategoryModel(1, "Salary"));
        IncomeDB.addCategory(new CategoryModel(1, "Interest"));
        IncomeDB.addCategory(new CategoryModel(1, "Investments"));
        IncomeDB.addCategory(new CategoryModel(1, "Pocket Money"));

    }

    // This methods adds Sample Categories to ExpenseCategory Table
    void addSampleExpenseCategory() {

        ExpenseDB.addCategory(new CategoryModel(1, "Grocery"));
        ExpenseDB.addCategory(new CategoryModel(1, "Travel"));
        ExpenseDB.addCategory(new CategoryModel(1, "Rent"));
        ExpenseDB.addCategory(new CategoryModel(1, "Tax"));

    }

}