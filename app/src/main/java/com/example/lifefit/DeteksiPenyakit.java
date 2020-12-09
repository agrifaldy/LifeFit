package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.card.MaterialCardView;

public class DeteksiPenyakit extends AppCompatActivity implements View.OnClickListener {
    private CardView cv_demam;
    private RadioButton demamIya;
    private RadioButton demamTidak;
    private RadioButton batukIya;
    private RadioButton batukTidak;
    private CardView submitDeteksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi_penyakit);

        demamIya = (RadioButton) findViewById(R.id.demamIya);
        demamIya.setOnClickListener(this);
        demamTidak = (RadioButton) findViewById(R.id.demamTidak);
        demamTidak.setOnClickListener(this);
        batukIya = (RadioButton) findViewById(R.id.batukIya);
        batukIya.setOnClickListener(this);
        batukTidak = (RadioButton) findViewById(R.id.batukTidak);
        batukTidak.setOnClickListener(this);
        submitDeteksi = (CardView) findViewById(R.id.submitDeteksi);
        submitDeteksi.setOnClickListener(this);

    }

    public void onClick(View v) {

        int i = v.getId();

        if (demamIya.isChecked() && batukIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, penyakit_flu.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked() && batukTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, penyakit_demam.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, penyakit_demam.class);
                startActivity(intent);
            }
        }
    }

}