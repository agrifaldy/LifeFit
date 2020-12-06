package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lifefit.IndeksMassaTubuh.indeks_massa_tubuh;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class akun_saya extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView tv_title_username;
    private TextView tv_title_email;
    private TextView tv_dp_username;
    private TextView tv_dp_email;
    private TextView tv_dp_notelp;

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

        tv_dp_notelp = findViewById(R.id.tv_dp_notelp);

    }

    public void toEdit(View view) {
        Intent intent = new Intent(this, akun_saya_edit.class);
        intent.putExtra("usernameP", tv_dp_username.getText().toString());
        intent.putExtra("emailP", tv_dp_email.getText().toString());
        intent.putExtra("phoneP", tv_dp_notelp.getText().toString());
        startActivity(intent);
    }
}