package com.founder.sipbus.syweb.cck.util;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.collections.map.ListOrderedMap;
 

/**
 * cckexcel导出类
 * @author zjl
 *
 */
public class CckExcelUtil  {
 
	private static void writeTable(WritableSheet sheet, List<?> list,
			 ListOrderedMap titleColumns) throws Exception {
			int row = 2;
			List keyList = new ArrayList();
			keyList.addAll(titleColumns.values());
			for (int i = 0; i < list.size(); i++) {
				WriteRowDataByMap(sheet, (Map) list.get(i), keyList, row);
				row++;
			}
 
	}
	
	/**
	 *	将map数据写入excel
	 *  @param sheet
	 *  @param obj
	 *  @param titleList
	 *  @param row
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:50:10 
	 */
	private static void WriteRowDataByMap (WritableSheet sheet,
			Map obj, List titleList, int row)throws Exception {
		int len = titleList.size();
		for (int column = 0; column < len; column++) {
			String keyTitle = (String) titleList.get(column);
//			String[] key = keyTitle.split("\\.");
			String value="";
			Object object = obj.get(keyTitle);
			if (null!=object) {
				value=object.toString();
			}

			sheet.addCell(new Label(column, row, value));
		}
		
	}
	/**
	 *main 方法
	 * <p>方法说明:</p>
	 *
	 *  @param args
	 *  
	 * @author zjl 
	 * @date : 2013-3-5 下午3:06:30 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
// 		CckExcelUtil.export(title, titleColumns, datas, os)
	}
	/**
	 *导出数据 
	 *  @param title 标题
	 *  @param titleColumns 表头
	 *  @param datas 数据
	 *  @param os 
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:49:17 
	 */
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
	
	/**
	 *添加 title
	 *  @param sheet
	 *  @param title
	 *  @param titleColumns
	 *  @throws RowsExceededException
	 *  @throws WriteException
	 *  @throws UnsupportedEncodingException
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:49:54 
	 */
	private static void writeTitle(WritableSheet sheet, String title,
			ListOrderedMap titleColumns) throws RowsExceededException,
			WriteException, UnsupportedEncodingException {
		Label labelTitle = new Label(0, 0, title);
		sheet.addCell(labelTitle);

		int column = 0;
		List keyList = new ArrayList();
		keyList.addAll(titleColumns.keySet());
		for (int i = 0; i < titleColumns.size(); i++) {
			labelTitle = new Label(column++, 1, (String) keyList.get(i));
			sheet.addCell(labelTitle);
		}
	}
}
