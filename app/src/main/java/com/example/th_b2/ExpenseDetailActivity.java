package com.example.th_b2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Màn hình Xem chi tiết và Xóa khoản chi tiêu
public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvDetailName;
    private TextView tvDetailAmount;
    private TextView tvDetailCategory;
    private TextView tvDetailDate;
    private TextView tvDetailNote;
    private Button btnBack;
    private Button btnDeleteExpense;

    private Expense currentExpense;
    private int expensePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        // Ánh xạ View
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailAmount = findViewById(R.id.tvDetailAmount);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        btnBack = findViewById(R.id.btnBack);
        btnDeleteExpense = findViewById(R.id.btnDeleteExpense);

        // Nhận dữ liệu truyền từ Intent (tương thích các phiên bản Android)
        Intent intent = getIntent();
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                currentExpense = intent.getSerializableExtra("EXPENSE", Expense.class);
            } else {
                currentExpense = (Expense) intent.getSerializableExtra("EXPENSE");
            }
            expensePosition = intent.getIntExtra("POSITION", -1);
        }

        // Hiển thị dữ liệu lên giao diện
        displayExpenseDetails();

        // Xử lý sự kiện nút Quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity, không thay đổi dữ liệu
            }
        });

        // Xử lý sự kiện nút Xóa khoản chi
        btnDeleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expensePosition != -1) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("DELETE_POSITION", expensePosition);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    // Hiển thị thông tin khoản chi tiêu
    private void displayExpenseDetails() {
        if (currentExpense != null) {
            tvDetailName.setText(getString(R.string.detail_name, currentExpense.getName()));
            String formattedAmount = ExpenseAdapter.formatCurrency(currentExpense.getAmount());
            tvDetailAmount.setText(getString(R.string.detail_amount, formattedAmount));
            tvDetailCategory.setText(getString(R.string.detail_category, currentExpense.getCategory()));
            tvDetailDate.setText(getString(R.string.detail_date, currentExpense.getDate()));

            String note = currentExpense.getNote();
            if (note == null || note.trim().isEmpty()) {
                tvDetailNote.setText(getString(R.string.detail_note, getString(R.string.no_note)));
            } else {
                tvDetailNote.setText(getString(R.string.detail_note, note));
            }
        }
    }
}
