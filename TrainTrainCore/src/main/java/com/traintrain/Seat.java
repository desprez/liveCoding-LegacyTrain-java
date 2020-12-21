package com.traintrain;

public class Seat {

	private String coachName;

	private int seatNumber;

	private String bookingRef;

	public Seat(final String coach, final int seatNumber) {
		this(coach, seatNumber, "");
	}

	public Seat(final String coachName, final int seatNumber, final String bookingRef) {
		this.coachName = coachName;
		this.seatNumber = seatNumber;
		this.bookingRef = bookingRef;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(final String coachName) {
		this.coachName = coachName;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(final int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getBookingRef() {
		return bookingRef;
	}

	public void setBookingRef(final String bookingRef) {
		this.bookingRef = bookingRef;
	}

	@Override
	public String toString() {
		return coachName + seatNumber;
	}
}
