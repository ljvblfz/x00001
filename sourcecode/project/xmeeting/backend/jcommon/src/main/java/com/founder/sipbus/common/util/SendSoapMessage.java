package com.founder.sipbus.common.util;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SendSoapMessage 
{ 
    public static void  buildSoapMessage() 
    { 
        try 
        { 
            SOAPConnectionFactory conntools=SOAPConnectionFactory.newInstance();         
            SOAPConnection conn=conntools.createConnection();    
            //创建消息工厂 
            MessageFactory factory=MessageFactory.newInstance(); 
            //创建soap消息reqMsg 
            SOAPMessage reqMsg=factory.createMessage(); 
            //创建soap消息的部分reqMsgpart 
            SOAPPart part=reqMsg.getSOAPPart(); 
            //创建sope信封envelope，要开始写信了 
            SOAPEnvelope envelope=part.getEnvelope();           
            envelope.setAttribute("xmlns:soapenv", "http://schemas.xmlsoap.org/soap/envelope/"); 
            envelope.setAttribute("xmlns:esb", "http://ngbss.huawei.com/esb/endpointurl/webservice");                       
            //写header 
            SOAPHeader header=envelope.getHeader(); 
            //写Body 
            SOAPBody body=envelope.getBody();           
            SOAPBodyElement bodyElement=body.addBodyElement(envelope.createName("EndPointUrlRequestMsg", "esb", "http://ngbss.huawei.com/esb/endpointurl/webservice"));          
            SOAPBodyElement requestHeader=(SOAPBodyElement) bodyElement.addChildElement("RequestHeader", "esb"); 
            SOAPBodyElement requestBody=(SOAPBodyElement) bodyElement.addChildElement("RequestBody", "esb"); 
            SOAPBodyElement urls=(SOAPBodyElement) requestBody.addChildElement("urls", "esb"); 
            SOAPBodyElement url=(SOAPBodyElement) urls.addChildElement("url", "esb");            
            //RquestHeader 
            requestHeader.addChildElement("userId", "esb").addTextNode("500"); 
            requestHeader.addChildElement("password", "esb").addTextNode("500");            
            //RequestBody 
            urls.setAttribute("Action", "Add"); 
            url.addChildElement("key", "esb").addTextNode("AR4CCInterfaceService"); 
            url.addChildElement("pass", "esb").addTextNode("TRUE"); 
            url.addChildElement("URL", "esb").addTextNode("http://10.137.30.66:9533/ar/ccinterface/wsdl/AR4CCInterfaceService.wsdl"); 
            url.addChildElement("frondEnd", "esb").addTextNode("CRM"); 
            url.addChildElement("backEnd", "esb").addTextNode("CBS"); 
            url.addChildElement("soap", "esb").addTextNode("SOAP11"); 
            url.addChildElement("timeOut", "esb").addTextNode("100");                           
            URL endPoint =new URL("http://localhost:7080/Demo/service"); 
            System.out.println("\n====================发送的消息:"); 
            reqMsg.writeTo(System.out);            
            SOAPMessage respMsg = conn.call(reqMsg, endPoint);           
           // System.out.println("\n服务端返回的信息- : " + getResult(respMsg));                      
            System.out.println("\n\n====================接收的消忿"); 
            respMsg.writeTo(System.out); 
//            ReceiverSoapMessage.receiverMessage(respMsg); 
            respMsg.getSOAPBody().getChildElements().next();
//        	returnStr=sos.toString();
            System.out.print("Success!"); 
        } 
        catch (Exception e) 
        { 
            e.printStackTrace();           
        }            
    } 
}

