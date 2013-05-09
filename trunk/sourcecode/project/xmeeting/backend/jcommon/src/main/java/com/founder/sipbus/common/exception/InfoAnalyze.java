package com.founder.sipbus.common.exception;

import java.io.UnsupportedEncodingException;
/**
 * 
 * @author Tracy Chen
 *
 */
public class InfoAnalyze
{

    private InfoXMLImpl Code;

    public InfoAnalyze()
        throws Exception
    {
        Code = InfoXMLImpl.getInstance(Code());
    }

    public Info getExceptionByNumber(long number, String strSource)
        throws Exception
    {
        Info info = Code.getInfoByNumber(number);
        if(info != null)
        {
            info.Source = strSource;
            info.Description = info.Description.replaceAll("%Value%", "");
            return info;
        } else
        {
            return null;
        }
    }

    private String Code()
        throws Exception
    {
    	String code = this.getClass().getClassLoader().getResource("info.xml").toString();
        return code;
    }

    public Info getExceptionByNumber(long number, String strSource, String strValue)
        throws Exception
    {
        Info info = Code.getInfoByNumber(number);
        if(info != null)
        {
            info.Source = strSource;
            Info info1 = new Info(info.Number, info.Code, info.Type, info.Description, info.HelpText, info.Source);
            strValue = strValue.length() == 0 ? "" : "[" + Description(strValue) + "]";
            info1.Description = info1.Description.replaceAll("%Value%", strValue);
            info1.HelpText = info1.HelpText.replaceAll("%Value%", strValue);
            return info1;
        } else
        {
            return null;
        }
    }

    public Info getExceptionByCode(String strCode, String strSource, String strValue)
        throws Exception
    {
        Info info = Code.getInfoByCode(strCode);
        if(info != null)
        {
            info.Source = strSource;
            Info info1 = new Info(info.Number, info.Code, info.Type, info.Description, info.HelpText, info.Source);
            strValue = strValue.length() == 0 ? "" : "[" + Description(strValue) + "]";
            info1.Description = info1.Description.replaceAll("%Value%", strValue);
            return info1;
        } else
        {
            return null;
        }
    }

    public Info getExceptionByCode(String strCode, String strSource)
        throws Exception
    {
        Info info = Code.getInfoByCode(strCode);
        if(info != null)
        {
            info.Source = strSource;
            info.Description = info.Description.replaceAll("%Value%", "");
            return info;
        } else
        {
            return null;
        }
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
}
