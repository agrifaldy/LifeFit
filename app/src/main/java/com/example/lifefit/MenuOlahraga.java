package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MenuOlahraga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_olahraga);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        TabLayout tab_layoutt = findViewById(R.id.tab_layout2);
        tab_layoutt.addTab(tab_layoutt.newTab().setText("Indoor"));
        tab_layoutt.addTab(tab_layoutt.newTab().setText("Outdoor"));
        tab_layoutt.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager pagerr = findViewById(R.id.pager2);
        PagerAdapterOr adapter = new PagerAdapterOr(getSupportFragmentManager(), tab_layoutt.getTabCount());
        pagerr.setAdapter(adapter);
        pagerr.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layoutt));
        tab_layoutt.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerr.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        // kita set default nya Home Fragment
//        loadFragment(new indoor());
//
//        // inisialisasi BottomNavigaionView
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_olahraga);
//
//        // default navbar
//        bottomNavigationView.setSelectedItemId(R.id.indoor);
//
//        // beri listener pada saat item/menu bottomnavigation terpilih
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    public void backToEnsiklopedia(View view) {
        finish();
    }

//    // method untuk load fragment yang sesuai
//    private boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fl_container_olahraga, fragment)
//                    .commit();
//            return true;
//        }   return false;
//    }
//
//    // method listener untuk logika pemilihan
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;   switch (item.getItemId()){
//            case R.id.indoor:
//                fragment = new indoor();
//                break;
//            case R.id.outdoor:
//                fragment = new outdoor();
//                break;
//        }   return loadFragment(fragment);
//    }
}