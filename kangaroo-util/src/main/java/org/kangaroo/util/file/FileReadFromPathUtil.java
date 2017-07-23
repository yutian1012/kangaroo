package org.kangaroo.util.file;

import java.io.File;
import java.util.List;

/**
 * 
 * 文件读取类
 */
public class FileReadFromPathUtil {
	public static final String XLS="xls";
	public static final String XLSX="xlsx";
	public static final String RAR="rar";
	public static final String ZIP="zip";
	/**
	 * 根据文件的后缀名读取文件夹中的文件路径
	 * @param ext
	 * @return
	 */
	public static String getFilePathBySuffix(String path,String ext){
		if(null!=path&&!"".equals(path)){
			File file=new File(path);
			if(file.exists()&&file.isDirectory()){
				File[] files=file.listFiles();
				if(null!=files&&files.length>0){
					for(File f:files){
						if(f.isFile()){
							if(ext.equals(FileTypeUtil.getFileTypeByFile(f)))
								return f.getAbsolutePath();
						}
						else{
							//递归
							return getFilePathBySuffix(f.getAbsolutePath(),ext);
						}
					}
				}
			}
		}
		return null;
	}
	
	public static File getFileBySuffix(String path,String ext){
		if(null!=path&&!"".equals(path)){
			File file=new File(path);
			if(file.exists()&&file.isDirectory()){
				File[] files=file.listFiles();
				if(null!=files&&files.length>0){
					for(File f:files){
						if(f.isFile()){
							if(ext.equals(FileTypeUtil.getFileTypeByFile(f)))
								return f;
						}
						else{
							//递归
							return getFileBySuffix(f.getAbsolutePath(),ext);
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据文件的后缀名读取文件夹中的文件路径
	 * @param ext
	 * @return
	 */
	public static void getFileListBySuffix(List<String> fileList,String path,String ext){
		if(null!=path&&!"".equals(path)){
			File file=new File(path);
			if(file.exists()&&file.isDirectory()){
				File[] files=file.listFiles();
				if(null!=files&&files.length>0){
					for(File f:files){
						if(f.isFile()&&ext.equals(FileTypeUtil.getFileTypeByFile(f))){
							fileList.add(f.getAbsolutePath());
						}
						else{
							//递归
							getFileListBySuffix(fileList,f.getAbsolutePath(),ext);
						}
					}
				}
			}
		}
	}
	/**
	 * 获取不带文件后缀的文件名
	 * @param fileName
	 */
	public static String getFileNameWithoutSuffix(String fileName){
		if(null!=fileName&&!"".equals(fileName)){
			if(fileName.lastIndexOf(".")!=-1){
				return fileName.substring(0,fileName.lastIndexOf("."));
			}
		}
		return fileName;
	}
	/**
	 * 根据文件名获取指定路径下的文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File getFileByName(String path,String fileName){
		if(null!=path&&!"".equals(path)){
			File file=new File(path);
			if(file.exists()&&file.isDirectory()){
				File[] files=file.listFiles();
				if(null!=files&&files.length>0){
					for(File f:files){
						if(f.isFile()){
							if(fileName.equals(getFileNameWithoutSuffix(f.getName())))
								return f;
						}
						else{
							//递归
							return getFileByName(f.getAbsolutePath(),fileName);
						}
					}
				}
			}
		}
		return null;
	}
}
