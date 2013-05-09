package com.founder.sipbus.common.exception;

import java.io.UnsupportedEncodingException;
/**
 * 
 * @author Tracy Chen
 *
 */
public class FounderException extends RuntimeException{

	private static final long serialVersionUID = 6930432926955049003L;
	private Info Code;

	/**
	 * Founder Exception based on Exception decription ("info.xml")
	 * @param number  Exception Number
	 * @param strSource Exception Source
	 * @param strValue  Exception Value
	 * @author Googol Gu
	 */
    public FounderException(long number, String strSource, String strValue)
    {
        Code = new Info();
        try
        {
            InfoAnalyze infoanalyze = new InfoAnalyze();
            if(strSource == null)
            	strSource = "";
            Code = infoanalyze.getExceptionByNumber(number, strSource, strValue);
        }
        catch(Exception exception)
        {
            Code.Number = 0xdbba1L;
            Code.Code = "InfoAnalyzeError";
            Code.Type = 3;
            Code.Description = "异常信息解析失败！";
            Code.HelpText = "无法得到异常信息，请检查系统文件info.xml在是否在系统目录下，并保证其可被访问和使用";
            Code.Source = "FounderException.FounderException";
        }
    }
    public FounderException(){}
    
    /**
	 * Founder Exception based on Exception decription ("info.xml")
	 * @param String  Exception Code (Key)
	 * @param strSource Exception Source
	 * @param strValue  Exception Value
	 * @author Googol Gu
	 */
    public FounderException(String strCode, String strSource, String strValue)
    {
        Code = new Info();
        try
        {
            InfoAnalyze infoanalyze = new InfoAnalyze();
            if(strValue == null)
                strValue = "";
            Code = infoanalyze.getExceptionByCode(strCode, strSource, strValue);
        }
        catch(Exception exception)
        {
            Code.Number = 0xdbba1L;
            Code.Code = "InfoAnalyzeError";
            Code.Type = 3;
            Code.Description = Description("异常信息解析失败！");
            Code.HelpText = Description("无法得到异常信息，请检查系统文件info.xml在是否在系统目录下，并保证其可被访问和使用");
            Code.Source = "FounderException.FounderException";
        }
    }


    /**
	 * Founder Exception based on Exception decription ("info.xml")
	 * @param String  Exception Code (Key)
	 * @param strSource Exception Source
	 * @author Googol Gu
	 */
    public FounderException(String strCode, String strSource)
    {
        Code = new Info();
        try
        {
            InfoAnalyze infoanalyze = new InfoAnalyze();
            Code = infoanalyze.getExceptionByCode(strCode, strSource);
        }
        catch(Exception exception)
        {
            Code.Number = 900001;
            Code.Code = "InfoAnalyzeError";
            Code.Type = 3;
            Code.Description = Description("异常信息解析失败！");
            Code.HelpText = Description("无法得到异常信息，请检查系统文件info.xml在是否在系统目录下，并保证其可被访问和使用");
            Code.Source = "FounderException.FounderException";
        }
    }
    
	/**
	 * Founder Exception based on Exception decription ("info.xml")
	 * @param number  Exception Number
	 * @param strSource Exception Source
	 * @author Googol Gu
	 */
    public FounderException(long number, String strSource)
    {
        Code = new Info();
        try
        {
            InfoAnalyze infoanalyze = new InfoAnalyze();
            Code = infoanalyze.getExceptionByNumber(number, strSource);
        }
        catch(Exception exception)
        {
            Code.Number = 900001;
            Code.Code = "InfoAnalyzeError";
            Code.Type = 3;
            Code.Description = Description("异常信息解析失败！");
            Code.HelpText = Description("无法得到异常信息，请检查系统文件info.xml在是否在系统目录下，并保证其可被访问和使用");
            Code.Source = "FounderException.FounderException";
        }
    }
    
    public FounderException(long number)
    {
        Code = new Info();
        try
        {
            InfoAnalyze infoanalyze = new InfoAnalyze();
            Code = infoanalyze.getExceptionByNumber(number, null);
        }
        catch(Exception exception)
        {
            Code.Number = 900001;
            Code.Code = "InfoAnalyzeError";
            Code.Type = 3;
            Code.Description = Description("异常信息解析失败！");
            Code.HelpText = Description("无法得到异常信息，请检查系统文件info.xml在是否在系统目录下，并保证其可被访问和使用");
            Code.Source = "FounderException.FounderException";
        }
    }

