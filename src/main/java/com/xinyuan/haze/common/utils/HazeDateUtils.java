package com.xinyuan.haze.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期时间辅助功能类
 * 
 * @author sofar
 * @see DateUtils
 */
public class HazeDateUtils extends DateUtils {

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static  Calendar cal = Calendar.getInstance();

	/**
	 * 获取当前日期时间
	 * @return Date 当前日期时间
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        return sdf.format(date);
    }
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static int getYear(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getWeek(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getDay(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断日期为周几
     * @param date 日期时间
     * @return 周日为1，以此类推
     */
    public static int getDayInWeek(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取两个日期差天数.
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return 排除周六周日后的天数
     */
    public static int getDiff(Date startTime, Date endTime) {
        long diff = endTime.getTime() - startTime.getTime();
        int hours = (int) (diff/(1000*60*60));
        int days = 0;
        if (hours % 24 != 0) {
            days = hours/24 + 1;
        } else {
            days = hours/24;
        }
        int newDays = 0;
        for (int i=0; i < days; i++) {
            Date newDate = addDays(startTime, i);
            //判断该天是周几
            int weekDay = getDayInWeek(newDate);
            //如果不是周日和周六
            if (weekDay != 1 && weekDay != 7) {
                newDays += 1;
            }
        }
        return newDays != 0 ? newDays : 1;
    }
}
