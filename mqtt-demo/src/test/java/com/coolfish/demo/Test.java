package com.coolfish.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @className: Test
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
public class Test {
    public static void main(String[] args) {
        Date time = Calendar.getInstance().getTime();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
        String format1 = format.format(time);
        String format2 = formatTime.format(time);
        System.out.println("format2 = " + format2);
        System.out.println("format1 = " + format1);
        //int date = time.getDate();
        //System.out.println("time.getTime() = " + time.getTime());
        //System.out.println(date);
        //System.out.println("time = " + time);
        String s = delHeaderChar(format2);
        System.out.println("s = " + s);
    }

    public static String delHeaderChar(String str) {
        if (str.startsWith("0")) {
            String s = str.replaceFirst("0", "");
            if (s.equals("0")) {
                return s;
            }
            return delHeaderChar(s);
        }
        return str;
    }
}
