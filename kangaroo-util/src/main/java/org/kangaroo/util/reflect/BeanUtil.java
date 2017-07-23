package org.kangaroo.util.reflect;

import java.lang.reflect.Method;

public class BeanUtil {
	/**
	 * 得到指定的方法
	 * 
	 * @param clz 方法的类型
	 * @param methodName 方法的名字
	 * @param paramTypes 方法的参数列表
	 * @return
	 */
	public static Method getMethod(Class<?> clz, String methodName,Class<?>... paramTypes) {
		Method method = null;
		try {
			method = clz.getMethod(methodName, paramTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return method;
	}
	
}
