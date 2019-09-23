package com.timeline.rss2;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.timeline.rss2.MainActivity.NEWS;
import static com.timeline.rss2.MainActivity.mcontext;


public class SectionPageAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title,String gudoc){

        mFragmentList.add(fragment);
        Log.e("프래그먼트", String.valueOf(mFragmentList));
      /*  if(gudoc.equals("세계일보"))
        {
            if(title.equals("최신뉴스")) {
                Economy economy = Economy.newInstance("http://rss.segye.com/segye_recent.xml","http://rss.segye.com/segye_recent.xml");
                mFragmentList.add(new homefragment());
            }
            else if(title.equals("정치")) {

                mFragmentList.add(new Policy());
            }
            else if(title.equals("사회")){

                mFragmentList.add( fragment);
            }
            else if(title.equals("경제")){
                Economy.newInstance("http://rss.segye.com/segye_economy.xml","http://rss.segye.com/segye_economy.xml");
                mFragmentList.add(new Economy());
            }
            else if(title.equals("문화연예")){
                CultureEntertainment.newInstance("http://rss.segye.com/segye_entertainment.xml","http://rss.segye.com/segye_entertainment.xml");
                mFragmentList.add(new CultureEntertainment());
            }
            else if(title.equals("스포츠")){
                Sports.newInstance("http://rss.segye.com/segye_sports.xml","http://rss.segye.com/segye_sports.xml");
                mFragmentList.add(new Sports());
            }
        }
        else if(gudoc.equals("동아일보")){
            Log.e("하이",gudoc);
            if(title.equals("최신뉴스")) {
                String string ="http://rss.donga.com/total.xml";
                mFragmentList.add(new homefragment());
            }
            else if(title.equals("정치")) {
                String sti ="http://rss.donga.com/politics.xml";
                mFragmentList.add(new Policy());
            }
            else if(title.equals("사회")){
                mFragmentList.add(Social.newInstance("http://rss.donga.com/national.xml","http://rss.donga.com/national.xml"));
            }
            else if(title.equals("경제")){

                mFragmentList.add(Economy.newInstance("http://rss.donga.com/economy.xml","http://rss.donga.com/economy.xml"));
            }
            else if(title.equals("문화연예")){
                mFragmentList.add(CultureEntertainment.newInstance("http://rss.donga.com/culture.xml","http://rss.donga.com/culture.xml"));
            }
            else if(title.equals("스포츠")){
                mFragmentList.add(Sports.newInstance("http://rss.donga.com/sports.xml","http://rss.donga.com/sports.xml"));
            }
            }
*/



        mFragmentTitleList.add(title);

    }
    public void Clear(){

        mFragmentList.clear();
    }
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    public SectionPageAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) { return mFragmentList.get(position); }


    public int getCount() {

        return mFragmentList.size() ;
    }

}
