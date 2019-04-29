package org.epos_ip.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

public class Utils {

	public static String EPOS_internal_format = "yyyy-MM-ddTHH:mm:ssZ";

	public static String convertISOPatternToJavaFormatPattern(String pattern) {
		if(pattern.contains("T") && !pattern.contains("'T'")) pattern = pattern.replace("T", "'T'");
		if(pattern.contains("Z") && !pattern.contains("'Z'")) pattern = pattern.replace("Z", "'Z'");
		pattern = pattern.replace("YYYY", "yyyy").replace("DD", "dd").replace("hh", "HH");
		return pattern;
	}

	public static String convert(String dateString, String inputFormat, String outputFormat) throws ParseException {
		if(inputFormat==null) inputFormat=EPOS_internal_format;
		if(dateString==null || inputFormat==null || outputFormat==null) throw new NullPointerException();

		String dateConverted = null;

		if(outputFormat.equals("YYYY.yyy")) {
			dateConverted = Float.toString(fromDateToDecimalYear(new SimpleDateFormat("yyyy-MM-dd").parse(dateString)));
		} else if(inputFormat.equals("YYYY.yyy")) {
			dateConverted = fromDecimalYearToDate(Float.parseFloat(dateString));
		}
		else {
			//inputFormat = convertISOPatternToJavaFormatPattern(inputFormat);
			if(!isValidFormat(convertISOPatternToJavaFormatPattern(inputFormat), dateString)) {
				inputFormat = EPOS_internal_format;
			}
			inputFormat = convertISOPatternToJavaFormatPattern(inputFormat);
			outputFormat = convertISOPatternToJavaFormatPattern(outputFormat);
			DateTimeFormatter format = org.joda.time.format.DateTimeFormat.forPattern(inputFormat);
			LocalDate lDate = null;
			System.out.println(dateString+ " "+inputFormat);

			DateTime dt = new DateTime(dateString);
			try {
				lDate = org.joda.time.LocalDate.parse(dt.toString(), format);
			}catch(Exception e) {
				try {
					lDate = org.joda.time.LocalDate.parse(dateString, format);
				}catch(Exception e1) {
					lDate = dt.toLocalDate();
				}
			}

			dateConverted = lDate.toString();
			dateConverted = DateTime.parse(dateConverted).toString(outputFormat);

		}
		return dateConverted;

	}

	private static float fromDateToDecimalYear(Date myDate) throws ParseException {
		DateTime dt = new DateTime(myDate);
		Calendar c = new GregorianCalendar();
		c.setTime(myDate);
		int days_in_year = c.getActualMaximum(Calendar.DAY_OF_YEAR);
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

	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

}
