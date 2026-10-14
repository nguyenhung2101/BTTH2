package com.example.th_b2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Màn hình Khởi động và Đăng nhập hệ thống
public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private TextView tvError;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các view từ XML layout
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);

        // Xử lý sự kiện khi nhấn nút Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    // Hàm kiểm tra và xử lý đăng nhập
    private void handleLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // 1. Kiểm tra nếu bỏ trống thông tin
        if (username.isEmpty() || password.isEmpty()) {
            tvError.setText(R.string.login_empty_error);
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        // 2. Kiểm tra tài khoản mật khẩu cố định (admin / 123456)
        if (username.equals("admin") && password.equals("123456")) {
            // Đúng tài khoản: Ẩn báo lỗi, chuyển sang MainActivity và gọi finish()
            tvError.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng LoginActivity để không quay lại bằng nút Back
        } else {
            // Sai tài khoản: Hiển thị đúng chuỗi thông báo lỗi theo yêu cầu
            tvError.setText("Sai tên đăng nhập hoặc mật khẩu");
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
