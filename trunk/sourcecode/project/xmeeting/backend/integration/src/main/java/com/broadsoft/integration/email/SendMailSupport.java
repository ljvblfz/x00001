package com.broadsoft.integration.email;

import com.founder.sipbus.fwk.config.Environment;

public class SendMailSupport {

	private final static String mailserverhost=Environment.getInstance().getString("com.broadsoft.integration.email.mailserverhost");
	private final static String mailserverport=Environment.getInstance().getString("com.broadsoft.integration.email.mailserverport");
	private final static String mailserverusername=Environment.getInstance().getString("com.broadsoft.integration.email.mailserverusername");
	private final static String mailserverpassword=Environment.getInstance().getString("com.broadsoft.integration.email.mailserverpassword");
	private final static String mailfromaddress=Environment.getInstance().getString("com.broadsoft.integration.email.mailfromaddress");
	//

	private final static String mailsubject=Environment.getInstance().getString("com.broadsoft.integration.email.mailsubject");
//	private final static String mailcontent=Environment.getInstance().getString("com.broadsoft.integration.email.mailcontent");
	
	public static void main(String[] args) {
		send_163();
	}
	
	// 163邮箱
	public static void send_163() {
		MailSenderInfo mailInfo = createMailSenderInfo();
		mailInfo.setCcAddress("xmmeeting@163.com,lu_zhen@founder.com.cn");
		mailInfo.setBccAddress("chen_wenzhi@founder.com.cn,wang.yongxing@founder.com.cn");
		//
		mailInfo.setContent("<b>会议资料含附件</b>");
		String[] attachFileNames={"D:\\04-SIPBUS_Deployment\\nj\\2011级武大经管院学生证填写说明.pdf","D:/04-SIPBUS_Deployment/nj/2012年最新小企业会计准则(会计科目主要账务处理和财务报表).pdf"};
		mailInfo.setAttachFileNames(attachFileNames);   
		// 这个类主要是设置邮件
		sendHtmlMail(mailInfo); 
	}

	
	/**
	 * html格式
	 * @param mailInfo
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo ) {
		return SimpleMailSender.sendHtmlMail(mailInfo); 
	}


	

	
	public static MailSenderInfo createMailSenderInfo() {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(mailserverhost);
		mailInfo.setMailServerPort(mailserverport);
		mailInfo.setValidate(true);
		mailInfo.setUserName(mailserverusername); // 实际发送者
		mailInfo.setPassword(mailserverpassword);// 您的邮箱密码
		mailInfo.setFromAddress(mailfromaddress); // 设置发送人邮箱地址
		mailInfo.setToAddress(mailfromaddress); // 
//		mailInfo.setSubject(mailsubject);
		return mailInfo;
	}
 
	
	public static MailSenderInfo createMailSenderInfo2() {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("xmmeeting@163.com"); // 实际发送者
		mailInfo.setPassword("!qaz2wsx");// 您的邮箱密码
		mailInfo.setFromAddress("xmmeeting@163.com"); // 设置发送人邮箱地址
		return mailInfo;
	}
}
