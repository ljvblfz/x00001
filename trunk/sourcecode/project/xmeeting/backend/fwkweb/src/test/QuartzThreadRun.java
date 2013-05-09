

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class QuartzThreadRun extends Thread{
	
	public void run(){
		//String doubleentryLogFile = AxaProperties.getInstance().getProperty(ConstantUtil.LOG_PROPERTY_FILE );
		PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties") );
	
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"spring/applicationContext-resource.xml",
						"spring/applicationContext-common.xml"
				});
		
		
//		System.out.println(((BizFeeTypeDaoImpl)context.getBean("bizFeeTypeDao")).findAll());
	}
	
	public static void main(String[] args) {
		
		List<String> l=new ArrayList<String>();
		l.add("123");
		l.add("asdf");
		l.add("阿斯顿发");
		System.out.println(JSONArray.fromObject(l));
		
//		QuartzThreadRun t=new QuartzThreadRun();
//		t.start();
	}
	
	
	
}
