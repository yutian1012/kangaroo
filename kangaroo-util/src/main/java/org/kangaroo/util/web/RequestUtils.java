package org.kangaroo.util.web;

import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kangaroo.util.date.DateFormatUtils;

/**
 * 获取请求参数的工具类
 */
public class RequestUtils {
	//将请求信息放入到ThreadLocal中，即线程级别的变量
	private static ThreadLocal<HttpServletRequest> reqLocal = new ThreadLocal<HttpServletRequest>();
	//将响应信息放置到ThreadLocal中
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();
	
	//private static Logger logger = Logger.getLogger(RequestUtils.class);
	
	public static final String RETURNURL = "returnUrl";
	
	/**
	 * 在RequestFilter中设置request对象到ThradLocal中
	 * 使用完成后要清空ThreadLocal中的变量，节省空间
	 * @param request
	 */
	public static void setHttpServletRequest(HttpServletRequest request) {
		reqLocal.set(request);
	}
	public static void setHttpServletResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}
	/**
	 * 清空ThreadLocal中的变量
	 */
	public static void clearHttpReqResponse() {
		reqLocal.remove();
		responseLocal.remove();
	}
	/**
	 * 获取当前所在线程的Request对象
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) reqLocal.get();
	}
	public static HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) responseLocal.get();
	}
	/**
	 * 向request中设置值
	 * @param name
	 * @param value
	 */
	public static void setAttribute(String name,Object value){
		HttpServletRequest request=reqLocal.get();
		request.setAttribute(name, value);
	}
	
	/**
	 * 获取request中的参数变量值
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key,String defaultValue) {
		String value = request.getParameter(key);
		if (null!=value&&!"".equals(value))
			return value.trim();
		return defaultValue;	
	}

	public static String getString(HttpServletRequest request, String key) {
		return getString(request, key, "");
	}
	/**
	 * 获取数组值，数组值之间使用逗号分隔。
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getAry2String(HttpServletRequest request, String key) {
		String[] aryValue = request.getParameterValues(key);
		if ((aryValue == null) || (aryValue.length == 0)) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (String v : aryValue) {
			stringBuilder.append(v).append(",");
		}
		return stringBuilder.substring(0,stringBuilder.length()-1);
	}
	/**
	 * 过滤掉sql注入后所得的字符串
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getSecureString(HttpServletRequest request,String key, String defaultValue) {
		String value = request.getParameter(key);
		if (null==value||"".equals(value))
			return defaultValue;
		return filterInject(value);
	}
	public static String getSecureString(HttpServletRequest request, String key) {
		return getSecureString(request, key, "");
	}
	/**
	 * 过滤参数中的字符串，放置sql注入
	 * |正则中表示或的关系，拆分以上的关键字：|、;、exec、insert、select、delete、update、count、chr、truncate、char
	 * @param str
	 * @return
	 */
	public static String filterInject(String str) {
		String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
		Pattern regex = Pattern.compile(injectstr);

		Matcher matcher = regex.matcher(str);
		str = matcher.replaceAll("");//将符合条件的字符串值全部替换。
		str = str.replace("'", "''");
		str = str.replace("-", "—");
		str = str.replace("(", "（");
		str = str.replace(")", "）");
		str = str.replace("%", "％");

		return str;
	}
	/**
	 * 将字符串转成小写字母
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getLowercaseString(HttpServletRequest request,String key) {
		return getString(request, key).toLowerCase();
	}
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String key,int defaultValue) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return defaultValue;
		return Integer.parseInt(str);
	}
	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}
	
	/**
	 * request中取出参数，并转成
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(HttpServletRequest request, String key,long defaultValue) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return defaultValue;
		return Long.parseLong(str.trim());
	}
	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0L);
	}
	/**
	 * 将request中的字符串数组转成Long类型的数组
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (null==aryKey||aryKey.length==0)
			return null;
		Long[] aryLong = new Long[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryLong[i] = Long.valueOf(Long.parseLong(aryKey[i]));
		}
		return aryLong;
	}
	/**
	 * 将以逗号分隔的字符串转成Long数组
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return null;
		String[] aryId = str.split(",");
		Long[] lAryId = new Long[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			if (!"".equals(aryId[i])&&!"".equals(aryId[i].trim()))
				lAryId[i] = Long.valueOf(Long.parseLong(aryId[i]));
		}
		return lAryId;
	}
	/**
	 * 通过逗号分隔的字符串，拆分出对应的string数组
	 * @param request
	 * @param key
	 * @return
	 */
	public static String[] getStringAryByStr(HttpServletRequest request,String key) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return null;
		return str.split(",");
	}
	public static String[] getStringAry(HttpServletRequest request,String key) {
		String[] str = request.getParameterValues(key);
		if (null==str||str.length==0)
			return null;
		return str;
	}
	/**
	 * 通过String数组，转换成Integer数组
	 * @param request
	 * @param key
	 * @return
	 */
	public static Integer[] getIntAryFromStrArr(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (null==aryKey||aryKey.length==0)
			return null;
		Integer[] aryInt = new Integer[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			if(!"".equals(aryKey[i])&&!"".equals(aryKey[i].trim()))
				aryInt[i] = Integer.valueOf(Integer.parseInt(aryKey[i]));
		}
		return aryInt;
	}
	/**
	 * 通过String类型的数组，转换成Float类型的数组
	 * @param request
	 * @param key
	 * @return
	 */
	public static Float[] getFloatAryFromStrArr(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (null==aryKey||aryKey.length==0)
			return null;
		Float[] fAryId = new Float[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			if(!"".equals(aryKey[i])&&!"".equals(aryKey[i].trim()))
				fAryId[i] = Float.valueOf(Float.parseFloat(aryKey[i]));
		}
		return fAryId;
	}
	/**
	 * 获取float值
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getFloat(HttpServletRequest request, String key,float defaultValue) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return defaultValue;
		return Float.parseFloat(request.getParameter(key));
	}
	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0.0F);
	}
	
	/**
	 * 获取boolean值
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key,boolean defaultValue) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return defaultValue;
		if (isNumeric(str))//大于等于1的字符串数值，返回true
			return Integer.parseInt(str) >= 1;
		return Boolean.parseBoolean(str);
	}
	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, false);
	}
	/**
	 * 判断字符串是否是数值
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]+");  
	    return pattern.matcher(str).matches();     
	}
	
	/**
	 * 获取short值
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Short getShort(HttpServletRequest request, String key,Short defaultValue) {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return defaultValue;
		return Short.valueOf(Short.parseShort(str));
	}
	public static Short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, Short.valueOf((short) 0));
	}
	
	/**
	 * 获取日期
	 * @param request
	 * @param key
	 * @param style
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key,String style) throws ParseException {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return null;
		if (null==style||"".equals(style))
			style = "yyyy-MM-dd HH:mm:ss";//提供默认的一个类型
		return DateFormatUtils.parse(str, style);
	}
	/**
	 * 使用yyyy-MM-dd直接解析字符串
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key)throws ParseException {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return null;
		return DateFormatUtils.parseDate(str);
	}
	/**
	 * 获取时间戳yyyy-MM-dd HH:mm:ss.SSS
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getTimestamp(HttpServletRequest request, String key)
			throws ParseException {
		String str = request.getParameter(key);
		if (null==str||"".equals(str))
			return null;
		return DateFormatUtils.parseDateTime(str);
	}
	/**
	 * 解析出请求的URL字符串
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(request.getRequestURI());
		Enumeration<?> e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}
	/**
	 * 返回上页的链接url地址
	 * @param request
	 * @return
	 */
	public static String getPrePage(HttpServletRequest request) {
		if (null==request.getParameter("returnUrl")||"".equals(request.getParameter("returnUrl"))) {
			return request.getHeader("Referer");
		}
		return request.getParameter("returnUrl");
	}
	/**
	 * 将请求查询封装到map对象中
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getRequestParams(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> params = request.getParameterNames();//所有请求参数名
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);//获取数组
			if ((values.length > 0) && (null!=values[0]&&!"".equals(values[0]))) {
				if (values.length == 1) {//只有一个值时，从数组中解析出来
					String val = values[0].trim();
					if (!"".equals(val))
						map.put(key, val);
				} else {
					map.put(key, values);//将数组直接放在到map中
				}
			}
		}
		return map;
	}
	/**
	 * 获取Locale
	 * @param request
	 * @return
	 */
	public static Locale getLocal(HttpServletRequest request) {
		Locale local = request.getLocale();
		if (local == null)
			local = Locale.CHINA;
		return local;
	}

	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0)|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 特殊字符校验
	 * 不含特殊字符，则返回true
	 * @return
	 */
	public static boolean checkCharacter(String str){
		//正则表达式为不含特殊字符
		String regEx="[^`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+";
		Pattern regex = Pattern.compile(regEx);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}
}
