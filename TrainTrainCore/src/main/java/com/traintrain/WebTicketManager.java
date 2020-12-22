package com.traintrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cache.ITrainCaching;
import com.cache.SeatEntity;
import com.cache.TrainCaching;

public class WebTicketManager {

	private final ITrainCaching trainCaching;

	private final BookingReferenceService bookingReferenceService;

	private final DataTrainService dataTrainService;

	public WebTicketManager(final BookingReferenceService bookingReferenceService,
			final DataTrainService dataTrainService) throws InterruptedException {
		trainCaching = new TrainCaching();
		trainCaching.Clear();
		this.bookingReferenceService = bookingReferenceService;
		this.dataTrainService = dataTrainService;
	}

	public WebTicketManager() throws InterruptedException {
		trainCaching = new TrainCaching();
		trainCaching.Clear();
		bookingReferenceService = new BookingReferenceServiceImpl();
		dataTrainService = new DataTrainServiceImpl();
	}

	public String reserve(final String train, final int seatsRequestedCount) throws IOException, InterruptedException {

		// get the train
		final String JsonTrain = dataTrainService.getTrain(train);

		final Train trainInst = new Train(JsonTrain);
		if (trainInst.doesNotExceedOverallTrainCapacityLimit(seatsRequestedCount)) {

			final List<Seat> availableSeats = trainInst.findAvailableSeats(seatsRequestedCount);

			if (availableSeats.size() == seatsRequestedCount) {

				final String bookingRef = bookingReferenceService.getBookingReference();

				for (final Seat availableSeat : availableSeats) {
					availableSeat.setBookingRef(bookingRef);
				}

				trainCaching.Save(toSeatsEntities(train, availableSeats, bookingRef));

				dataTrainService.applyReservation(train, availableSeats, bookingRef);

				return String.format("{{\"train_id\": \"%s\", \"booking_reference\": \"%s\", \"seats\": %s}}", train,
						bookingRef, dumpSeats(availableSeats));
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
