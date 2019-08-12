package com.timeline.rss2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
    holder.content.setText(mlist.get(position).Content.substring(0,45)+"...");
    holder.webView.loadUrl(mlist.get(position).imgurl);

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



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView title;
    private TextView content;
    private TextView Pubdate;
        public final View mview;
    private WebView webView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            title = itemView.findViewById(R.id.title);
            content =itemView.findViewById(R.id.content);
            Pubdate = itemView.findViewById(R.id.date);
            webView =itemView.findViewById(R.id.webview);
        }
    }
}
