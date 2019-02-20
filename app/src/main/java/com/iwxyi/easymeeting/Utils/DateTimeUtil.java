package com.iwxyi.easymeeting.Utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    /**
     * 时间戳转换到年
     * @param timestamp 时间戳
     * @return 年
     */
    public static int getYearFromTimestamp(long timestamp) {
        if (timestamp == 0) return 0;
        String s = null;
        try {
            s = longToString(timestamp, "yyyy");
        } catch (ParseException e) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 时间戳转换到月
     * @param timestamp 时间戳
     * @return 月
     */
    public static int getMonthFromTimestamp(long timestamp) {
        if (timestamp == 0) return 0;
        String s = null;
        try {
            s = longToString(timestamp, "MM"); // 大写M是月份，小写m是分钟
        } catch (ParseException e) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 时间戳转换到日
     * @param timestamp 时间戳
     * @return 日
     */
    public static int getDateFromTimestamp(long timestamp) {
        if (timestamp == 0) return 0;
        String s = null;
        try {
            s = longToString(timestamp, "dd");
        } catch (ParseException e) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 时间戳转换到小时
     * @param timestamp 时间戳
     * @return 小时
     */
    public static int getHourFromTimestamp(long timestamp) {
        if (timestamp == 0) return 0;
        String s = null;
        try {
            s = longToString(timestamp, "hh");
        } catch (ParseException e) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 时间戳转换到分钟
     * @param timestamp 时间戳
     * @return 分钟
     */
    public static int getMinuteFromTimestamp(long timestamp) {
        if (timestamp == 0) return 0;
        String s = null;
        try {
            s = longToString(timestamp, "mm");
        } catch (ParseException e) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 时间戳转换到秒
     * @param timestamp 时间戳
     * @return 秒
     */
    public static int getSecondFromTimestamp(long timestamp) {
        if (timestamp == 0) return 0;
        String s = null;
        try {
            s = longToString(timestamp, "ss");
        } catch (ParseException e) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    /**
     * 散落的数字转换成文本（填充0）
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static String dataToString(int year, int month, int date) {
        String rst = year + "/";
        if (month < 10)
            rst += "0";
        rst += month + "/";
        if (date < 10)
            rst += "0";
        rst += date;
        return rst;
    }

    /**
     * 散落的时间转换到字符串
     * @param hour
     * @param minute
     * @return
     */
    public static String timeToString(int hour, int minute) {
        String rst = "";
        if (hour < 10)
            rst += "0";
        rst += hour + ":";
        if (minute < 10)
            rst += "0";
        rst += minute;
        return rst;
    }

    /**
     * 散落的日期时间转换到具体的时间戳
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static long valsToTimestamp(int year, int month, int date, int hour, int minute, int second) {
        String o = year + "-" + month+"-"+date + " " + hour + ":" + minute + ":" + second;
        if (year == 0)
            return 0;

        Date d = null;
        try {
            d = stringToDate(o, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return d.getTime();
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        return dateToString(date, formatType);
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss //yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss
    // yyyy年MM月dd日 HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}
