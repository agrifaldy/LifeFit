package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class akun_saya extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView tv_title_username;
    private TextView tv_title_email;
    private TextView tv_dp_username;
    private TextView tv_dp_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_akun_saya);

        mAuth = FirebaseAuth.getInstance();
        String emailUser = mAuth.getCurrentUser().getEmail();
        String namaUser = mAuth.getCurrentUser().getDisplayName();

        tv_title_username = findViewById(R.id.tv_title_username);
        tv_title_username.setText(namaUser);
        tv_title_email = findViewById(R.id.tv_title_email);
        tv_title_email.setText(emailUser);

        tv_dp_username = findViewById(R.id.tv_dp_username);
        tv_dp_username.setText(namaUser);
        tv_dp_email = findViewById(R.id.tv_dp_email);
        tv_dp_email.setText(emailUser);

    }
}