package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapterOr extends FragmentStatePagerAdapter {

    int noFragment;

    public PagerAdapterOr(@NonNull FragmentManager fm, int noTab) {
        super(fm);
        noFragment = noTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new indoor();
            case 1 : return new outdoor();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return noFragment;
    }
}
