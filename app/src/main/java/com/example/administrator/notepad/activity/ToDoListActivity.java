package com.example.administrator.notepad.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.adapter.ToDoAdapter;
import com.example.administrator.notepad.utils.AlarmReceiver;
import com.example.administrator.notepad.utils.DataService;
import com.example.administrator.notepad.utils.MyApplication;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ToDoListActivity extends AppCompatActivity {
    String groupName;
    ListView lvToDos;
    List<Map.Entry<String, String>> toDos2;
    SharedPreferences shared;
    int flagData0;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list2);

        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        flagData0 = Integer.parseInt(intent.getStringExtra("flagData0"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(groupName);
        setSupportActionBar(toolbar);
        lvToDos = (ListView) findViewById(R.id.lv_toDos);
        lvToDos.setLongClickable(true);

        lvToDos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String timeValue= toDos2.get(position).getValue();
                content=toDos2.get(position).getKey();
                String [] iFields=timeValue.split("&");//按%切割字符串
                String iTimeString=iFields[1];
                Intent intent1 = new Intent(ToDoListActivity.this, ContentActivity.class);
                intent1.putExtra("content",content);
                intent1.putExtra("iTime",iTimeString);
                startActivity(intent1);
            }
        });


        //设置长按删除事项
        lvToDos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认删除");
                builder.setCancelable(false);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shared = ToDoListActivity.this.getSharedPreferences(groupName, MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.remove(toDos2.get(position).getKey().toString());
                        editor.commit();
                        toDos2.remove(position);
                        lvToDos.setAdapter(new ToDoAdapter(ToDoListActivity.this, toDos2));     //为listview设置适配器
                        new ToDoAdapter(ToDoListActivity.this, toDos2).notifyDataSetChanged();  //更新适配器内容

                        //删除闹钟和广播提醒
                        Intent intent = new Intent(ToDoListActivity.this, AlarmReceiver.class);
                        PendingIntent pi = PendingIntent.getBroadcast(ToDoListActivity.this, position + flagData0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                        am.cancel(pi);

                        Intent intent1 = new Intent(ToDoListActivity.this, DialogActivity.class);
                        PendingIntent pi1 = PendingIntent.getActivity(ToDoListActivity.this, position + flagData0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager am1 = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                         am1.cancel(pi1);

                        //删除文件
                        File file=new File(content+".txt");
                        if (file.exists())
                        file.delete();

                        deleteFile(content+".txt"); //两种删除方法

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.create().show();
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        toDos2 = DataService.getToDoList(this, groupName);
        lvToDos.setAdapter(new ToDoAdapter(ToDoListActivity.this, toDos2));

    }

    @Override


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_add:

                Intent intent = new Intent(ToDoListActivity.this, AddToDoActivity.class);
                intent.putExtra("groupName", groupName);
                intent.putExtra("flagData", toDos2.size() + flagData0 + "");
                MyApplication.setContext(ToDoListActivity.this);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
