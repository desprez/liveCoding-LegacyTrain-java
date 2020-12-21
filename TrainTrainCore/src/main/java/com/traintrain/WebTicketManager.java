package com.traintrain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.cache.ITrainCaching;
import com.cache.SeatEntity;
import com.cache.TrainCaching;

public class WebTicketManager {

	private static final String uriBookingReferenceService = "http://localhost:51691";
	private static final String urITrainDataService = "http://localhost:50680";
	private final ITrainCaching trainCaching;

	public WebTicketManager() throws InterruptedException {
		trainCaching = new TrainCaching();
		trainCaching.Clear();
	}

	public String reserve(final String train, final int seats) throws IOException, InterruptedException {
		final List<Seat> availableSeats = new ArrayList<Seat>();
		int count = 0;
		String result = "";
		String bookingRef;

		// get the train
		final String JsonTrain = getTrain(train);

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
				final Client client = ClientBuilder.newClient();
				try {
					bookingRef = getBookRef(client);
				} finally {
					client.close();
				}
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

				final String postContent = buildPostContent(train, bookingRef, availableSeats);

				final Client client = ClientBuilder.newClient();
				try {
					final WebTarget webTarget = client.target(urITrainDataService + "/reserve/");
					final Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
					request.post(Entity.text(postContent));
				} finally {
					client.close();
				}
				return String.format("{{\"train_id\": \"%s\", \"booking_reference\": \"%s\", \"seats\": %s}}", train,
						bookingRef, dumpSeats(availableSeats));
			}

		}
		return String.format("{{\"train_id\": \"%s\", \"booking_reference\": \"\", \"seats\": []}}", train);
	}

	private static String buildPostContent(final String trainId, final String booking_ref, final List<Seat> availableSeats) {
		final StringBuilder seats = new StringBuilder("[");

		boolean firstTime = true;

		for (final Seat seat : availableSeats) {
			if (!firstTime) {
				seats.append(", ");
			} else {
				firstTime = false;
			}

			seats.append(String.format("\"%d%s\"", seat.getSeatNumber(), seat.getCoachName()));
		}

		seats.append("]");

		final String result = String.format(
				"{{\r\n\t\"train_id\": \"%s\",\r\n\t\"seats\": %s,\r\n\t\"booking_reference\": \"%S\"\r\n}}", trainId,
				seats.toString(), booking_ref);

		return result;
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

	protected String getTrain(final String train) {
		String JsonTrainTopology;
		final Client client = ClientBuilder.newClient();
		try {

			final WebTarget target = client.target(urITrainDataService + "/api/data_for_train/");
			final WebTarget path = target.path(String.valueOf(train));
			final Invocation.Builder request = path.request(MediaType.APPLICATION_JSON);
			JsonTrainTopology = request.get(String.class);
		} finally {
			client.close();
		}
		return JsonTrainTopology;
	}

	protected String getBookRef(final Client client) {
		String booking_ref;

		final WebTarget target = client.target(uriBookingReferenceService + "/booking_reference/");
		booking_ref = target.request(MediaType.APPLICATION_JSON).get(String.class);

		return booking_ref;
	}

	private List<SeatEntity> toSeatsEntities(final String train, final List<Seat> availableSeats, final String bookingRef)
			throws InterruptedException {
		final List<SeatEntity> seatEntities = new ArrayList<SeatEntity>();
		for (final Seat seat : availableSeats) {
			seatEntities.add(new SeatEntity(train, bookingRef, seat.getCoachName(), seat.getSeatNumber()));
		}
		return seatEntities;
	}
}
