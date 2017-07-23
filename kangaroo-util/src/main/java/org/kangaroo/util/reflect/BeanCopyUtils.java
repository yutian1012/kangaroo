package org.kangaroo.util.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * bean对象值得拷贝操作
 */
public class BeanCopyUtils {
	private static Logger log=Logger.getLogger(BeanCopyUtils.class.getName());
	/**
	 * bean对象转成map集合
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(null!=value)
						map.put(key, value);
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "BeanCopyUtils is exception");
			e.printStackTrace();
		}

		return map;
	}
}
