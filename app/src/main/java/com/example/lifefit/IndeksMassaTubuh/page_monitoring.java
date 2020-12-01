package com.example.lifefit.IndeksMassaTubuh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lifefit.R;

public class page_monitoring extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_monitoring);
    }

    public void toIndeksMassaTubuh(View view) {
        Intent intent = new Intent(this, indeks_massa_tubuh.class);
        startActivity(intent);
    }
}