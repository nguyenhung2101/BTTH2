package com.example.th_b2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

// Màn hình chính - Quản lý và hiển thị danh sách chi tiêu
public class MainActivity extends AppCompatActivity {

    private TextView tvTotalExpense;
    private ListView lvExpenses;
    private Button btnAddExpense;

    private ArrayList<Expense> expenseList;
    private ExpenseAdapter adapter;

    // Khai báo ActivityResultLauncher nhận kết quả từ màn hình Thêm khoản chi
    private final ActivityResultLauncher<Intent> addExpenseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Expense newExpense;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            newExpense = data.getSerializableExtra("NEW_EXPENSE", Expense.class);
                        } else {
                            newExpense = (Expense) data.getSerializableExtra("NEW_EXPENSE");
                        }
                        if (newExpense != null) {
                            expenseList.add(newExpense);
                            adapter.notifyDataSetChanged();
                            updateTotal();
                        }
                    }
                }
            }
    );

    // Khai báo ActivityResultLauncher nhận kết quả từ màn hình Chi tiết (Xóa khoản chi)
    private final ActivityResultLauncher<Intent> detailExpenseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        int deletePosition = result.getData().getIntExtra("DELETE_POSITION", -1);
                        if (deletePosition >= 0 && deletePosition < expenseList.size()) {
                            expenseList.remove(deletePosition);
                            adapter.notifyDataSetChanged();
                            updateTotal();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ View
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        lvExpenses = findViewById(R.id.lvExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        // Khởi tạo danh sách dữ liệu mẫu
        initSampleData();

        // Cấu hình Adapter cho ListView
        adapter = new ExpenseAdapter(this, R.layout.item_expense, expenseList);
        lvExpenses.setAdapter(adapter);

        // Cập nhật tổng số tiền ban đầu
        updateTotal();

        // Xử lý sự kiện nhấn nút "Thêm khoản chi"
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                addExpenseLauncher.launch(intent);
            }
        });

        // Xử lý sự kiện nhấn vào 1 dòng trong danh sách chi tiêu
        lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense selectedExpense = expenseList.get(position);
                Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("EXPENSE", selectedExpense);
                intent.putExtra("POSITION", position);
                detailExpenseLauncher.launch(intent);
            }
        });
    }

    // Khởi tạo 3 khoản chi tiêu mẫu theo yêu cầu đề bài
    private void initSampleData() {
        expenseList = new ArrayList<>();
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", today, "Bánh mì + Cà phê"));
        expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", today, "Xăng RON 95"));
        expenseList.add(new Expense("Mua sách", 120000, "Học tập", today, "Sách Lập trình Android"));
    }

    // Hàm cộng dồn số tiền và cập nhật TextView hiển thị
    private void updateTotal() {
        long total = 0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        String formattedTotal = ExpenseAdapter.formatCurrency(total);
        tvTotalExpense.setText(getString(R.string.total_expense_label, formattedTotal));
    }
}
