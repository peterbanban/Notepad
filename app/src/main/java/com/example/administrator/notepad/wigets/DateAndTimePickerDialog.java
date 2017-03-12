package com.example.administrator.notepad.wigets;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.administrator.notepad.activity.AddToDoActivity;

/**
 * Created by Administrator on 2017/02/21.
 */

public class DateAndTimePickerDialog {

    private  static OnDateAndTimePickedListener onDateAndTimePickedListener;
    public static void showDateAndTimePickerDialog(Context context,OnDateAndTimePickedListener listener){
        showDatePickerDialog(context);
        onDateAndTimePickedListener=listener;
    }
   private static int pickedYear;
   private static int pickedMonth;
   private static int pickedDay;
   private static int pickedHour;
   private static int pickedMinute;
   public static void showDatePickerDialog(final Context context){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        int nowYear=calendar.get(java.util.Calendar.YEAR);
        int nowMonth=calendar.get(java.util.Calendar.MONTH);
        int nowDay=calendar.get(java.util.Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               showTimePickerDialog(context);
                pickedYear=year;
                pickedDay=dayOfMonth;
                pickedMonth=month;
            }
        },nowYear,nowMonth,nowDay);
        datePickerDialog.show();
    }
    public static void showTimePickerDialog(Context context){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        int nowHour=calendar.get(java.util.Calendar.HOUR);
        int nowMinute=calendar.get(java.util.Calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(context
                , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pickedMinute=minute;
                pickedHour=hourOfDay;
                String pickedTime=pickedYear+"/"+(pickedMonth+1)+"/"+pickedDay+" "+pickedHour+":"+pickedMinute;
                if(onDateAndTimePickedListener!=null)
                {
                    onDateAndTimePickedListener.onDateAndTimePicked(pickedTime);
                }
            }
        },nowHour,nowMinute,true);
        timePickerDialog.show();
    }
        public interface OnDateAndTimePickedListener{
            void onDateAndTimePicked(String pickTime );
        }
}
