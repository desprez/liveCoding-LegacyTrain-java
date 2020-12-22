package com.traintrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cache.SeatEntity;

public class WebTicketManager {

	private final BookingReferenceService bookingReferenceService;

	private final DataTrainService dataTrainService;

	public WebTicketManager(final BookingReferenceService bookingReferenceService,
			final DataTrainService dataTrainService) throws InterruptedException {

		this.bookingReferenceService = bookingReferenceService;
		this.dataTrainService = dataTrainService;
	}

	public WebTicketManager() throws InterruptedException {
		bookingReferenceService = new BookingReferenceServiceImpl();
		dataTrainService = new DataTrainServiceImpl();
	}

	public String reserve(final String train, final int seatsRequestedCount) throws IOException, InterruptedException {

		// get the train
		final String JsonTrain = dataTrainService.getTrain(train);

		final Train trainInst = new Train(JsonTrain);
		if (trainInst.doesNotExceedOverallTrainCapacityLimit(seatsRequestedCount)) {

			final BookingAttempt bookingAttempt = trainInst.buildBookingAttempt(seatsRequestedCount);

			if (bookingAttempt.isFullFilled()) {

				final String bookingRef = bookingReferenceService.getBookingReference();

				bookingAttempt.assignBookingReference(bookingRef);

				dataTrainService.applyReservation(train, bookingAttempt.getAvailableSeats(), bookingRef);

				return String.format("{{\"train_id\": \"%s\", \"booking_reference\": \"%s\", \"seats\": %s}}", train,
						bookingRef, dumpSeats(bookingAttempt.getAvailableSeats()));
			}

		}
		return String.format("{{\"train_id\": \"%s\", \"booking_reference\": \"\", \"seats\": []}}", train);
	}

	private String dumpSeats(final List<Seat> seats) {
		final StringBuilder sb = new StringBuilder("[");

		boolean firstTime = true;

		for (final Seat seat : seats) {
			if (!firstTime) {
				sb.append(", ");
			} else {
				firstTime = false;
			}

			sb.append(String.format("\"%d%s\"", seat.getSeatNumber(), seat.getCoachName()));
		}

		sb.append("]");

		return sb.toString();
	}

	private List<SeatEntity> toSeatsEntities(final String train, final List<Seat> availableSeats,
			final String bookingRef) throws InterruptedException {
		final List<SeatEntity> seatEntities = new ArrayList<SeatEntity>();
		for (final Seat seat : availableSeats) {
			seatEntities.add(new SeatEntity(train, bookingRef, seat.getCoachName(), seat.getSeatNumber()));
		}
		return seatEntities;
	}
}
