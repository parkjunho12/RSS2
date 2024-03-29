package com.timeline.rss2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RSSfeedAdapter extends RecyclerView.Adapter<RSSfeedAdapter.MyViewHolder> {
private ArrayList<Feed> mlist;
private LayoutInflater minflater;
private Context context;
private View mview;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mlistener =null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener =listener;
    }

    public RSSfeedAdapter(Context context, ArrayList<Feed> item){
        this.minflater = LayoutInflater.from(context);
        this.context =context;
        this.mlist =item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.board, parent,false);
        MyViewHolder viewHolder= new MyViewHolder(view);
        mview=view;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
    holder.title.setText(mlist.get(position).Title);
    holder.Pubdate.setText(mlist.get(position).Pubdate);

    if(mlist.get(position).Content.length()>=45)
    {
        holder.content.setText(mlist.get(position).Content.substring(0, 45) + "...");
    }
    else
        {
            holder.content.setText(mlist.get(position).Content);
        }


   // holder.webView.loadUrl(mlist.get(position).imgurl);
        if(mlist.get(position).imgurl.isEmpty())
        {

        }
        else {
            new DownloadImageTask(holder.imageView).execute(mlist.get(position).imgurl);
        }

        holder.index.setText(String.valueOf(position+1));


    holder.mview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            if(mlistener!=null){
                mlistener.onItemClick(view,position);
            }
        }
    });

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
               // LogUtil.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView title;
    private TextView content;
    private TextView Pubdate;
    public final View mview;
    private ImageView imageView;
    private TextView index;
        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            index = itemView.findViewById(R.id.index);
            title = itemView.findViewById(R.id.title);
            content =itemView.findViewById(R.id.content);
            Pubdate = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.webview);
           // webView =itemView.findViewById(R.id.webview);
        }
    }
}
