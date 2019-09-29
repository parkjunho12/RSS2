package com.timeline.rss2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {
    private ArrayList<String> mlist;
    private LayoutInflater minflater;
    private Context context;
    private View mview;
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);

    }
    private MyAdapter.OnItemClickListener mlistener =null;
    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener){
        this.mlistener =listener;
    }

    public MyAdapter(Context context, ArrayList<String> item) {
        this.context = context;
        this.mlist = item;
        this.minflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.board2, parent,false);
        MyAdapter.MyViewholder viewHolder= new MyAdapter.MyViewholder(view);
        mview=view;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder,final int position) {
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);

        Cursor c = null;
        ArrayList arrayList = new ArrayList<String>();
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

        holder.title.setText(String.valueOf(arrayList.get(position)));
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
    public class MyViewholder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;
        private TextView Pubdate;
        public final View mview;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            title = itemView.findViewById(R.id.my_title);



        }
    }
}
