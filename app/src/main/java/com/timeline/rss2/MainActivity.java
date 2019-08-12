package com.timeline.rss2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String urlinfo = "http://rss.donga.com/total.xml";
    private String tagname ="";
    private String title ="";
    private String desc="";
    private String pubdate="";
    private String imgsrc="sibal";
    private String link="";
    private boolean ismedia =false;
    private boolean isTitle =false;
    private boolean isItem =false;
    private boolean isLink =false;
    private boolean isdate =false;
    private boolean isdesc =false;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment =null;
            switch (menuItem.getItemId())
            {
                case R.id.Home_nav:
                    fragment = new homefragment();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.nav_view);
        nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute();
    }
    class BackgroundTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(urlinfo);
                XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                XmlPullParser parser =factory.newPullParser();
                parser.setInput(new InputStreamReader(url.openStream(),"UTF-8"));
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    switch (eventType){

                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tagname = parser.getName();
                            if(tagname.equals("item")){
                                isItem =true;
                            }
                            else if (tagname.equalsIgnoreCase("media:content")){

                                    if (ismedia==false){
                                        ismedia = true;
                                        String thumbnailUrl = parser.getAttributeValue(null, "url");
                                        imgsrc = thumbnailUrl;
                                        Log.d("---------media--------", imgsrc);

                                    }

                            }
                            break;
                        case XmlPullParser.TEXT:
                            if(isItem){
                                if(tagname.equals("title")){
                                    if(isTitle == false){
                                        isTitle =true;
                                        title =parser.getText();
                                        Log.d("--------title--------",title);
                                    }
                                }
                                if(tagname.equals("pubDate")){
                                    if(isdate ==false){
                                        isdate =true;
                                        pubdate = parser.getText();
                                        Log.d("--------date------",pubdate);

                                    }
                                }
                                    if(tagname.equals("description")){
                                        if(isdesc==false){
                                            isdesc = true;
                                            desc = parser.getText();
                                            desc = desc.substring(desc.lastIndexOf(">")+1);
                                            Log.d("--------desc---------",desc);
                                        }
                                    }
                                    if(tagname.equals("link")){
                                        if (isLink==false){
                                            isLink = true;
                                            link =parser.getText();
                                            Log.d("---------link--------",link);

                                        }
                                }

                            }
                            break;
                        case XmlPullParser.END_TAG:
                            tagname =parser.getName();

                            if(tagname.equals("item") ){
                                ismedia =false;
                                isItem =false;
                                isTitle =false;
                                isLink =false;
                                isdate =false;
                                isdesc =false;
                                pubdate =null;
                                desc =null;
                                title =null;
                                link =null;
                                imgsrc=null;
                            }
                            break;


                    }
                    eventType =parser.next();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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
