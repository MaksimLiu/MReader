package com.maksimliu.mreader.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MaksimLiu on 2017/3/7.
 * <h3>日期工具类</h3>
 * <p>
 * <p>
 * 1.{@link #convertDateForZhiHuApi(int, int, int)}将Calendar的时间信息转换为API请求的时间格式
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
    public static String convertDateForZhiHuApi(int year, int monthOfYear, int dayOfMonth) {

        monthOfYear++;//月份从0开始

        return convertDate(year) + "" + convertDate(monthOfYear) + convertDate(dayOfMonth);
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

        return DateUtil.convertDateForZhiHuApi(year, monthOfYear, dayOfMonth);

    }



    public static String convertDate(int date) {

        // API 时间格式为20170305
        // 少于10时拼接0

        if (date < 10) {
            return "0" + date;
        } else {
            return date + "";
        }
    }


}
