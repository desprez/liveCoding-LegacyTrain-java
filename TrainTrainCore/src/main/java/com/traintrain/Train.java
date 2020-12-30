package com.traintrain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Train implements ValueObject {

	private static final double TRAIN_MAX_THRESHOLD_RESERVABLE_SEATS = 0.70;

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
		return getSeats().stream().filter(seat -> !seat.isAvailable()).count();
	}

	public boolean doesNotExceedOverallTrainCapacityLimit(final int seatsRequestedCount) {
		return getReservedSeats() + seatsRequestedCount <= Math
				.floor(TRAIN_MAX_THRESHOLD_RESERVABLE_SEATS * getMaxSeat());
	}

	public BookingAttempt buildBookingAttempt(final int seatsRequestedCount) {

		BookingAttempt bookingAttempt = new BookingAttempt(seatsRequestedCount);

		bookingAttempt = buildBookingAttemptIdealCase(seatsRequestedCount, bookingAttempt);

		if (!bookingAttempt.isFullFilled()) {
			bookingAttempt = buildBookingAttemptNotIdealCase(seatsRequestedCount, bookingAttempt);
		}
		return bookingAttempt;
	}

	private BookingAttempt buildBookingAttemptNotIdealCase(final int seatsRequestedCount,
			BookingAttempt bookingAttempt) {
		for (final Coach coach : coaches.values()) {
			bookingAttempt = coach.buildBookingAttempt(seatsRequestedCount);
			if (bookingAttempt.isFullFilled()) {
				break;
			}
		}
		return bookingAttempt;
	}

	private BookingAttempt buildBookingAttemptIdealCase(final int seatsRequestedCount, BookingAttempt bookingAttempt) {
		for (final Coach coach : coaches.values()) {
			if (coach.doesNotExceedOverallTrainCapacityLimit(seatsRequestedCount)) {
				bookingAttempt = coach.buildBookingAttempt(seatsRequestedCount);
				if (bookingAttempt.isFullFilled()) {
					break;
				}
			}
		}
		return bookingAttempt;
	}

	public Map<String, Coach> getCoaches() {
		return coaches;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Train)) {
			return false;
		}
		final Train other = (Train) obj;
		return new EqualsBuilder() //
				.append(getReservedSeats(), other.getReservedSeats()) //
				.append(getCoaches(), other.getCoaches()) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder() //
				.append(getReservedSeats()) //
				.append(getCoaches()) //
				.toHashCode();
	}

}