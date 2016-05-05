package cn.edu.pku.wechat.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {

	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 将日期转换为字符串
	 * @param date
	 * @return
	 */
	public static String parseToString(Date date){
		return DATE_FORMAT.format(date);
	}
	
	/**
	 * 将字符串转换为日期
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseToDate(String str) throws ParseException{
		return DATE_FORMAT.parse(str);
	}
}
