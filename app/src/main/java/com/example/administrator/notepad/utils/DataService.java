package com.example.administrator.notepad.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/02/28.
 */

public class DataService {//sharedPreference存储的文件可供程序类或应用间使用

    public static List<Map.Entry>toDos1;
    public static <T>List<T> getToDoList(Context context,String groupName){//groupName 是通过sharepreference存储的文件名
        SharedPreferences sharedPreferences=context.getSharedPreferences(groupName,context.MODE_PRIVATE);
        //getSharePreferences是一个上下文中的方法，所以要传入一个context
        Map all=(Map)sharedPreferences.getAll();
        Set<Map.Entry> entry=all.entrySet();
        toDos1=new ArrayList<>(entry);
        ListUtil.sortToDos(toDos1, new ListUtil.Comparator() {
            @Override
            public boolean compare(int i, int j) {
                String iValue = (String) toDos1.get(i).getValue();
                String [] iFields=iValue.split("&");//按%切割字符串
                String iTimeString=iFields[2];
                long iTime=Long.parseLong(iTimeString);

                String jValue = (String) toDos1.get(j).getValue();
                String [] jFields=jValue.split("&");//按%切割字符串
                String jTimeString=jFields[2];
                long jTime=Long.parseLong(jTimeString);

                return iTime>jTime;
            }
        });
        return (List<T>) toDos1;
    }
}
