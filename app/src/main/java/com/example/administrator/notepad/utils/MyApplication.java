package com.example.administrator.notepad.utils;


import android.content.Context;

/**
 * Created by Administrator on 2017/05/02.
 */

public class MyApplication  {
    private static Context context;

    public static Context getContext() {
        return context;
    }
    public static void setContext(Context context1) {
         context=context1;
    }


}
