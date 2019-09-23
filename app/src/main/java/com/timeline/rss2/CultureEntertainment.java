package com.timeline.rss2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CultureEntertainment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CultureEntertainment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CultureEntertainment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String urlinfo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private Policy.OnFragmentInteractionListener mListener;

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
    private ArrayList<Feed> RSSList = null;
    private Feed feed =null;
    public CultureEntertainment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CultureEntertainment newInstance(String param1, String param2) {
        CultureEntertainment fragment = new CultureEntertainment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_homefragment, container, false);

        recyclerView =(RecyclerView) view.findViewById(R.id.home_recycle);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext().getApplicationContext(), new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int i;
        String[] news = getResources().getStringArray(R.array.donga);




        CultureEntertainment.BackgroundTask backgroundTask = new CultureEntertainment.BackgroundTask();
        backgroundTask.execute();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    class BackgroundTask extends AsyncTask<String,Void,String> {

        @SuppressLint("WrongThread")
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
                            RSSList = new ArrayList<Feed>();
                            break;
                        case XmlPullParser.START_TAG:
                            tagname = parser.getName();
                            if(tagname.equals("item")){
                                feed = new Feed();
                                isItem =true;
                            }
                            else if (tagname.equalsIgnoreCase("media:content")){

                                if (ismedia==false) {
                                    ismedia = true;
                                    String thumbnailUrl = parser.getAttributeValue(null, "url");
                                    imgsrc = thumbnailUrl;
                                    Log.d("---------media--------", imgsrc);
                                    feed.setImgurl(imgsrc);

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
                                        feed.setTitle(title);
                                    }
                                }
                                if(tagname.equals("pubDate")){
                                    if(isdate ==false){
                                        isdate =true;
                                        pubdate = parser.getText();
                                        Log.d("--------date------",pubdate);
                                        feed.setPubdate(pubdate);

                                    }
                                }
                                if(tagname.equals("description")){
                                    if(isdesc==false){
                                        isdesc = true;
                                        desc = parser.getText();
                                        desc = desc.substring(desc.lastIndexOf(">")+1);
                                        Log.d("--------desc---------",desc);
                                        feed.setContent(desc);
                                    }
                                }
                                if(tagname.equals("link")){
                                    if (isLink==false){
                                        isLink = true;
                                        link =parser.getText();
                                        Log.d("---------link--------",link);
                                        feed.setLink(link);

                                    }
                                }

                            }
                            break;
                        case XmlPullParser.END_TAG:
                            tagname =parser.getName();

                            if(tagname.equals("item") ){
                                RSSList.add(feed);
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
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            RSSfeedAdapter adapter = new RSSfeedAdapter(getContext().getApplicationContext(), RSSList);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new RSSfeedAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(RSSList.get(pos).link)));
                }
            });
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
