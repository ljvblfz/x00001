package com.founder.core.debug;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapDebugTool {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static String getDebugInfoFromMap(Map<String,?> map){
		StringBuilder sb=new StringBuilder();
		if(null==map){
			return "The map is null.";
		}
		
		Set<String> keySet=map.keySet();
		Iterator<String> keyIter=keySet.iterator();
		while(keyIter.hasNext()){
			String key=keyIter.next();
			Object value=map.get(key);
			if(value instanceof String[]){
				String[] arrValue=(String[]) value;
				StringBuilder sbValue=new StringBuilder();
				for(int i=0;i<arrValue.length;i++){
					String strValue=arrValue[i];
					sbValue.append(strValue);
					if(i<arrValue.length-1){ 
						sbValue.append(",");
					}
					sb.append("[ "+key+" ==> "+sbValue.toString()+" ]\n");
				}
			}else{
				sb.append("[ "+key+" ==> "+value+" ]\n");
			}
		} 
		return sb.toString();
	}
}
