package com.example.lifefit;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // kita set default nya Home Fragment
        loadFragment(new monitoring());

        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);

        // default navbar
        bottomNavigationView.setSelectedItemId(R.id.monitoring);

        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    // method untuk load fragment yang sesuai
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }   return false;
    }

    // method listener untuk logika pemilihan
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;   switch (item.getItemId()){
            case R.id.monitoring:
                fragment = new monitoring();
                break;
            /**case R.id.penyakit:
                fragment = new penyakit();
                break;**/
            case R.id.ensiklopedia:
                fragment = new ensiklopedia();
                break;
            /**case R.id.makanMinum:
                fragment = new makanMinum();
                break;
            case R.id.olahraga:
                fragment = new olahraga();
                break;**/
            case R.id.profil:
                fragment = new profil();
                break;
        }   return loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            //super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            finish();

        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

}