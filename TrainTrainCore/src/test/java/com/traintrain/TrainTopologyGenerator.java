package com.traintrain;

public class TrainTopologyGenerator {

	public static String fixtureWith4AvailableSeats() {
		return "{\"seats\":" //
				+ " {\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," //
				+ " \"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," //
				+ " \"3A\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"A\"}," //
				+ " \"4A\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"A\"}}}";
	}

	public static String fixtureWith4SeatsAnd3AlreadyReserved() {
		return //
				"{\"seats\": " //
				+ "{\"1A\": {\"booking_reference\": \"aaa\", \"seat_number\": \"1\", \"coach\": \"A\"}," //
				+ " \"2A\": {\"booking_reference\": \"bbbbb\", \"seat_number\": \"2\", \"coach\": \"A\"}," //
				+ " \"3A\": {\"booking_reference\": \"cccccc\", \"seat_number\": \"3\", \"coach\": \"A\"}," //
				+ " \"4A\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"A\"}}}";
	}

	public static String fixtureWith2CoachesAnd3seatsAlreadyReservedIn1rstCoach() {
		return "{\"seats\": {" //
				+ "\"1A\": {\"booking_reference\": \"a\", \"seat_number\": \"1\", \"coach\": \"A\"}, "
				+ "\"2A\": {\"booking_reference\": \"a\", \"seat_number\": \"2\", \"coach\": \"A\"}, "
				+ "\"3A\": {\"booking_reference\": \"a\", \"seat_number\": \"3\", \"coach\": \"A\"}, "
				+ "\"4A\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"A\"}, "
				+ "\"1B\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"B\"}, "
				+ "\"2B\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"B\"}, "
				+ "\"3B\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"B\"}, "
				+ "\"4B\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"B\"}, "
				+ "\"5B\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"B\"}}}";
	}

	public static String fixtureWith3CoachesAnd6seatsAlreadyReservedIn1rstCoach() {
		return "{\"seats\": {" //
				+ "\"1A\": {\"booking_reference\": \"a\", \"seat_number\": \"1\", \"coach\": \"A\"}, "
				+ "\"2A\": {\"booking_reference\": \"a\", \"seat_number\": \"2\", \"coach\": \"A\"}, "
				+ "\"3A\": {\"booking_reference\": \"a\", \"seat_number\": \"3\", \"coach\": \"A\"}, "
				+ "\"4A\": {\"booking_reference\": \"a\", \"seat_number\": \"4\", \"coach\": \"A\"}, "
				+ "\"5A\": {\"booking_reference\": \"a\", \"seat_number\": \"5\", \"coach\": \"A\"}, "
				+ "\"6A\": {\"booking_reference\": \"a\", \"seat_number\": \"6\", \"coach\": \"A\"}, "
				+ "\"7A\": {\"booking_reference\": \"\", \"seat_number\": \"7\", \"coach\": \"A\"}, "
				+ "\"8A\": {\"booking_reference\": \"\", \"seat_number\": \"8\", \"coach\": \"A\"}, "
				+ "\"9A\": {\"booking_reference\": \"\", \"seat_number\": \"9\", \"coach\": \"A\"}, "
				+ "\"10A\": {\"booking_reference\": \"\", \"seat_number\": \"10\", \"coach\": \"A\"}, "
				+ "\"1B\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"B\"}, "
				+ "\"2B\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"B\"}, "
				+ "\"3B\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"B\"}, "
				+ "\"4B\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"B\"}, "
				+ "\"5B\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"B\"}, "
				+ "\"6B\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"B\"}, "
				+ "\"7B\": {\"booking_reference\": \"\", \"seat_number\": \"7\", \"coach\": \"B\"}, "
				+ "\"8B\": {\"booking_reference\": \"\", \"seat_number\": \"8\", \"coach\": \"B\"}, "
				+ "\"9B\": {\"booking_reference\": \"\", \"seat_number\": \"9\", \"coach\": \"B\"}, "
				+ "\"10B\": {\"booking_reference\": \"\", \"seat_number\": \"10\", \"coach\": \"B\"}, "
				+ "\"1C\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"C\"}, "
				+ "\"2C\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"C\"}, "
				+ "\"3C\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"C\"}, "
				+ "\"4C\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"C\"}, "
				+ "\"5C\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"C\"}, "
				+ "\"6C\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"C\"}, "
				+ "\"7C\": {\"booking_reference\": \"\", \"seat_number\": \"7\", \"coach\": \"C\"}, "
				+ "\"8C\": {\"booking_reference\": \"\", \"seat_number\": \"8\", \"coach\": \"C\"}, "
				+ "\"9C\": {\"booking_reference\": \"\", \"seat_number\": \"9\", \"coach\": \"C\"}, "
				+ "\"10C\": {\"booking_reference\": \"\", \"seat_number\": \"10\", \"coach\": \"C\"}}}";
	}

