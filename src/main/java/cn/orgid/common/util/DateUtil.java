package cn.orgid.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	
	public static Date getDayBegin(Date date){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d=sf.parse(sf.format(date));
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static Date getDayEnd(Date date){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date d=sf.parse(sf.format(date));
			long t=d.getTime()+60*1000*60*24;
			return new Date(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String d1, String string) {
		
		SimpleDateFormat sf = new SimpleDateFormat(string);
		try {
			return sf.parse(d1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Date date, String string) {
		
		SimpleDateFormat sf = new SimpleDateFormat(string);
		return sf.format(date);
		
	}

}
