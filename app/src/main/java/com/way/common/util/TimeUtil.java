package com.way.common.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author way
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	public static String converTime(long time) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time / 1000;// 与现在时间相差秒数
		String timeStr = null;
		if(timeGap > 3*24 *60*60){
			timeStr = getDayTime(time) + " " + getMinTime(time);
		}
		else if (timeGap > 24 * 2 * 60 * 60) {// 2天以上就返回标准时间
			timeStr = "前天 " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60) {// 1天-2天
			timeStr = timeGap / (24 * 60 * 60) + "昨天 " + getMinTime(time);
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "今天 " + getMinTime(time);
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "今天 " + getMinTime(time);
		} else {// 1秒钟-59秒钟
			timeStr = "今天 " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getChatTime(long time) {
		return getMinTime(time);
	}

	public static String getPrefix(long time) {
		long currentSeconds = System.currentTimeMillis();
		long timeGap = currentSeconds - time;// 与现在时间差
		String timeStr = null;
		L.i(" " + (timeGap - (60 * 60 * 1000)));
		if (timeGap > 24 * 3 * 60 * 60 * 1000) {
			timeStr = getDayTime(time) + " " + getMinTime(time);
		} else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
			timeStr = "前天 " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60 * 1000) {
			timeStr = "昨天 " + getMinTime(time);
		} else {
			timeStr = "今天 " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getDayTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		return format.format(new Date(time));
	}

	public static String getMinTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		return format.format(new Date(time));
	}
}
