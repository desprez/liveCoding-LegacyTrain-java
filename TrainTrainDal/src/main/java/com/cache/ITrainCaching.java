package com.cache;

import java.util.List;

public interface ITrainCaching {
	void Clear() throws InterruptedException;

	void Save(List<SeatEntity> seatEntities) throws InterruptedException;
}
