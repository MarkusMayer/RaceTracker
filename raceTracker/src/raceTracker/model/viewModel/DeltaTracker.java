package raceTracker.model.viewModel;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raceTracker.model.enums.Driver;
import raceTracker.model.gameStructs.LapStruct;
import raceTracker.model.gameStructs.Participant;

public class DeltaTracker {

	Map<Driver, List<LocalTime>> deltas = new HashMap<>();
	public static final int stepWidth = 100;

	public void trackProgress(List<LapStruct> readings, List<Participant> drivers, LocalTime sessionTime) {
		if (readings.size() != drivers.size())
			throw new IllegalArgumentException("readings.size()!=drivers.size()");
		for (int i = 0; i < readings.size(); i++) {
			List<LocalTime> posTime;
			if ((posTime = deltas.get(drivers.get(i).getDriverId())) == null) {
				posTime = new ArrayList<>(1000);
				posTime.add(LocalTime.MIN);
				deltas.put(drivers.get(i).getDriverId(), posTime);
			}
			LapStruct aLap = readings.get(i);
			int idx = (int) aLap.getTotalDistance() / stepWidth;
			if (idx == posTime.size()) {
				posTime.add(sessionTime);
			}
			if (idx > posTime.size()) {
				throw new IllegalArgumentException(
						String.format("missed posTime step idx: %d, posTime.size(): %d %n", idx, posTime.size()));
			}
		}

	}

	public Duration getDeltaBetweenCars(Driver driver1, Driver driver2) {
		List<LocalTime> posTimeDriver1 = deltas.get(driver1);
		List<LocalTime> posTimeDriver2 = deltas.get(driver2);

		int idx = Math.min(posTimeDriver1.size() - 1, posTimeDriver2.size() - 1);
		LocalTime driver1Time = posTimeDriver1.get(idx);
		LocalTime driver2Time = posTimeDriver2.get(idx);

		return driver1Time.isAfter(driver2Time) ? Duration.between(driver2Time, driver1Time)
				: Duration.between(driver2Time, driver1Time);
	}
}
