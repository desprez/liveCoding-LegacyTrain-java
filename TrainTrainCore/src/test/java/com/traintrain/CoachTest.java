package com.traintrain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CoachTest {

	@Test
	public void should_be_an_immutable_value_object() {
		final Coach coach = new Coach("A");
		final Coach other = new Coach("A");

		assertThat(other).isEqualTo(coach);
		other.addSeat(new Seat("A", 1));
	}

}
