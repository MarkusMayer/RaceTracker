package raceTracker.model.viewModel;

import java.time.Duration;
import java.util.Objects;

import raceTracker.model.gameStructs.LapStruct;

public class Lap {

	private final int lapNum;
	private final Duration lapTime, s1, s2, s3, deltaLapPersonalBest, deltaLapBest, deltaS1PersonalBest,
			deltaS2PersonalBest, deltaS3PersonalBest, deltaS1Best, deltaS2Best, deltaS3Best;
	private final boolean finished;

	Lap(int lapNum, Duration lapTime, Duration s1, Duration s2, Duration s3, Duration deltaLapPersonalBest,
			Duration deltaLapBest, Duration deltaS1PersonalBest, Duration deltaS2PersonalBest,
			Duration deltaS3PersonalBest, Duration deltaS1Best, Duration deltaS2Best, Duration deltaS3Best,
			boolean finished) {
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
		this.finished = finished;
	}
	
	Lap(int lapNum, Duration lapTime, Duration s1, Duration s2, Duration s3, Duration deltaLapPersonalBest,
			Duration deltaS1PersonalBest, Duration deltaS2PersonalBest,
			Duration deltaS3PersonalBest,
			boolean finished,LapRecord bestLap, SectorRecord bestS1, SectorRecord bestS2,
			SectorRecord bestS3) {
		super();
		this.lapNum = lapNum;
		this.lapTime = lapTime;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.finished = finished;
		this.deltaLapPersonalBest = deltaLapPersonalBest;
		this.deltaS1PersonalBest = deltaS1PersonalBest;
		this.deltaS2PersonalBest = deltaS2PersonalBest;
		this.deltaS3PersonalBest = deltaS3PersonalBest;

		this.deltaLapBest = getDeltaAbsoluteBestLap(bestLap);
		this.deltaS1Best = getDeltaAbsoluteBestS1(bestS1);
		this.deltaS2Best = getDeltaAbsoluteBestS2(bestS2);
		this.deltaS3Best = getDeltaAbsoluteBestS3(bestS3);
	}

	public Lap finalizeLap(LapStruct lap, LapRecord bestLap, SectorRecord bestS1, SectorRecord bestS2,
			SectorRecord bestS3, SectorRecord personalBestS3) {
		Duration lastLapTime = lap.getLastLapTimeDuration();
		Duration lastS3 = lastLapTime.minus(s1.plus(s2));
		return new Lap(lapNum, lastLapTime, s1, s2, lastS3, lap.getDeltaLastLapPersonalBestLapTime(),
				bestLap.getDelta(lastLapTime), lap.getDeltaPersonalS1Best(), lap.getDeltaPersonalS2Best(),
				personalBestS3.getDelta(lastS3), bestS1.getDelta(lap.getSector1Time()),
				bestS2.getDelta(lap.getSector2Time()), bestS3.getDelta(lastS3), true);
	}

