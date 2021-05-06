package raceTracker.model.viewModel;

import java.time.Duration;
import java.util.Objects;

import raceTracker.model.enums.Driver;

public class LapRecord implements Comparable<LapRecord>{

	private final Duration lapTime;
	private final Driver driver;
	private final int lapNum;

	private final boolean isSet;

	public LapRecord(Duration lapTime, Driver driver, int lapNum) {
		this(lapTime, driver, lapNum, true);
	}

	private LapRecord(Duration lapTime, Driver driver, int lapNum, boolean isSet) {
		this.lapTime = lapTime;
		this.driver = driver;
		this.lapNum = lapNum;
		this.isSet = isSet;
	}
	
	private LapRecord() {
		this(Duration.ofMillis(Long.MAX_VALUE),Driver.human0,-1,false);
	}

	public Duration getLapTime() {
		return lapTime;
	}

	public Driver getDriver() {
		return driver;
	}

	public int getLapNum() {
		return lapNum;
	}
	
	public boolean isSet() {
		return isSet;
	}

	public LapRecord update(Lap aLap, Driver driver) {
		Objects.requireNonNull(aLap);
		if (!aLap.isFinished())
			return this;
		if (getLapTime().minus(aLap.getLapTime()).isNegative()) {
			return this;
		}
		return new LapRecord(aLap.getLapTime(), driver, aLap.getLapNum());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + (isSet ? 1231 : 1237);
		result = prime * result + lapNum;
		result = prime * result + ((lapTime == null) ? 0 : lapTime.hashCode());
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
		LapRecord other = (LapRecord) obj;
		if (driver != other.driver)
			return false;
		if (isSet != other.isSet)
			return false;
		if (lapNum != other.lapNum)
			return false;
		if (lapTime == null) {
			if (other.lapTime != null)
				return false;
		} else if (!lapTime.equals(other.lapTime))
			return false;
		return true;
	}
	
	public String getLapRecordDescription() {
		return String.format("L%d - %s - %s", lapNum,driver.getShortName(),RacePosition.durationToMinSecMillis(lapTime));
	}

	@Override
	public String toString() {
		return "LapRecord [lapTime=" + lapTime + ", driver=" + driver + ", lapNum=" + lapNum + ", isSet=" + isSet + "]";
	}

	public static LapRecord unset() {
		return new LapRecord();
	}

	@Override
	public int compareTo(LapRecord o) {
		return getLapTime().compareTo(o.getLapTime());
	}
	
	public Duration getDelta(Duration compLapTime) {
		return isSet() ? compLapTime.minus(getLapTime()): Duration.ZERO;
	}

}
