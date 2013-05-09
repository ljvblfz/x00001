package com.founder.sipbus.common.exception;

import java.util.Hashtable;
/**
 * 
 * @author Tracy chen
 *
 */
public class InfoXMLImpl
{

    private String append;
    private Hashtable<String,Info> get;
    private Hashtable<String,Info> getInfoEntityByCodeKey;
    private static InfoXMLImpl getInfoEntityByNumberKey;

    public InfoXMLImpl()
    {
    }

    public InfoXMLImpl(String s)
    {
        append = s;
    }

    public void finalize()
    {
    }

    public synchronized Info getInfoByNumber(long l)
        throws Exception
    {
        if(getInfoEntityByCodeKey == null)
        {
            InfoXMLSAX infoxmlsax = new InfoXMLSAX();
            infoxmlsax.parse(append);
            getInfoEntityByCodeKey = infoxmlsax.getInfoEntityByNumberKey();
        }
        return (Info)getInfoEntityByCodeKey.get("Key:" + String.valueOf(l));
    }

    public synchronized Info getInfoByCode(String s)
        throws Exception
    {
        if(get == null)
        {
            InfoXMLSAX infoxmlsax = new InfoXMLSAX();
            infoxmlsax.parse(append);
            get = infoxmlsax.getInfoEntityByCodeKey();
        }
        return (Info)get.get(s);
    }

    public static synchronized InfoXMLImpl getInstance(String s)
    {
        if(getInfoEntityByNumberKey == null)
            getInfoEntityByNumberKey = new InfoXMLImpl(s);
        return getInfoEntityByNumberKey;
    }

    public String getInfoXMLPath()
    {
        return append;
    }
}
