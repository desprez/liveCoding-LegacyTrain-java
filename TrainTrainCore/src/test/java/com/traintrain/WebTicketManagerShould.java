package com.traintrain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebTicketManagerShould {

	private static final String TRAIN_ID = "9043-2020-12-21";

	private WebTicketManager webTicketManager;

	@Mock
	private BookingReferenceService bookingReferenceService;

	@Mock
	private DataTrainService dataTrainService;

	@Before
	public void beforeEach() throws InterruptedException, IOException {
		webTicketManager = new WebTicketManager(bookingReferenceService, dataTrainService);
	}

	@Test
	public void reserve_seats_requested_when_train_is_empty() throws IOException, InterruptedException {
		// given

		when(dataTrainService.getTrain(TRAIN_ID)).thenReturn(TrainTopologyGenerator.fixtureWith4AvailableSeats());

		final int nbSeatRequested = 1;

		final String bookingRef = "2dadaz4";

		when(bookingReferenceService.getBookingReference()).thenReturn(bookingRef);

		// when
		final String reservation = webTicketManager.reserve(TRAIN_ID, nbSeatRequested);

		// then
		assertThat(reservation).isEqualTo(
				"{{\"train_id\": \"9043-2020-12-21\", \"booking_reference\": \"2dadaz4\", \"seats\": [\"1A\"]}}");
	}

	@Test
	public void not_reserve_if_train_is_over_threshold() throws IOException, InterruptedException {
		// given

		when(dataTrainService.getTrain(TRAIN_ID))
		.thenReturn(TrainTopologyGenerator.fixtureWith4SeatsAnd3AlreadyReserved());

		// when
		final String reservation = webTicketManager.reserve(TRAIN_ID, 1);

		// then
		assertThat(reservation)
		.isEqualTo("{{\"train_id\": \"9043-2020-12-21\", \"booking_reference\": \"\", \"seats\": []}}");
	}

	@Test
	public void reserve_all_seats_in_same_coach() throws IOException, InterruptedException {
		// given
		final String trainTopology = TrainTopologyGenerator.fixtureWith2CoachesAnd3seatsAlreadyReservedIn1rstCoach();
		final String bookingRef = "2dadaz4";
		when(bookingReferenceService.getBookingReference()).thenReturn(bookingRef);
		when(dataTrainService.getTrain(TRAIN_ID)).thenReturn(trainTopology);

		// when
		final String reservation = webTicketManager.reserve(TRAIN_ID, 2);

		// then
		assertThat(reservation).isEqualTo(
				"{{\"train_id\": \"9043-2020-12-21\", \"booking_reference\": \"2dadaz4\", \"seats\": [\"1B\", \"2B\"]}}");
	}



}
