package cn.edu.pku.wechat.tools;


import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 请求工具类
 * @author Firefox
 *
 */
public class BeanTools {
	/**
	 * json字符串转换实体对象
	 * @param json 数据字符串
	 * @param type 转换类型
	 * @return 转换后的数据
	 */
	public static <T> T getModelByJson(String json, Class<T> type){
		JSONObject j = JSONObject.parseObject(json);
		return (T)JSONObject.toJavaObject(j, type); 
	}
	/**
	 * json字符串转换实体对象集合
	 * @param json  数据字符串
	 * @param type  转换类型
	 * @return 转换后的集合
	 */
	public static <T> List<T> getModelByJsonList(String text, Class<T> clazz){
		return JSONArray.parseArray(text, clazz);        
	}
}
