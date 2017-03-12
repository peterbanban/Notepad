package com.example.administrator.notepad.utils;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/02/27.
 */

public class ListUtil {
    public interface Comparator {
         boolean compare(int i,int j);

    }
        public static  <T>void sortToDos(List<T> list,Comparator comparator){

        //冒泡排序
        for(int i=0;i<list.size();i++)
            for (int j=i;j<list.size();j++){
                if (comparator.compare(i,j)) {
                      T t=list.get(i);
                      list.set(i,list.get(j));
                      list.set(j,t);
                }
            }
    }
}

