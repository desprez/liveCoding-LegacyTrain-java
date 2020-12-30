package com.traintrain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Seat implements ValueObject {

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

	public boolean isAvailable() {
		return getBookingReference().isEmpty();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Seat)) {
			return false;
		}
		final Seat other = (Seat) obj;
		return new EqualsBuilder() //
				.append(getCoachName(), other.getCoachName()) //
				.append(getSeatNumber(), other.getSeatNumber()) //
				.append(getBookingReference(), other.getBookingReference()) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder() //
				.append(getCoachName()) //
				.append(getSeatNumber()) //
				.append(getBookingReference()) //
				.toHashCode();
	}

}
