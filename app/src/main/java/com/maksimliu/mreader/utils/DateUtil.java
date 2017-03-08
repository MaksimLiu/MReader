package com.maksimliu.mreader.utils;

import java.util.Calendar;

/**
 * Created by MaksimLiu on 2017/3/7.
 * <h3>日期工具类</h3>
 * <p>
 * <p>
 * 1.{@link #convertDateForApi(int, int, int)}将Calendar的时间信息转换为API请求的时间格式
 * 2.{@link #subDateForApi(Calendar)}当前日期减去一天用于API获取过往信息
 * </p>
 */

public class DateUtil {


    /**
     * 将Calendar的时间信息转换为API请求的时间格式
     *
     * @param year        年
     * @param monthOfYear 月
     * @param dayOfMonth  天
     * @return String
     */
    public static String convertDateForApi(int year, int monthOfYear, int dayOfMonth) {

        monthOfYear++;//月份从0开始
        String month;
        String day;

        // API 时间格式为20170305
        // 少于10时拼接0
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = monthOfYear + "";
        }
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = dayOfMonth + "";
        }

        return year + "" + month + day;
    }

    /**
     * 当前日期减去一天用于API获取过往信息
     *
     * @param calendar 当前日期信息
     * @return String
     */
    public static String subDateForApi(Calendar calendar) {

        //在当前日期减去一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return DateUtil.convertDateForApi(year, monthOfYear, dayOfMonth);

    }

}
