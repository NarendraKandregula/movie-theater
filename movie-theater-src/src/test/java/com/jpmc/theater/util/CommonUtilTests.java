package com.jpmc.theater.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jpmc.theatre.model.Movie;
import com.jpmc.theatre.model.Showing;
import com.jpmc.theatre.util.CommonUtil;
import com.jpmc.theatre.util.LocalDateProvider;

class CommonUtilTests {

	@Test
	void printScheduleTest() {
		List<Showing> schedule = mock(List.class);
		doNothing().when(schedule).forEach(any());
		CommonUtil.printSchedule(LocalDateProvider.getInstance(), schedule);
		verify(schedule, times(1)).forEach(any());
	}

	@Test
	void printMovieScheduleTest_spy() {
		List<Showing> schedule = new ArrayList<Showing>();
		LocalDateProvider provider = LocalDateProvider.getInstance();
		Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
		schedule.add(new Showing(spiderMan, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))));
		List<Showing> spy_schedule = spy(schedule);

		CommonUtil.printSchedule(provider, spy_schedule);
		verify(spy_schedule).forEach(any());
	}

	@Test
	void calculateSeqDiscountTest() {

		assertAll("calculateSeqDiscount Test success/failure cases",
				() -> assertEquals(3, CommonUtil.calculateSeqDiscount(1)),
				() -> assertEquals(2, CommonUtil.calculateSeqDiscount(2)),
				() -> assertEquals(0, CommonUtil.calculateSeqDiscount(3)));
	}

	@Test
	void calculateTimeBasedDiscount() {

		/**
		 * case-1: Show time 9 AM - expected discount = 0 case-1: Show time 11 AM -
		 * expected discount = 2.5 case-1: Show date 7th - expected discount = 1 case-1:
		 * Show date 7th & time11 AM - expected discount = 2.5
		 * 
		 */

		double ticketPrice = 10;
		assertAll("calculateTimeBasedDiscount Test success/failure cases",
				() -> assertEquals(0,
						CommonUtil.calculateTimeBasedDiscount(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
								ticketPrice)),
				() -> assertEquals(2.5,
						CommonUtil.calculateTimeBasedDiscount(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)),
								ticketPrice)),
				() -> assertEquals(1,
						CommonUtil.calculateTimeBasedDiscount(
								LocalDateTime.of(LocalDate.of(2023, 1, 7), LocalTime.of(9, 0)), ticketPrice)),
				() -> assertEquals(2.5, CommonUtil.calculateTimeBasedDiscount(
						LocalDateTime.of(LocalDate.of(2023, 1, 7), LocalTime.of(12, 0)), ticketPrice)));

	}

	@Test
	void getDiscountTest() {

		List<Showing> showings = CommonUtil.getMoviesList(LocalDateProvider.getInstance());

		assertAll("getDiscount Test success/failure cases",
				() -> assertEquals(8, CommonUtil.calculateDiscountedPrice(showings.get(0))),
				() -> assertEquals(9.375, CommonUtil.calculateDiscountedPrice(showings.get(1))),
				() -> assertEquals(6.75, CommonUtil.calculateDiscountedPrice(showings.get(2))),
				() -> assertEquals(10.50, CommonUtil.calculateDiscountedPrice(showings.get(7))));
	}

}
