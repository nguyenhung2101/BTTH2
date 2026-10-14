package com.example.th_b2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

// Màn hình Thêm khoản chi tiêu mới
public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtExpenseName;
    private EditText edtExpenseAmount;
    private Spinner spExpenseCategory;
    private DatePicker dpExpenseDate;
    private EditText edtExpenseNote;
    private Button btnSaveExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Ánh xạ View
        edtExpenseName = findViewById(R.id.edtExpenseName);
        edtExpenseAmount = findViewById(R.id.edtExpenseAmount);
        spExpenseCategory = findViewById(R.id.spExpenseCategory);
        dpExpenseDate = findViewById(R.id.dpExpenseDate);
        edtExpenseNote = findViewById(R.id.edtExpenseNote);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        // Xử lý sự kiện khi nhấn nút "Lưu khoản chi"
        btnSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });
    }

    // Hàm kiểm tra tính hợp lệ và tạo đối tượng Expense mới
    private void saveExpense() {
        String name = edtExpenseName.getText().toString().trim();
        String amountStr = edtExpenseAmount.getText().toString().trim();
        String category = spExpenseCategory.getSelectedItem().toString();
        String note = edtExpenseNote.getText().toString().trim();

        // 1. Kiểm tra Tên khoản chi
        if (name.isEmpty()) {
            edtExpenseName.setError(getString(R.string.error_empty_name));
            edtExpenseName.requestFocus();
            return;
        }

        // 2. Kiểm tra Số tiền (dùng try-catch để tránh crash)
        long amount;
        try {
            amount = Long.parseLong(amountStr);
            if (amount <= 0) {
                edtExpenseAmount.setError(getString(R.string.error_invalid_amount));
                edtExpenseAmount.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            edtExpenseAmount.setError(getString(R.string.error_invalid_amount));
            edtExpenseAmount.requestFocus();
            return;
        }

        // 3. Lấy ngày chi tiêu từ DatePicker (định dạng dd/MM/yyyy)
        int day = dpExpenseDate.getDayOfMonth();
        int month = dpExpenseDate.getMonth() + 1; // Month bắt đầu từ 0
        int year = dpExpenseDate.getYear();
        String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);

        // 4. Tạo đối tượng Expense
        Expense newExpense = new Expense(name, amount, category, date, note);

        // 5. Trả kết quả về MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_EXPENSE", newExpense);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
