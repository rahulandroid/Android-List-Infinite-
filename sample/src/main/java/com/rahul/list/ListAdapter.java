package com.rahul.list;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rahul Mane on 25/11/2019.
 */
public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private LayoutInflater inflater;
    private boolean isLoading;

    public ListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addElement(String s) {
        list.add(s);
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
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.row_listview_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(list.get(i));
        return view;
    }

    private class ViewHolder {
        private TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
