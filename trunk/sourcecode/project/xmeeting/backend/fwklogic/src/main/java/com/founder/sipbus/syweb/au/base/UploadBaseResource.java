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

public class UploadBaseResource extends SyBaseResource {
	
	private long maxFileSize=4194304;//4m
	protected BasicAttachmentDaoImpl basicAttachmentDao;
	private String serverRootPath="/upload/";
	private String domain="normal";
	private String typeFilter=null;
	
//	private void getNowDomain()
//	{
//		String filePath = this.getHttpRequest().getSession().getServletContext().getRealPath("/");
//		
//		
//		
//	}
	private int arrayCompareIgnoreCase(String[] arrayString,String str)
	{
		for(int i=0;i<arrayString.length;i++)
		{
			String tmpStr=arrayString[i];
			if(tmpStr.equalsIgnoreCase(str))
			{
				return i;
			}
		}
		return -1;
	}
	
	
	//获得文件绝对路径 例如e:/tomcat/webapps/basicdata
	private String getFilePath()
	{
		String example="webapps";
		String filePath = this.getHttpRequest().getSession().getServletContext().getRealPath("/");
		//E:\tomcat7\webapps\basicdata\ 默认最后一个为域
		String[] arrayFilePath=filePath.split("\\\\");
		int pos=this.arrayCompareIgnoreCase(arrayFilePath,example);
		String fp=" ";
		if(pos!=-1)
		{
			for(int i=0;i<arrayFilePath.length;i++)
			{
				if(i==0)
				{
					fp=arrayFilePath[i];
				}
				else if(i!=pos)
				{
					fp+=File.separator+arrayFilePath[i];
				}
				else
				{
					fp+=File.separator+arrayFilePath[i]+File.separator+"upload";
				}
			}
		}
		else
		{
			fp="d:/upload";
		}
		return fp+"/";
//		String fileServerPath=null;
//		int times=this.occurTimes(filePath, "\\");
//		if(times>=4)//例如 
//		{
//			//String pathPrefix=
//			char[] ac=filePath.toCharArray();
//			int lac=ac.length;
//			String ms=String.copyValueOf(ac, 0, lac-1);	
//			String pathPrefix=ms.substring(0, ms.lastIndexOf("\\")); 
//			String[] mPath=filePath.split("\\\\");
//			String mDomain=mPath[3];
//			if(mDomain!=null&&mDomain!=this.domain)
//			{
//				this.domain=mDomain;
//			}
//			fileServerPath=pathPrefix+serverRootPath+this.domain+"/";
//		}
//		else
//		{
//			fileServerPath=filePath+serverRootPath+this.domain+"/";
//		}
//		return fileServerPath;
	}

	
	//获得网址的绝对路径  例如 http://www.123.com/basicdata
	private String getServerPath()
	{
		String example="webapps";
		String filePath = this.getHttpRequest().getSession().getServletContext().getRealPath("/");
		//E:\tomcat7\webapps\basicdata\ 默认最后一个为域
		String[] arrayFilePath=filePath.split("\\\\");
		int pos=this.arrayCompareIgnoreCase(arrayFilePath,example);
		String fp="";
		if(pos!=-1)
		{
			for(int i=pos;i<arrayFilePath.length;i++)
			{
				if(i!=pos)
				{
					fp+="/"+arrayFilePath[i];
				}
				else
				{
					fp+="/"+"upload";
				}
			}
		}
		else
		{
			fp="/upload";
		}
		return fp+"/";
//		String hostPath = this.getHttpRequest().getRequestURI();
//		String uploadWebPath=null;
//		int times=this.occurTimes(hostPath, "/");
//		if(times==3)//例如 /basicdata/rs/hrCertificatePhotoUpload
//		{
//			String[] mPath=hostPath.split("/");
//			String mDomain=mPath[1];
//			if(mDomain!=null&&mDomain!=this.domain)
//			{
//				this.domain=mDomain;
//			}
//			uploadWebPath=serverRootPath+this.domain+"/";
//		}
//		else if(times>=5)
//		{
//			String[] mPath=hostPath.split("/");
//			String mDomain=mPath[3];
//			if(mDomain!=null&&mDomain!=this.domain)
//			{
//				this.domain=mDomain;
//			}
//			uploadWebPath=serverRootPath+this.domain+"/";
//		}
//		else
//		{
//			uploadWebPath=serverRootPath+this.domain+"/";
//		}
//		return uploadWebPath;
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
//			String fileExt=oldFileName.substring(oldFileName.lastIndexOf("."));
//			String saveFileName=this.getRandFileName()+fileExt;
//			Long size=fi.getSize();
//			String path=this.getServerPath();
//			String filePath=this.getFilePath();
//			
//			String fileDomain=this.domain;
//			BasicAttachment ba=new BasicAttachment();
//			ba.setContentType(contentType);
//			ba.setDomain(fileDomain);
//			ba.setFileSize(size);
//			//ba.setMemo(memo)
//			ba.setPath(path);
//			ba.setSaveFileName(saveFileName);
//			ba.setUploadFileName(oldFileName);
//			
//			//this.basicAttachmentDao.add(ba);
//			File fullFile = new File(saveFileName);
//			File savedFile = new File(filePath, fullFile.getName());
//			fi.write(savedFile);
//			basicAttachmentDao.add(ba);
			BasicAttachment ba=this.saveSingleFile(fi);
			alba.add(ba);
		}
		return alba;
	}
	
	public BasicAttachment saveSingleFile(FileItem fi) throws Exception
	{
		String contentType=fi.getContentType();
		String oldFileName=fi.getName();
		String fileExt=oldFileName.substring(oldFileName.lastIndexOf("."));
		String saveFileName=this.getRandFileName()+fileExt;
		Long size=fi.getSize();
		String path=this.getServerPath();
		String filePath=this.getFilePath();
		
		String fileDomain=this.domain;
		BasicAttachment ba=new BasicAttachment();
		ba.setContentType(contentType);
		ba.setDomain(fileDomain);
		ba.setFileSize(size);
		//ba.setMemo(memo)
		ba.setPath(path);
		ba.setSaveFileName(saveFileName);
		ba.setUploadFileName(oldFileName);
		//this.basicAttachmentDao.add(ba);
		File fp=new File(filePath);
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
	
	/**
	 * 字符在字符串中出现的次数
	 * 
	 * @param string
	 * @param a
	 * @return
	 */
	private int occurTimes(String string, String a) {
	    int pos = -2;
	    int n = 0;
	 
	    while (pos != -1) {
	        if (pos == -2) {
	            pos = -1;
	        }
	        pos = string.indexOf(a, pos + 1);
	        if (pos != -1) {
	            n++;
	        }
	    }
	    return n;
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

	public String getTypeFilter()
	{
		return typeFilter;
	}

	public void setTypeFilter(String typeFilter)
	{
		this.typeFilter = typeFilter;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}
	
	
}