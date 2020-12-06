package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;

public class DeteksiPenyakit extends AppCompatActivity {
    private CardView cv_demam;
    private CardView b_demamIya;
    private CardView b_demamTidak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi_penyakit);

        /**b_demamIya = findViewById(R.id.cv_demamIya);
        if (b_demamIya.isClickable()) {
            b_demamIya.setBackgroundColor(Color.parseColor("#5ba19b"));
        }**/


    }
}