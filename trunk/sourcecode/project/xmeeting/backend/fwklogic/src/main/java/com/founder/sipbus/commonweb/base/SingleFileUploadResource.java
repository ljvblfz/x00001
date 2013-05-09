package com.founder.sipbus.commonweb.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.sipbus.fwk.config.Environment;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

public class SingleFileUploadResource extends SyBaseResource {
	private Logger logger = LoggerFactory.getLogger(SingleFileUploadResource.class);

	public final static String UPLOAD_BASE_DIRECTORY = "com.broadsoft.platform.upload.base.directory";

	public final static String UPLOAD_BASE_URL = "com.broadsoft.platform.upload.base.url";

	public String getRandomFileNamePrefix() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dateFormat.format(now);
		Long randLong = Math.round(Math.random() * 899999999 + 100000000);
		String randStr = randLong.toString();
		String fileName = dateStr + randStr;
		return fileName;
	}

	public String getBaseUplodDirectory() {
		String baseDir = Environment.getInstance().getString(
				UPLOAD_BASE_DIRECTORY);
		return baseDir;

	}

	public String getBaseUplodUrl() {
		String baseUrl = Environment.getInstance().getString(UPLOAD_BASE_URL);
		return baseUrl;

	}
	
	public FileItem getUploadSingleFileItem(Representation entity)   { 
		FileItem fItem=null;
		if (entity != null) {
			if(logger.isTraceEnabled()){
				logger.trace("The entity's media type {}.",entity.getMediaType());
			} 
			if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),true)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024*4);
				RestletFileUpload upload = new RestletFileUpload(factory);
				upload.setSizeMax(1024*1024*10); // 设置最大文件尺寸，这里是4MB
				List<FileItem> items =null;
				try {
					items = upload.parseRequest(getRequest());
				} catch (FileUploadException e) { 
					e.printStackTrace();
				} 
				if(!items.isEmpty())
				{
					fItem=items.get(0);
				}
			}//end of if
		}
		return fItem;
	}

	public List<FileItem> getUploadFileItems(Representation entity) {
		List<FileItem> items = null; 
		if (entity != null) {
			if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType())) {//
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096);
				RestletFileUpload upload = new RestletFileUpload(factory);
				// upload.setSizeMax(maxFileSize); // 设置最大文件尺寸，这里是4MB
				try {
					items = upload.parseRequest(getRequest());
				} catch (FileUploadException e) {
					e.printStackTrace();
				} 
			}
		}//end of if
		return items;
	}// end of getUploadSingleFile
	
	
	public JSONObject saveSingleFile(FileItem fi,String module,String saveFileNamePrefix)  {

	     System.out.println("-----saveSingleFile--------->"+fi.getFieldName() + "   " +
	    		 fi.getName() + "   " +
	    		 fi.isInMemory() + "    " +
	    		 fi.getContentType() + "   " +
	    		 fi.getSize());
		String contentType=fi.getContentType();
		String strFileName=fi.getName();
		if(null==strFileName){
			strFileName=fi.getString();
		}
	
		long fileSize=fi.getSize(); 
		String saveFileExtName=strFileName.substring(strFileName.lastIndexOf("."));
		if(logger.isTraceEnabled()){
			logger.trace("fileExtName is {}.",saveFileExtName);
		}
		String saveFileFullName=saveFileNamePrefix+saveFileExtName; 
		String fileUrlPath=this.getBaseUplodUrl()+module+"/"+saveFileFullName;
		String fileDirectory=this.getBaseUplodDirectory()+module+"/"; 
		
		//创建新目录
		File fDir=new File(fileDirectory);
		if (!fDir.exists()) {
			fDir.mkdirs();
		}
		//创建新文件 
//		File fullFile = new File(saveFileName);
		System.out.println("saveFileFullName-------->"+saveFileFullName);
		File savedFile = new File(fileDirectory+saveFileFullName);
		if (!savedFile.exists()) {
			try {
				System.out.println("createNewFile-------->");
//				savedFile.createNewFile();
//				fi.write(savedFile);
				
			    OutputStream out = new FileOutputStream(savedFile);  
                InputStream inp = fi.getInputStream();     				
				IOUtils.copy(inp, out);
				out.flush();
				IOUtils.closeQuietly(inp);
				IOUtils.closeQuietly(out); 
			}catch (Exception e) { 
				e.printStackTrace();
			} 
		}  
		//
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("fileUrlPath", fileUrlPath);
		jsonObject.put("fileDirectory", fileDirectory);
		jsonObject.put("fileContentType", contentType); 
		jsonObject.put("fileSize", fileSize);  
		System.out.println("jsonObject-------->"+jsonObject);
		return jsonObject;
	}//end of saveSingleFile

}
