package com.traintrain;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class DataTrainServiceImpl implements DataTrainService {

	private static final String urITrainDataService = "http://localhost:50680";

	@Override
	public String getTrain(final String train) {
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

	@Override
	public void applyReservation(final String train, final List<Seat> availableSeats, final String bookingRef) {
		final String postContent = buildPostContent(train, bookingRef, availableSeats);

		final Client client = ClientBuilder.newClient();
		try {
			final WebTarget webTarget = client.target(urITrainDataService + "/reserve/");
			final Invocation.Builder request = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
			request.post(Entity.text(postContent));
		} finally {
			client.close();
		}
	}

	private static String buildPostContent(final String trainId, final String booking_ref,
			final List<Seat> availableSeats) {
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
}
