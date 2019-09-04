package com.timeline.rss2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ViewPager mViewPager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
    private TabLayout tabLayout;


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
        DbOpenHelper dbOpenHelper = new DbOpenHelper(this);
        try {
            dbOpenHelper.open();
            dbOpenHelper.create();
            dbOpenHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.nav_view);
        nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

         tabLayout = (TabLayout) findViewById(R.id.tabs);
         tabLayout.setupWithViewPager(mViewPager);

    }
    public void setupViewPager(ViewPager viewPager){
        String[] news = getResources().getStringArray(R.array.donga);

                int i=0;

                for(i=0;i<news.length;i++)
                {

                        adapter.addFragment(new homefragment(), news[i]);

                }
                viewPager.setAdapter(adapter);
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
