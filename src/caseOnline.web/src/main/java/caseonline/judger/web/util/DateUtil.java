package caseonline.judger.web.util;

import java.util.Calendar;
import java.util.Date;

/**
 * The Class DateUtil.
 */
public class DateUtil {
	
	/**
	 * Instantiates a new date util.
	 */
	private  DateUtil() {}
	
	/**
	 * 获取当前日期
	 *
	 * @return the date
	 */
	public static Date getDate(){
		Calendar calendar=Calendar.getInstance();
		Date currentTime=calendar.getTime();
		return currentTime;
	}
}
