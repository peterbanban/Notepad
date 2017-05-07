package com.example.administrator.notepad.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.activity.AddToDoActivity;
import com.example.administrator.notepad.activity.DialogActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("short")){
            Toast.makeText(context,"闹钟响了",Toast.LENGTH_LONG).show();
        }
    }
}
