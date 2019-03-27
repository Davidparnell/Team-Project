package com.moneyapp;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.ListFragment;

public class FragmentsStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> Fraglist = new ArrayList<>();
    private final List<String> FragTitleList = new ArrayList<>();

    public FragmentsStatePagerAdapter(FragmentManager fm) {
        super( fm );
    }

    public void addFragment(Fragment fragment,String title)
    {
        Fraglist.add(fragment);
        FragTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return Fraglist.get(position);
    }

    @Override
    public int getCount() {
        return Fraglist.size();
    }
}
