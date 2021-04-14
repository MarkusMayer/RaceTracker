package raceTracker.model.viewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Sector;

public class DriverLapHistory {

	private Map<Driver, LapHistory> histories =new HashMap<>();
	private LapRecord bestLap = LapRecord.unset();
	private SectorRecord bestS1 = SectorRecord.unset(Sector.sector1);
	private SectorRecord bestS2 = SectorRecord.unset(Sector.sector2);
	private SectorRecord bestS3 = SectorRecord.unset(Sector.sector3);

	public void addLaps(List<RacePosition> racePos) {
		for (RacePosition aPos : racePos) {
			Driver d = aPos.getDriverId();
			addLapForDriver(aPos, d);
		}
	}

	void addLapForDriver(RacePosition pos, Driver aDriver) {
		if (!histories.containsKey(aDriver)) {
			histories.put(aDriver, new LapHistory(aDriver));
		}
		histories.get(aDriver).addLap(pos);
		updateRecords();
	}


	void updateRecords() {
		bestLap=histories.values().stream().map(r -> r.getPersonalBestLap()).min(Comparator.naturalOrder()).orElse(bestLap);
		bestS1 = histories.values().stream().map(r -> r.getPersonalBestS1()).min(Comparator.naturalOrder()).orElse(bestS1);
		bestS2 = histories.values().stream().map(r -> r.getPersonalBestS2()).min(Comparator.naturalOrder()).orElse(bestS2);
		bestS3 = histories.values().stream().map(r -> r.getPersonalBestS3()).min(Comparator.naturalOrder()).orElse(bestS3);
		doNotification();
	}

	private void doNotification() {
		for (LapHistory history : histories.values()) {
			history.notifyRecords(bestLap, bestS1, bestS2, bestS3);
		}
	}

	public Map<Driver, LapHistory> getHistories() {
		return Collections.unmodifiableMap(histories);
	}

	public LapRecord getBestLap() {
		return bestLap;
	}

	public SectorRecord getBestS1() {
		return bestS1;
	}

	public SectorRecord getBestS2() {
		return bestS2;
	}

	public SectorRecord getBestS3() {
		return bestS3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bestLap == null) ? 0 : bestLap.hashCode());
		result = prime * result + ((bestS1 == null) ? 0 : bestS1.hashCode());
		result = prime * result + ((bestS2 == null) ? 0 : bestS2.hashCode());
		result = prime * result + ((bestS3 == null) ? 0 : bestS3.hashCode());
		result = prime * result + ((histories == null) ? 0 : histories.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverLapHistory other = (DriverLapHistory) obj;
		if (bestLap == null) {
			if (other.bestLap != null)
				return false;
		} else if (!bestLap.equals(other.bestLap))
			return false;
		if (bestS1 == null) {
			if (other.bestS1 != null)
				return false;
		} else if (!bestS1.equals(other.bestS1))
			return false;
		if (bestS2 == null) {
			if (other.bestS2 != null)
				return false;
		} else if (!bestS2.equals(other.bestS2))
			return false;
		if (bestS3 == null) {
			if (other.bestS3 != null)
				return false;
		} else if (!bestS3.equals(other.bestS3))
			return false;
		if (histories == null) {
			if (other.histories != null)
				return false;
		} else if (!histories.equals(other.histories))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DriverLapHistory [histories=" + histories + ", bestLap=" + bestLap + ", bestS1=" + bestS1 + ", bestS2="
				+ bestS2 + ", bestS3=" + bestS3 + "]";
	}

}
