package com.founder.sipbus.syweb.au.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;

import com.founder.sipbus.syweb.au.dao.BasicAttachmentDaoImpl;
import com.founder.sipbus.syweb.au.po.BasicAttachment;

public class BaseUploadResource extends SyBaseResource {
	
	private long maxFileSize=4194304;//4m
	protected BasicAttachmentDaoImpl basicAttachmentDao; 
	private String domain="normal"; 
	private String webappDir="";
	private String uploadSubDir="upload";
	private String domainSubDir="";
	private String functionSubDir="";
	
	
	//获得文件绝对路径 例如e:/tomcat/webapps/basicdata
	public void initDir()
	{
		String filePath = this.getHttpRequest().getSession().getServletContext().getRealPath("/");
		//E:\tomcat7\webapps\basicdata\ 默认最后一个为域
		System.out.println("filePath------>"+filePath+"     File.separator---->"+File.separator);
		String[] arrFilePath=filePath.split("\\\\");
		int length=arrFilePath.length;
		String pathx=arrFilePath[length-1]; 
		this.domainSubDir=pathx;
		String strWebAppPath="";
		for(int i=0;i<length-1;i++){
			String pathy=arrFilePath[i];
			strWebAppPath+=pathy+"\\";
		}
		this.webappDir=strWebAppPath; 
	}
	
	
	protected String getSavedFileDir(){
		String savedFilePath=webappDir+this.uploadSubDir+"\\"+this.domainSubDir+"\\"+functionSubDir+"\\"; 
		//System.out.println("savedFilePath------->"+savedFilePath);
		return savedFilePath;
		
	}
	
	protected String getHttpAccessPath(){  
		String httpAccessPath="/"+uploadSubDir+"/"+this.domainSubDir+"/"+functionSubDir+"/"; 
		//System.out.println("httpAccessPath------->"+httpAccessPath);
		return httpAccessPath;
		
	}
  
	




	public List<FileItem> getUploadFiles(Representation entity) throws FileUploadException {
		List<FileItem> items=null;
		if (entity != null) {
			if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),
					true)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096);
				
				RestletFileUpload upload = new RestletFileUpload(factory);
				//upload.setSizeMax(maxFileSize*4); // 设置最大文件尺寸，这里是4*4=16MB
				items = upload.parseRequest(getRequest());
			}
		}
		return items;
	}
	
	public FileItem getUploadSingleFile(Representation entity) throws FileUploadException  {
		List<FileItem> items=null;
		FileItem fItem=null;
		if (entity != null) {
			if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),
					true)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096);
				RestletFileUpload upload = new RestletFileUpload(factory);
				//upload.setSizeMax(maxFileSize); // 设置最大文件尺寸，这里是4MB
				items = upload.parseRequest(getRequest());
				if(!items.isEmpty())
				{
					fItem=items.get(0);
				}
			}
		}
		return fItem;
	}	
	
	//返回对应保存之后的file list对象
	public List<BasicAttachment> saveAllFiles(List<FileItem> lfi) throws Exception
	{
		List<BasicAttachment> alba=new ArrayList<BasicAttachment>();
		int ll=lfi.size();
		for(int i=0;i<ll;i++)
		{
			FileItem fi=lfi.get(i);
//			String contentType=fi.getContentType();
//			String oldFileName=fi.getName();
//			if(null==oldFileName){//swf upload 为null
//				oldFileName=fi.getString();
//			} 
//			String fileExt=oldFileName.substring(oldFileName.lastIndexOf("."));
//			String saveFileName=this.getRandFileName()+fileExt;
//			Long size=fi.getSize();
//			String httpAccessPath=this.getHttpAccessPath();
//			String savedFilePath=this.getSavedFileDir();
//			
//			String fileDomain=this.domain;
//			BasicAttachment ba=new BasicAttachment();
//			ba.setContentType(contentType);
//			ba.setDomain(fileDomain);
//			ba.setFileSize(size);
//			//ba.setMemo(memo)
//			ba.setPath(httpAccessPath);
//			ba.setSaveFileName(saveFileName);
//			ba.setUploadFileName(oldFileName);
//			
////			this.basicAttachmentDao.add(ba);
//			File fullFile = new File(saveFileName);
//			File savedFile = new File(savedFilePath, fullFile.getName());
//			fi.write(savedFile);
//			basicAttachmentDao.add(ba);
			BasicAttachment ba=saveSingleFile(fi);
			alba.add(ba);
		}
		return alba;
	}
	
	public BasicAttachment saveSingleFile(FileItem fi) throws Exception
	{ 
		String contentType=fi.getContentType();
		String oldFileName=fi.getName(); 
		if(null==oldFileName){//swf upload 为null
			oldFileName=fi.getString();
		} 
		String fileExt=oldFileName.substring(oldFileName.lastIndexOf(".")); 
		String randname=getRandFileName(); 
		String saveFileName=randname+fileExt; 
		Long size=fi.getSize();
		String httpAccessPath=this.getHttpAccessPath();
		String saveFileDir=this.getSavedFileDir();
		
		String fileDomain=this.domain;
		BasicAttachment ba=new BasicAttachment();
		ba.setContentType(contentType);
		ba.setDomain(fileDomain);
		ba.setFileSize(size);
		//ba.setMemo(memo)
		ba.setPath(httpAccessPath);
		ba.setSaveFileName(saveFileName);
		ba.setUploadFileName(oldFileName);
//		this.basicAttachmentDao.add(ba);
		File fp=new File(saveFileDir);
		File fullFile = new File(saveFileName);
		if (!fp.exists()) 
		{
			fp.mkdirs();
		}
		File savedFile = new File(fp, fullFile.getName());
		if (!savedFile.exists()) 
		{
			savedFile.createNewFile();
		}

		fi.write(savedFile);
		basicAttachmentDao.add(ba);
		return ba;
	}
	
	
	
	private String getRandFileName()
	{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dateFormat.format(now);
		Long randLong=Math.round(Math.random()*899999999+100000000);
		String randStr=randLong.toString();
		String fileName=dateStr+randStr;
		return fileName;
	}

	public void setBasicAttachmentDao(BasicAttachmentDaoImpl basicAttachmentDao)
	{
		this.basicAttachmentDao = basicAttachmentDao;
	}

	public long getMaxFileSize()
	{
		return maxFileSize;
	}

	public void setMaxFileSize(long maxFileSize)
	{
		this.maxFileSize = maxFileSize;
	} 

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}


	public String getFunctionSubDir() {
		return functionSubDir;
	}


	public void setFunctionSubDir(String functionSubDir) {
		this.functionSubDir = functionSubDir;
	}
	
	
}