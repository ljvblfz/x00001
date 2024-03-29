import java.text.CollationKey;
import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TestSort {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> map = new TreeMap<String, String>(new Comparator<Object>(){          
			Collator collator = Collator.getInstance();            
			public int compare(Object o1, Object o2) {               
				CollationKey key1 = collator.getCollationKey(o1.toString());  
				CollationKey key2 = collator.getCollationKey(o2.toString());        
				return key1.compareTo(key2);               
				}});
		for (int i = 0; i < 10; i++) {
			String s = "" + (int) (Math.random() * 1000);
			map.put(s, s);
		}
		map.put("abcd", "abcd");
		map.put("Abc", "Abc");
		map.put("bbb", "bbb");
		map.put("BBBB", "BBBB");
		map.put("北京", "北京");
		map.put("中国", "中国");
		map.put("上海", "上海");
		map.put("厦门", "厦门");
		map.put("香港", "香港");
		map.put("碑海", "碑海");
		Collection col = map.values();
		Iterator it = col.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
