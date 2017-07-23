package org.kangaroo.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩工具 ，zip文件的压缩和解压
 */
public class ZipUtil {
	private static final int BUFFEREDSIZE=1024;
	
	public synchronized static void unzipFile(String zipFileName) throws Exception {
		File f = new File(zipFileName);//读入文件
		ZipFile zipFile = new ZipFile(zipFileName);//将文件包装成zip压缩的文件
		try {
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(f.getParent());//获取文件所在的目录
			strPath = tempFile.getAbsolutePath();
			Enumeration<? extends ZipEntry> e = zipFile.entries();//获取压缩包中的实体文件
			while (e.hasMoreElements()) {
				ZipEntry zipEnt = (ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + "/" + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else {
					// 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					gbkPath = zipEnt.getName();
					strtemp = strPath + "/" + gbkPath;
					// 建目录
					String strsubdir = gbkPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
							String temp = strPath + "/"+ strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
						}
					}
					FileOutputStream fos = new FileOutputStream(strtemp);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int c;
					while ((c = bis.read()) != -1) {
						bos.write((byte) c);
					}
					bos.close();
					fos.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != zipFile) {
				zipFile.close();
			}
		}
	}
	
	/** 
     * 解压zip格式的压缩文件到指定位置 
     * @param zipFileName 压缩文件 
     * @param extDir 解压目录 
     * @throws Exception 
     */  
    public synchronized static void unzip(String zipFileName, String extDir) throws Exception {  
    	(new File(extDir)).mkdirs();//创建目录
    	File f = new File(zipFileName);
    	//System.out.println(Charset.defaultCharset());
    	ZipFile zipFile = new ZipFile(zipFileName,Charset.defaultCharset());
    	/*ZipFile zipFile = new ZipFile(zipFileName,Charset.forName("UTF-8"));
    	System.out.println(Charset.forName("UTF-8").name());*/
        try {  
            if((!f.exists()) && (f.length() <= 0)) {  
                throw new Exception("要解压的文件不存在!");  
            }  
            String strPath, gbkPath, strtemp;  
            File tempFile = new File(extDir);
            strPath = tempFile.getAbsolutePath();  
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            while(e.hasMoreElements()){
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath=zipEnt.getName();
                if(zipEnt.isDirectory()){
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    //读写文件  
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath=zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;
                    //建目录  
                    String strsubdir = gbkPath;
                    for(int i = 0; i < strsubdir.length(); i++) {
                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if(!subdir.exists())
                            subdir.mkdir();
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
			if (null != zipFile) {
				zipFile.close();
			}
		}
    }
    
    

	/**
	 * 解压jar包
	 * @param fileName
	 * @param directory
	 */
	public synchronized static void unjar(String fileName, String directory) {
		//构建解压目录
		directory=buildDestDir(directory);
		if(null==buildDestDir(directory)) return;
		
		BufferedOutputStream out = null;
		JarInputStream jarIn = null;
		try {
			JarEntry jar = null;
			jarIn = new JarInputStream(new FileInputStream(fileName));
			while ((jar = jarIn.getNextJarEntry()) != null) {
				String name = jar.getName();
				if (jar.isDirectory()) {
					name = name.substring(0, name.length() - 1);
					File file = new File(directory + name);
					file.mkdir();
				} else {
					if (name.lastIndexOf("/") != -1) {
						String fname = directory+ jar.getName().substring(0,jar.getName().lastIndexOf("/"));
						File dt = new File(fname);
						if (!dt.exists()) {
							dt.mkdirs();
						}
					}
					File file = new File(directory + jar.getName());
					file.createNewFile();
					out = new BufferedOutputStream(new FileOutputStream(file));
					int b;
					while ((b = jarIn.read()) != -1) {
						out.write(b);
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != jarIn) {
				try {
					jarIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 构建目录
	 * @param destDir
	 */
	private static String buildDestDir(String destDir){
		if(null!=destDir&&!"".equals(destDir)){
			File file=new File(destDir);
			if(file.isDirectory()&&!file.exists()) file.mkdirs();
			return file.getAbsolutePath()+File.separator;
		}
		return null;
	}
	
	/** 
     * 压缩zip格式的压缩文件 
     * @param inputFilename 压缩的文件或文件夹及详细路径 
     * @param zipFilename 输出文件名称及详细路径 
     * @throws IOException 
     */  
    public synchronized static void zip(String inputFilename, String zipFilename) throws IOException { 
        zip(new File(inputFilename), zipFilename);  
    } 
    
    /** 
     * 压缩zip格式的压缩文件 
     * @param inputFile 需压缩文件 
     * @param zipFilename 输出文件及详细路径 
     * @throws IOException 
     */  
    public synchronized static void zip(File inputFile, String zipFilename) throws IOException {  
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));  
        try {  
            zip(inputFile, out, "");  
        } catch (IOException e) {  
            throw e;  
        } finally {  
            out.close();  
        }  
    }
    
    /** 
     * 压缩zip格式的压缩文件 
     * @param inputFile 需压缩文件 
     * @param out 输出压缩文件 
     * @param base 结束标识 
     * @throws IOException 
     */  
    private synchronized static void zip(File inputFile, ZipOutputStream out, String base) throws IOException {  
        if (inputFile.isDirectory()) {  
            File[] inputFiles = inputFile.listFiles();  
            out.putNextEntry(new ZipEntry(base + "/"));  
            base = base.length() == 0 ? "" : base + "/";  
            for (int i = 0; i < inputFiles.length; i++) {  
                zip(inputFiles[i], out, base + inputFiles[i].getName());  
            }  
        } else {
            if (base.length() > 0) {  
                out.putNextEntry(new ZipEntry(base));  
            } else {  
                out.putNextEntry(new ZipEntry(inputFile.getName()));  
            }  
            FileInputStream in = new FileInputStream(inputFile);  
            try {  
                int c;  
                byte[] by = new byte[BUFFEREDSIZE];  
                while ((c = in.read(by)) != -1) {
                    out.write(by, 0, c);  
                }  
            } catch (IOException e) {  
                throw e;  
            } finally {  
                in.close();  
            }  
        }  
    }
}
