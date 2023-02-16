package com.jpmc.theatre.util;

import java.time.LocalDate;

public final class LocalDateProvider {
    private static volatile LocalDateProvider INSTANCE = null;

    private LocalDateProvider() {
    }
    /**
     * @return make sure to return singleton instance
     */
    public static LocalDateProvider getInstance() {
        if (INSTANCE == null) {
        	synchronized (LocalDateProvider.class) {
        		 if (INSTANCE == null) {
        			 INSTANCE = new LocalDateProvider();
        		 }
			}
        }
            return INSTANCE;
        }

    public LocalDate currentDate() {
            return LocalDate.now();
    }
}
