package com.example.ndanh.androidbeginner.Supporter;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by ndanh on 3/20/2017.
 */

public class Common {
    public static String parseDateToString(Calendar calendar, String sign, boolean isUI){
        String currentDate;
        if(isUI){
            currentDate = calendar.get(Calendar.DATE)  >= 10 ? String.valueOf(calendar.get(Calendar.DATE)) : "0" + String.valueOf(calendar.get(Calendar.DATE));
            currentDate += sign;
            currentDate += (calendar.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf(calendar.get(Calendar.MONTH) + 1) : "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
            currentDate += sign;
            currentDate += String.valueOf(calendar.get(Calendar.YEAR));
            return currentDate;
        }else{
            currentDate = String.valueOf(calendar.get(Calendar.YEAR)) + sign;
            currentDate += (calendar.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf(calendar.get(Calendar.MONTH) + 1) : "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
            currentDate += sign;
            currentDate += calendar.get(Calendar.DATE)  >= 10 ? String.valueOf(calendar.get(Calendar.DATE)) : "0" + String.valueOf(calendar.get(Calendar.DATE));
            return currentDate;
        }
    }

    public static String parseTimeToString(Calendar calendar, String sign){
        String currentDate= calendar.get(Calendar.HOUR_OF_DAY)  >= 10 ? String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) : "0" + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            currentDate += sign;
            currentDate += calendar.get(Calendar.MINUTE) >= 10 ? String.valueOf(calendar.get(Calendar.MINUTE)) : "0" + String.valueOf(calendar.get(Calendar.MINUTE));
            return currentDate;
    }
    public static Calendar parseStringToDate(String date){
        String[] parts = date.split("/");
        Calendar cal = Calendar.getInstance();
        if(parts.length == 3){
            cal.set(Integer.parseInt(parts[0].toString().trim()),Integer.parseInt(parts[1].toString().trim()) - 1,Integer.parseInt(parts[2].toString().trim()));
            return cal;
        }else if(parts.length == 1) {
            int dateInt = Integer.parseInt(date);
            int day = dateInt % 100;
            dateInt = dateInt / 100;
            int month = dateInt % 100;
            dateInt = dateInt / 100;
            cal.set(day,month,dateInt);
            return cal;
        } else {
            return null;
        }
    }

    public static Calendar parseStringToTime(String date){
        String[] parts = date.split(":");
        Calendar cal = Calendar.getInstance();
        if(parts.length == 2){
            cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE) ,Integer.parseInt(parts[0].toString().trim()),Integer.parseInt(parts[1].toString().trim()));
            return cal;
        }else if(parts.length == 1) {
            int timeInt = Integer.parseInt(date);
            int minute = timeInt % 100;
            timeInt = timeInt / 100;
            cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE) ,timeInt,minute);
            return cal;
        } else {
            return null;
        }
    }
}
