
<%
	//com.founder.sipbus.common.memcache.util.StdMemCacheUtil.getMemCachedClient().delete( "T_DICTIONARY_DISPATCHreasons");

	String key=request.getParameter("key");

	if(null!=key)
	com.founder.sipbus.common.memcache.util.StdMemCacheUtil.getMemCachedClient().delete(key);

%>