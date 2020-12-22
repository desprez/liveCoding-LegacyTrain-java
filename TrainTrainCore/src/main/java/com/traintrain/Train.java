package com.traintrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Train {

	public int ReservedSeats;

	public List<Seat> Seats;

	public Train(final String trainTopol) throws IOException {

		Seats = new ArrayList<Seat>();

		// var sample:
		// "{\"seats\": {\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\",
		// \"coach\": \"A\"}, \"2A\": {\"booking_reference\": \"\", \"seat_number\":
		// \"2\", \"coach\": \"A\"}}}";
		final ObjectMapper objectMapper = new ObjectMapper();

		final Map<String, Map<String, SeatJson>> stuff_in_stuff = objectMapper.readValue(trainTopol,
				new TypeReference<Map<String, Map<String, SeatJson>>>() {
		});

		for (final Map<String, SeatJson> value : stuff_in_stuff.values()) {
			for (final SeatJson seatJson : value.values()) {
				final int seat_number = Integer.parseInt(seatJson.seat_number);
				Seats.add(new Seat(seatJson.coach, seat_number, seatJson.booking_reference));
				if (!(new Seat(seatJson.coach, seat_number, seatJson.booking_reference).getBookingRef() == "")) {
					ReservedSeats++;
				}
			}
		}
	}

	public int getMaxSeat() {
		return Seats.size();
	}

	public boolean hasLessThanThreshold(final int i) {
		return ReservedSeats < i;
	}

	public boolean doesNotExceedOverallTrainCapacityLimit(final int seatsRequestedCount) {
		return ReservedSeats + seatsRequestedCount <= Math.floor(ThresholdManager.getMaxRes() * getMaxSeat());
	}

	public BookingAttempt buildBookingAttempt(final int seatsRequestedCount) {

		final List<Seat> availableSeats = new ArrayList<Seat>();

		// find seats to reserve
		for (int index = 0, i = 0; index < Seats.size(); index++) {
			final Seat each = Seats.get(index);
			if (each.getBookingRef() == "") {
				i++;
				if (i <= seatsRequestedCount) {
					availableSeats.add(each);
				}
			}
		}
		return new BookingAttempt(seatsRequestedCount, availableSeats);
	}
}