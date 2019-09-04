package com.timeline.rss2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("눌림",list.get(i));
                show(i);
            }
        });
        return view;
    }
    class ViewHolder{
        public TextView label;
        public View view;
    }
    void show(final int i)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(list.get(i));
        builder.setMessage(list.get(i) + "를 구독하시겠습니까?");
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int in) {
                        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
                        try {
                            dbOpenHelper.open();
                            dbOpenHelper.insertColumn("phone_token",list.get(i));
                            dbOpenHelper.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context,"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
    }
}
