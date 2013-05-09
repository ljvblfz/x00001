package com.founder.sipbus.common.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.collections.map.ListOrderedMap;

public class ExcelUtil {
	public static void export(String title, ListOrderedMap titleColumns,
			List<?> datas, OutputStream os) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);

		WritableSheet sheet = workbook.createSheet(title, 0);
		sheet.mergeCells(0, 0, 0, 0);

		writeTitle(sheet, title, titleColumns);
		writeTable(sheet, datas, titleColumns);

		workbook.write();
		workbook.close();
		os.close();
	}

	private static void writeTitle(WritableSheet sheet, String title,
			ListOrderedMap titleColumns) throws RowsExceededException,
			WriteException, UnsupportedEncodingException {
		Label labelTitle = new Label(0, 0, title);
		sheet.addCell(labelTitle);

		int column = 0;
		// List keyList = titleColumns.valueList();
		List keyList = new ArrayList();
		keyList.addAll(titleColumns.keySet());
		for (int i = 0; i < titleColumns.size(); i++) {
			labelTitle = new Label(column++, 1, (String) keyList.get(i));
			sheet.addCell(labelTitle);
		}
	}

	private static void writeTable(WritableSheet sheet, List<?> list,
			 ListOrderedMap titleColumns) throws Exception {
		int row = 2;
//		if (obj != null) {
//			PropertyDescriptor[] props = Introspector.getBeanInfo(
//					obj.getClass(), Object.class).getPropertyDescriptors();
			List keyList = new ArrayList();
			// titleColumns.keySet().toArray()
			keyList.addAll(titleColumns.values());
			for (int i = 0; i < list.size(); i++) {
				WriteRowDataByObject(sheet, list.get(i), keyList, row);
				row++;
			}
//		} else {
//			 List keyList = titleColumns.keyList();
//			List keyList = new ArrayList();
//			// titleColumns.keySet().toArray()
//			keyList.addAll(titleColumns.keySet());
//			for (int i = 0; i < list.size(); i++) {
//				HashMap map = (HashMap) list.get(i);
//				WriteRowData(sheet, map, keyList, row);
//				row++;
//			}
//		}
	}

	private static void WriteRowDataByObject(WritableSheet sheet, Object obj,
			List<String> titleList,  int row)
			throws Exception {
		int len = titleList.size();
		for (int column = 0; column < len; column++) {
			String keyTitle = (String) titleList.get(column);
			String[] key = keyTitle.split("\\.");
			String value = "";
			PropertyDescriptor property = new PropertyDescriptor(key[0],
					obj.getClass());
			if ((key.length == 1)
					&& ((property.getName() == key[0]) || (property.getName()
							.equals(key[0])))
					&& (property.getReadMethod().invoke(obj, new Object[0]) != null)) {
				if (property.getPropertyType().equals(Date.class))
					value = DateUtil.formatDate((Date) property.getReadMethod()
							.invoke(obj, new Object[0]));
				else {
					value = property.getReadMethod().invoke(obj, new Object[0])
							.toString();
				}

			}

			if (key.length == 2) {
				Object object = property.getReadMethod().invoke(obj,
						new Object[0]);
				if (object != null) {
					PropertyDescriptor[] propertys = Introspector.getBeanInfo(
							object.getClass(), Object.class)
							.getPropertyDescriptors();
					for (PropertyDescriptor prop : propertys) {
						if (((prop.getName() != key[1]) && (!prop.getName()
								.equals(key[1])))
								|| (prop.getReadMethod().invoke(object,
										new Object[0]) == null))
							continue;
						if (prop.getPropertyType().equals(Date.class))
							value = DateUtil.formatDate((Date) prop
									.getReadMethod().invoke(object,
											new Object[0]));
						else {
							value = prop.getReadMethod()
									.invoke(object, new Object[0]).toString();
						}
					}
				}

			}

			if (key.length == 3) {
				Object object = property.getReadMethod().invoke(obj,
						new Object[0]);
				if (object != null) {
					PropertyDescriptor[] propertys = Introspector.getBeanInfo(
							object.getClass(), Object.class)
							.getPropertyDescriptors();
					for (PropertyDescriptor prop : propertys) {
						if ((prop.getName() == key[1])
								|| (prop.getName().equals(key[1]))) {
							Object o = prop.getReadMethod().invoke(object,
									new Object[0]);
							if (o != null) {
								PropertyDescriptor[] pros = Introspector
										.getBeanInfo(o.getClass(), Object.class)
										.getPropertyDescriptors();
								for (PropertyDescriptor pro : pros) {
									if (((pro.getName() != key[2]) && (!pro
											.getName().equals(key[2])))
											|| (pro.getReadMethod().invoke(o,
													new Object[0]) == null))
										continue;
									if (pro.getPropertyType()
											.equals(Date.class))
										value = DateUtil.formatDate((Date) pro
												.getReadMethod().invoke(o,
														new Object[0]));
									else {
										value = pro.getReadMethod()
												.invoke(o, new Object[0])
												.toString();
									}
								}
							}
						}
					}
				}

			}

			sheet.addCell(new Label(column, row, value));
		}
	}

	private static void WriteRowData(WritableSheet sheet,
			HashMap<String, Object> obj, List<String> titleList, int row)
			throws Exception {
		int len = obj.size();
		for (int column = 0; column < len; column++) {
			String keyTitle = (String) titleList.get(column);
			String value = (String) obj.get(keyTitle);
			sheet.addCell(new Label(column, row, value));
		}
	}
}