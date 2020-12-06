package com.example.lifefit.IndeksMassaTubuh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.lifefit.R;
import com.example.lifefit.TekananDarah.tensi;

public class page_monitoring extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_page_monitoring);
    }

    public void toIndeksMassaTubuh(View view) {
        Intent intent = new Intent(this, indeks_massa_tubuh.class);
        startActivity(intent);
    }

    public void toTensi(View view) {
        Intent intent = new Intent(this, tensi.class);
        startActivity(intent);
    }
}