package com.example.administrator.notepad.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.utils.AlarmReceiver;
import com.example.administrator.notepad.utils.MyApplication;
import com.example.administrator.notepad.wigets.DateAndTimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddToDoActivity extends AppCompatActivity {
    TextView tvPickTime;
    EditText etToDoName;
    CheckBox cbNeedNotify;
    String pickedTime;
    String groupName;
    int flagData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        groupName = getIntent().getStringExtra("groupName");
        flagData=Integer.parseInt(getIntent().getStringExtra("flagData"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加任务");
        setSupportActionBar(toolbar);

        findViewById(R.id.rl_time_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimePickerDialog.showDateAndTimePickerDialog(AddToDoActivity.this, new DateAndTimePickerDialog.OnDateAndTimePickedListener() {
                    @Override
                    public void onDateAndTimePicked(String pickTime) {
                        pickedTime = pickTime;
                        tvPickTime.setText(pickTime);
                    }
                });
            }
        });
        tvPickTime = (TextView) findViewById(R.id.tv_time);
        etToDoName = (EditText) findViewById(R.id.et_todo_name);
        cbNeedNotify = (CheckBox) findViewById(R.id.cb_need_notify);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_confirm:
                String toDoName = etToDoName.getText().toString().trim(); // trim()方法为去掉前后空格
                if (toDoName == null || "".equals(toDoName)) {
                    Toast.makeText(AddToDoActivity.this, "请先输入任务名", Toast.LENGTH_LONG).show();
                } else {
                    boolean needNotify = cbNeedNotify.isChecked();
                    SharedPreferences sharedPreferences = getSharedPreferences(groupName, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(toDoName, needNotify + "&" + pickedTime+"&"+System.currentTimeMillis());
                    editor.commit();
                    finish();
                }
                break;
        }
       if (cbNeedNotify.isChecked()) {
           clockRemind();
       }
        return super.onOptionsItemSelected(item);
    }
    public void clockRemind() {
        Intent intent =new Intent(MyApplication.getContext(), AlarmReceiver.class);
        intent.setAction("short");
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MyApplication.getContext(),flagData,intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent1=new Intent(MyApplication.getContext(),DialogActivity.class);
        PendingIntent pendingIntent1=PendingIntent.getActivity(MyApplication.getContext(),flagData,intent1,
                PendingIntent.FLAG_CANCEL_CURRENT);
        String strTime=pickedTime+":00";
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date= null;
        try {
            date = format.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MyApplication.setContext(AddToDoActivity.this);
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager1.set(AlarmManager.RTC_WAKEUP,date.getTime(),pendingIntent1);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTime(),pendingIntent);
    }

}

