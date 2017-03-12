package com.example.administrator.notepad.activity;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.notepad.R;

public class IconChooseActivity extends AppCompatActivity {
    ListView lvIcons;
    Pair<Integer,String>[] icons=new Pair[]{    //利用Pair存储数对，相当于一个集合
            new Pair<>(R.drawable.icon_clock,"clock"),
            new Pair<>(R.drawable.icon_git,"git"),
            new Pair<>(R.drawable.icon_hospital,"hospital"),
            new Pair<>(R.drawable.icon_oppointment,"appointment"),
            new Pair<>(R.drawable.icon_photoshop,"photo"),
            new Pair<>(R.drawable.icon_shop,"shop"),
            new Pair<>(R.drawable.icon_trip,"trip"),
            new Pair<>(R.drawable.icon_washing,"washing"),
            new Pair<>(R.drawable.icon_work,"work")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_choose);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择icon");

        lvIcons=(ListView) findViewById(R.id.lv_icons);
        //为listView添加适配器adapter
        lvIcons.setAdapter(new BaseAdapter() {
            @Override  //总的多少行
            public int getCount() {
                if (icons==null)
                {
                    return 0;
                }
                return icons.length;
            }
            @Override  //每行显示的内容
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                   if(convertView==null){
                       viewHolder=new ViewHolder();
                       LayoutInflater inflater=LayoutInflater.from(IconChooseActivity.this);
                       convertView = inflater.inflate(R.layout.item_icon_choose,parent,false);

                      viewHolder.ivIcon= (ImageView)convertView.findViewById(R.id.iv_icon);
                       viewHolder.tvName=(TextView)convertView.findViewById(R.id.tv_name);
                       convertView.setTag(viewHolder);
                   }
                    else
                   {
                       viewHolder=(ViewHolder) convertView.getTag();
                   }
              viewHolder.ivIcon.setImageResource(icons[position].first);
              viewHolder.tvName.setText(icons[position].second);
                return convertView;
            }
            class ViewHolder{
                ImageView ivIcon;
                TextView tvName;
            }
            @Override

            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        });
        lvIcons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("chosenIconId",icons[position].first);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

}
