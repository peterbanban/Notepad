package com.example.administrator.notepad.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.wigets.DateAndTimePickerDialog;

public class AddToDoActivity extends AppCompatActivity {
    TextView tvPickTime;
    EditText etToDoName;
    CheckBox cbNeedNotify;
    String pickedTime;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        groupName = getIntent().getStringExtra("groupName");

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
        return super.onOptionsItemSelected(item);
    }
}
