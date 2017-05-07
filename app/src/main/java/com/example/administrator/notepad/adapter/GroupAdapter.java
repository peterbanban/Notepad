package com.example.administrator.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.notepad.R;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Administrator on 2017/02/15.
 */

public class GroupAdapter extends MyBaseAdapter{
    List<Map.Entry<String,Integer>>groups;

    public GroupAdapter(Context context, List<Entry<String, Integer>> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        if (groups==null)
        {
            return 0;
        }
        return groups.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();    //填充页面
            convertView=LayoutInflater.from(context).inflate(R.layout.item_icon_choose,parent,false);
            viewHolder.ivIcon= (ImageView)convertView.findViewById(R.id.iv_icon);  //解析控件
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        }
        else
        {

            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(groups.get(position).getValue());
        viewHolder.tvName.setText(groups.get(position).getKey());
        return convertView;
    }
    class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
    }
}
