package com.sarveshhon.expensemanager.expense_category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sarveshhon.expensemanager.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class DBCategoryExpenseHelper extends SQLiteOpenHelper {

    // This contains all the details about ExpenseCategory Table
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBM";
    private static final String TABLE_CATEGORY = "ExpenseCategory";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "title";


    public DBCategoryExpenseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // This onCreate() method creates Table ExpenseCategory in Database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT " + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

    }

    // This onUpgrade() method will upgrade ExpenseCategory
    // By Dropping previous table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

    }

    // This addCategory method accepts CategoryModel
    // its adds new Category type in ExpenseCategory Table
    public void addCategory(CategoryModel category) {

        SQLiteDatabase db = this.getWritableDatabase();
        checkTable(db);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, category.getTitle());
        db.insert(TABLE_CATEGORY, null, values);
        db.close();

    }

    // This method will return list of all the Categories in ExpenseCategory Table
    public List<CategoryModel> getAllCategory() {

        List<CategoryModel> categoryList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " ORDER BY " + KEY_ID + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        checkTable(db);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CategoryModel category = new CategoryModel();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setTitle(cursor.getString(1));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        return categoryList;

    }

    // This method will check if Table is Exist if not it will create Table
    // This method is called before performing Database Operation for Avoiding
    // error of Table not found
    // It accepts Database Object
    public void checkTable(SQLiteDatabase db) {

        String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT " + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

    }

    // This method will accept CategoryModel
    // It will delete the Category using ID
    public void deleteCategory(CategoryModel category) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();

    }

}
