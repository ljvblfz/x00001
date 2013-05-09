package com.founder.sipbus.common.exception;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 
 * @author Tracy chen
 *
 */
public class InfoXMLParser extends DefaultHandler
{

    private Hashtable<String,Info> append;
    private Hashtable<String,Info> delete;
    private StringBuffer equals;

    public InfoXMLParser()
    {
        append = null;
        delete = null;
        equals = new StringBuffer();
    }

    public void startDocument()
        throws SAXException
    {
        append = new Hashtable<String,Info>();
        delete = new Hashtable<String,Info>();
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
        throws SAXException
    {
        equals.delete(0, equals.length());
        if(s2.equals("record"))
        {
            String s3 = attributes.getValue(0);
            long l = Long.parseLong(attributes.getValue(1));
            String s4 = "";
            String s5 = "";
            try
            {
                s4 = new String(attributes.getValue(2).toString().getBytes("GB2312"), "GB2312");
                s5 = new String(attributes.getValue(3).toString().getBytes("GB2312"), "GB2312");
            }
            catch(UnsupportedEncodingException unsupportedencodingexception) { }
            int i = Integer.parseInt(attributes.getValue(4).trim());
            Info info = new Info(l, s3, i, s4, s5, "");
            append.put(s3.toString(), info);
            delete.put("Key:" + String.valueOf(l), info);
        }
    }

    public void characters(char ac1[], int k, int l)
        throws SAXException
    {
    }

    public void endElement(String s3, String s4, String s5)
        throws SAXException
    {
    }

    public void endDocument()
        throws SAXException
    {
//        System.out.println("共解析" + append.size() + "个异常信息数据！");
    }

    public Hashtable<String,Info> getInfoEntityByCodeKey()
    {
        return append;
    }

    public Hashtable<String,Info> getInfoEntityByNumberKey()
    {
        return delete;
    }
}
