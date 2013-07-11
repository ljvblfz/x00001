package com.broadsoft.integration.email;

import java.io.UnsupportedEncodingException;
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
	public static boolean sendTextMail(MailSenderInfo mailInfo) {
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
	
	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
		Session sendMailSession = createSession(mailInfo);
		try {  
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
			String[] attachFullPaths=mailInfo.getAttachFullPath();
			if(null!=attachFileNames&&null!=attachFullPaths){ 
				for(int i=0;i<attachFileNames.length;i++){
					String attachFileName=attachFileNames[i];
					String attachFullPath=attachFullPaths[i];
					BodyPart htmlBodyAttachment = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(attachFullPath);
					DataHandler dh = new DataHandler(fds);
//					String fileName=attachFileName; 
					try { 
						attachFileName = javax.mail.internet.MimeUtility.encodeWord(attachFileName,"gb2312",null);
					} catch (UnsupportedEncodingException e) { 
						e.printStackTrace();
					} 
					htmlBodyAttachment.setFileName(attachFileName);
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
	}//end of sendHtmlMail
	
	
	/**
	 * 
	 * @param mailInfo
	 * @return
	 */
	private static Session createSession(MailSenderInfo mailInfo) {
		MailAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MailAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		return sendMailSession;
	}

	/**
	 * 
	 * @param mailInfo
	 * @param sendMailSession
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private static Message createMailMessage(MailSenderInfo mailInfo,
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
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameByPath(String filePath){
		int idx = filePath.replaceAll("\\\\", "/").lastIndexOf("/");
		return idx >= 0 ? filePath.substring(idx + 1) : filePath; 
	}
}
