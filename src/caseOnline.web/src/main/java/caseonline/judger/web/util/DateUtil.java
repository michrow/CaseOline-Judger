package caseonline.judger.web.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private  DateUtil() {}
	
	public static Date getDate(){
		Calendar calendar=Calendar.getInstance();
		Date currentTime=calendar.getTime();
		return currentTime;
	}
}
