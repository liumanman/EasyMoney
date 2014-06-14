package w1.app.easymoney.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {
	public static String DateToString(Date date){
		if (date == null){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
		}
	
	public static Date StringToDate(String string) throws ParseException{
		if (string == null || string.length() < 1){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(string);
	}

    public static Date getTheSunday(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        int day2 = c.get(Calendar.DATE);

        c.set(Calendar.DATE, day2 - (day - 1));

        return c.getTime();
    }

    public static  Date getTheSaturday(Date date){
        Date sunday = getTheSunday(date);

        Calendar c = Calendar.getInstance();
        c.setTime(sunday);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 6);

        return c.getTime();
    }

    public static Date removeTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date addDate(Date date, int days){
       Calendar c = Calendar.getInstance();
        c.setTime(date);

        int day = c.get(Calendar.DATE);
        day = day + days;
        c.set(Calendar.DATE, day);

        return c.getTime();
    }

    public static Date getFirstDayInMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);

        return c.getTime();
    }

    public static Date getLastDayInMonth(Date date){
       Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);

        return c.getTime();
    }

    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }
}
