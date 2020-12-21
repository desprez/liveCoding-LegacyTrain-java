package com.traintrain;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class BookingReferenceServiceImpl implements BookingReferenceService {

	private static final String uriBookingReferenceService = "http://localhost:51691";

	@Override
	public String getBookingReference() {
		final String bookingReference;

		final Client client = ClientBuilder.newClient();
		try {
			final WebTarget target = client.target(uriBookingReferenceService + "/booking_reference/");
			bookingReference = target.request(MediaType.APPLICATION_JSON).get(String.class);
		} finally {
			client.close();
		}

		return bookingReference;
	}

}
