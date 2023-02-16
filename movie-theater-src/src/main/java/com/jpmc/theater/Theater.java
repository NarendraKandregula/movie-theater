package com.jpmc.theater;

import java.util.List;

import com.jpmc.theatre.exceptions.InvalidShowSequenceException;
import com.jpmc.theatre.model.Customer;
import com.jpmc.theatre.model.Reservation;
import com.jpmc.theatre.model.Showing;
import com.jpmc.theatre.util.CommonUtil;
import com.jpmc.theatre.util.LocalDateProvider;

public class Theater {

    LocalDateProvider provider;
    private List<Showing> schedule;

    public Theater(LocalDateProvider provider) {
        this.provider = provider;
        schedule = CommonUtil.getMoviesList(provider); 
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (ArrayIndexOutOfBoundsException ex) {
           // ex.printStackTrace();
        	System.out.println("Not able to find any showing for given sequence due to :"+ex.getMessage());
            throw new InvalidShowSequenceException("Not able to find any showing for given sequence " + sequence, ex);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

   

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.getInstance());
        CommonUtil.printSchedule(theater.provider, theater.schedule);
        CommonUtil.printSchedule_Json(theater.schedule);
    }
}
