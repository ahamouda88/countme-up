package com.countme.up.utils;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testGetDate() {
		/** Create dates **/
		Date startDate = DateUtils.getDate(2020, 2, 23);
		Date endDate = DateUtils.getDate(2020, 2, 25);

		assertNotNull("Start date is not created successfully!", startDate);
		assertNotNull("End date is not created successfully!", startDate);
		assertTrue("Start date should be before the end date!", (startDate.before(endDate)));
	}

	@Test
	public void testGetDateByHourAndMin() {
		/** Create dates **/
		Date startDate = DateUtils.getDate(2020, 2, 23, 1, 4);
		Date endDate = DateUtils.getDate(2020, 2, 23, 1, 3);

		assertNotNull("Start date is not created successfully!", startDate);
		assertNotNull("End date is not created successfully!", startDate);
		assertTrue("Start date should be before the end date!", (startDate.before(endDate)));
	}
}
