package com.xinyuan.haze.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期时间辅助功能类
 * 
 * @author sofar
 * @see DateUtils
 */
public class HazeDateUtils extends DateUtils {

	public static final String PATTERN = "yyyy年MM月dd日  HH:mm:ss";

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
}
