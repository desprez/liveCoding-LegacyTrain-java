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

	public String reserve(final String train, final int seats) throws IOException, InterruptedException {
		final List<Seat> availableSeats = new ArrayList<Seat>();
		int count = 0;
		String result = "";
		String bookingRef;

		// get the train
		final String JsonTrain = dataTrainService.getTrain(train);

		result = JsonTrain;

		final Train trainInst = new Train(JsonTrain);
		if (trainInst.ReservedSeats + seats <= Math.floor(ThresholdManager.getMaxRes() * trainInst.getMaxSeat())) {
			int numberOfReserv = 0;
			// find seats to reserve
			for (int index = 0, i = 0; index < trainInst.Seats.size(); index++) {
				final Seat each = trainInst.Seats.get(index);
				if (each.getBookingRef() == "") {
					i++;
					if (i <= seats) {
						availableSeats.add(each);
					}
				}
			}

			for (final Seat seat : availableSeats) {
				count++;
			}

			int reservedSets = 0;

			if (count != seats) {
				return String.format("{{\"train_id\": \"%s\", \"booking_reference\": \"\", \"seats\": []}}", train);
			} else {

				bookingRef = bookingReferenceService.getBookingReference();

				for (final Seat availableSeat : availableSeats) {
					availableSeat.setBookingRef(bookingRef);
					numberOfReserv++;
					reservedSets++;
				}
			}

			if (numberOfReserv == seats) {

				trainCaching.Save(toSeatsEntities(train, availableSeats, bookingRef));

				if (reservedSets == 0) {
					final String output = String.format("Reserved seat(s): ", reservedSets);
					System.out.println(output);
				}

				final String todod = "[TODOD]";

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
