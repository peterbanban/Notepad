package com.example.administrator.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.notepad.R;

import java.security.Key;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2017/02/22.
 */

public class ToDoAdapter extends MyBaseAdapter {
    List<Map.Entry<String, String>> toDos;

    public <T>ToDoAdapter(Context context, List<T> toDos) {
        this.context = context;
        this.toDos = (List<Map.Entry<String, String>>) toDos;
    }

    @Override
    public int getCount() {
        if(toDos==null) {
            return 0;
        }
        return toDos.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.item_todo,parent,false);
            viewHolder.tv= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);

        }else {
           viewHolder = (ViewHolder) convertView.getTag();
        }
           viewHolder.tv.setText(toDos.get(position).getKey());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
