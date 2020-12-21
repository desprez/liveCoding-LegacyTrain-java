package com.cache;

import java.util.ArrayList;
import java.util.List;

public class TrainEntity {

	private final String trainId;
	public List<SeatEntity> Seats = new ArrayList<SeatEntity>();

	public TrainEntity(String trainId) {

		this.trainId = trainId;
	}

	public String getTrainId() {
		return trainId;
	}
}
