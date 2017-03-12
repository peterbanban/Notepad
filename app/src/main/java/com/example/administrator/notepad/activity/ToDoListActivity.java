package com.example.administrator.notepad.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.adapter.GroupAdapter;
import com.example.administrator.notepad.adapter.ToDoAdapter;
import com.example.administrator.notepad.utils.DataService;
import com.example.administrator.notepad.utils.ListUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.administrator.notepad.utils.DataService.toDos1;
import static com.example.administrator.notepad.utils.DataService.toDos1;

public class ToDoListActivity extends AppCompatActivity {
    String groupName;
    ListView  lvToDos;
    public int flag=-1;
    MediaPlayer mediaPlayer;
    List<Map.Entry<String,String>> toDos2;
    SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list2);

        Intent intent=getIntent();
         groupName=intent.getStringExtra("groupName");
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(groupName);
        setSupportActionBar(toolbar);
        lvToDos= (ListView) findViewById(R.id.lv_toDos);
        lvToDos.setLongClickable(true);
        lvToDos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ToDoListActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认删除");
                builder.setCancelable(false);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       shared=ToDoListActivity.this.getSharedPreferences(groupName,MODE_PRIVATE);
                          SharedPreferences.Editor editor=shared.edit();
                        editor.remove(toDos2.get(position).getKey().toString());
                        editor.commit();
                        toDos2.remove(position);
                        lvToDos.setAdapter(new ToDoAdapter(ToDoListActivity.this,toDos2));     //为listview设置适配器
                        new ToDoAdapter( ToDoListActivity.this, toDos2).notifyDataSetChanged();  //更新适配器内容
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return true;
            }
        });
    /*    final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.run();*/
        while (true){
            clockRemind();
            if(flag==0){
               break;
            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
          toDos2=DataService.getToDoList(this,groupName);
        lvToDos.setAdapter(new ToDoAdapter( ToDoListActivity.this, toDos2));
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
                intent.putExtra("groupName",groupName);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void  clockRemind(){
        toDos2=DataService.getToDoList(this,groupName);
        MediaPlayer.create(ToDoListActivity.this,R.raw.run);
        Calendar calendar=Calendar.getInstance();
        int hour= calendar.get(Calendar.HOUR);//24小时制 hour是24小时
        int minutes=calendar.get(Calendar.MINUTE);
        String currentTime=hour+":"+minutes;
//        Toast.makeText(this,currentTime+" "+toDos2.size(),Toast.LENGTH_LONG).show();
//        Toast.makeText(this,currentTime+" "+toDos2.get(0).getValue(),Toast.LENGTH_LONG).show();
//        Toast.makeText(this,currentTime+" "+toDos2.get(1).getValue(),Toast.LENGTH_LONG).show();

//        Log.v("size",toDos2.size()+"");

        for (int i=0;i<toDos2.size();i++){
            String [] b=toDos2.get(i).getValue().toString().split("&");
            String []a=b[1].split(" ");
            Toast.makeText(this,a[1],Toast.LENGTH_LONG).show();
//            Log.v("setTime",a[1]);
            if (a[1]==currentTime){
                flag=1;
            }
        }
        if (flag==1) {
            mediaPlayer.start();
            AlertDialog alertDialog=new AlertDialog.Builder(this).create();
            alertDialog.setTitle("响铃提示");
            alertDialog.setMessage("取消响铃");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mediaPlayer.pause();
                    flag=0;
                }
            });
            alertDialog.show();
        }

    }
}
