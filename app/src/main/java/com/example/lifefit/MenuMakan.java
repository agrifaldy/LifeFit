package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuMakan extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makan);

        // kita set default nya Home Fragment
        loadFragment(new breakfast());

        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_makanan);

        // default navbar
        bottomNavigationView.setSelectedItemId(R.id.breakfast);

        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    // method untuk load fragment yang sesuai
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container_makanan, fragment)
                    .commit();
            return true;
        }   return false;
    }

    // method listener untuk logika pemilihan
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;   switch (item.getItemId()){
            case R.id.breakfast:
                fragment = new breakfast();
                break;
            case R.id.lunch:
                fragment = new lunch();
                break;
            case R.id.dinner:
                fragment = new dinner();
                break;
        }   return loadFragment(fragment);
    }
}