package com.timeline.rss2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public SearchAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null) {
                view = layoutInflater.inflate(R.layout.row_list, null);
                viewHolder = new ViewHolder();
            viewHolder.label = (TextView) view.findViewById(R.id.label);
            view.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder)view.getTag();
            }

        viewHolder.label.setText(list.get(i));

        return view;
    }
    class ViewHolder{
        public TextView label;
    }
}
