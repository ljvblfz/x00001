package com.founder.sipbus.common.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	// 用来全局控制 上一周，本周，下一周的周数变化
	private static int weeks = 0;
	private static int MaxDate;// 一月最大天数
	private static int MaxYear;// 一年最大天数
	
	public static int FIFTEEN_DAY= 15; //15天
	public static int ONE_MONTH=1; //1个月
	public static int THREE_MONTH= 3; //3个月
	public static int SIX_MONTH=6; //6个月
	public static int TWELVE_MONTH=12; //12个月

	

	  public static int getDayOffset(DateUtil dateX, DateUtil dateY) {
	    Calendar calX = Calendar.getInstance();
	    calX.setTime(dateX.getValue());
	    calX.set(11, 0);
	    calX.set(12, 0);
	    calX.set(13, 0);
	    calX.set(14, 0);

	    Calendar calY = Calendar.getInstance();
	    calY.setTime(dateY.getValue());
	    calY.set(11, 0);
	    calY.set(12, 0);
	    calY.set(13, 0);
	    calY.set(14, 0);

	    long secondOffset = calY.getTime().getTime() - calX.getTime().getTime();

	    long dayOffset = secondOffset / 86400000L;

	    return new Long(dayOffset).intValue();
	  }

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	public static String date2Str(Date date){
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return myFormatter.format(date);
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}
	
	
	public static long getDays(Date date1, Date date2) {
		if (date1 == null)
			return 0;
		if (date2 == null)
			return 0;
		
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	// 当前日期加上n天后日期,返回字符串
	public static String getPlusDay(int amount) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, amount);

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	//指定日期加上n天后日期,返回字符串
	public static String getPlusDay(Date theDate,int amount) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(theDate);
		lastDate.add(Calendar.DATE, amount);

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	//指定日期加上n个月后日期,返回字符串
	public static String getPlusMonth(Date theDate,int months){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(theDate);
		lastDate.add(Calendar.MONTH, months); 

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	//指定日期加上n个月后日期,返回字符串
	public static String getPlusYear(Date theDate,int years){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(theDate);
		lastDate.add(Calendar.YEAR, years);

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	//获得指定日期的年份,返回字符串
	public static int getYear(Date theDate){
		int i_year= -1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
		String str= sdf.format(theDate);
		i_year= str==null?i_year:Integer.parseInt(str);
		
		return i_year;
	}

	// 上月第一天
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public static String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public static String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public static String getSaturday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public static String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public static String getPreviousWeekday() {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public static String getNextMonday() {
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public static String getNextSunday() {

		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private static int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得上月最后一天的日期
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public static String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public static String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public static String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// 获得本年有多少天
	private static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public static String getPreviousYearEnd() {
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	// 获得本季度
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * 获取季度
	 * 
	 * @param month
	 *            月
	 * @return
	 */
	public static int getSeason(int month) {
		if(month == 1 || month == 2 || month == 3) {
			return 1;
		}else if(month == 4 || month == 5 || month == 6) {
			return 2;
		}else if(month == 7 || month == 8 || month == 9) {
			return 3;
		}if(month == 10 || month == 11 || month == 12) {
			return 4;
		}
		return 0;
	}
	
	//比较首日期 - 次日期后,是否小于次日期 + n个月后的日期
	public static boolean isLessThanTheMonths(Date firstDate,Date secondDate,int months){
		boolean status=false;
		try{
			String strDate= DateUtils.getPlusMonth(secondDate,months);
			Date dDate= new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			if(DateUtils.getDays(firstDate, secondDate)< DateUtils.getDays(dDate, secondDate)){
				status= true;
			}
			else{
				status= false;
			}
		}
	    catch(Exception e){
	    	
	    }
		return status;
	}
	
	public static String date2ChineseStr(Date date){
		StringBuffer buffer = new StringBuffer("");
		if(date != null){
			String dateStr = date2Str(date);
			String[] st = dateStr.split("-");
			if(st.length>2){
				buffer.append(st[0]).append("年").append(st[1]).append("月").append(st[2]).append("日");
			}
		}
		
		return buffer.toString();
	}
	
	 /**   
     * @param args   
     */   
    public static void main(String[] args) {    
        /*System.out.println("获取当天日期:"+getNowTime("yyyy-MM-dd"));    
        System.out.println("获取当天日期:"+getPlusDay(7));
        System.out.println("获取本周一日期:"+getMondayOFWeek());    
        System.out.println("获取本周日的日期~:"+getCurrentWeekday());    
        System.out.println("获取上周一日期:"+getPreviousWeekday());    
        System.out.println("获取上周日日期:"+getPreviousWeekSunday());    
        System.out.println("获取下周一日期:"+getNextMonday());    
        System.out.println("获取下周日日期:"+getNextSunday());    
        System.out.println("获得相应周的周六的日期:"+getNowTime("yyyy-MM-dd"));    
        System.out.println("获取本月第一天日期:"+getFirstDayOfMonth());    
        System.out.println("获取本月最后一天日期:"+getDefaultDay());    
        System.out.println("获取上月第一天日期:"+getPreviousMonthFirst());    
        System.out.println("获取上月最后一天的日期:"+getPreviousMonthEnd());    
        System.out.println("获取下月第一天日期:"+getNextMonthFirst());    
        System.out.println("获取下月最后一天日期:"+getNextMonthEnd());    
        System.out.println("获取本年的第一天日期:"+getCurrentYearFirst());    
        System.out.println("获取本年最后一天日期:"+getCurrentYearEnd());    
        System.out.println("获取去年的第一天日期:"+getPreviousYearFirst());    
        System.out.println("获取去年的最后一天日期:"+getPreviousYearEnd());    
        System.out.println("获取明年第一天日期:"+getNextYearFirst());    
        System.out.println("获取明年最后一天日期:"+getNextYearEnd());    
        System.out.println("获取本季度第一天到最后一天:"+getThisSeasonTime(11));    
        System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"+DateUtils.getTwoDay("2008-12-1","2008-9-29"));
        System.out.println("获取两个日期之间间隔天数:"+DateUtils.getDays(strToDate("2011-07-18"),strToDate(getNowTime("yyyy-MM-dd"))));
        System.out.println("获取日期:"+date2Str(new Date()));
        
        System.out.println("获取年"+getNowTime("yyyy"));
        System.out.println("获取月:"+getNowTime("MM"));
        System.out.println("获取季度:"+getSeason(Integer.parseInt(getNowTime("MM"))));
        
        System.out.println("指定日期加3个月:" + getPlusMonth(new Date(),6));
        System.out.println("当前年份:" + getYear(new Date()));
        System.out.println("减去n天后日期: " + getPlusDay(new Date(),-5));*/
    	System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"+DateUtils.getTwoDay("2008-12-2","2008-12-1"));
    	
    	System.out.println(DateUtils.getDays("2011-06-21", "2011-03-21"));
    	System.out.println(DateUtils.getDays("2011-09-21", "2011-06-21"));
    	System.out.println(DateUtils.getDays("2011-12-21", "2011-09-21"));
    	System.out.println(DateUtils.getDays("2012-03-21", "2011-12-21"));
    }    
}
