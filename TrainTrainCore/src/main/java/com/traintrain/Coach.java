package com.traintrain;

import java.util.ArrayList;
import java.util.List;

public class Coach {

	private static final double COACH_MAX_THRESHOLD_RESERVABLE_SEATS = 0.70;

	private final String name;

	private final List<Seat> seats = new ArrayList<>();

	public Coach(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addSeat(final Seat seat) {
		seats.add(seat);
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public BookingAttempt buildBookingAttempt(final int seatsRequestedCount) {

		final List<Seat> availableSeats = new ArrayList<Seat>();

		// find seats to reserve
		for (int index = 0, i = 0; index < seats.size(); index++) {
			final Seat seat = getSeats().get(index);
			if (seat.isAvailable()) {
				i++;
				if (i <= seatsRequestedCount) {
					availableSeats.add(seat);
				}
			}
		}
		return new BookingAttempt(seatsRequestedCount, availableSeats);
	}

	public boolean doesNotExceedOverallTrainCapacityLimit(final int seatsRequestedCount) {
		return getReservedSeats() + seatsRequestedCount <= Math
				.floor(COACH_MAX_THRESHOLD_RESERVABLE_SEATS * seats.size());
	}

	private long getReservedSeats() {
		return getSeats().stream().filter(seat -> !seat.isAvailable()).count();
	}
}
