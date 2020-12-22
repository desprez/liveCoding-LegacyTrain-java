package com.traintrain;

public class Seat {

	private String coachName;

	private int seatNumber;

	private String bookingReference;

	public Seat(final String coach, final int seatNumber) {
		this(coach, seatNumber, "");
	}

	public Seat(final String coachName, final int seatNumber, final String bookingReference) {
		this.coachName = coachName;
		this.seatNumber = seatNumber;
		this.bookingReference = bookingReference;
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

	public String getBookingReference() {
		return bookingReference;
	}

	public void setBookingReference(final String bookingReference) {
		this.bookingReference = bookingReference;
	}

	@Override
	public String toString() {
		return coachName + seatNumber;
	}
}
