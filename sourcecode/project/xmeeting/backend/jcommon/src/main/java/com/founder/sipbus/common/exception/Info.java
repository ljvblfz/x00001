package com.founder.sipbus.common.exception;

import java.io.*;
/**
 * 
 * @author Tracy chen
 *
 */
public class Info
    implements Serializable
{

	private static final long serialVersionUID = -1265263737778009599L;
	public long Number;
    public String Code;
    public int Type;
    public String Description;
    public String HelpText;
    public String Source;

    public Info()
    {
    }

    public Info(long number, String strCode, int intType, String strDesc, String strHelp, String strSource)
    {
        Number = number;
        Code = strCode;
        Type = intType;
        Description = strDesc;
        HelpText = strHelp;
        Source = strSource;
    }

    public String getGB2312ByISO8859(String s)
    {
        try
        {
            s = new String(s.getBytes("ISO8859_1"), "GB2312");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    public String getISO8859ByGB2312(String s)
    {
        try
        {
            s = new String(s.getBytes("GB2312"), "ISO8859_1");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    private void writeObject(ObjectOutputStream oos)
        throws IOException
    {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois)
        throws ClassNotFoundException, IOException
    {
        ois.defaultReadObject();
    }
}
