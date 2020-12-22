package com.traintrain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

public class TrainTrainTest {

	@Test
	public void should_expose_coaches() throws IOException {
		// Given
		final String fixture = TrainTopologyGenerator.fixtureWith2CoachesAnd3seatsAlreadyReservedIn1rstCoach();
		// When
		final Train train = new Train(fixture);
		// then
		assertThat(train.getCoaches()).hasSize(2);
		assertThat(train.getCoaches().get("A").getSeats()).hasSize(4);
		assertThat(train.getCoaches().get("B").getSeats()).hasSize(5);
	}

}
