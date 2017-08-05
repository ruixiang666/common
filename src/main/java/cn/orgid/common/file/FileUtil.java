package cn.orgid.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	public static String getExName(String name){
		
		if(name==null)
			return "";
		if(name.lastIndexOf(".")<0){
			return "";
		}
		int i = name.lastIndexOf(".");
		return name.substring(i, name.length());
	}
	
	public static String getName(String fullName){
		
		if(fullName==null)
			return "";
		if(fullName.lastIndexOf(".")<0){
			return fullName;
		}
		int i = fullName.lastIndexOf(".");
		return fullName.substring(0, i);
		
	}
	
	public static String getMediumName(String file){
		
		return getName(file)+"_m"+getExName(file);
	}
	
	public static String getSmallName(String file){
		
		return getName(file)+"_s"+getExName(file);
	}
	
	public static String create(String path,byte[] bs,String ext){
		
		Date d = new Date();
		String p = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/").format(d);
		
		FileOutputStream os=null;
		File f1=null;
		String fileName=p+UUID.randomUUID().toString()+ext;
		try {
			long  t1 = System.currentTimeMillis();
			new File(path+p).mkdirs();
			long t2 = System.currentTimeMillis();
			System.out.println("mkdirs time cost:" +(t2-t1));
			System.out.println(path+fileName);
			f1 = new File(path+fileName);
			os = new FileOutputStream(f1);
			os.write(bs);
			os.flush();
			long t3 = System.currentTimeMillis();
			System.out.println("write file time cost:" +(t3-t2));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			if(os!=null){
				try {
					long t =System.currentTimeMillis();
					os.close();
					System.out.println("close file cost:"+(System.currentTimeMillis()-t));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileName;
		
	}
	
	
	public static String create(String path,String localPath,byte[] bs,String ext){
		
		long t0 =System.currentTimeMillis();
		Date d = new Date();
		String p = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/").format(d);
		
		FileOutputStream os=null;
		
		String fileName=UUID.randomUUID().toString()+ext;
		try {
			File f = new File(path+p);
			if(!f.exists()){
				f.mkdirs();
			}
			os = new FileOutputStream(new File(localPath+"/"+fileName));
			os.write(bs);
			os.flush();
			Runtime r = java.lang.Runtime.getRuntime();
			r.exec("cp "+ localPath+"/"+fileName + " " +path+p);
			r.exec("rm "+localPath+"/"+fileName);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			if(os!=null){
				try {
					long t =System.currentTimeMillis();
					os.close();
					System.out.println("close file cost:"+(System.currentTimeMillis()-t));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("create file total time cost:"+(System.currentTimeMillis()-t0));
		return fileName;
		
	}
	

	
	
	
	public static void main(String[] args) {
		
		long t1 = System.currentTimeMillis();
		new java.io.File("").mkdirs();
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		System.out.println(FileUtil.getExName("tets.jpg"));
		System.out.println(FileUtil.getMimeTypeName("/ss/stets"));
		//System.out.println(FileUtil.getExName(".jpg"));
		
	}

	public static byte[] getFileData(String uploadFilePath, String f) {
		
		byte[] bs =null;
		InputStream is =null;
		try {
			is = new FileInputStream(uploadFilePath+f);
			is.read(bs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bs;
	}
	
	public  static String getMimeTypeName(String name){
		String ext = getExName(name);
		if(ext.indexOf(".")==0&&ext.length()>1){
			ext=ext.substring(1, ext.length());
			return MimeType.valueOf(ext).getMime();
		}else{
			return MimeType.unknow.getMime();
		}
		
	}
	
	public enum MimeType{
		
		bmp("application/x-bmp"),
		jpe("image/jpeg"),
		jpeg("image/jpeg"),
		jpg("application/x-jpg"),
		tif("image/tiff"),
		png("application/x-png"),
		gif("image/gif"),
		unknow("application/octet-stream");
		
		String mime;
		
		
		public String getMime() {
			return mime;
		}


		public void setMineType(String mimeType) {
			this.mime = mimeType;
		}


		MimeType(String mime){
			this.mime=mime;
		}
		
	}
	
	
}
