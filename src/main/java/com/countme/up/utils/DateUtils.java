package com.countme.up.utils;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * A Utility class that perform operations on a {@link Date}(s)
 */
public final class DateUtils {

	private DateUtils() {
	}

	/**
	 * This method returns a date given the year, month and day
	 * 
	 * @param year
	 *            the year
	 * @param month
	 *            the month of the year, from 1 to 12
	 * @param day
	 *            the day of the month, from 1 to 31
	 * 
	 * @return a new created date
	 */
	public static Date getDate(int year, int month, int day) {
		LocalDate localDate = new LocalDate(year, month, day);
		return localDate.toDate();
	}

	/**
	 * This method returns a date given the year, month, day, hour, and minute
	 * 
	 * @param year
	 *            the year
	 * @param month
	 *            the month of the year, from 1 to 12
	 * @param day
	 *            the day of the month, from 1 to 31
	 * @param hour
	 *            the hour of the day, from 0 to 23
	 * @param minute
	 *            the minute of the hour, from 0 to 59
	 *
	 * @return a new created date
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute) {
		LocalDateTime localDate = new LocalDateTime(year, month, day, hour, minute);
		return localDate.toDate();
	}

}
