package com.cache;

import java.util.ArrayList;
import java.util.List;

public class TrainCaching implements ITrainCaching {

	List<SeatEntity> seats = new ArrayList<>();

	@Override
	public void Clear() throws InterruptedException {
		seats.clear();
		Thread.sleep(500);
	}

	@Override
	public void Save(final List<SeatEntity> seatEntities) throws InterruptedException {
		seats.addAll(seatEntities);
		Thread.sleep(500);
	}
}

final class Factory {
	public static ITrainCaching create() {
		return new TrainCaching();
	}
}