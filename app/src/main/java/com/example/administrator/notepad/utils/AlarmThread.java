package com.example.administrator.notepad.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.notepad.activity.ToDoListActivity;

/**
 * Created by Administrator on 2017/03/30.
 */

public class AlarmThread implements  Runnable {
    Context context;

    public AlarmThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000,2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(context,"123", Toast.LENGTH_LONG).show();
    }
}
