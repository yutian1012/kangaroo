package org.kangaroo.util.file;

import java.io.File;

public class DirectoryUtil {
	/**
	 * 判断路径是否存在
	 * @param filePath 文件路径
	 * @return
	 */
	public static boolean isFileDirectoryExists(String filePath){
		String directory="";
		if(filePath.indexOf(File.separator)!=-1){
			directory=filePath.substring(0,filePath.lastIndexOf(File.separator));
		}else if(filePath.indexOf("/")!=-1){
			directory=filePath.substring(0,filePath.lastIndexOf("/"));
		}
		if(!"".equals(directory)){
			//判断路径是否存在
			File file=new File(directory);
			return file.exists();
		}
		return false;
	}
	/**
	 * 创建文件路径
	 * @param filePath 文件路径
	 */
	public static void mkFileDirectory(String filePath){
		String directory="";
		if(filePath.indexOf(File.separator)!=-1){
			directory=filePath.substring(0,filePath.lastIndexOf(File.separator));
		}else if(filePath.indexOf("/")!=-1){
			directory=filePath.substring(0,filePath.lastIndexOf("/"));
		}
		if(!"".equals(directory)){
			//判断路径是否存在
			File file=new File(directory);
			if(!file.exists()){
				file.mkdirs();
			}
		}
	}
}
