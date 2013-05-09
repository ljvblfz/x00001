package com.founder.sipbus.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class PMGridCopyUtil {

	@SuppressWarnings("unchecked")
	public static boolean copyGridToDto(Object dto, Map map) {
		// String str1 = dto + ".";
		BeanInfo localBeanInfo = null;
		PropertyDescriptor[] arrayOfPropertyDescriptor = null;
		try {
			localBeanInfo = Introspector.getBeanInfo(dto.getClass(),
					Object.class);
		} catch (IntrospectionException localIntrospectionException) {
			localIntrospectionException.printStackTrace();
		}
		arrayOfPropertyDescriptor = localBeanInfo.getPropertyDescriptors();
		for (int i = 0; i < arrayOfPropertyDescriptor.length; ++i) {
			String str2 = arrayOfPropertyDescriptor[i].getName();
			// str2 = str1 + str2;
			Object object3 = null;
			if (!map.containsKey(str2))
				continue;
			object3 = map.get(str2);
			Class localClass = arrayOfPropertyDescriptor[i].getPropertyType();
			copyProperties(dto, arrayOfPropertyDescriptor, i, object3,
					localClass);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private static void copyProperties(Object dto,
			PropertyDescriptor[] arrayOfPropertyDescriptor, int i,
			Object object3, Class localClass) {
		try {
			if (null == object3) {
				arrayOfPropertyDescriptor[i].getWriteMethod().invoke(dto, new Object[]{null});
			} else {
				if (localClass.equals(Date.class))

					if (object3.toString().length() == 19)
						arrayOfPropertyDescriptor[i].getWriteMethod()
						.invoke(dto,
								new Object[] { TimeUtils.string2Date(
										"yyyy-MM-dd HH:mm:ss",
										object3.toString()) });
					else if(object3.toString().length() == 16)
						arrayOfPropertyDescriptor[i].getWriteMethod()
						.invoke(dto,
								new Object[] { TimeUtils.string2Date(
										"yyyy-MM-dd HH:mm",
										object3.toString()) });
					
					else if (object3.toString().length() == 10)
						arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
								dto,
								new Object[] { TimeUtils.string2Date(
										"yyyy-MM-dd", object3.toString()) });
					else if (object3.toString().length() == 8)
						arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
								dto,
								new Object[] { TimeUtils.string2Date(
										"yyyy-MM-dd HH:mm:ss",TimeUtils.date2String("yyyy-MM-dd", new Date()) +" "+ object3.toString()) });
					else if (object3.toString().length() == 5)
						arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
								dto,
								new Object[] { TimeUtils.string2Date(
										"yyyy-MM-dd HH:mm", TimeUtils.date2String("yyyy-MM-dd", new Date()) +" "+ object3.toString()) });
					else
						arrayOfPropertyDescriptor[i].getWriteMethod()
								.invoke(dto,
										new Object[] { TimeUtils.string2Date(
												"yyyy-MM-dd HH:mm",
												object3.toString()) });
				else if (localClass.equals(String.class))
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { object3 == null ? null : object3
									.toString() });
				else if ((localClass == Integer.class)
						|| (localClass == Integer.TYPE))
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { (object3.toString().trim()
									.equals("")) ? null : Integer
									.valueOf(Integer.parseInt(object3
											.toString())) });
				else if ((localClass == Short.class)
						|| (localClass == Short.TYPE))
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { (object3.toString().trim()
									.equals("")) ? null : Short.valueOf(Short
									.parseShort(object3.toString())) });
				else if ((localClass == Long.class)
						|| (localClass == Long.TYPE))
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { (object3.toString().trim()
									.equals("")) ? null : Long.valueOf(Long
									.parseLong(object3.toString())) });
				else if ((localClass == Float.class)
						|| (localClass == Float.TYPE))
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { (object3.toString().trim()
									.equals("")) ? null : Float.valueOf(Float
									.parseFloat(object3.toString())) });
				else if ((localClass == Double.class)
						|| (localClass == Double.TYPE))
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { (object3.toString().trim()
									.equals("")) ? null : Double.valueOf(Double
									.parseDouble(object3.toString())) });
				else if (localClass == BigDecimal.class)
					arrayOfPropertyDescriptor[i].getWriteMethod().invoke(
							dto,
							new Object[] { (object3.toString().trim()
									.equals("")) ? null : BigDecimal
									.valueOf(Double.parseDouble(object3
											.toString())) });
			}
		} catch (Exception ae) {
			ae.printStackTrace();
		}
	}
}