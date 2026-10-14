package com.example.th_b2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

// Adapter hiển thị danh sách khoản chi tiêu lên ListView
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private final Context context;
    private final int resource;
    private final List<Expense> expenses;

    public ExpenseAdapter(@NonNull Context context, int resource, @NonNull List<Expense> expenses) {
        super(context, resource, expenses);
        this.context = context;
        this.resource = resource;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView tvItemName = convertView.findViewById(R.id.tvItemName);
        TextView tvItemAmount = convertView.findViewById(R.id.tvItemAmount);
        TextView tvItemCategory = convertView.findViewById(R.id.tvItemCategory);
        TextView tvItemDate = convertView.findViewById(R.id.tvItemDate);

        if (expense != null) {
            tvItemName.setText(expense.getName());
            tvItemAmount.setText(formatCurrency(expense.getAmount()));
            tvItemCategory.setText("Danh mục: " + expense.getCategory());
            tvItemDate.setText(expense.getDate());
        }

        return convertView;
    }

    // Hàm hỗ trợ định dạng số tiền có phân cách hàng nghìn và đơn vị "đ"
    public static String formatCurrency(long amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(amount) + " đ";
    }
}