    public long getInfoNumber()
    {
        if(Code != null)
            return Code.Number;
        else
            return 0L;
    }

    public String getInfoCode()
    {
        if(Code != null)
            return Code(Code.Code);
        else
            return "";
    }

    public String getInfoDescription()
    {
        if(Code != null)
            return Code(Code.Description);
        else
            return "";
    }

    public String getInfoHelpText()
    {
        if(Code != null)
            return Code(Code.HelpText);
        else
            return "";
    }

    public String getInfoSource()
    {
        if(Code != null)
            return Code(Code.Source);
        else
            return "";
    }

    public int getInfoType()
    {
        if(Code != null)
            return Code.Type;
        else
            return 0;
    }

    private String Code(String s)
    {
        try
        {
            s = new String(s.getBytes("GB2312"), "GB2312");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    private String Description(String s)
    {
        try
        {
            s = new String(s.getBytes("GB2312"), "GB2312");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    private String HelpText()
    {
        String s = "";
        s = s + "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        s = s + "<Exception>";
        s = s + "<record id=\"" + Code.Code + "\" number=\"" + String.valueOf(Code.Number) + "\" source=\"" + dvHTMLEncode(Code(Code.Source)) + "\"  description=\"" + dvHTMLEncode(Code(Code.Description)) + "\" helptext=\"" + Code(Code.HelpText) + "\" type=\"" + dvHTMLEncode(String.valueOf(Code.Type)) + "\"/>";
        s = s + "</Exception>";
        return s;
    }

    public String getInfoXML(Exception exception)
    {
        String s = exception.getClass().getName();
        String s1 = exception.getStackTrace()[0].toString();
        if(s.equals("com.founder.trust.common.exception.FounderException"))
        {
            return ((FounderException)exception).HelpText();
        } else
        {
            String s2 = "";
            s2 = s2 + "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
            s2 = s2 + "<Exception>";
            s2 = s2 + "<record id=\"JavaException\" number=\"900003\" source=\"" + dvHTMLEncode(s1) + "\" description=\"操作系统或Web服务器引发了内部异常！\" helptext=\"系统异常数据" + dvHTMLEncode(exception.getMessage()) + "\" type=\"4\"/>";
            s2 = s2 + "</Exception>";
            return s2;
        }
    }

    public static String getSampleXML()
    {
        String s = "";
        s = s + "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        s = s + "<Exception>";
        s = s + "<record id=\"JavaException\" number=\"900003\" source=\"产生异常的类\" description=\"操作系统或Web服务器引发了内部异常！\" helptext=\"系统异常数据：异常详细信息 type=\"3\"/>";
        s = s + "</Exception>";
        return s;
    }

    public static String getSampleXMLDescription()
    {
        String s = "";
        s = "Exception：是根节点;\n\r";
        s = "record：节点包含异常详细信息；\n\r";
        s = "record@id：描述异常的标识，record@id:描述异常的号码，record@source:描述产生异常的类,record@description:描述异常的详细信息，record@helptext:描述异常的帮助信息，record@type:描述异常的类型(0-提示，1－帮助，2－警告，3－错误)\n\r";
        return s;
    }

    public String dvHTMLEncode(String s)
    {
        String s1 = s;
        if(s1 != null)
        {
            s1 = s1.replaceAll(">", "&gt;");
            s1 = s1.replaceAll("<", "&lt;");
            s1 = s1.replaceAll("\"", "&quot;");
            s1 = s1.replaceAll("'", "&#39;");
            s1 = s1.replaceAll("\n", "");
            s1 = s1.replaceAll("\r\n", "</P><P>");
            s1 = s1.replaceAll("\r", "<BR>");
        }
        return s1;
    }
    
    /**
     * 使用参考main方法
     * @param args
     */
    public static void main(String[] args){
    	System.out.println(new FounderException(100082,FounderException.class.toString()).getInfoDescription());
    	System.out.println(new FounderException("InfoNumberOverflow",FounderException.class.toString()).getInfoDescription());
    }
}