	public static Lap unfinishedLap(LapStruct lap, LapRecord bestLap, SectorRecord bestS1, SectorRecord bestS2, SectorRecord bestS3) {
// TODO: fix finalizeLap Method, unfinished Laps are added to Lap History to save S1 and S2 times. S3 and lapTime are set when next Lap is started and finalizeLap is called
// TODO: Add absolute Records to signature to calculate deltas to best driver in session		
		return new Lap(lap.getCurrentLapNum(),Duration.ZERO,lap.getSector1Time(),lap.getSector2Time(),Duration.ZERO,Duration.ZERO,
				lap.getDeltaPersonalS1Best(),lap.getDeltaPersonalS2Best(),
				Duration.ZERO,false,bestLap,bestS1,bestS2,bestS3);
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

	public boolean isPersonalBest() {
		return isFinished() && getDeltaLapPersonalBest() == Duration.ZERO;
	}

	public boolean isOverallBest() {
		return isFinished() && getDeltaLapBest() == Duration.ZERO;
	}

	public boolean isPersonalBestS1() {
		return isS1Finished() && getDeltaS1PersonalBest() == Duration.ZERO;
	}

	public boolean isOverallBestS1() {
		return isS1Finished() && getDeltaS1Best() == Duration.ZERO;
	}

	public boolean isPersonalBestS2() {
		return isS2Finished() && getDeltaS2PersonalBest() == Duration.ZERO;
	}

	public boolean isOverallBestS2() {
		return isS2Finished() && getDeltaS2Best() == Duration.ZERO;
	}

	public boolean isPersonalBestS3() {
		return isS3Finished() && getDeltaS3PersonalBest() == Duration.ZERO;
	}

	public boolean isOverallBestS3() {
		return isS3Finished() && getDeltaS3Best() == Duration.ZERO;
	}

	public boolean isFinished() {
		return finished;
	}
	
	public boolean isS1Finished() {
		return s1!=Duration.ZERO;
	}

	public boolean isS2Finished() {
		return s2!=Duration.ZERO;
	}

	public boolean isS3Finished() {
		return s3!=Duration.ZERO;
	}

	
	public Lap updateRecords(LapRecord bestLap, SectorRecord bestS1, SectorRecord bestS2, SectorRecord bestS3) {
		Objects.requireNonNull(bestLap);
		Objects.requireNonNull(bestS1);
		Objects.requireNonNull(bestS2);
		Objects.requireNonNull(bestS3);
		Duration deltaLap = getDeltaAbsoluteBestLap(bestLap);
		Duration deltaS1 = getDeltaAbsoluteBestS1(bestS1);
		Duration deltaS2 = getDeltaAbsoluteBestS2(bestS2);
		Duration deltaS3 = getDeltaAbsoluteBestS3(bestS3);

		return new Lap(getLapNum(), getLapTime(), getS1(), getS2(), getS3(), getDeltaLapPersonalBest(),
				min(deltaLap, getDeltaLapBest()), getDeltaS1PersonalBest(), getDeltaS2PersonalBest(),
				getDeltaS3PersonalBest(), min(deltaS1, getDeltaS1Best()), min(deltaS2, getDeltaS2Best()),
				min(deltaS3, getDeltaS3Best()), isFinished());
	}

	private Duration getDeltaAbsoluteBestS1(SectorRecord bestS1) {
		return bestS1.isSet() && isS1Finished() ? getS1().minus(bestS1.getSecTime()):Duration.ZERO;
	}

	private Duration getDeltaAbsoluteBestS2(SectorRecord bestS2) {
		return bestS2.isSet() && isS2Finished() ? getS2().minus(bestS2.getSecTime()):Duration.ZERO;
	}
	
	private Duration getDeltaAbsoluteBestS3(SectorRecord bestS3) {
		return bestS3.isSet() && isS3Finished() ? getS3().minus(bestS3.getSecTime()):Duration.ZERO;
	}


	private Duration getDeltaAbsoluteBestLap(LapRecord bestLap) {
		return isFinished() ? getLapTime().minus(bestLap.getLapTime()) : Duration.ZERO;
	}

	public Lap updatePersonalRecords(LapRecord personalBestLap, SectorRecord personalBestS1,
			SectorRecord personalBestS2, SectorRecord personalBestS3) {
		Objects.requireNonNull(personalBestLap);
		Objects.requireNonNull(personalBestS1);
		Objects.requireNonNull(personalBestS2);
		Objects.requireNonNull(personalBestS3);
		Duration deltaPersonalBestLap = getDeltaAbsoluteBestLap(personalBestLap);
		Duration deltaPersonalBestS1 = getDeltaAbsoluteBestS1(personalBestS1);
		Duration deltaPersonalBestS2 = getDeltaAbsoluteBestS2(personalBestS2);
		Duration deltaPersonalBestS3 = getDeltaAbsoluteBestS3(personalBestS3);

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
		String res = (isFinished() ? RacePosition.durationToMinSecMillis(getLapTime()) : "-")
				+ (isFinished()
						? " (" + RacePosition.formatDurationSecMillis(getDeltaLapPersonalBest()) + " / "
								+ RacePosition.formatDurationSecMillis(getDeltaLapBest()) + ") "
						: "");

//			System.out.println(res+" ==> getBestLapNr:"+getBestLapNr()+", getCurLapTime():"+getCurLapTime()+", getBestLapTime():"+getBestLapTime()+", getDeltaLastBestLap():"+getDeltaLastBestLap());

		return res;
	}

	public String getS1Description() {
		return RacePosition.formatOptionalSectorTime(getS1()) + " / " + " ("
				+ RacePosition.formatOptionalDeltaMillis(getS1(), getDeltaS1PersonalBest()) + " / "
				+ RacePosition.formatOptionalDeltaMillis(getS1(), getDeltaS1Best()) + ") ";
	}

	public String getS2Description() {
		return RacePosition.formatOptionalSectorTime(getS2()) + " / " + " ("
				+ RacePosition.formatOptionalDeltaMillis(getS2(), getDeltaS2PersonalBest()) + " / "
				+ RacePosition.formatOptionalDeltaMillis(getS2(), getDeltaS2Best()) + ") ";
	}

	public String getS3Description() {
		return RacePosition.formatOptionalSectorTime(getS3()) + " / " + " ("
				+ RacePosition.formatOptionalDeltaMillis(getS3(), getDeltaS3PersonalBest()) + " / "
				+ RacePosition.formatOptionalDeltaMillis(getS3(), getDeltaS3Best()) + ") ";
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
		result = prime * result + (finished ? 1231 : 1237);
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
		if (finished != other.finished)
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
				+ deltaS2Best + ", deltaS3Best=" + deltaS3Best + ", finished=" + finished + "]";
	}

}
