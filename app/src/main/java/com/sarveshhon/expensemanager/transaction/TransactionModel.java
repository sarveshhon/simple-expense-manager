package com.sarveshhon.expensemanager.transaction;

public class TransactionModel {

    int id;
    String amount;
    String type;
    String category;
    String date;

    public TransactionModel() {

    }

    public TransactionModel(int id, String amount, String type, String category, String date) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
