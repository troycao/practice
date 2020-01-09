package com.troy.practice.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss") ;
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyyMMdd");

    public static String formatDate(Date date){
        String datetime = dateFormat1.format(date);
        return datetime;
    }

    public static String formatDate(SimpleDateFormat dateFormat,Date date){
        String datetime = dateFormat.format(date);
        return datetime;
    }

    public static Date parseDate(SimpleDateFormat dateFormat,String dateStr){
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDate(String dateStr){
        Date date = null;
        try {
            date = dateFormat2.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        System.out.println(parseDate("20190723230306"));
        System.out.println(formatDate(parseDate("20190723230306")));
    }

}
