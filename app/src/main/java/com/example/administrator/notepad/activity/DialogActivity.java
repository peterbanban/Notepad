package com.example.administrator.notepad.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.utils.AlarmReceiver;

public class DialogActivity extends AppCompatActivity {
   MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        AlertDialog alertDialog=new AlertDialog.Builder(DialogActivity.this).create();
        mediaPlayer = MediaPlayer.create(DialogActivity.this, R.raw.run);
            mediaPlayer.start();

            alertDialog.setTitle("闹钟响了");
            alertDialog.setMessage("成功实现闹钟");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mediaPlayer.pause();
                    Intent intent=new Intent(DialogActivity.this, AlarmReceiver.class);
                    PendingIntent pi= PendingIntent.getBroadcast(DialogActivity.this,which,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager am= (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                    am.cancel(pi);
                    finish();
                }
            });
          alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
