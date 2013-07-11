package com.broadsoft.integration.email;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class SimpleMailSender {
	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		Session sendMailSession = createSession(mailInfo);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = createMailMessage(mailInfo, sendMailSession); 
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			mailMessage.saveChanges();//??
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private Session createSession(MailSenderInfo mailInfo) {
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		return sendMailSession;
	}

	private Message createMailMessage(MailSenderInfo mailInfo,
			Session sendMailSession) throws AddressException,
			MessagingException {
		Message mailMessage = new MimeMessage(sendMailSession); 
		mailMessage.setSubject(mailInfo.getSubject());
		mailMessage.setSentDate(new Date());
		// From
		Address from = new InternetAddress(mailInfo.getFromAddress()); 
		mailMessage.setFrom(from); 
		//TO
		String strToAddress=mailInfo.getToAddress();

		if(null!=strToAddress){
			Address[] arrCcAddress=null;
			if(strToAddress.indexOf(",")>0){
				String[] ccAddressArray=strToAddress.split(",");
				arrCcAddress=new Address[ccAddressArray.length];
				for(int i=0;i<ccAddressArray.length;i++){
					String strCC=ccAddressArray[i];
					Address cc = new InternetAddress(strCC);
					arrCcAddress[i]=cc;
				}
			}else{
				arrCcAddress=new Address[1];
				Address cc = new InternetAddress(strToAddress);
				arrCcAddress[0]=cc;
			}
			mailMessage.setRecipients(Message.RecipientType.TO, arrCcAddress);
		} 
		//CC
		String strCcAddress=mailInfo.getCcAddress();
		if(null!=strCcAddress){
			Address[] arrCcAddress=null;
			if(strCcAddress.indexOf(",")>0){
				String[] ccAddressArray=strCcAddress.split(",");
				arrCcAddress=new Address[ccAddressArray.length];
				for(int i=0;i<ccAddressArray.length;i++){
					String strCC=ccAddressArray[i];
					Address cc = new InternetAddress(strCC);
					arrCcAddress[i]=cc;
				}
			}else{
				arrCcAddress=new Address[1];
				Address cc = new InternetAddress(strCcAddress);
				arrCcAddress[0]=cc;
			}
			mailMessage.setRecipients(Message.RecipientType.CC, arrCcAddress);
		}
		//BCC  
		String strBccAddress=mailInfo.getBccAddress();
		if(null!=strBccAddress){
			Address[] arrCcAddress=null;
			if(strBccAddress.indexOf(",")>0){
				String[] ccAddressArray=strBccAddress.split(",");
				arrCcAddress=new Address[ccAddressArray.length];
				for(int i=0;i<ccAddressArray.length;i++){
					String strCC=ccAddressArray[i];
					Address cc = new InternetAddress(strCC);
					arrCcAddress[i]=cc;
				}
			}else{
				arrCcAddress=new Address[1];
				Address cc = new InternetAddress(strBccAddress);
				arrCcAddress[0]=cc;
			}
			mailMessage.setRecipients(Message.RecipientType.BCC, arrCcAddress);
		}
		return mailMessage;
	}//end of createMailMessage

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public boolean sendHtmlMail(MailSenderInfo mailInfo) {
		Session sendMailSession = createSession(mailInfo);
		try {
//			// 根据session创建一个邮件消息
//			Message mailMessage = new MimeMessage(sendMailSession);
//			// 创建邮件发送者地址
//			Address from = new InternetAddress(mailInfo.getFromAddress());
//			// 设置邮件消息的发送者
//			mailMessage.setFrom(from);
//			// 创建邮件的接收者地址，并设置到邮件消息中
//			Address to = new InternetAddress(mailInfo.getToAddress());
//			// Message.RecipientType.TO属性表示接收者的类型为TO
//			mailMessage.setRecipient(Message.RecipientType.TO, to);
//			// 设置邮件消息的主题
//			mailMessage.setSubject(mailInfo.getSubject());
//			// 设置邮件消息发送的时间
			

			// 根据session创建一个邮件消息
			Message mailMessage = createMailMessage(mailInfo, sendMailSession); 
			
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart htmlBodyText = new MimeBodyPart();
			// 设置HTML内容
			htmlBodyText.setContent(mailInfo.getContent(), "text/html; charset=gb2312");
			mainPart.addBodyPart(htmlBodyText);
			
			//设置信件的附件(用本地上的文件作为附件)
			String[] attachFileNames=mailInfo.getAttachFileNames();
			if(null!=attachFileNames){
				for(String attacheFileName:attachFileNames){
					BodyPart htmlBodyAttachment = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(attacheFileName);
					DataHandler dh = new DataHandler(fds);
					String fileName=getFileNameByPath(attacheFileName); 
					try { 
						fileName = javax.mail.internet.MimeUtility.encodeWord(fileName,"gb2312",null);
					} catch (UnsupportedEncodingException e) { 
						e.printStackTrace();
					} 
					htmlBodyAttachment.setFileName(fileName);
					htmlBodyAttachment.setDataHandler(dh); 
					mainPart.addBodyPart(htmlBodyAttachment); 
				}
			} 
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			mailMessage.saveChanges(); 
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	
	public static String getFileNameByPath(String filePath){
		int idx = filePath.replaceAll("\\\\", "/").lastIndexOf("/");
		return idx >= 0 ? filePath.substring(idx + 1) : filePath; 
	}
}
