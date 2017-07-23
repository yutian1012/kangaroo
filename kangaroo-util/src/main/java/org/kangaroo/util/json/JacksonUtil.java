package org.kangaroo.util.json;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * json数据解析
 */
public class JacksonUtil {
	/**
	 * 解析一个对象，输出json字符串
	 * @param obj，对象可以是Bean，list，map等数据结构
	 * @return
	 */
	public static String parseObjectToString(Object obj){
		ObjectMapper mapper=new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> readJson2Map(String json){
		ObjectMapper mapper=new ObjectMapper();
		try {
			return mapper.readValue(json, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
