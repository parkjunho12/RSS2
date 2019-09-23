package com.timeline.rss2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.timeline.rss2.Social.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    public static Context mcontext;
    public static ArrayList<String> NEWS = null;
    private RecyclerView recyclerView;
    public ViewPager mViewPager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
    private TabLayout tabLayout;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private Spinner spinner;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment =null;
            switch (menuItem.getItemId())
            {
                case R.id.Home_nav:
                    fragment = new Fragment();
                    replaceFrag(fragment);
                    return true;
                case R.id.Search_nav:
                    fragment = new searchfragment();
                    replaceFrag(fragment);
                    return true;
                case R.id.Mypage_nav:
                    fragment = new MyFragment();
                    replaceFrag(fragment);
                    return true;

            }

            return false;
        }
    };
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;

        NEWS = new ArrayList<>();
        NEWS.clear();
        String[] news = getResources().getStringArray(R.array.dongailbo);
        for(int j=0;j<news.length;j++){
            NEWS.add(news[j]);
        }



        DbOpenHelper dbOpenHelper = new DbOpenHelper(this);
        try {
            dbOpenHelper.open();
            dbOpenHelper.create();
            dbOpenHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.nav_view);
        nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        mViewPager = (ViewPager) findViewById(R.id.container);

         tabLayout = (TabLayout) findViewById(R.id.tabs);
         tabLayout.setupWithViewPager(mViewPager);
        Cursor c = null;
        arrayList = new ArrayList<>();
        String result = null;
        try {
            dbOpenHelper.open();
            c = dbOpenHelper.selectColumns();


            while(c.moveToNext()) {
                result =  c.getString(c.getColumnIndex("구독"));
                arrayList.add(result);

            }

            dbOpenHelper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //setupViewPager(mViewPager, "");
        spinner = (Spinner) findViewById(R.id.spinner);

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item,arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplication(),arrayList.get(i)+"가 선택되었습니다.",Toast.LENGTH_SHORT).show();

                    setupViewPager(mViewPager, arrayList.get(i));
//                if (arrayList.get(i).equals("세계일보")){
//                    Social.newInstance("http://rss.segye.com/segye_society.xml","http://rss.segye.com/segye_society.xml").doingBack();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void setupViewPager(ViewPager viewPager,String newsi){
       String[] news = getResources().getStringArray(R.array.donga);

        String[] column = getResources().getStringArray(R.array.dongailbo);
        if (newsi.equals("세계일보"))
        {
            column = getResources().getStringArray(R.array.segye);
        }
        else if(newsi.equals("동아일보")){
            column = getResources().getStringArray(R.array.dongailbo);
        }
        else if(newsi.equals("경향닷컴")){
            column = getResources().getStringArray(R.array.khan);
        }
        else if(newsi.equals("조선닷컴")){
            column = getResources().getStringArray(R.array.chosun);
        }
        else if(newsi.equals("다음뉴스")){
            column = getResources().getStringArray(R.array.daum);
        }
        else if(newsi.equals("헤럴드경제")){
            column = getResources().getStringArray(R.array.herald);
        }
        else if(newsi.equals("매일경제")){
            column = getResources().getStringArray(R.array.mail);
        }
                int i=0;
                adapter.Clear();

                for(i=0;i<news.length;i++)
                {
                       // Log.e(String.valueOf(i),newsi);

                    if(news[i].equals("최신뉴스")) {

                                homefragment homefragment = new homefragment();
                                homefragment.urlinfo = column[i];
                                adapter.addFragment(homefragment, news[i],newsi);

                        }
                        else if(news[i].equals("정치")){

                              Policy policy = new Policy();
                                policy.urlinfo =column[i];
                            adapter.addFragment(policy,news[i],newsi);


                        }
                        else if(news[i].equals("사회")) {

                        Social social = new Social();
                        social.urlinfo = column[i];
                        adapter.addFragment(social, news[i], newsi);


                            }
                        else if(news[i].equals("경제")) {


                        Economy economy = new Economy();
                        economy.urlinfo = column[i];
                        adapter.addFragment(economy, news[i], newsi);



                        }else if(news[i].equals("문화연예")){

                            CultureEntertainment cultureEntertainment = new CultureEntertainment();
                            cultureEntertainment.urlinfo = column[i];
                            adapter.addFragment(cultureEntertainment,news[i],newsi);

                        }
                        else if(news[i].equals("스포츠")){

                            Sports sports = new Sports();
                            sports.urlinfo = column[i];
                            adapter.addFragment(sports,news[i],newsi);

                        }
                }
                viewPager.setAdapter(adapter);
                refresh();
    }
    void refresh(){
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        refresh();
        super.onResume();

    }


    private void replaceFrag(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.change_frag,fragment);
        transaction.commit();
    }


}
