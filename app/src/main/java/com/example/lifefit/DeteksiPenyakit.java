package com.example.lifefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.card.MaterialCardView;

public class DeteksiPenyakit extends AppCompatActivity implements View.OnClickListener {
    private CardView cv_demam;
    private RadioButton demamIya;
    private RadioButton demamTidak;
    private RadioButton batukIya;
    private RadioButton batukTidak;
    private RadioButton lendirtIya;
    private RadioButton lendirtTidak;
    private RadioButton sakitkIya;
    private RadioButton sakitkTidak;
    private RadioButton hidungtIya;
    private RadioButton hidungtTidak;
    private Button submitDeteksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_deteksi_penyakit);

        demamIya = (RadioButton) findViewById(R.id.demamIya);
        demamIya.setOnClickListener(this);
        demamTidak = (RadioButton) findViewById(R.id.demamTidak);
        demamTidak.setOnClickListener(this);
        batukIya = (RadioButton) findViewById(R.id.batukIya);
        batukIya.setOnClickListener(this);
        batukTidak = (RadioButton) findViewById(R.id.batukTidak);
        batukTidak.setOnClickListener(this);
        lendirtIya = (RadioButton) findViewById(R.id.lendirtIya);
        lendirtIya.setOnClickListener(this);
        lendirtTidak = (RadioButton) findViewById(R.id.lendirtTidak);
        lendirtTidak.setOnClickListener(this);
        sakitkIya = (RadioButton) findViewById(R.id.sakitkIya);
        sakitkIya.setOnClickListener(this);
        sakitkTidak = (RadioButton) findViewById(R.id.sakitkTidak);
        sakitkTidak.setOnClickListener(this);
        hidungtIya = (RadioButton) findViewById(R.id.hidungtIya);
        hidungtIya.setOnClickListener(this);
        hidungtTidak = (RadioButton) findViewById(R.id.hidungtTidak);
        hidungtTidak.setOnClickListener(this);

        submitDeteksi = (Button) findViewById(R.id.submitDeteksi);
        submitDeteksi.setOnClickListener(this);

    }

    public void onClick(View v) {

        int i = v.getId();

        if (demamIya.isChecked() && batukIya.isChecked() && lendirtTidak.isChecked() && sakitkTidak.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked() && batukIya.isChecked() && lendirtTidak.isChecked() && sakitkIya.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked() && batukTidak.isChecked() && lendirtTidak.isChecked() && sakitkIya.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked() && batukTidak.isChecked() && lendirtIya.isChecked() && sakitkIya.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_radang_tenggorokan.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukIya.isChecked() && lendirtTidak.isChecked() && sakitkTidak.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_radang_tenggorokan.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukTidak.isChecked() && lendirtTidak.isChecked() && sakitkIya.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_sinusitis.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukIya.isChecked() && lendirtIya.isChecked() && sakitkIya.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_sinusitis.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukIya.isChecked() && lendirtIya.isChecked() && sakitkIya.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_sinusitis.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked() && batukTidak.isChecked() && lendirtTidak.isChecked() && sakitkTidak.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukIya.isChecked() && lendirtTidak.isChecked() && sakitkTidak.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukTidak.isChecked() && lendirtIya.isChecked() && sakitkTidak.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_radang_tenggorokan.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukTidak.isChecked() && lendirtTidak.isChecked() && sakitkIya.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_sinusitis.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukTidak.isChecked() && lendirtTidak.isChecked() && sakitkTidak.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else if (demamTidak.isChecked() && batukTidak.isChecked() && lendirtTidak.isChecked() && sakitkTidak.isChecked() && hidungtTidak.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_sehat.class);
                startActivity(intent);
            }
        } else if (demamIya.isChecked() && batukIya.isChecked() && lendirtIya.isChecked() && sakitkIya.isChecked() && hidungtIya.isChecked()){
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        } else {
            if (i == R.id.submitDeteksi) {
                Intent intent = new Intent(this, hasil_deteksi_flu.class);
                startActivity(intent);
            }
        }
    }

    public void backDashboard(View view) {
        finish();
    }
}