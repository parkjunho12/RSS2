package com.timeline.rss2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.timeline.rss2.homefragment.urlinfo;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        if(title.equals("정치"))
        {
            fragment = new Policy();
        }
        else if(title.equals("최신뉴스"))
        {
            urlinfo ="http://rss.donga.com/total.xml";
        }
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    public SectionPageAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) { return mFragmentList.get(position); }

    @Override
    public int getCount() {
        return mFragmentList.size() ;
    }
}
