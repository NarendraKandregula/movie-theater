package com.jpmc.theater.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.jpmc.theatre.util.LocalDateProvider;

class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
    	
    	assertAll("LocalProvider getInstance test",
    			() -> assertEquals(LocalDate.now(), LocalDateProvider.getInstance().currentDate()),
    			() -> assertEquals(LocalDateProvider.getInstance().hashCode(), LocalDateProvider.getInstance().hashCode()));
    }
}
