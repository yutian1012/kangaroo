package org.kangaroo.util.web;

import javax.servlet.ServletContext;

/**
 * 获取项目路径
 * 
 * @author Administrator
 * 
 */
public class AppPathUtil {
	private static ServletContext servletContext;

	public static void init(ServletContext _servletContext) {
		servletContext = _servletContext;
	}

	public static String getAppAbsolutePath() {
		return servletContext.getRealPath("/");
	}

	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	public static String getContextPath() {
		return servletContext.getContextPath();
	}
	
	public static String getContextName(){
		return servletContext.getContextPath().substring(1);
	}

}