package com.example.administrator.notepad.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.notepad.R;
import com.example.administrator.notepad.utils.SharedPreferencesTool;

public class AddGroupActivity extends AppCompatActivity {
    ImageView ivChoseIcon;
    EditText editText;
    int chosenIconId=-1;
    public static  final int  CodeChooseIcon=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("添加分组");
        setSupportActionBar(toolbar);
        editText=(EditText) findViewById(R.id.editText);
        ivChoseIcon=(ImageView) findViewById(R.id.iv_choose_icon);
        RelativeLayout rl_icon_choose=(RelativeLayout) findViewById(R.id.rl_icon_choose);
        rl_icon_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AddGroupActivity.this,IconChooseActivity.class);
                 startActivityForResult(intent,CodeChooseIcon);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CodeChooseIcon){
            if(resultCode==RESULT_OK){
               chosenIconId=data.getIntExtra("chosenIconId",-1);
                ivChoseIcon.setImageResource(chosenIconId);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        switch (itemId){
            case  R.id.item_confirm:
                String groupName=editText.getText().toString().trim();
                if (groupName==null||groupName.equals("")){
                    Toast.makeText(AddGroupActivity.this,"请输入分组名",Toast.LENGTH_LONG).show();
                     return  true;
                }
                if (chosenIconId==-1){
                    Toast.makeText(AddGroupActivity.this,"请选择图标",Toast.LENGTH_LONG).show();
                    return  true;
                }
                SharedPreferencesTool.SaveData(AddGroupActivity.this,groupName,chosenIconId);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
