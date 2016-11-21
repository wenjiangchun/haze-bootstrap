package com.xinyuan.haze.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 字符串辅助类
 * 
 * @author sofar
 * @see StringUtils 
 */
public class HazeStringUtils extends StringUtils {

	/**
	 * 通过正则表达式获取字符串集合的值
	 * 
	 * @param obj 字符串集合
	 * @param regex 表达式
	 * 
	 * @return List
	 */
	public static List<String> getValue(Collection<String> obj,String regex){

        List<String> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(obj)) {
			return result;
		}
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(StringUtils.join(obj, ","));
		for (String s : obj) {
			result.add("perms[" + s + "]");
		}
		while(matcher.find()){
        	result.add(matcher.group(1));
        }
		return result;
	}
}
