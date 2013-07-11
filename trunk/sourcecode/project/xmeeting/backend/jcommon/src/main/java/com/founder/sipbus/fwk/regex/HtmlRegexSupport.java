package com.founder.sipbus.fwk.regex;


public class HtmlRegexSupport {
	
	
	public static String replaceHtmlTag(String strInput){
		String regex1="<([^>^br^BR]*)>"; 
		String strInput2=strInput.replaceAll(regex1, ""); 
		String regex2="<\\s*br\\s*/>"; 
		String strInput3=strInput2.replaceAll(regex2, "\n"); 
		String regex3="&nbsp;";
		String strInput4=strInput3.replaceAll(regex3, " "); 
		
		
		return strInput4;
	}
	
	
	
	public static void main(String[] args){
		String testInput="<p>你好</p>强<br />&nbsp;他们&nbsp;&nbsp;&nbsp;&nbsp;";
		
		System.out.println("---->"+replaceHtmlTag(testInput));
	}
	
	
	

}
