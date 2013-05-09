package com.founder.sipbus.common.exception;

import java.util.Hashtable;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 * 
 * @author Tracy chen
 *
 */
public class InfoXMLSAX
{

    private Hashtable<String,Info> getInfoEntityByCodeKey;
    private Hashtable<String,Info> getInfoEntityByNumberKey;

    public InfoXMLSAX()
    {
        getInfoEntityByCodeKey = null;
        getInfoEntityByNumberKey = null;
    }

    public void parse(String s)
        throws Exception
    {
        InfoXMLParser infoxmlparser = new InfoXMLParser();
        SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
        saxparserfactory.setNamespaceAware(false);
        saxparserfactory.setValidating(false);
        SAXParser saxparser = saxparserfactory.newSAXParser();
        try
        {
            saxparser.parse(s, infoxmlparser);
            getInfoEntityByCodeKey = infoxmlparser.getInfoEntityByCodeKey();
            getInfoEntityByNumberKey = infoxmlparser.getInfoEntityByNumberKey();
        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        finally
        {
        }
    }

    public Hashtable<String,Info> getInfoEntityByCodeKey()
    {
        return getInfoEntityByCodeKey;
    }

    public Hashtable<String,Info> getInfoEntityByNumberKey()
    {
        return getInfoEntityByNumberKey;
    }
}
