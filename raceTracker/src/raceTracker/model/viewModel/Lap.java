package raceTracker.model.viewModel;

import java.time.Duration;
import java.util.Objects;

public class Lap {

	private final int lapNum;
	private final Duration lapTime, s1, s2, s3, deltaLapPersonalBest, deltaLapBest, deltaS1PersonalBest,
			deltaS2PersonalBest, deltaS3PersonalBest, deltaS1Best, deltaS2Best, deltaS3Best;
	private final boolean isFinished;

	public Lap(int lapNum, Duration lapTime, Duration s1, Duration s2, Duration s3, Duration deltaLapPersonalBest,
			Duration deltaLapBest, Duration deltaS1PersonalBest, Duration deltaS2PersonalBest,
			Duration deltaS3PersonalBest, Duration deltaS1Best, Duration deltaS2Best, Duration deltaS3Best,
			boolean isFinished) {
		super();
		this.lapNum = lapNum;
		this.lapTime = lapTime;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.deltaLapPersonalBest = deltaLapPersonalBest;
		this.deltaLapBest = deltaLapBest;
		this.deltaS1PersonalBest = deltaS1PersonalBest;
		this.deltaS2PersonalBest = deltaS2PersonalBest;
		this.deltaS3PersonalBest = deltaS3PersonalBest;
		this.deltaS1Best = deltaS1Best;
		this.deltaS2Best = deltaS2Best;
		this.deltaS3Best = deltaS3Best;
		this.isFinished = isFinished;
	}

	public Lap(RacePosition pos) {
		this.lapNum = pos.getCurrentLapNum();
		this.lapTime = Duration.ZERO;
		this.s1 = pos.getSector1TimeInMS();
		this.s2 = pos.getSector2TimeInMS();
		this.s3 = Duration.ZERO;
		this.deltaLapPersonalBest = Duration.ZERO;
		this.deltaLapBest = pos.getDeltaLastBestLap();
		this.deltaS1PersonalBest = Duration.ZERO;
		this.deltaS2PersonalBest = Duration.ZERO;
		this.deltaS3PersonalBest = Duration.ZERO;
		this.deltaS1Best = pos.getDeltaS1BestS1MS();
		this.deltaS2Best = pos.getDeltaS2BestS2MS();
		this.deltaS3Best = Duration.ZERO;
		this.isFinished = false;
	}

	public int getLapNum() {
		return lapNum;
	}

	public Duration getLapTime() {
		return lapTime;
	}

	public Duration getS1() {
		return s1;
	}

	public Duration getS2() {
		return s2;
	}

	public Duration getS3() {
		return s3;
	}

	public Duration getDeltaLapPersonalBest() {
		return deltaLapPersonalBest;
	}

	public Duration getDeltaLapBest() {
		return deltaLapBest;
	}

	public Duration getDeltaS1PersonalBest() {
		return deltaS1PersonalBest;
	}

	public Duration getDeltaS2PersonalBest() {
		return deltaS2PersonalBest;
	}

	public Duration getDeltaS3PersonalBest() {
		return deltaS3PersonalBest;
	}

	public Duration getDeltaS1Best() {
		return deltaS1Best;
	}

	public Duration getDeltaS2Best() {
		return deltaS2Best;
	}

