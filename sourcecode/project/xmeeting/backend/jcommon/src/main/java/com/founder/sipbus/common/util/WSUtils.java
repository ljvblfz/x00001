package com.founder.sipbus.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


public class WSUtils {
	
	/**
	 * 执行WebServices
	 *
	 * @param soapStr xml格式的soap 包 
	 * @param endPoint endPoint 地址  切记不能带?wsdl
	 * @return webservice返回的soap xml
	 * @throws Exception
	 * @author 陈文智
	 */
	@SuppressWarnings("finally")
	public static String executeSoapPackage(String soapStr,String endPoint) throws Exception{
		String returnStr="";
        ByteArrayInputStream in= new ByteArrayInputStream(soapStr.getBytes());
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        try{
	        MessageFactory mf = MessageFactory.newInstance();
	        SOAPConnection con = SOAPConnectionFactory.newInstance().createConnection();
	        SOAPMessage reqMessage = mf.createMessage(null,in);
            System.out.println("\n Soap Request:\n");
            reqMessage.writeTo(System.out);
            System.out.println();
            URL endpoint = new URL(endPoint);
            SOAPMessage response = con.call(reqMessage, endpoint);
//	        StringWriter sw=new StringWriter();
//	        PrintWriter pw=new PrintWriter(sw);
            response.writeTo(sos);
	        response.getSOAPBody().getChildElements().next();
        	returnStr=sos.toString();
        }catch(Exception e){
        	returnStr=e.getMessage();
        	throw e;
        }finally{
        	in.close();
        	sos.close();
        	return returnStr;
        }
	}
	public static void main(String[] args){
		String soapStr =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns0=\"http://login.webservice.bos.kingdee.com\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
			"  <soapenv:Body>" +
			" <ns0:login>" +
			" <in0 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:string\">"+"user"+"</in0>" +
			" <in1 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:string\">"+"szxt"+"</in1>" +
			" <in2 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:string\">"+"eas"+"</in2>" +
			" <in3 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:string\">"+"002"+"</in3>" +
			" <in4 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:string\">"+"L2"+"</in4>" +
			" <in5 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:int\">"+2+"</in5>" +
			" <in6 soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"xsd:string\">"+"BaseDB"+"</in6>" +
			" </ns0:login>" +
			" </soapenv:Body>" +
			" </soapenv:Envelope>";
		String responseStr="";
		try {
			responseStr=WSUtils.executeSoapPackage(soapStr, "http://192.168.168.217:6888/ormrpc/services/EASLogin");
			System.out.println("================responseStr===================");
			System.out.println(responseStr);
			if(responseStr!=null&&responseStr.toLowerCase().indexOf("sessionid")!=-1){//登录成功
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
