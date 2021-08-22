package com.sarveshhon.expensemanager.transaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBExpenseTransactionHelper extends SQLiteOpenHelper {

    // This contains all the details about ExpenseTransaction Table
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBM";
    private static final String TABLE_EXPENSE_TRANSACTION = "ExpenseTransaction";
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TYPE = "type";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DATE = "date";

    public DBExpenseTransactionHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // This onCreate() method creates Table ExpenseTransaction in Database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_EXPENSE_TRANSACTION = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE_TRANSACTION + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_AMOUNT + " TEXT," +
                KEY_TYPE + " TEXT," +
                KEY_CATEGORY + " TEXT," +
                KEY_DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_TABLE_EXPENSE_TRANSACTION);

    }

    // This onUpgrade() method will upgrade ExpenseTransaction Table
    // By Dropping previous table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_TRANSACTION);

    }

    // This addExpenseTransaction() method accepts TransactionModel
    // its adds new Transaction in ExpenseTransaction Table
    public void addExpenseTransaction(TransactionModel transactionModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        checkTable(db);
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, transactionModel.getAmount());
        values.put(KEY_TYPE, transactionModel.getType());
        values.put(KEY_CATEGORY, transactionModel.getCategory());
        values.put(KEY_DATE, transactionModel.getDate());
        db.insert(TABLE_EXPENSE_TRANSACTION, null, values);
        db.close();

    }

    // This method will return list of all the Transactions from ExpenseTransaction Table
    public List<TransactionModel> getAllExpenseTransaction() {

        List<TransactionModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE_TRANSACTION + " ORDER BY " + KEY_ID + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        checkTable(db);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TransactionModel transactionModel = new TransactionModel();
                transactionModel.setId(Integer.parseInt(cursor.getString(0)));
                transactionModel.setAmount(cursor.getString(1));
                transactionModel.setType(cursor.getString(2));
                transactionModel.setCategory(cursor.getString(3));
                transactionModel.setDate(cursor.getString(4));
                list.add(transactionModel);
            } while (cursor.moveToNext());
        }

        return list;

    }

    // This method will check if Table is Exist if not it will create Table
    // This method is called before performing Database Operation for Avoiding
    // error of Table not found
    // It accepts Database Object
    public void checkTable(SQLiteDatabase db) {

        String CREATE_EXPENSE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE_TRANSACTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_AMOUNT + " TEXT," +
                KEY_TYPE + " TEXT," +
                KEY_CATEGORY + " TEXT," +
                KEY_DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_EXPENSE_TRANSACTION_TABLE);

    }

    // This method will accept TransactionModel
    // It will delete the Transaction using ID
    public void deleteExpenseTransaction(TransactionModel transactionModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE_TRANSACTION, KEY_ID + " = ?",
                new String[]{String.valueOf(transactionModel.getId())});
        db.close();

    }

    // This method will return Total Sum of all the Transaction Present in a ExpenseTransaction Table
    public String getTotal() {

        int result = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE_TRANSACTION;
        SQLiteDatabase db = this.getWritableDatabase();
        checkTable(db);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                result += Integer.parseInt(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return String.valueOf(result);

    }


}
