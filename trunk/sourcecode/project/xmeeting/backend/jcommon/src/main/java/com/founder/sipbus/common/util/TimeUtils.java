package com.founder.sipbus.common.util;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <p>
 * Title: Common Utils
 * </p>
 * <p>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: founder
 * </p>
 * 
 * @author Tracy chen
 * @version 1.0
 */

public class TimeUtils {
	//Default date long
	public static final long DEFAULT_DATE = -5364691200000L;

	//"1800-01-01 00:00:00.0"

	//-------------------------------------------------------------date2Calendar
	/**
	 * �NDate�ƾ������ରCalendar����
	 * 
	 * @param date
	 *            Date
	 * @return Calendar
	 */
	public static Calendar date2Calendar(Date date) {
		Calendar cal = null;
		if (date != null) {
			cal = GregorianCalendar.getInstance();
			cal.setTime(date);
		}
		return cal;
	}

	//-------------------------------------------------------------timestamp2Calendar
	/**
	 * �NTimestamp�ƾ������ରCalendar����
	 * 
	 * @param timestamp
	 *            Timestamp
	 * @return Calendar
	 */
	public static Calendar timestamp2Calendar(java.sql.Timestamp timestamp) {
		Calendar cal = null;
		if (timestamp != null) {
			cal = GregorianCalendar.getInstance();
			cal.setTime(timestamp);
		}
		return cal;
	}

