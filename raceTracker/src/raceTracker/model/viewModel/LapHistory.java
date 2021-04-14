package raceTracker.model.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.enums.Sector;

public class LapHistory {

	Map<Integer, Lap> laps = new HashMap<>();

	private LapRecord personalBestLap = LapRecord.unset();
	private SectorRecord personalBestS1 = SectorRecord.unset(Sector.sector1);
	private SectorRecord personalBestS2 = SectorRecord.unset(Sector.sector2);
	private SectorRecord personalBestS3 = SectorRecord.unset(Sector.sector3);

	private final Driver driver;

	public LapHistory(Driver aDriver) {
		driver = aDriver;
	}

	public void addLap(RacePosition pos) {
		int currentLapNum = pos.getCurrentLapNum();
		if (!laps.containsKey(currentLapNum) && laps.containsKey(currentLapNum - 1)) {
			Lap finalizedLap = laps.get(currentLapNum - 1).finalizeLap(pos);
			if (pos.getResultStatus() != ResultStatus.finished) {
				laps.put(currentLapNum - 1, finalizedLap);
			}
			updatePersonalRecords(finalizedLap);
		}

		Lap newLap = new Lap(pos);
		laps.put(currentLapNum, newLap);

		updatePersonalRecords(newLap);
	}

	public List<Lap> getLaps() {
		return Collections.unmodifiableList(new ArrayList<Lap>(laps.values()));
	}

	public void notifyRecords(LapRecord bestLap, SectorRecord bestS1, SectorRecord bestS2, SectorRecord bestS3) {
		for (int lapNum : laps.keySet()) {
			laps.put(lapNum, laps.get(lapNum).updateRecords(bestLap, bestS1, bestS2, bestS3));
		}
	}

	void updatePersonalRecords(Lap aLap) {
		personalBestLap = personalBestLap.update(aLap);
		personalBestS1 = personalBestS1.update(aLap.getS1(), driver, aLap.getLapNum());
		personalBestS2 = personalBestS2.update(aLap.getS2(), driver, aLap.getLapNum());
		personalBestS3 = personalBestS3.update(aLap.getS3(), driver, aLap.getLapNum());

		doNotificationPersonalBest();
	}

	private void doNotificationPersonalBest() {
		for (int lapNum : laps.keySet()) {
			laps.put(lapNum, laps.get(lapNum).updatePersonalRecords(personalBestLap, personalBestS1, personalBestS2,
					personalBestS3));
		}
	}

	public LapRecord getPersonalBestLap() {
		return personalBestLap;
	}

	public SectorRecord getPersonalBestS1() {
		return personalBestS1;
	}

	public SectorRecord getPersonalBestS2() {
		return personalBestS2;
	}

	public SectorRecord getPersonalBestS3() {
		return personalBestS3;
	}

	public Driver getDriver() {
		return driver;
	}

	private Lap getCurrentLap() {
		int highestLapNum = laps.keySet().stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
		return laps.get(highestLapNum);
	}

	public int getCurrentLapNum() {
		return getCurrentLap().getLapNum();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((laps == null) ? 0 : laps.hashCode());
		result = prime * result + ((personalBestLap == null) ? 0 : personalBestLap.hashCode());
		result = prime * result + ((personalBestS1 == null) ? 0 : personalBestS1.hashCode());
		result = prime * result + ((personalBestS2 == null) ? 0 : personalBestS2.hashCode());
		result = prime * result + ((personalBestS3 == null) ? 0 : personalBestS3.hashCode());
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
		LapHistory other = (LapHistory) obj;
		if (driver != other.driver)
			return false;
		if (laps == null) {
			if (other.laps != null)
				return false;
		} else if (!laps.equals(other.laps))
			return false;
		if (personalBestLap == null) {
			if (other.personalBestLap != null)
				return false;
		} else if (!personalBestLap.equals(other.personalBestLap))
			return false;
		if (personalBestS1 == null) {
			if (other.personalBestS1 != null)
				return false;
		} else if (!personalBestS1.equals(other.personalBestS1))
			return false;
		if (personalBestS2 == null) {
			if (other.personalBestS2 != null)
				return false;
		} else if (!personalBestS2.equals(other.personalBestS2))
			return false;
		if (personalBestS3 == null) {
			if (other.personalBestS3 != null)
				return false;
		} else if (!personalBestS3.equals(other.personalBestS3))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LapHistory [laps=" + laps + ", personalBestLap=" + personalBestLap + ", personalBestS1="
				+ personalBestS1 + ", personalBestS2=" + personalBestS2 + ", personalBestS3=" + personalBestS3
				+ ", driver=" + driver + "]";
	}

}
