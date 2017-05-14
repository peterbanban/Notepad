package com.example.administrator.notepad.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.notepad.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

public class ContentActivity extends AppCompatActivity {

    String iTime="";
    String todoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent1=getIntent();
        todoName=intent1.getStringExtra("content");
        iTime=intent1.getStringExtra("iTime");
        TextView tvName= (TextView) findViewById(R.id.tv_content);
        TextView tvTime= (TextView) findViewById(R.id.tv_time1);
        EditText etContent= (EditText) findViewById(R.id.et_content);
        tvName.setText(todoName);
        if (iTime.length()>5)
           tvTime.setText(iTime);
           else
               tvTime.setText("");
        try {

            FileInputStream inputStream=openFileInput(todoName+".txt");
                int length=inputStream.available();
                byte []buffer=new byte[length];
                  inputStream.read(buffer);
            String str=new String(buffer,"UTF-8");
             etContent.setText(str);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
