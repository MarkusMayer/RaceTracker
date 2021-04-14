package raceTracker.model.viewModel;

import java.time.Duration;
import java.util.Objects;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Sector;

public class SectorRecord implements Comparable<SectorRecord> {

	private final Sector sector;
	private final Duration secTime;
	private final Driver driver;
	private final int lapNum;
	private final boolean isSet;

	public SectorRecord(Sector sector, Duration secTime, Driver driver, int lapNum) {
		this(sector, secTime, driver, lapNum, true);
	}

	private SectorRecord(Sector sector, Duration secTime, Driver driver, int lapNum, boolean isSet) {
		super();
		this.sector = sector;
		this.secTime = secTime;
		this.driver = driver;
		this.lapNum = lapNum;
		this.isSet = isSet;
	}

	public static SectorRecord unset(Sector sector) {
		return new SectorRecord(sector, Duration.ofMillis(Long.MAX_VALUE), Driver.human0, 0, false);
	}

	public Sector getSector() {
		return sector;
	}

	public Duration getSecTime() {
		return secTime;
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

	public SectorRecord update(Duration secTime, Driver driver,int lapNum) {
		Objects.requireNonNull(secTime);
		if (secTime == Duration.ZERO)
			return this;
		if (this.secTime.minus(secTime).isNegative())
			return this;
		return new SectorRecord(getSector(), secTime, driver, lapNum);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + (isSet ? 1231 : 1237);
		result = prime * result + lapNum;
		result = prime * result + ((secTime == null) ? 0 : secTime.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
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
		SectorRecord other = (SectorRecord) obj;
		if (driver != other.driver)
			return false;
		if (isSet != other.isSet)
			return false;
		if (lapNum != other.lapNum)
			return false;
		if (secTime == null) {
			if (other.secTime != null)
				return false;
		} else if (!secTime.equals(other.secTime))
			return false;
		if (sector != other.sector)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SectorRecord [sector=" + sector + ", secTime=" + secTime + ", driver=" + driver + ", lapNum=" + lapNum
				+ ", isSet=" + isSet + "]";
	}

	@Override
	public int compareTo(SectorRecord o) {
		return getSecTime().compareTo(o.getSecTime());
	}

}
