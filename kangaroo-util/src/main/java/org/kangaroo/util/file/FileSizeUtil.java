package org.kangaroo.util.file;

import java.text.DecimalFormat;

public class FileSizeUtil {
	public static final String UNITB="B";//字节
	public static final String UNITKB="KB";//千字节
	public static final String UNITMB="MB";//兆字节
	public static final String UNITGB="GB";//千兆字节
	
	public static String getFileSize(long fileLength,String unit){
		StringBuilder sb=new StringBuilder();
		DecimalFormat df = new DecimalFormat("###.00");
		if(UNITB.equals(unit)){
			sb.append(fileLength).append(UNITB);
			return sb.toString();
		}
		else if(UNITKB.equals(unit)){
			//小数点后省略两位
			double size=fileLength/1024*1.0;
			sb.append(df.format(size)).append(UNITKB);
		}
		else if(UNITMB.equals(unit)){
			double size=fileLength/1024/1024*1.0;
			sb.append(df.format(size)).append(UNITMB);
		}else{
			double size=fileLength/1024/1024/1024*1.0;
			sb.append(df.format(size)).append(UNITGB);
		}
		return sb.toString();
	}
}
