package com.example.th_b2;

import java.io.Serializable;

// Lớp Dữ liệu Khoản chi tiêu implement Serializable để truyền dữ liệu qua Intent
public class Expense implements Serializable {
    private String name;     // Tên khoản chi
    private long amount;     // Số tiền chi (đ)
    private String category; // Danh mục chi tiêu
    private String date;     // Ngày chi tiêu (dd/MM/yyyy)
    private String note;     // Ghi chú

    // Constructor đầy đủ tham số
    public Expense(String name, long amount, String category, String date, String note) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