	public static String fixtureWith3CoachesAnd6seatsThen4Then4AlreadyReserved() {
		return "{\"seats\": {" //
				+ "\"1A\": {\"booking_reference\": \"a\", \"seat_number\": \"1\", \"coach\": \"A\"}, "
				+ "\"2A\": {\"booking_reference\": \"a\", \"seat_number\": \"2\", \"coach\": \"A\"}, "
				+ "\"3A\": {\"booking_reference\": \"a\", \"seat_number\": \"3\", \"coach\": \"A\"}, "
				+ "\"4A\": {\"booking_reference\": \"a\", \"seat_number\": \"4\", \"coach\": \"A\"}, "
				+ "\"5A\": {\"booking_reference\": \"a\", \"seat_number\": \"5\", \"coach\": \"A\"}, "
				+ "\"6A\": {\"booking_reference\": \"a\", \"seat_number\": \"6\", \"coach\": \"A\"}, "
				+ "\"7A\": {\"booking_reference\": \"\", \"seat_number\": \"7\", \"coach\": \"A\"}, "
				+ "\"8A\": {\"booking_reference\": \"\", \"seat_number\": \"8\", \"coach\": \"A\"}, "
				+ "\"9A\": {\"booking_reference\": \"\", \"seat_number\": \"9\", \"coach\": \"A\"}, "
				+ "\"10A\": {\"booking_reference\": \"\", \"seat_number\": \"10\", \"coach\": \"A\"}, "
				+ "\"1B\": {\"booking_reference\": \"b\", \"seat_number\": \"1\", \"coach\": \"B\"}, "
				+ "\"2B\": {\"booking_reference\": \"b\", \"seat_number\": \"2\", \"coach\": \"B\"}, "
				+ "\"3B\": {\"booking_reference\": \"b\", \"seat_number\": \"3\", \"coach\": \"B\"}, "
				+ "\"4B\": {\"booking_reference\": \"b\", \"seat_number\": \"4\", \"coach\": \"B\"}, "
				+ "\"5B\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"B\"}, "
				+ "\"6B\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"B\"}, "
				+ "\"7B\": {\"booking_reference\": \"\", \"seat_number\": \"7\", \"coach\": \"B\"}, "
				+ "\"8B\": {\"booking_reference\": \"\", \"seat_number\": \"8\", \"coach\": \"B\"}, "
				+ "\"9B\": {\"booking_reference\": \"\", \"seat_number\": \"9\", \"coach\": \"B\"}, "
				+ "\"10B\": {\"booking_reference\": \"\", \"seat_number\": \"10\", \"coach\": \"B\"}, "
				+ "\"1C\": {\"booking_reference\": \"c\", \"seat_number\": \"1\", \"coach\": \"C\"}, "
				+ "\"2C\": {\"booking_reference\": \"c\", \"seat_number\": \"2\", \"coach\": \"C\"}, "
				+ "\"3C\": {\"booking_reference\": \"c\", \"seat_number\": \"3\", \"coach\": \"C\"}, "
				+ "\"4C\": {\"booking_reference\": \"c\", \"seat_number\": \"4\", \"coach\": \"C\"}, "
				+ "\"5C\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"C\"}, "
				+ "\"6C\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"C\"}, "
				+ "\"7C\": {\"booking_reference\": \"\", \"seat_number\": \"7\", \"coach\": \"C\"}, "
				+ "\"8C\": {\"booking_reference\": \"\", \"seat_number\": \"8\", \"coach\": \"C\"}, "
				+ "\"9C\": {\"booking_reference\": \"\", \"seat_number\": \"9\", \"coach\": \"C\"}, "
				+ "\"10C\": {\"booking_reference\": \"\", \"seat_number\": \"10\", \"coach\": \"C\"}}}";
	}

}