	//--------------------------------------------------------getDefaultCalendar
	/**
	 * ��o�q�{���
	 * 
	 * @param timestamp
	 *            Timestamp
	 * @return Calendar
	 */
	public static final Calendar getDefaultCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(DEFAULT_DATE); //"1800-01-01 00:00:00.0"
		return cal;
	}
	
	

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(Date mydate, Date date) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	//-------------------------------------------------------getDefaultTimestamp
	/**
	 * ��o�q�{���
	 * 
	 * @param timestamp
	 *            Timestamp
	 * @return Calendar
	 */
	public static final Timestamp getDefaultTimestamp() {
		return new Timestamp(DEFAULT_DATE); //"1800-01-01 00:00:00.0"
	}

	//------------------------------------------------------------getCurrentDate
	/**
	 * ��o��e���
	 * 
	 * @param timestamp
	 *            Timestamp
	 * @return Calendar
	 */
	public static Calendar getCurrentDate() {
		return Calendar.getInstance();
	}

	//------------------------------------------------------------getCurrentTimestamp
	/**
	 * ��o��e���
	 * 
	 * @param timestamp
	 *            Timestamp
	 * @return Calendar
	 */
	public static Timestamp getCurrentTimestamp() {
		long time = System.currentTimeMillis();
		return new Timestamp(time);
	}

	/**
	 * ��o��e���
	 * <p>
	 * <code>getCurrentTimestamp</code>
	 * </p>
	 * 
	 * @param format
	 * @return
	 * @author david 2004-10-15
	 * @since 1.0
	 */
	public static Timestamp getCurrentTimestamp(String format) {
		if (format == null) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
				format);

		java.util.Date date = null;
		try {
			date = simpleDateFormat.parse(getCurrentTime(format));
		} catch (java.text.ParseException e) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	//-----------------------------------------------------------getCurrentTime
	/**
	 * ��o��e���
	 * 
	 * @param parrten
	 *            ��X�ɶ����榡
	 * @return String ��^�ɶ�
	 */
	public static String getCurrentTime(String parrten) {
		String timestr;
		if (parrten == null || parrten.equals("")) {
			parrten = "yyyy-MM-dd";
		}
		java.util.Date cday = new java.util.Date();

		SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		timestr = sdf.format(cday);
		return timestr;
	}

	//--------------------------------------------------------isDefaultTimestamp
	/**
	 * �P�_Timestamp�ȬO�_�����q�{���
	 * 
	 * @param time
	 *            Timestamp
	 * @return boolean
	 */
	public static boolean isDefaultTimestamp(Timestamp time) {
		if (time.getTime() == DEFAULT_DATE) {
			return true;
		} else {
			return false;
		}
	}

	//--------------------------------------------------------isDefaultTimestamp
	/**
	 * �P�_Timestamp�ȬO�_�����q�{���
	 * 
	 * @param time
	 *            Timestamp
	 * @return boolean
	 */
	public static boolean isDefaultTimestamp(Object time) {
		if (time instanceof java.sql.Timestamp) {
			Timestamp timeValue = (Timestamp) time;
			if (timeValue.getTime() == DEFAULT_DATE) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	//---------------------------------------------------------isDefaultCalendar
	/**
	 * �P�_Calendar�ȬO�_�����q�{���
	 * 
	 * @param time
	 *            Calendar
	 * @return boolean
	 */
	public static boolean isDefaultCalendar(Calendar time) {
		if (time.getTimeInMillis() == DEFAULT_DATE) {
			return true;
		} else {
			return false;
		}
	}

	//-----------------------------------------------------------calendar2String
	/**
	 * �NCalendar�ରString
	 * 
	 * @param pCalendar
	 *            Calendar
	 * @return Timestamp
	 */
	public static String calendar2String(String format, Calendar cal) {
		if (cal.equals(getDefaultCalendar())) {
			return "";
		}

		if (format == null) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);

		if (cal == null) {
			return null;
		}
		return formatter.format(cal.getTime());
	}

	//----------------------------------------------------------string2Timestamp
	/**
	 * �NString�ରTimestamp
	 * 
	 * @param time
	 *            String
	 * @return String
	 */
	public static final Timestamp calendar2Timestamp(Calendar cal) {

		java.util.Date date = null;
		date = cal.getTime();

		return new Timestamp(date.getTime());
	}

	/**
	 * �NDate�ରString
	 * <p>
	 * <code>Date2String</code>
	 * </p>
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @author frank 2004-9-14
	 * @since 1.0
	 */
	public static String date2String(String format, Date date) {
		if (date.equals(getDefaultCalendar())) {
			return "";
		}

		if (format == null|| format.equals("")) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);

		if (date == null) {
			return null;
		}
		return formatter.format(date);
	}

	//----------------------------------------------------------timestamp2String
	/**
	 * �NTimestamp�ରString
	 * 
	 * @param pCalendar
	 *            Calendar
	 * @return Timestamp
	 */
	public static String timestamp2String(String format, Timestamp time) {
		if (time.equals(getDefaultTimestamp())) {
			return "";
		}

		if (format == null) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);

		if (time == null) {
			return null;
		}
		return formatter.format(time);
	}

	//----------------------------------------------------------string2Timestamp
	/**
	 * �NString�ରTimestamp
	 * 
	 * @param time
	 *            String
	 * @return String
	 */
	public static final Timestamp string2Timestamp(String format, String time) {
		if (format == null) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
				format);

		java.util.Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (java.text.ParseException e) {
			return null;
		}
		return new Timestamp(date.getTime());
	}


	public static final Date string2Date(String format, String time) {
		if (format == null) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
				format);

		java.util.Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (java.text.ParseException e) {
			return null;
		}
		return date;
	}
	
	public static final Date string2DateExp(String format, String time) throws ParseException {
		if (format == null) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
				format);

		java.util.Date date = null;
		date = simpleDateFormat.parse(time);
		return date;
	}
	
	//-----------------------------------------------------------string2Calendar
	/**
	 * �NString�ରCalendar
	 * 
	 * @param dateString
	 *            String
	 * @return Timestamp
	 */
	public static final Calendar string2Calendar(String format, String cal) {
		if (format == null||"".equals(format)) {
			format = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
				format);
		java.util.Date date = null;
		try {
			date = simpleDateFormat.parse(cal);
		} catch (java.text.ParseException e) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	//--------------------------------------------------------------getMonthDays
	/**
	 * ��o�C�몺�Ѽ�
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @return int
	 */
	public static final int getMonthDays(int year, int month) {
		switch (month) {
		case 1:
			return 31;
		case 2:
			if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
				return 29;
			} else {
				return 28;
			}
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		}
		return 0;
	}
	
	public static final int getMonthDays(Date date) {
		return getMonthDays(date.getYear(),date.getMonth()+1);
	}

	
	public static final Date getMonthLastDay(Date date) {
		return addDays(string2Date("yyyyMMdd",date2String("yyyyMM", date)+getMonthDays(date)),0);
	}
	
	public static final Date getMonthFirstDay(Date date) {
		return string2Date("yyyyMMdd",date2String("yyyyMM", date)+"01");
	}
	
	/**
	 * 获取最近银行结息日
	 *
	 * @param date
	 * @return
	 * @author 陈文智
	 */
	public static final Date getBankProfitDay(Date date) {
		date=addDays(date, 1);
		while(!date2String("MMdd", date).endsWith("321")&&!date2String("MMdd", date).endsWith("621")
				&&!date2String("MMdd", date).endsWith("921")
				&&!date2String("MMdd", date).endsWith("1221")
				){
			date=addDays(date, 1);
		}
		return date;
	}
	
	
	public static boolean dayeq(Date d1,Date d2){
		return date2String(null, d1).equals(date2String(null,d2));
	}
	
	public static final Date getCurrentSesonEnd(Date date) {
		Date thisMonthDay=string2Date("yyyyMMdd",date2String("yyyyMM", date)+"31");
		String dayEnd=date2String("MMdd", thisMonthDay);
		while(!dayEnd.endsWith("331")&&!dayEnd.endsWith("630")&&!dayEnd.endsWith("930")  
				&&!dayEnd.endsWith("1231")){
			thisMonthDay=addMonths(thisMonthDay, 1);
			dayEnd=date2String("MMdd", thisMonthDay);
		}
		return thisMonthDay;
	}

	//---------------------------------------------------------------getDaysDiff
	/**
	 * ��o��� sdate2-sdate1 ���t�ȡA�p�G��n榡����h��^-1
	 * 
	 * @param sdate1
	 *            String
	 * @param sdate2
	 *            String
	 * @param format
	 *            String Date��format�榡
	 * @param tz
	 *            TimeZone
	 * @return int ��o��� sdate2-sdate1 ���t�ȡA�p�G��n榡����h��^-1
	 */
	public static int getDaysDiff(String sdate1, String sdate2, String format,
			java.util.TimeZone tz) {
		SimpleDateFormat df = new SimpleDateFormat(format);

		java.util.Date date1 = null;
		java.util.Date date2 = null;

		try {
			date1 = df.parse(sdate1);
			date2 = df.parse(sdate2);
		} catch (java.text.ParseException pe) {
			return -1;
		}

		Calendar cal1 = null;
		Calendar cal2 = null;

		if (tz == null) {
			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
		} else {
			cal1 = Calendar.getInstance(tz);
			cal2 = Calendar.getInstance(tz);
		}

		// different date might have different offset
		cal1.setTime(date1);
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET)
				+ cal1.get(Calendar.DST_OFFSET);

		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET)
				+ cal2.get(Calendar.DST_OFFSET);

		// Use integer calculation, truncate the decimals
		int hr1 = (int) (ldate1 / 3600000); //60*60*1000
		int hr2 = (int) (ldate2 / 3600000);

		int days1 = (int) hr1 / 24;
		int days2 = (int) hr2 / 24;
		int dateDiff = days2 - days1;
		return dateDiff;
		//        int dateDiff = days2 - days1;
		//        int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) -
		//                          cal1.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
		//        int weekDiff = dateDiff / 7 + weekOffset;
		//        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		//        int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) -
		//                        cal1.get(Calendar.MONTH);
	}

	/**
	 * 
	 * <p>
	 * <code>getWeek</code>
	 * </p>
	 * 
	 * @param num
	 * @return
	 * @author Ginger 2004-10-20
	 * @since 1.0
	 */
	public static Calendar getWeek(int num) {

		Calendar date = Calendar.getInstance();
		int weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
		weekOfYear += num;
		date.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		return date;
	}

	/**
	 * 
	 * <p>
	 * <code>getWeek</code>
	 * </p>
	 * 
	 * @param num
	 * @return
	 * @author Ginger 2004-10-20
	 * @since 1.0
	 */
	public static Calendar getBeforeWeek(int num) {

		Calendar date = Calendar.getInstance();
		int weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
		weekOfYear -= num;
		date.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		return date;
	}
	
	

	/**
	 * 
	 * <p>
	 * <code>addDays</code>
	 * </p>
	 * 
	 * @param num
	 * @since 1.0
	 */
	public static Date addDays(Date oldDate,int amount) {
		Calendar date = date2Calendar(oldDate);
		date.add(Calendar.DATE, amount);
		return calendar2Timestamp(date);
	}

	/**
	 * 
	 * <p>
	 * <code>addDays</code>
	 * </p>
	 * 
	 * @param num
	 * @since 1.0
	 */
	public static Date addMinute(Date oldDate,int amount) {
		Calendar date = date2Calendar(oldDate);
		date.add(Calendar.MINUTE, amount);
		return calendar2Timestamp(date);
	}

	public static Date addMonths(Date oldDate,int amount) {
		Calendar date = date2Calendar(oldDate);
		date.add(Calendar.MONTH, amount);
		return calendar2Timestamp(date);
	}
	
	public static Date addMonthsEndDay(Date oldDate,int amount) {
		Calendar date = date2Calendar(oldDate);
		date.add(Calendar.MONTH, amount);
		return getMonthLastDay(calendar2Timestamp(date));
	}
	public static Date addSeson(Date oldDate,int amount) {
		return addMonths(oldDate,3*amount);
	}
	public static Date addSesonEndDay(Date oldDate,int amount) {
		return getMonthLastDay(addMonths(oldDate,3*amount));
	}
	public static Date addHalfYear(Date oldDate,int amount) {
		return addMonths(oldDate,6*amount);
	}
	public static Date addYear(Date oldDate,int amount) {
		Calendar date = date2Calendar(oldDate);
		date.add(Calendar.YEAR, amount);
		return calendar2Timestamp(date);
	}
	
	
	
	/**
	 * 
	 * <p>
	 * <code>getMonth</code>
	 * </p>
	 * 
	 * @param num
	 * @return
	 * @author Ginger 2004-10-20
	 * @since 1.0
	 */
	public static Calendar getMonth(int num) {

		Calendar date = Calendar.getInstance();
		int monthOfYear = date.get(Calendar.MONTH);
		monthOfYear += num;
		date.set(Calendar.MONTH, monthOfYear);
		return date;
	}

	/**
	 * 
	 * <p>
	 * <code>getMonth</code>
	 * </p>
	 * 
	 * @param num
	 * @return
	 * @author Ginger 2004-10-20
	 * @since 1.0
	 */
	public static Calendar getBeforeMonth(int num) {

		Calendar date = Calendar.getInstance();
		int monthOfYear = date.get(Calendar.MONTH);
		monthOfYear += num;
		date.set(Calendar.MONTH, monthOfYear);
		return date;
	}

	/**
	 * getDay
	 * <p>
	 * <code>getDay</code>
	 * </p>
	 * 
	 * @param num
	 * @return
	 * @author Ginger 2004-10-20
	 * @since 1.0
	 */
	public static Calendar getDay(int num) {

		Calendar date = Calendar.getInstance();
		int dayOfYear = date.get(Calendar.DATE);
		dayOfYear += num;
		date.set(Calendar.DATE, dayOfYear);
		return date;
	}

	/**
	 * �NDate�ƾ������ରTimestamp�����A�ɶ��O0�I
	 * <p>
	 * <code>date2TimestampStart</code>
	 * </p>
	 * 
	 * @param date
	 *            Date
	 * @return Timestamp
	 * @author Joe 2004-8-15
	 * @since 1.0
	 */
	public static Timestamp date2TimestampStart(Date date) {
		return new Timestamp(date.getTime());
	} // end date2TimestampStart

	/**
	 * �NDate�ƾ������ରTimestamp�����A�ɶ��O24�I
	 * <p>
	 * <code>date2TimestampEnd</code>
	 * </p>
	 * 
	 * @param date
	 *            Date
	 * @return Timestamp
	 * @author Joe 2004-8-15
	 * @since 1.0
	 */
	public static Timestamp date2TimestampEnd(Date date) {
		Calendar cal = null;
		if (date != null) {
			cal = GregorianCalendar.getInstance();
			cal.setTime(date);
		}
		cal.add(Calendar.DAY_OF_YEAR, 1);

		return new Timestamp(cal.getTimeInMillis());
	} // end date2TimestampEnd

	/**
	 * �NDate�ƾ������ରTimestamp�����A�ɶ��O24�I
	 * <p>
	 * <code>getDateString</code>
	 * </p>
	 * 
	 * @param date
	 *            Date
	 * @return String
	 * @author Ginger 2005-4-21
	 * @since 1.0
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	} // end getDateString

	/**
	 * �NDate�ƾ������ରTimestamp�����A�ɶ��O24�I
	 * <p>
	 * <code>getCurrentDayWithSpecialPattern</code>
	 * </p>
	 * 
	 * @return String
	 * @author Ginger 2005-4-21
	 * @since 1.0
	 */
	public static String getCurrentDayWithSpecialPattern() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		String today = "";
		if (hour >= 12) {
			today = calendar2String("yyyy-MM-dd hh:mm", c);
		} else {

			today = calendar2String("yyyy-MM-dd hh:mm", c);
		}
		return today;
	} // end getCurrentDayWithSpecialPattern
	
	public static String getChineseSeason(int month) {
		String season = null;
		if(month == 1 || month == 2 || month == 3) {
			season = "第一季度";
		}else if(month == 4 || month == 5 || month == 6) {
			season = "第二季度";
		}else if(month == 7 || month == 8 || month == 9) {
			season = "第三季度";
		}else {
			season = "第四季度";
		}
		
		return season;
	}
	

    public static void main(String[] args ) {
    	 Calendar cal = Calendar.getInstance();
    	 System.out.println("今天的日期: " + Calendar.YEAR);
    	  
    	 System.out.println("本周末: " + cal.getTime());
    	    }

    
//    public static Date getWeekDate(int day){
//    	Calendar cal = Calendar.getInstance();
//    	
//    	return d
//    }
}