	public Duration getDeltaS3Best() {
		return deltaS3Best;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public Lap finalizeLap(RacePosition pos) {
		return new Lap(getLapNum(), pos.getLastLapTime(), getS1(), getS2(),
				pos.getLastLapTime().minus(getS1()).minus(getS2()), getDeltaLapPersonalBest(), getDeltaLapBest(),
				getDeltaS1PersonalBest(), getDeltaS2PersonalBest(), getDeltaS3PersonalBest(), getDeltaS1Best(),
				getDeltaS2Best(), getDeltaS3Best(), true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deltaLapBest == null) ? 0 : deltaLapBest.hashCode());
		result = prime * result + ((deltaLapPersonalBest == null) ? 0 : deltaLapPersonalBest.hashCode());
		result = prime * result + ((deltaS1Best == null) ? 0 : deltaS1Best.hashCode());
		result = prime * result + ((deltaS1PersonalBest == null) ? 0 : deltaS1PersonalBest.hashCode());
		result = prime * result + ((deltaS2Best == null) ? 0 : deltaS2Best.hashCode());
		result = prime * result + ((deltaS2PersonalBest == null) ? 0 : deltaS2PersonalBest.hashCode());
		result = prime * result + ((deltaS3Best == null) ? 0 : deltaS3Best.hashCode());
		result = prime * result + ((deltaS3PersonalBest == null) ? 0 : deltaS3PersonalBest.hashCode());
		result = prime * result + (isFinished ? 1231 : 1237);
		result = prime * result + lapNum;
		result = prime * result + ((lapTime == null) ? 0 : lapTime.hashCode());
		result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
		result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
		result = prime * result + ((s3 == null) ? 0 : s3.hashCode());
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
		Lap other = (Lap) obj;
		if (deltaLapBest == null) {
			if (other.deltaLapBest != null)
				return false;
		} else if (!deltaLapBest.equals(other.deltaLapBest))
			return false;
		if (deltaLapPersonalBest == null) {
			if (other.deltaLapPersonalBest != null)
				return false;
		} else if (!deltaLapPersonalBest.equals(other.deltaLapPersonalBest))
			return false;
		if (deltaS1Best == null) {
			if (other.deltaS1Best != null)
				return false;
		} else if (!deltaS1Best.equals(other.deltaS1Best))
			return false;
		if (deltaS1PersonalBest == null) {
			if (other.deltaS1PersonalBest != null)
				return false;
		} else if (!deltaS1PersonalBest.equals(other.deltaS1PersonalBest))
			return false;
		if (deltaS2Best == null) {
			if (other.deltaS2Best != null)
				return false;
		} else if (!deltaS2Best.equals(other.deltaS2Best))
			return false;
		if (deltaS2PersonalBest == null) {
			if (other.deltaS2PersonalBest != null)
				return false;
		} else if (!deltaS2PersonalBest.equals(other.deltaS2PersonalBest))
			return false;
		if (deltaS3Best == null) {
			if (other.deltaS3Best != null)
				return false;
		} else if (!deltaS3Best.equals(other.deltaS3Best))
			return false;
		if (deltaS3PersonalBest == null) {
			if (other.deltaS3PersonalBest != null)
				return false;
		} else if (!deltaS3PersonalBest.equals(other.deltaS3PersonalBest))
			return false;
		if (isFinished != other.isFinished)
			return false;
		if (lapNum != other.lapNum)
			return false;
		if (lapTime == null) {
			if (other.lapTime != null)
				return false;
		} else if (!lapTime.equals(other.lapTime))
			return false;
		if (s1 == null) {
			if (other.s1 != null)
				return false;
		} else if (!s1.equals(other.s1))
			return false;
		if (s2 == null) {
			if (other.s2 != null)
				return false;
		} else if (!s2.equals(other.s2))
			return false;
		if (s3 == null) {
			if (other.s3 != null)
				return false;
		} else if (!s3.equals(other.s3))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lap [lapNum=" + lapNum + ", lapTime=" + lapTime + ", s1=" + s1 + ", s2=" + s2 + ", s3=" + s3
				+ ", deltaLapPersonalBest=" + deltaLapPersonalBest + ", deltaLapBest=" + deltaLapBest
				+ ", deltaS1PersonalBest=" + deltaS1PersonalBest + ", deltaS2PersonalBest=" + deltaS2PersonalBest
				+ ", deltaS3PersonalBest=" + deltaS3PersonalBest + ", deltaS1Best=" + deltaS1Best + ", deltaS2Best="
				+ deltaS2Best + ", deltaS3Best=" + deltaS3Best + ", isFinished=" + isFinished + "]";
	}

	public Lap updateRecords(LapRecord bestLap, SectorRecord bestS1, SectorRecord bestS2, SectorRecord bestS3) {
		Objects.requireNonNull(bestLap);
		Objects.requireNonNull(bestS1);
		Objects.requireNonNull(bestS2);
		Objects.requireNonNull(bestS3);
		Duration deltaLap = getLapTime().minus(bestLap.getLapTime());
		Duration deltaS1 = getS1().minus(bestS1.getSecTime());
		Duration deltaS2 = getS2().minus(bestS2.getSecTime());
		Duration deltaS3 = getS3().minus(bestS3.getSecTime());

		return new Lap(getLapNum(), getLapTime(), getS1(), getS2(), getS3(), getDeltaLapPersonalBest(),
				min(deltaLap, getDeltaLapBest()), getDeltaS1PersonalBest(), getDeltaS2PersonalBest(),
				getDeltaS3PersonalBest(), min(deltaS1, getDeltaS1Best()), min(deltaS2, getDeltaS2Best()),
				min(deltaS3, getDeltaS3Best()), isFinished());
	}

	public Lap updatePersonalRecords(LapRecord personalBestLap, SectorRecord personalBestS1,
			SectorRecord personalBestS2, SectorRecord personalBestS3) {
		Objects.requireNonNull(personalBestLap);
		Objects.requireNonNull(personalBestS1);
		Objects.requireNonNull(personalBestS2);
		Objects.requireNonNull(personalBestS3);
		Duration deltaPersonalBestLap = getLapTime().minus(personalBestLap.getLapTime());
		Duration deltaPersonalBestS1 = getS1().minus(personalBestS1.getSecTime());
		Duration deltaPersonalBestS2 = getS2().minus(personalBestS2.getSecTime());
		Duration deltaPersonalBestS3 = getS3().minus(personalBestS3.getSecTime());

		return new Lap(getLapNum(), getLapTime(), getS1(), getS2(), getS3(),
				min(deltaPersonalBestLap, getDeltaLapPersonalBest()), getDeltaLapBest(),
				min(deltaPersonalBestS1, getDeltaS1PersonalBest()), min(deltaPersonalBestS2, getDeltaS2PersonalBest()),
				min(deltaPersonalBestS3, getDeltaS3PersonalBest()), getDeltaS1Best(), getDeltaS2Best(),
				getDeltaS3Best(), isFinished());

	}

	private static Duration min(Duration d1, Duration d2) {
		Objects.requireNonNull(d1);
		Objects.requireNonNull(d2);
		return d1.compareTo(d2) > 0 ? d1 : d2;
	}

	public String getLapTimeDescription() {
			String res= 
							(isFinished() ? RacePosition.durationToMinSecMillis(getLapTime()) : "-") + (isFinished() ?
							" ("+ RacePosition.formatDurationSecMillis(getDeltaLapPersonalBest())+" / "+RacePosition.formatDurationSecMillis(getDeltaLapBest()) + ") " : "");

//			System.out.println(res+" ==> getBestLapNr:"+getBestLapNr()+", getCurLapTime():"+getCurLapTime()+", getBestLapTime():"+getBestLapTime()+", getDeltaLastBestLap():"+getDeltaLastBestLap());

			return res;
	}
	
	public String getS1Description() {
		return RacePosition.formatOptionalSectorTime(getS1()) + " / "
				+ " (" + RacePosition.formatOptionalDeltaMillis(getS1(), getDeltaS1PersonalBest()) + " / "+ RacePosition.formatOptionalDeltaMillis(getS1(), getDeltaS1Best())+ ") ";
	}
	
	public String getS2Description() {
		return RacePosition.formatOptionalSectorTime(getS2()) + " / "
				+ " (" + RacePosition.formatOptionalDeltaMillis(getS2(), getDeltaS2PersonalBest()) + " / "+ RacePosition.formatOptionalDeltaMillis(getS2(), getDeltaS2Best())+ ") ";
	}
	
	public String getS3Description() {
		return RacePosition.formatOptionalSectorTime(getS3()) + " / "
				+ " (" + RacePosition.formatOptionalDeltaMillis(getS3(), getDeltaS3PersonalBest()) + " / "+ RacePosition.formatOptionalDeltaMillis(getS3(), getDeltaS3Best())+ ") ";
	}
}
