package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.jpmc.theatre.exceptions.InvalidShowSequenceException;
import com.jpmc.theatre.model.Customer;
import com.jpmc.theatre.model.Reservation;
import com.jpmc.theatre.util.LocalDateProvider;

class TheaterTests {
	@Test
	void totalFeeForCustomer() {
		Theater theater = new Theater(LocalDateProvider.getInstance());
		Customer john = new Customer("John Doe", "id-12345");
		Reservation reservation = theater.reserve(john, 2, 4);
		
		assertAll("totalFee/discountedfee validation",
				() -> assertEquals(50, reservation.totalFee()),
				() -> assertEquals(37.50, reservation.discountedFee()));
	}

	@Test
	void reserve_ExceptonTest() {
		Theater theater = new Theater(LocalDateProvider.getInstance());
		Customer john = new Customer("John Doe", "id-12345");

		InvalidShowSequenceException thrown = assertThrows(InvalidShowSequenceException.class, () -> {
			theater.reserve(john, 20, 4);
		});

		assertTrue(thrown.getMessage().contentEquals("Not able to find any showing for given sequence 20"));

	}

}
