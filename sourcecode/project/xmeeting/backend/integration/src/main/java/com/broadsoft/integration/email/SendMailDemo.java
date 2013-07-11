package com.broadsoft.integration.email;

public class SendMailDemo {
	
	public static void main(String[] args) {
		SendMailDemo.send_163();
	}
	
	// 163邮箱
	public static void send_163() {
		// 这个类主要是设置邮件
		sendHtmlMail(); 
	}

	private static void sendHtmlMail() {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("xmmeeting@163.com"); // 实际发送者
		mailInfo.setPassword("!qaz2wsx");// 您的邮箱密码
		mailInfo.setFromAddress("xmmeeting@163.com"); // 设置发送人邮箱地址
		mailInfo.setToAddress("lu_zhen@founder.com.cn"); // 设置接受者邮箱地址、
		mailInfo.setCcAddress("xmmeeting@163.com,lu_zhen@founder.com.cn");
		mailInfo.setBccAddress("chen_wenzhi@founder.com.cn,wang.yongxing@founder.com.cn");
		mailInfo.setSubject("会议资料含附件");
		mailInfo.setContent("<b>会议资料含附件</b>");
		String[] attachFileNames={"D:\\04-SIPBUS_Deployment\\nj\\2011级武大经管院学生证填写说明.pdf","D:/04-SIPBUS_Deployment/nj/2012年最新小企业会计准则(会计科目主要账务处理和财务报表).pdf"};
		mailInfo.setAttachFileNames(attachFileNames);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
//		sms.sendTextMail(mailInfo); // 发送文体格式
		sms.sendHtmlMail(mailInfo); // 发送html格式
	}
}
