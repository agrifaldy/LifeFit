package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MenuMakan extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_makan);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tab_layoutt = findViewById(R.id.tab_layout);
        tab_layoutt.addTab(tab_layoutt.newTab().setText("Breakfast"));
        tab_layoutt.addTab(tab_layoutt.newTab().setText("Lunch"));
        tab_layoutt.addTab(tab_layoutt.newTab().setText("Dinner"));
        tab_layoutt.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager pagerr = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tab_layoutt.getTabCount());
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
//        loadFragment(new breakfast());
//
//        // inisialisasi BottomNavigaionView
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_makanan);
//
//        // default navbar
//        bottomNavigationView.setSelectedItemId(R.id.breakfast);
//
//        // beri listener pada saat item/menu bottomnavigation terpilih
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

//    // method untuk load fragment yang sesuai
//    private boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fl_container_makanan, fragment)
//                    .commit();
//            return true;
//        }   return false;
//    }
//
//    // method listener untuk logika pemilihan
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;   switch (item.getItemId()){
//            case R.id.breakfast:
//                fragment = new breakfast();
//                break;
//            case R.id.lunch:
//                fragment = new lunch();
//                break;
//            case R.id.dinner:
//                fragment = new dinner();
//                break;
//        }   return loadFragment(fragment);
//    }
}