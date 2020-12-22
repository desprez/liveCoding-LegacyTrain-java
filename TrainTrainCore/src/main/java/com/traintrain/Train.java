package com.traintrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Train {

	public int ReservedSeats;

	public Map<String, Coach> coaches = new HashMap<>();

	public Train(final String trainTopol) throws IOException {

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

				final Seat seat = new Seat(seatJson.coach, seat_number, seatJson.booking_reference);

				Coach coach = coaches.get(seat.getCoachName());
				if (coach == null) {
					coach = new Coach(seat.getCoachName());
					coaches.put(seat.getCoachName(), coach);
				}
				coach.addSeat(seat);
			}
		}
	}

	public int getMaxSeat() {
		return getSeats().size();
	}

	public List<Seat> getSeats() {
		return coaches.values().stream().map(x -> x.getSeats()).flatMap(List::stream).collect(Collectors.toList());
	}

	public boolean hasLessThanThreshold(final int i) {
		return getReservedSeats() < i;
	}

	private long getReservedSeats() {
		return getSeats().stream().filter(seat -> !seat.getBookingReference().isEmpty()).count();
	}

	public boolean doesNotExceedOverallTrainCapacityLimit(final int seatsRequestedCount) {
		return getReservedSeats() + seatsRequestedCount <= Math.floor(ThresholdManager.getMaxRes() * getMaxSeat());
	}

	public BookingAttempt buildBookingAttempt(final int seatsRequestedCount) {
		final List<Seat> availableSeats = new ArrayList<Seat>();

		for (final Coach coach : coaches.values()) {

			final BookingAttempt bookingAttempt = coach.buildBookingAttempt(seatsRequestedCount);
			if (bookingAttempt.isFullFilled()) {
				return bookingAttempt;
			}

		}
		return new BookingAttempt(seatsRequestedCount, availableSeats);
	}

	public Map<String, Coach> getCoaches() {
		return coaches;
	}
}