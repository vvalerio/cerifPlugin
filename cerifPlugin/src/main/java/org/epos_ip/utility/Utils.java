package org.epos_ip.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;

public class Utils {

	public static String EPOS_internal_format = "yyyy-MM-ddTHH:mm:ssZ";

	public static String convertISOPatternToJavaFormatPattern(String pattern) {
		return pattern.replace("YYYY", "yyyy").replace("DD", "dd").replace("hh", "HH").replace("T", "'T'").replace("Z", "'Z'");
	}

	public static String convert(String dateString, String inputFormat, String outputFormat) throws ParseException {
		if(dateString==null || inputFormat==null || outputFormat==null) throw new NullPointerException();

		String dateConverted;
		System.out.println(inputFormat+" "+outputFormat);

		if(outputFormat.equals("YYYY.yyy")) {
			dateConverted = Float.toString(fromDateToDecimalYear(new SimpleDateFormat("yyyy-MM-dd").parse(dateString)));
			System.out.println(dateConverted);
		} else if(inputFormat.equals("YYYY.yyy")) {
			System.out.println(fromDecimalYearToDate(Float.parseFloat("2000.415")));
			dateConverted = fromDecimalYearToDate(Float.parseFloat(dateString));
		}
		else {
			inputFormat = convertISOPatternToJavaFormatPattern(inputFormat);
			inputFormat = convertISOPatternToJavaFormatPattern(inputFormat);
			outputFormat = convertISOPatternToJavaFormatPattern(outputFormat);
			dateConverted = DateTime.parse(dateString).toString(outputFormat);
		}

		return dateConverted;

	}

	private static float fromDateToDecimalYear(Date myDate) throws ParseException {
		DateTime dt = new DateTime(myDate);
		Calendar c = new GregorianCalendar();
		c.setTime(myDate);
		int days_in_year = c.getActualMaximum(Calendar.DAY_OF_YEAR);
		System.out.println(days_in_year);
		int day_number = dt.getDayOfYear();
		float day = ((float)(day_number-1)/days_in_year) + dt.getYear();
		return Float.parseFloat(String.format("%.3f", day).replace(',', '.'));
	}

	private static String fromDecimalYearToDate(float date) throws ParseException {
		int year = (int) date;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		Calendar c = new GregorianCalendar();
		c.setTime(new Date(year));

		int days_in_year = c.getActualMaximum(Calendar.DAY_OF_YEAR);

		float day = (date - year)*days_in_year;
		c.add(Calendar.DATE, (int)day);
		c.set(Calendar.YEAR, year);

		return sdf.format(c.getTime());
	}

}
