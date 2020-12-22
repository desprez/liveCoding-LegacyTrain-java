package com.traintrain;

import java.util.ArrayList;
import java.util.List;

public class BookingAttempt {

	private List<Seat> availableSeats = new ArrayList<Seat>();

	private final int seatsRequestedCount;

	public BookingAttempt(final int seatsRequestedCount, final List<Seat> availableSeats) {
		this.seatsRequestedCount = seatsRequestedCount;
		this.availableSeats = availableSeats;
	}

	public boolean isFullFilled() {
		return availableSeats.size() == seatsRequestedCount;
	}

	public List<Seat> getAvailableSeats() {
		return availableSeats;
	}

	public void assignBookingReference(final String bookingReference) {
		for (final Seat availableSeat : getAvailableSeats()) {
			availableSeat.setBookingReference(bookingReference);
		}
	}

}
