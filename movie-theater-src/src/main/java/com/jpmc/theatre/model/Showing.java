package com.jpmc.theatre.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.jpmc.theatre.util.CommonUtil;

public class Showing {
	private Movie movie;
	private int sequenceOfTheDay;
	private LocalDateTime showStartTime;

	public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
		this.movie = movie;
		this.sequenceOfTheDay = sequenceOfTheDay;
		this.showStartTime = showStartTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public LocalDateTime getStartTime() {
		return showStartTime;
	}


	public double getMovieFee() {
		return movie.getTicketPrice();
	}

	public int getSequenceOfTheDay() {
		return sequenceOfTheDay;
	}

	public double getdiscountedFee() {
		return CommonUtil.calculateDiscountedPrice(this);
	}

	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Showing showing = (Showing) obj;
	        return Objects.equals(showing.getMovie(), movie)
	                && Objects.equals(showing.getSequenceOfTheDay(), sequenceOfTheDay)
	                && Objects.equals(showing.showStartTime, showStartTime);
		
	}
	
	
	@Override
	public int hashCode() {
		 return Objects.hash(movie, sequenceOfTheDay, showStartTime);
	}

	

	@Override
	public String toString() {
		return  "Showing : "+movie.toString()+ " , sequenceOfTheDay : "+sequenceOfTheDay +
				" , showStartTime : "+showStartTime;
	}
	
	
}
