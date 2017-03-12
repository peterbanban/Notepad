package com.example.administrator.notepad.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/03/01.
 */

//自定义类

public class SharedPreferencesTool {

    public static void  SaveData(Context context,String groupName,int chosenIconId){
        SharedPreferences sharedPreferences=context.getSharedPreferences("group", Context.MODE_PRIVATE);/*PRIVATE为私有权限*/
        SharedPreferences.Editor editor=sharedPreferences.edit(); //调用editor方法获取对象一个编辑器
        editor.putInt(groupName,chosenIconId); //把一个int值放在缓存区里
        editor.commit();  //把缓存区的东西都写入文件
    }
}
