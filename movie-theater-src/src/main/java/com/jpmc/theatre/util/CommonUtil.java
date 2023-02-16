package com.jpmc.theatre.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jpmc.theatre.model.Movie;
import com.jpmc.theatre.model.Showing;

public class CommonUtil {

	public static final int MOVIE_CODE_SPECIAL = 1;

	public static double calculateSeqDiscount(int showSequence) {

		double discount = 0;
		
		if(showSequence == 1) {
			discount = 3;
		} else if(showSequence == 2) {
			discount = 2;
		}
		
		return discount;
	}

	public static double calculateTimeBasedDiscount(LocalDateTime showStartime, double ticketPrice) {
		double discount = 0;

		// Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount

		if (showStartime.getHour() >= 11 && showStartime.getHour() <= 16) {
			discount = Math.max(discount, ticketPrice * 0.25);
		}
		// Any movies showing on 7th, you'll get 1$ discount
		if (showStartime.getDayOfMonth() == 7) {
			discount = Math.max(discount, 1);
		}

		return discount;
	}

	private static double getDiscount(Showing showing) {
		Movie movie = showing.getMovie();
		double finalDiscount = 0;

		// seq based discount rules
		finalDiscount = Math.max(finalDiscount, CommonUtil.calculateSeqDiscount(showing.getSequenceOfTheDay()));
		// time based discount rules
		finalDiscount = Math.max(finalDiscount,
				CommonUtil.calculateTimeBasedDiscount(showing.getStartTime(), movie.getTicketPrice()));

		// 20% discount for special movie
		if (CommonUtil.MOVIE_CODE_SPECIAL == movie.getSpecialCode()) {
			finalDiscount = Math.max(finalDiscount, Math.floor(movie.getTicketPrice() * 0.2));
		}

		return finalDiscount;
	}

	public static double calculateDiscountedPrice(Showing showing) {
		return showing.getMovie().getTicketPrice() - getDiscount(showing);
	}

	 // (s) postfix should be added to handle plural correctly
	public static String handlePlural(long value) {
		if (value == 1) {
			return "";
		} else {
			return "s";
		}
	}
	
	public static List<Showing> getMoviesList(LocalDateProvider provider) {
		    Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
	        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
	        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
	        
	        return List.of(
	            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
	            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
	            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
	            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
	            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
	            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
	            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
	            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
	            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
	        );
	}
	
	 private static String humanReadableFormat(Duration duration) {
	        long hour = duration.toHours();
	        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

	        return String.format("(%s hour%s %s minute%s)", hour, CommonUtil.handlePlural(hour), remainingMin, CommonUtil.handlePlural(remainingMin));
	    }
	 
	 public static void printSchedule_Json(List<Showing> schedule) {
 	 ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      objectMapper.registerModule(new JavaTimeModule());
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      objectMapper.setDateFormat(df);
      try {
			System.out.println("---Json value--- "+objectMapper.writeValueAsString(schedule));
		} catch (JsonProcessingException e) {
			System.out.println("Exception occured during json conversion due to "+e.getMessage());
			//e.printStackTrace();
		}
 }
	 
	 public static void printSchedule(LocalDateProvider provider, List<Showing> schedule) {
	        System.out.println(provider.currentDate());
	        System.out.println("===================================================");
	        schedule.forEach(s ->
	                System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + CommonUtil.humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee())
	        );
	        System.out.println("===================================================");

	       
	    }

}
