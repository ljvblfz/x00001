/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   DateUtil.java

package com.founder.sipbus.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil
{

    public static Date parseDate(String value, String pattern)
        throws Exception
    {
        if(value == null || "".equals(value))
            return null;
        try {
        	SimpleDateFormat format;
            format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return format.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception((new StringBuilder("\u65E5\u671F\u8F6C\u6362\u51FA\u9519\uFF1A")).append(value).toString());
		}
    }

    public static String formatDate(Date date, String pattern)
    {
        if(date == null)
        {
            return "";
        } else
        {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return format.format(date);
        }
    }

    public static Date parseDate(String value)
        throws Exception
    {
        return parseDate(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date)
    {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public DateUtil[] getDaySection()
    {
        DateUtil ret[] = new DateUtil[2];
        ret[0] = getCopyWithNoTime();
        Date tempDate = ret[0].getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(tempDate);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 99);
        ret[1] = new DateUtil(cal.getTime());
        return ret;
    }

    public DateUtil getCopyWithNoTime()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateValue);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return new DateUtil(cal.getTime());
    }

    public static int getDayOffset(DateUtil dateX, DateUtil dateY)
    {
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
        return (new Long(dayOffset)).intValue();
    }

    public void addDay(int offset)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateValue);
        calendar.add(5, offset);
        dateValue = calendar.getTime();
    }

    public String getValue(String formatString)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        if(dateValue == null)
            return null;
        else
            return dateFormat.format(dateValue);
    }

    public Date getValue()
    {
        return dateValue;
    }

    public DateUtil(Date datetime)
    {
        dateValue = datetime;
    }

    public DateUtil()
    {
    }

    public DateUtil(String datetime, String pattern)
    {
        try
        {
            dateValue = parseDate(datetime, pattern);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getCurrentDateYYYYMMDDHHmmss()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }

    public static String getCurrentDateYYYYMMDD()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }

    public static String getTime(String time1, String time2)
    {
        Integer hour = Integer.valueOf(time1.substring(0, 2));
        Integer second = Integer.valueOf(time1.substring(2, 4));
        Integer sum = Integer.valueOf(hour.intValue() * 60 + second.intValue());
        Integer difference = Integer.valueOf(sum.intValue() - Integer.valueOf(time2).intValue());
        StringBuffer ret = new StringBuffer();
        Integer hour_later = Integer.valueOf(difference.intValue() / 60);
        Integer second_later = Integer.valueOf(difference.intValue() % 60);
        if(hour_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(hour_later);
            ret.append(h.toString());
        } else
        {
            ret.append(hour_later);
        }
        if(second_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(second_later);
            ret.append(h.toString());
        } else
        {
            ret.append(second_later);
        }
        return ret.toString();
    }

    public static String getArrivelTime(String time1, String time2)
    {
        StringBuffer ret = new StringBuffer();
        Integer hour = Integer.valueOf(time1.substring(0, 2));
        Integer second = Integer.valueOf(time1.substring(2, 4));
        Integer sum = Integer.valueOf(hour.intValue() * 60 + second.intValue());
        Integer arrvT = Integer.valueOf(sum.intValue() + Integer.valueOf(time2).intValue());
        Integer hour_later = Integer.valueOf(arrvT.intValue() / 60);
        Integer second_later = Integer.valueOf(arrvT.intValue() % 60);
        if(hour_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(hour_later);
            ret.append(h.toString());
        } else
        {
            ret.append(hour_later);
        }
        if(second_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(second_later);
            ret.append(h.toString());
        } else
        {
            ret.append(second_later);
        }
        return ret.toString();
    }

    public static String getRunTime(String time1, String time2)
    {
        StringBuffer ret = new StringBuffer();
        Integer hour1 = Integer.valueOf(time1.substring(0, 2));
        Integer second1 = Integer.valueOf(time1.substring(2, 4));
        Integer sum1 = Integer.valueOf(hour1.intValue() * 60 + second1.intValue());
        Integer hour2 = Integer.valueOf(time2.substring(0, 2));
        Integer second2 = Integer.valueOf(time2.substring(2, 4));
        Integer sum2 = Integer.valueOf(hour2.intValue() * 60 + second2.intValue());
        Integer runTime = Integer.valueOf(sum2.intValue() - sum1.intValue());
        Integer hour_later = Integer.valueOf(runTime.intValue() / 60);
        Integer second_later = Integer.valueOf(runTime.intValue() % 60);
        if(hour_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(hour_later);
            ret.append(h.toString());
        } else
        {
            ret.append(hour_later);
        }
        if(second_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(second_later);
            ret.append(h.toString());
        } else
        {
            ret.append(second_later);
        }
        return ret.toString();
    }

    public static String getArrivelTimeTwo(String time1, String time2)
    {
        StringBuffer ret = new StringBuffer();
        Integer hour1 = Integer.valueOf(time1.substring(0, 2));
        Integer second1 = Integer.valueOf(time1.substring(2, 4));
        Integer sum1 = Integer.valueOf(hour1.intValue() * 60 + second1.intValue());
        Integer hour2 = Integer.valueOf(time2.substring(0, 2));
        Integer second2 = Integer.valueOf(time2.substring(2, 4));
        Integer sum2 = Integer.valueOf(hour2.intValue() * 60 + second2.intValue());
        Integer runTime = Integer.valueOf(sum2.intValue() + sum1.intValue());
        Integer hour_later = Integer.valueOf(runTime.intValue() / 60);
        Integer second_later = Integer.valueOf(runTime.intValue() % 60);
        if(hour_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(hour_later);
            ret.append(h.toString());
        } else
        {
            ret.append(hour_later);
        }
        if(second_later.intValue() < 10)
        {
            StringBuffer h = new StringBuffer();
            h.append("0").append(second_later);
            ret.append(h.toString());
        } else
        {
            ret.append(second_later);
        }
        return ret.toString();
    }

    public static String getDateAndWeek(String value)
        throws Exception
    {
        Date date = parseDate(value, "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int daysofweek = c.get(7) - 1;
        if(daysofweek == 0)
            daysofweek = 7;
        StringBuffer ret = new StringBuffer();
        ret.append(c.get(1)).append("\u5E74").append(c.get(2) + 1).append("\u6708").append(c.get(5)).append("\u65E5").append("\u661F\u671F").append(daysofweek);
        return ret.toString();
    }

    public static void main(String arg[])
    {
        try
        {
            System.out.println(getDateAndWeek("2012-03-03"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private Date dateValue;
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\bus_class\WebRoot\WEB-INF\lib\bus.jar
	Total time: 62 ms
	Jad reported messages/errors:
The class file version is 49.0 (only 45.3, 46.0 and 47.0 are supported)
Couldn't fully decompile method parseDate
Couldn't resolve all exception handlers in method parseDate
	Exit status: 0
	Caught exceptions:
*/