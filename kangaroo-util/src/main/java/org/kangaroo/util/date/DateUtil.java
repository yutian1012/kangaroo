package org.kangaroo.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date getDatePlusDay(int days){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}
	public static Date getDatePlusDay(Date date,int days){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}
	/**
	 * 获取日期距离当前的天数
	 * @param date
	 * @return
	 */
	public static int getDaysBetweenNow(Date date){
		Calendar c=Calendar.getInstance();
		Long now=c.getTimeInMillis();
		c.setTime(date);
		Long time=c.getTimeInMillis();
		Long between_days=(time-now)/(24*60*60*1000);
		return between_days.intValue();
	}
	
	public static Date getDatePlusMonth(int months){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}
	
	public static Date getDatePlusMonth(Date date,int months){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}
	/**
	 * 在已有的日期上，
	 * @param data
	 * @param days
	 * @param months
	 * @return
	 */
	public static Date addDaysAndMonth(Date date,int days,int months){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		c.add(Calendar.MONTH,months);
		return c.getTime();
	}
	
	public static Date getDatePlusYear(int year){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}
	
	public static Date getDatePlusYear(Date date,int year){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}
	
	public static Date getLastDayOfYear(){
		Calendar c=Calendar.getInstance();
		c.set(Calendar.MONTH, Calendar.DECEMBER);//12月
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		
		return c.getTime();
	}
	
	public static int getCurrentMonth(){
		Calendar c=Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}
	
	public static Date getLastHourOfDay(Date date){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
}
