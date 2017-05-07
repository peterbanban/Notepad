package com.example.administrator.notepad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.Toolbar;
import android.telecom.Connection;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.adapter.GroupAdapter;
import com.example.administrator.notepad.utils.DataService;
import com.example.administrator.notepad.utils.SharedPreferencesTool;

import java.io.File;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.administrator.notepad.R.id.activity_main;
import static com.example.administrator.notepad.R.id.list_item;
import static com.example.administrator.notepad.R.id.toolbar;

public class MainActivity extends AppCompatActivity {
    ListView lvGroups;
    List<Map.Entry<String,Integer>> groups;

//    GroupAdapter   groupAdapter=new GroupAdapter(MainActivity.this,groups);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = (Toolbar)findViewById(toolbar);
        toolBar.setTitle("分组列表");
        setSupportActionBar(toolBar);
        lvGroups= (ListView) findViewById(R.id.iv_groups);
        lvGroups.setLongClickable(true);

        lvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupName=groups.get(position).getKey();
                Intent intent=new Intent(MainActivity.this, ToDoListActivity.class);
                intent.putExtra("groupName",groupName);
                intent.putExtra("flagData0",groups.size()*1000+"");
                startActivity(intent);
            }
        });

        lvGroups.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                  AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认删除");
                builder.setCancelable(false);    //阻止单击对话框以外的地方产生其他事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=MainActivity.this.getSharedPreferences("group",MainActivity.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.remove(groups.get(position).getKey().toString());     //删除本地文件的表项
                            editor.commit();
                        groups.remove(position);
                        lvGroups.setAdapter( new GroupAdapter(MainActivity.this,groups));  //更新adapter里的表项
                        new GroupAdapter(MainActivity.this,groups).notifyDataSetChanged();  //更新adapter里的表项
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();    //上面是创建对话框的过程，此方法是将创建的对话框显示的界面上
                return true;
            }
        });
    }
    protected  void  onResume(){
        super.onResume();
        SharedPreferences sharedPreferences=getSharedPreferences("group",MODE_PRIVATE);//从sharedPreferences里提取数据
        Map<String,Integer>map= (Map<String, Integer>) sharedPreferences.getAll();//取全部数据 map无序
        Set<Map.Entry<String, Integer>> set=map.entrySet();//map有一个方法叫做entrySet,这方法可以将Map的键值对的映射关系作为set集合的元素存储到Set集合当中，而这种映射关系的类型就是Entry的类型
        groups=new ArrayList(set); //将无序的map转为有序的list
        Collections.sort(groups, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    int key1=o1.getValue();
                    int key2=o2.getValue();

                return key1-key2;
            }
        });
        lvGroups.setAdapter( new GroupAdapter(MainActivity.this,groups));

    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);*/
        getMenuInflater().inflate(R.menu.menu_main,menu);
        // TODO: 2017/04/14
        return true;
    }
     
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_add:
                Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                startActivity(intent);
                break;

        }
            return super.onOptionsItemSelected(item);//return true;

    }

}
