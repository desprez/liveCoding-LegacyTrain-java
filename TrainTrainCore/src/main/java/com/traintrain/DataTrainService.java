package com.traintrain;

import java.util.List;

public interface DataTrainService {

	String getTrain(String train);

	void applyReservation(String train, List<Seat> availableSeats, String bookingRef);

}