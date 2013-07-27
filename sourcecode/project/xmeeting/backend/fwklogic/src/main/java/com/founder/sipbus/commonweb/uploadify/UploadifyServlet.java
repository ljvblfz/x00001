package com.founder.sipbus.commonweb.uploadify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.founder.sipbus.fwk.config.Environment;

public class UploadifyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 this.doPost(request, response);
	} 
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String meetingId=request.getParameter("meetingId");  
		String xmmpicGuid=request.getParameter("xmmpicGuid"); 
//		String xmmpicGuid=request.getParameter("xmmpicGuid"); 
		
		

		System.out.println("doPost----meetingId---->"+meetingId);
//		System.out.println("doPost----xmmpicGuid---->"+xmmpicGuid);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		PrintWriter out = response.getWriter();
		String requestURI=request.getRequestURI();
		System.out.println("requestURI-------->"+requestURI);
//		System.out.println("ContextPath-------->"+request.getContextPath());
		
		String[] arrUri=requestURI.split("/"); 
		String[] arrName=arrUri[2].split("\\.");
		String module=arrName[0]; 

//		System.out.println("doPost------2-->");
		String fileUrlPathPrefix=this.getBaseUplodUrl();
		String fileDirectory=this.getBaseUplodDirectory();

		
		if(null!=meetingId){
//			if(meetingId.indexOf("-")>0){
//				String[] idarr=meetingId.split("-");
//				meetingId=idarr[0];
//				xmmpicGuid=idarr[1];
//			}
			
			fileUrlPathPrefix+="xmeeting"+"/"+meetingId+"/"+module+"/";
			fileDirectory+="xmeeting"+"/"+meetingId+"/"+module+"/"; 
			
		}else if(null!=xmmpicGuid){ //图片主题  
			fileUrlPathPrefix+=module+"/";
			fileDirectory+=module+"/"; 
			fileUrlPathPrefix+=xmmpicGuid+"/";
			fileDirectory+=xmmpicGuid+"/"; 
		} else{//视频
			fileUrlPathPrefix+=module+"/";
			fileDirectory+=module+"/";  
		}
	 
		//创建文件夹
		File dirFile = new File(fileDirectory);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
 
		
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。    

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
//		int sizeM=300;
		int sizeM=Environment.getInstance().getInt(UPLOAD_FILE_SIZEMAX);
		upload.setFileSizeMax(1024*1024*sizeM);//300M
		

		JSONObject jsonObject=new JSONObject();
		List<FileItem> items =null;
		try {
			  items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<FileItem> itr = items.iterator();  
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String originalFileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				String fileContentType=item.getContentType();
				String fileNameExt = getFileNameExt(originalFileName);
				String newFileName = getRandomFileNamePrefix() +"." + fileNameExt;
				try{
					File uploadedFile = new File(fileDirectory, newFileName); 
                    OutputStream os = new FileOutputStream(uploadedFile);
                    InputStream is = item.getInputStream();
                    byte buf[] = new byte[1024];//可以修改 1024 以提高读取速度
                    int length = 0;  
                    while( (length = is.read(buf)) > 0 ){  
                        os.write(buf, 0, length);  
                    }  
                    //关闭流  
                    os.flush();
                    os.close();  
                    is.close(); 
                    //
            		jsonObject.put("fileUrlPath", fileUrlPathPrefix+newFileName);
            		jsonObject.put("fileDirectory", fileDirectory);
            		jsonObject.put("fileContentType", fileContentType); 
            		jsonObject.put("fileSize", fileSize);  
            		System.out.println("jsonObject-------->"+jsonObject.toString());
                    out.print(jsonObject); 
				}catch(Exception e){
					e.printStackTrace();
				}
			} //end of if
		} //end of while  
		out.flush();
		out.close();
	}


	private String getFileNameExt(String originalFileName) {
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
		return fileExt;
	}


	public final static String UPLOAD_BASE_DIRECTORY = "com.broadsoft.platform.upload.base.directory";

	public final static String UPLOAD_BASE_URL = "com.broadsoft.platform.upload.base.url";
	public final static String UPLOAD_FILE_SIZEMAX = "com.broadsoft.platform.upload.file.sizemax";

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
	
	 

}
