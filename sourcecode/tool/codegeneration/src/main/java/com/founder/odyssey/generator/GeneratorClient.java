/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */

package com.founder.odyssey.generator;

/**
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class GeneratorClient {

	public static void main(String[] args) throws Exception {
		OdysseyGenerator pg = new OdysseyGenerator();
		pg.clean();     
		///
		pg.generateCRUDByTable("PBS_DISPATCH_BUS_ORDER","dispatch","pbs","3");     
		pg.generateCRUDByTable("PBS_RATING_TIME","dispatch","pbs","3");     
		pg.generateCRUDByTable("PBS_SCHEDULE_SOLUTION","dispatch","pbs","3");     
		pg.generateCRUDByTable("PBS_SCHEDULE_SOLUTION_DETAIL","dispatch","pbs","3");     
		 
		
		browsePath("d:/codegen/dispatch");
		
	}

	public static void browsePath(String strPath) {
		String[] execString = new String[2];
		String filePath = null;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().startsWith("windows")) {
			// Window System;
			execString[0] = "explorer";
			try {
				filePath = strPath.replace("/", "\\");
			} catch (Exception ex) {
				filePath = strPath;
			}
		} else {
			// Unix or Linux;
			execString[0] = "netscape";
			filePath = strPath;
		}
		execString[1] = filePath;
		try {
			Runtime.getRuntime().exec(execString);
			// Runtime.getRuntime().exec("exit");
		} catch (Exception ex) {
			System.out.println("瀵倸鐖堕崯锟�..");
		}
	}
}
