import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traintrain.Seat;
import com.traintrain.SeatJson;

public class TrainTopologyTest {

	@Test
	public void should_deserialize_train_topology() {
		final String trainTopology = "{\"seats\": {\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}, \"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}}}";
		final List<Seat> Seats = new ArrayList<Seat>();

		try {
			final ObjectMapper objectMapper = new ObjectMapper();

			final Map<String, Map<String, SeatJson>> stuff_in_stuff = objectMapper.readValue(trainTopology,
					new TypeReference<Map<String, Map<String, SeatJson>>>() {
			});

			for (final Map<String, SeatJson> value : stuff_in_stuff.values()) {
				for (final SeatJson seatJson : value.values()) {
					final int seat_number = Integer.parseInt(seatJson.seat_number);
					Seats.add(new Seat(seatJson.coach, seat_number, seatJson.booking_reference));
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		AssertSeats(Seats);
	}

	private void AssertSeats(final List<Seat> seats) {
		final String seatA1 = seats.get(0).toString();
		final String seatA2 = seats.get(1).toString();
		Assert.assertEquals(2, seats.size());
		Assert.assertEquals("A1", seatA1);
		Assert.assertEquals("A2", seatA2);
	}
}
