package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class hasil_deteksi_sehat extends AppCompatActivity {

    private SimpleDateFormat dateFormat;
    private TextView tanggalDeteksi;
    private String date;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hasil_deteksi_sehat);

        tanggalDeteksi = findViewById(R.id.tanggalDeteksi);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("id"));
        date = dateFormat.format(calendar.getTime());
        tanggalDeteksi.setText(date);
    }
    public void toMonitoring(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}