package com.broadsoft.integration.mms;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;

import com.broadsoft.integration.dom4j.Dom4jSupport;
import com.broadsoft.integration.http.HttpPostSupport;
 

public class MmsSupport {
	
//	private final static String posturl="http://10658.com.cn/api/sendmms2/";
//	private final static String postuid="ydrj";
//	private final static String postpwd="ydrj123";
	//
	private final static String mtitle="江苏省电力公司会议系统通知";
//	private final static String mtime="2011-12-31 00:00:00";
	private final static String mtime="";
	private final static String playTime="3";
	private final static String pic_ext="jpg";
	
	public static String sendMms(String mto,String content,String picHex) throws Exception{
		 String posturl="http://10658.com.cn/api/sendmms2/";
		 String postuid="ydrj";
		 String postpwd="ydrj123";
		
		 JSONObject paramJson=new JSONObject();
		 paramJson.put("uid", postuid);
		 paramJson.put("pwd", postpwd);
		 paramJson.put("mtitle", mtitle);
		 paramJson.put("mobile", mto);
		 paramJson.put("mtime", mtime);
		 paramJson.put("playTime_1",playTime);
		 paramJson.put("content_1", content);
		 paramJson.put("pic_1", picHex);
		 paramJson.put("pic_ext_1", pic_ext);  
		 String result=HttpPostSupport.executePostMethodByHttpClient(posturl, paramJson);
		 System.out.println("mms result: \n"+result);
		 return  parseResponse(result); 
	}


	private static String parseResponse(String result) {
		Document doc=Dom4jSupport.stringToXMLDocment(result);
		Element rootElm = doc.getRootElement();
		Element code=rootElm.element("Code");
		Element responseMessage=rootElm.element("ResponseMessage"); 
		String strCode=code.getTextTrim();
		String strRespMsg=responseMessage.getTextTrim();
		String strResp=strCode+"-"+strRespMsg;
		return strResp;
		
	}
	
	
	private static String image2HexString() throws IOException{
		byte[] imageInByte = image2Bytes();
		String strHex=DatatypeConverter.printHexBinary(imageInByte);
		return strHex;
	}


	private static byte[] image2Bytes() throws IOException {
		BufferedImage image =  ImageIO.read(MmsSupport.class.getResourceAsStream("/welcome.jpg")); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	
	public static void main(String[] args) throws Exception{
		String mto="13962118202";
		String content="江苏省电力公司会议通知\nnew line\n       new line2\n new line2";
		String picHex=image2HexString();
		String result=sendMms(mto,content,picHex);
		System.out.println("result------------->"+result);
	}
	

}
