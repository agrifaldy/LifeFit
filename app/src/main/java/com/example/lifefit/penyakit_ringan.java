package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.lifefit.IndeksMassaTubuh.indeks_massa_tubuh;

public class penyakit_ringan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_penyakit_ringan);


    }

    public void toPenyakitFlu(View view) {
        Intent intent = new Intent(this, penyakit_flu.class);
        startActivity(intent);
    }

    public void toPenyakitDemam(View view) {
        Intent intent = new Intent(this, penyakit_demam.class);
        startActivity(intent);
    }

    public void toPenyakitSakitTenggorokan(View view) {
        Intent intent = new Intent(this, penyakit_sakit_tenggorokan.class);
        startActivity(intent);
    }

    public void backToEnsiklopedia(View view) {
    }
}