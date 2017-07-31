package com.iis.rims.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public final class DateTimeFormatUtils {	
	
	/*
	 * SimpleDateFormat is a none thread-safe class therefore it might be caused the format or parse issues in slowly system
	 * For the current system implemented manual code to fix issue or upgrade the lang.common3.4.jar to implement the class FastDateParser instead
	 */
	private static final FormattersThreadCache fmtCache = new FormattersThreadCache();
	// TODO Move to the MedinetDateTimeFormatUtils.
	public static final String SIMPLE_DATE_FORMATTER = "ddMMyyyy";
	public static final String DATE_FORMATTER = "dd/MM/yyyy";
	public static final String DATE_FORMATTER_SQL = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMATTER = "dd/MM/yyyy HH:mm:ss";
	public static final String YYYYMMDD_FORMATTER = "yyyyMMdd";
	public static final String YYYYMMDDHHMMSS_FORMATTER = "yyyyMMddHHmmss";
	public static final FastDateFormat FAST_DATE_TIME_FORMATTER = FastDateFormat.getInstance(DATE_TIME_FORMATTER);
	public static final FastDateFormat FAST_YYYYMMDDHHMMSS_FORMATTER = FastDateFormat.getInstance(YYYYMMDDHHMMSS_FORMATTER);
	public static final FastDateFormat FAST_YYYYMMDD_FORMATTER = FastDateFormat.getInstance(YYYYMMDD_FORMATTER);
	public static final FastDateFormat ISO_DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	public static final FastDateFormat XML_DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	public static final String ISO_8601_DATETIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss";

	public static final FastDateFormat MONTH_YEAR_DATE_FORMATTER = FastDateFormat.getInstance("MMM yyyy");
	public static final SimpleDateFormat TIME_FORMATTER_12h = new SimpleDateFormat("hh:mm a");
	public static final SimpleDateFormat TIME_FORMATTER_24h = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat SHORT_TIME_FORMATTER = new SimpleDateFormat("HH:mm");
	
	private static final String DAYS_OF_WEEK_REGEX = "[A-Z]{3}";
	private static final Pattern DAYS_OF_WEEK_PATTERN = Pattern.compile(DAYS_OF_WEEK_REGEX);
	private static final List<String> DAYS_OF_WEEK = Arrays.asList("SUN","MON","TUE","WED","THU","FRI","SAT");
	
	public static Date formatDateTime(String value) {
		try {
			return fmtCache.get(DATE_TIME_FORMATTER).parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date formatDateTime(String value, String dateFormat) {
		try {
			return fmtCache.get(dateFormat).parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date formatDate(String value) {
		try {
			return (!StringUtils.isEmpty(value))?fmtCache.get(DATE_FORMATTER).parse(value):null;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date formatISO8601DateTime(String value) {
		try {
			return fmtCache.get(ISO_8601_DATETIME_FORMATTER).parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toDate(Date date) {
		return fmtCache.get(DATE_FORMATTER).format(date);
	}
	
	public static String toSimpleDate(Date date) {
		return fmtCache.get(SIMPLE_DATE_FORMATTER).format(date);
	}
	
	public static String toDateTime(Date date) {
		return toDateTime(date, FAST_DATE_TIME_FORMATTER);
	}
	
	public static String toDateTimeISO(Date date) {
		return toDateTime(date, ISO_DATETIME_FORMATTER);
	}
	
	public static String toDateTimeXML(Date date) {
		return toDateTime(date, XML_DATETIME_FORMATTER);
	}
	
	public static String toDateTime(Date date, FastDateFormat simpleFormatter) {
		return simpleFormatter.format(date);
	}
	
	public static String toTime12h(Date date) {
		return TIME_FORMATTER_12h.format(date);
	}
	
	public static String toTime24h(Date date) {
		return TIME_FORMATTER_24h.format(date);
	}
	
	public static String toShortTime(Date date) {
		return SHORT_TIME_FORMATTER.format(date);
	}
	
	public static Calendar getTodayDateEmptyTime() {
		Calendar calendar = Calendar.getInstance();
		return removeTime(calendar);
	}	
	
	public static Calendar removeTime(Calendar cal) {
		return DateUtils.truncate(cal, Calendar.DATE);
	}

	public static Date removeTime(Date time) {
		return DateUtils.truncate(time, Calendar.DATE);
	}

	public static boolean isDateBetween(Date compare, Date from, Date to) {
		return isDateAfterAndEquals(compare, from) && isDateBeforeAndEquals(compare, to);
	}
	
	public static boolean isDateBetween(Date compare, Date from, Date to, boolean ignoreTime) {
		return isDateAfterAndEquals(compare, from, ignoreTime) && isDateBeforeAndEquals(compare, to, ignoreTime);
	}
	
	public static boolean isDateBeforeAndEquals(Date compare, Date when) {
		return isDateBeforeAndEquals(compare, when, false);
	}
	
	public static boolean isDateBeforeAndEquals(Date compare, Date when, boolean ignoreTime) {
		if (ignoreTime) {
			compare = removeTime(compare);
			when = removeTime(when);
		}
		return compare.before(when) || compare.equals(when);
	}
	
	public static boolean isDateAfterAndEquals(Date compare, Date when) {
		return isDateAfterAndEquals(compare, when, false);
	}
	
	public static boolean isDateAfterAndEquals(Date compare, Date when, boolean ignoreTime) {
		if (ignoreTime) {
			compare = removeTime(compare);
			when = removeTime(when);
		}
		return compare.after(when) || compare.equals(when);
	}
	
	private static List<Integer> getDaysPattern(String pattern) {
		List<Integer> list = new ArrayList<Integer>();
		Matcher matcher = DAYS_OF_WEEK_PATTERN.matcher(pattern);
		while(matcher.find()) {
			String day = matcher.group();
			int index = DAYS_OF_WEEK.indexOf(day);
			if (index == -1) {
				throw new RuntimeException("The pattern format is connect correct: " + pattern);
			}
			list.add(index + 1);
		}
		return list;
	}
	
	public static List<Date> getDates(Date startDate, Date endDate, String pattern) {
		Calendar calendar = Calendar.getInstance();
		startDate = removeTime(startDate);
		endDate = removeTime(endDate);
		List<Integer> days = getDaysPattern(pattern);
		List<Date> dates = new ArrayList<Date>();
		calendar.setTime(startDate);
		while (isDateBeforeAndEquals(startDate, endDate)) {
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (days.contains(day)) {
				dates.add(startDate);
			}
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
		}
		return dates;
	}
	
	public static Date getFirstDate(Date date) {
		MutableDateTime dateTime = new DateTime(date.getTime()).toMutableDateTime();
		dateTime.setDayOfMonth(1);
		dateTime.setTime(0, 0, 0, 0);
		return dateTime.toDate();
	}
	
	public static Date getFirstDateTimeOfCurrentMonth() {
		return getFirstDate(new Date());
	}
	
	public static Date getLastDate(Date date) {
		MutableDateTime dateTime = new DateTime(getFirstDate(date).getTime()).toMutableDateTime();
		dateTime.addMonths(1);
		dateTime.addMillis(-1);
		return dateTime.toDate();
	}
	
	public static Date getLastDateTimeOfCurrentMonth() {
		return getLastDate(new Date());
	}
	
	public static Date getDateLastTime(Date today) {
		MutableDateTime dateTime = new DateTime(today.getTime()).toMutableDateTime();
		dateTime.setSecondOfDay(59);
		dateTime.setMinuteOfDay(59);
		dateTime.setHourOfDay(23);		
		return dateTime.toDate();
	}
	
	public static Date getDateStartTime(Date today) {
		MutableDateTime dateTime = new DateTime(today.getTime()).toMutableDateTime();
		dateTime.setSecondOfDay(1);
		dateTime.setMinuteOfDay(0);
		dateTime.setHourOfDay(0);		
		return dateTime.toDate();
	}
	
	public static Date plusDays(Date date, int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + numberOfDays);
		
		return calendar.getTime();
	}
	
	public static XMLGregorianCalendar convertStringDateToXmlGregorianCalendar(
			String dateStr, String dateFormat, boolean noTimezone) {
		try {
			// this may throw DatatypeConfigurationException
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			GregorianCalendar calendar = new GregorianCalendar();
			// reset all fields
			calendar.clear();

			Calendar parsedCalendar = Calendar.getInstance();
			// eg "yyyy-MM-dd"
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			// this may throw ParseException
			Date rawDate = sdf.parse(dateStr);
			parsedCalendar.setTime(rawDate);

			// set date from parameter and leave time as default calendar values
			calendar.set(parsedCalendar.get(Calendar.YEAR),
					parsedCalendar.get(Calendar.MONTH),
					parsedCalendar.get(Calendar.DATE));

			XMLGregorianCalendar xmlCalendar = datatypeFactory
					.newXMLGregorianCalendar(calendar);
			// clears default timezone
			if (noTimezone) {
				xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			}

			return xmlCalendar;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static XMLGregorianCalendar convertStringTimeToXmlGregorianCalendar(
			String timeStr, String timeFormat, boolean noFractionalSecs,
			boolean noTimezone) {
		try {
			// this may throw DatatypeConfigurationException
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			GregorianCalendar calendar = new GregorianCalendar();
			// reset all fields
			calendar.clear();

			Calendar parsedCalendar = Calendar.getInstance();
			// eg "HH:mm:ss"
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
			// this may throw ParseException
			Date rawDate = sdf.parse(timeStr);
			parsedCalendar.setTime(rawDate);

			// set time from time parameter and set date to default values
			calendar.set(calendar.get(GregorianCalendar.YEAR),
					calendar.get(GregorianCalendar.MONTH),
					calendar.get(GregorianCalendar.DATE),
					parsedCalendar.get(Calendar.HOUR),
					parsedCalendar.get(Calendar.MINUTE),
					parsedCalendar.get(Calendar.SECOND));

			XMLGregorianCalendar xmlCalendar = datatypeFactory
					.newXMLGregorianCalendar(calendar);
			// clears default timezone
			if (noTimezone) {
				xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			}
			// clears default fractional seconds
			if (noFractionalSecs) {
				xmlCalendar.setFractionalSecond(null);
			}

			return xmlCalendar;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Date toDate(XMLGregorianCalendar xmlDate) {
		return xmlDate.toGregorianCalendar().getTime();
	}

	public static XMLGregorianCalendar toXmlDate(Date date)
			throws DatatypeConfigurationException {
		if(date == null) return null;
		DatatypeFactory f = DatatypeFactory.newInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		XMLGregorianCalendar cal = f.newXMLGregorianCalendar(sdf.format(date));
		return cal;
	}
	
	public static String getTimeFromDate(Date date) {
		try {
			if (date != null) {
				String timeString = DateTimeFormatUtils.toShortTime(date);
				if (!"00:00".equals(timeString)) {
					return timeString;
				}
			}
		} 
		catch(Exception ex) {
			
		}
		
		return null;
	}
	
	static class FormattersThreadCache extends ThreadLocal<Map<String, SimpleDateFormat>> {
		@Override
		protected Map<String, SimpleDateFormat> initialValue() {
			return new HashMap<String, SimpleDateFormat>();
		}
 
		public SimpleDateFormat get(String formatString) {
			Map<String, SimpleDateFormat> map = get();
			SimpleDateFormat sdf = map.get(formatString);
			if (sdf == null) {
				sdf = new SimpleDateFormat(formatString);
				map.put(formatString, sdf);
			}
			return sdf;
		}
	};
	
	public static Calendar getSQLDate(String dateValue) {
		Calendar calendar = Calendar.getInstance();
		if(dateValue != null)
			try {
				calendar.setTime(fmtCache.get(DATE_FORMATTER_SQL).parse(dateValue));
			} catch (ParseException e) {
				try {
					calendar.setTime(fmtCache.get(DATE_FORMATTER).parse(dateValue));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		return calendar;
	}
	
	public static void main(String[] args) throws Exception {
		/*System.out.println(DateUtils.truncate(new Date(), Calendar.DATE));
		System.out.println(DateUtils.round(new Date(), Calendar.SECOND));
		System.out.println(DateTimeFormatUtils.getDateLastTime(new Date()));
		System.out.println(DateTimeFormatUtils.getDateStartTime(new Date()));*/
		
		String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1454630400000l));
		System.out.println(dateAsText);
		dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1453852800000l));
		System.out.println(dateAsText);
		dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1453852800000l));
		System.out.println(dateAsText);
		dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1453507200000l));
		System.out.println(dateAsText);
	}
	
}
