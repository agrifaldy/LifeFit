package com.example.lifefit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int noFragment;

    public PagerAdapter(@NonNull FragmentManager fm, int noTab) {
        super(fm);
        noFragment = noTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new breakfast();
            case 1 : return new lunch();
            case 2 : return new dinner();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return noFragment;
    }
}
