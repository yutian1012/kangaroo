package org.kangaroo.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.kangaroo.util.date.DateFormatUtils;
/**
 * 导出excel文档
 */
public class ExportUtil {
	/**
	 * 导出excel
	 * @param title
	 * @param headers
	 * @param data 每行记录放置到一个map对象中
	 * @param field
	 * @param out
	 */
	public static void exportExcel(String title, String[] headers,List<Map<String,Object>> data,String[] field, OutputStream out) {
		// 声明一个工作薄
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    // 生成一个表格
	    HSSFSheet sheet = workbook.createSheet(title);
	    //设置表格默认列宽度为20个字节	
	    sheet.setDefaultColumnWidth(20);
	    // 生成标题样式
	    HSSFCellStyle headStyle =getHeadStyle(workbook);
	    //生成正文样式
	    HSSFCellStyle contentStyle=getContentStyle(workbook);
	    
	    //生成标题行
	    HSSFRow row = sheet.createRow(0);//创建一行
	    for (int i = 0; i < headers.length; i++) {//headers为指定生产的标题行的头部
	         HSSFCell cell = row.createCell(i);//在行中生成单元格
	         cell.setCellStyle(headStyle);//设置单元格的样式
	         HSSFRichTextString text = new HSSFRichTextString(headers[i]);//为文本应用丰富的样式
	         cell.setCellValue(text);//设置单元格值
	    }
	    
	    //展示内容
	    if(null!=data&&data.size()>0){
	    	for(int index=0;index<data.size();index++){
	    		Map<String,Object> rowMap=data.get(index);
	    		row=sheet.createRow(index+1);
	    		for(int i=0;i<field.length;i++){
	    			HSSFCell cell = row.createCell(i);//在行中生成单元格
	    			if(i==0){
	    				cell.setCellStyle(headStyle);
	    				cell.setCellValue(rowMap.get(field[i]).toString());
	    			}else{
	    				cell.setCellStyle(contentStyle);//设置单元格的样式
	    				cell.setCellValue(Double.parseDouble(rowMap.get(field[i]).toString()));
	    			}
	   	         	//HSSFTextbox text = new HSSFRichTextString(rowMap.get(field[i]).toString());//为文本应用丰富的样式
	    		}
	    	}
	    }
	    
	    //输出内容
	    try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 标题行样式
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle getHeadStyle(HSSFWorkbook workbook){
		HSSFCellStyle headStyle = workbook.createCellStyle();
	    headStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居中显示内容
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short)12);//字体大小
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体
	    // 把字体应用到当前的样式
	    headStyle.setFont(font);
	    return headStyle;
	}
	/**
	 * 单元格样式
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle getContentStyle(HSSFWorkbook workbook){
		HSSFCellStyle contentStyle = workbook.createCellStyle();
	    contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
	    // 生成另一个字体
	    HSSFFont font2 = workbook.createFont();
	    font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    // 把字体应用到当前的样式
	    contentStyle.setFont(font2);
	    return contentStyle;
	}
	/**
	 * 导出记录到excel文档中
	 * @param c
	 * @param field
	 * @param out
	 * @param data
	 * @throws Exception 
	 */
	public static <T> void exportExcel(Class<T> c,String[] field, OutputStream out,List<T> data,String title,String[] headers) throws Exception{
		// 声明一个工作薄
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    // 生成一个表格
	    HSSFSheet sheet = workbook.createSheet(title);
	    //设置表格默认列宽度为20个字节	
	    sheet.setDefaultColumnWidth(20);
	    // 生成标题样式
	    HSSFCellStyle headStyle =getHeadStyle(workbook);
	    //生成正文样式
	    HSSFCellStyle contentStyle=getContentStyle(workbook);
	    
	    Method[] getMethod=getOrSetMethod(field,"get",c);
        Field[] fieldTypes=getFiledTypes(c,field);
	  //生成标题行
	    HSSFRow row = sheet.createRow(0);//创建一行
	    for (int i = 0; i < headers.length; i++) {//headers为指定生产的标题行的头部
	         HSSFCell cell = row.createCell(i);//在行中生成单元格
	         cell.setCellStyle(headStyle);//设置单元格的样式
	         HSSFRichTextString text = new HSSFRichTextString(headers[i]);//为文本应用丰富的样式
	         cell.setCellValue(text);//设置单元格值
	    }
	  //展示内容
	    if(null!=data&&data.size()>0){
	    	for(int index=0;index<data.size();index++){
	    		T model=data.get(index);//获取对象
	    		row=sheet.createRow(index+1);
	    		for(int i=0;i<field.length;i++){
	    			Object obj=null;
	    			HSSFCell cell = row.createCell(i);//在行中生成单元格
	    			if(fieldTypes[i].getType()==double.class||fieldTypes[i].getType()==Double.class){
	    				cell.setCellStyle(contentStyle);//设置单元格的样式
	    				obj=getMethod[i].invoke(model);
	    				if(null!=obj)
	    					cell.setCellValue((Double)obj);
                	}
                	else if(fieldTypes[i].getType()==float.class||fieldTypes[i].getType()==Float.class){
                		cell.setCellStyle(contentStyle);//设置单元格的样式
                		obj=getMethod[i].invoke(model);
                		if(null!=obj)
                			cell.setCellValue((Float)obj);
                	}
                	else if(fieldTypes[i].getType()==int.class||fieldTypes[i].getType()==Integer.class){
                		cell.setCellStyle(contentStyle);//设置单元格的样式
                		obj=getMethod[i].invoke(model);
                		if(null!=obj)
                			cell.setCellValue((Integer)obj);
                	}
                	else if(fieldTypes[i].getType()==Date.class){
                		obj=getMethod[i].invoke(model);
                		if(null!=obj){
                			String date=DateFormatUtils.format((Date)obj, "yyyy-MM-dd");
                			cell.setCellStyle(contentStyle);//设置单元格的样式
                			cell.setCellValue(date);
                		}
                	}
                	else{//字符串类型
                		cell.setCellStyle(contentStyle);//设置单元格的样式
                		obj=getMethod[i].invoke(model);
                		if(null!=obj)
                			cell.setCellValue(obj.toString());
                	}
	    			
	    		}
	    	}
	    }
	    
	    //输出内容
	    try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取对象中的get和set方法，boolean类型也使用set方法，而不是使用is开头的方法
	 * 注：需要为相应的boolean类型提供set方法
	 * @param field
	 * @param type
	 * @return
	 */
	private static Method[] getOrSetMethod(String[] field,String type,Class<?> c)throws Exception{
		Method[] temp=new Method[field.length];
		for(int i=0;i<field.length;i++){
			String methodName=type+ field[i].substring(0, 1).toUpperCase() + field[i].substring(1);
			if(type.equals("set")){
				Field f=c.getDeclaredField(field[i]);
				temp[i]=c.getDeclaredMethod(methodName,f.getType());
			}
			else{
				temp[i]=c.getDeclaredMethod(methodName,new Class[]{});
			}
		}
		return temp;
	}
	/**
	 * 获取字段的类型
	 * @param c
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private static Field[] getFiledTypes(Class<?> c,String[] field)throws Exception{
		Field[] temp=new Field[field.length];
		for(int i=0;i<field.length;i++){
			Field f=c.getDeclaredField(field[i]);
			temp[i]=f;
		}
		return temp;
	}
}
