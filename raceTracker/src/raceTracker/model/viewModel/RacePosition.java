package raceTracker.model.viewModel;

import java.time.Duration;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.PitStatus;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.gameStructs.Lap;
import raceTracker.model.gameStructs.Participant;

public final class RacePosition {

	private final int curPosition;
	private final String driverName;
	private final Driver driverId;
	private final int currentLapNum, deltaPosition, bestLapNr, bestOverallS1LapNum, bestOverallS2LapNum,
			bestOverallS3LapNum, penalties;
	private final Duration lastLapTime, bestLapTime, deltaLastBestLap, curLapTime, s1, s2, bestLapS1Time, bestLapS2Time,
			bestLapS3Time, bestOverallS1Time, bestOverallS2Time, bestOverallS3Time, deltaS1BestLap, deltaS2BestLap,
			deltaS1Best, deltaS2Best;
	private final PitStatus pitStatus;
	private final ResultStatus resultStatus;

	public RacePosition(Lap aLap, Participant aParticipant) {
		curPosition = aLap.getCarPosition();
		driverName = aParticipant.getDriverName();
		driverId = aParticipant.getDriverId();
		s1 = aLap.getSector1TimeMSDuration();
		s2 = aLap.getSector2TimeMSDuration();
		currentLapNum = aLap.getCurrentLapNum();
		deltaPosition = aLap.getGridPosition() - aLap.getCarPosition();
		lastLapTime = aLap.getLastLapTimeDuration();
		bestLapTime = aLap.getBestLapTimeDuration();
		deltaLastBestLap = lastLapTime.minus(bestLapTime);

		bestLapNr = aLap.getBestLapNum();
		curLapTime = aLap.getCurrentLapTimeDuration();
		bestLapS1Time = aLap.getBestLapSector1TimeInMSDuration();
		bestLapS2Time = aLap.getBestLapSector2TimeInMSDuration();
		bestLapS3Time = aLap.getBestLapSector3TimeInMSDuration();
		bestOverallS1Time = aLap.getBestOverallSector1TimeInMSDuration();
		bestOverallS1LapNum = aLap.getBestOverallSector1LapNum();
		bestOverallS2Time = aLap.getBestOverallSector2TimeInMSDuration();
		bestOverallS2LapNum = aLap.getBestOverallSector2LapNum();
		bestOverallS3Time = aLap.getBestOverallSector3TimeInMSDuration();
		bestOverallS3LapNum = aLap.getBestOverallSector3LapNum();
		deltaS1BestLap = s1.minus(bestLapS1Time);
		deltaS2BestLap = s2.minus(bestLapS2Time);
		deltaS1Best = s1.minus(bestOverallS1Time);
		deltaS2Best = s2.minus(bestOverallS2Time);
		pitStatus = aLap.getPitStatus();
		penalties = aLap.getPenalties();
		resultStatus = aLap.getResultStatus();
	}

	public RacePosition(RacePosition aPos, ResultStatus resultStatus) {
		curPosition = aPos.getCurPosition();
		driverName = aPos.getDriverName();
		driverId = aPos.getDriverId();
		s1 = aPos.getSector1TimeInMS();
		s2 = aPos.getSector2TimeInMS();
		currentLapNum = aPos.getCurrentLapNum();
		deltaPosition = aPos.getDeltaPosition();
		lastLapTime = aPos.getLastLapTime();
		bestLapTime = aPos.getBestLapTime();
		deltaLastBestLap = aPos.getDeltaLastBestLap();

		bestLapNr = aPos.getBestLapNr();
		curLapTime = aPos.getCurLapTime();
		bestLapS1Time = aPos.getBestLapSector1TimeInMS();
		bestLapS2Time = aPos.getBestLapSector2TimeInMS();
		bestLapS3Time = aPos.getBestLapSector3TimeInMS();
		bestOverallS1Time = aPos.getBestOverallSector1TimeInMS();
		bestOverallS1LapNum = aPos.getBestOverallS1LapNum();
		bestOverallS2Time = aPos.getBestOverallSector2TimeInMS();
		bestOverallS2LapNum = aPos.getBestOverallS2LapNum();
		bestOverallS3Time = aPos.getBestOverallSector3TimeInMS();
		bestOverallS3LapNum = aPos.getBestOverallS3LapNum();
		deltaS1BestLap = aPos.getDeltaS1BestLapS1MS();
		deltaS2BestLap = aPos.getDeltaS2BestLapS2MS();
		deltaS1Best = aPos.getDeltaS1BestS1MS();
		deltaS2Best = aPos.getDeltaS2BestS2MS();
		pitStatus = aPos.getPitStatus();
		penalties = aPos.getPenalties();
		this.resultStatus = resultStatus;
	}

	public int getCurPosition() {
		return curPosition;
	}

	public String getDriverName() {
		return driverName;
	}

	public Duration getSector1TimeInMS() {
		return s1;
	}

	public static String formatDurationSecMillis(Duration d) {
		return String.format("%s%d : %03d ", d.isNegative() ? "-" : "",
				d.isNegative() ? d.toSecondsPart() + 1 : d.toSecondsPart(),
				d.isNegative() ? 1000 - d.toMillisPart() : d.toMillisPart());
	}

	public String getSector1() {
		return formatDurationSecMillis(s1);
	}

	public Duration getSector2TimeInMS() {
		return s2;
	}

	public String getSector2() {
		return formatDurationSecMillis(s1);
	}

	public int getCurrentLapNum() {
		return currentLapNum;
	}

	public int getDeltaPosition() {
		return deltaPosition;
	}

	public int getBestLapNr() {
		return bestLapNr;
	}

	public Duration getBestLapSector1TimeInMS() {
		return bestLapS1Time;
	}

	public Duration getBestLapSector2TimeInMS() {
		return bestLapS2Time;
	}

	public Duration getBestLapSector3TimeInMS() {
		return bestLapS3Time;
	}

	public Duration getBestOverallSector1TimeInMS() {
		return bestOverallS1Time;
	}

	public Duration getBestOverallSector2TimeInMS() {
		return bestOverallS2Time;
	}

	public Duration getBestOverallSector3TimeInMS() {
		return bestOverallS3Time;
	}

	public Duration getDeltaS1BestLapS1MS() {
		return deltaS1BestLap;
	}

	public Duration getDeltaS2BestLapS2MS() {
		return deltaS2BestLap;
	}

	public Duration getDeltaS1BestS1MS() {
		return deltaS1Best;
	}

	public Duration getDeltaS2BestS2MS() {
		return deltaS2Best;
	}

	public int getPenalties() {
		return penalties;
	}

	public Duration getLastLapTime() {
		return lastLapTime;
	}

	public Duration getBestLapTime() {
		return bestLapTime;
	}

	public Duration getCurLapTime() {
		return curLapTime;
	}

	public PitStatus getPitStatus() {
		return pitStatus;
	}

	public Duration getDeltaLastBestLap() {
		return deltaLastBestLap;
	}

	public int getBestOverallS1LapNum() {
		return bestOverallS1LapNum;
	}

	public int getBestOverallS2LapNum() {
		return bestOverallS2LapNum;
	}

	public int getBestOverallS3LapNum() {
		return bestOverallS3LapNum;
	}

	public String getPenaltiesDescription() {
		return getPenalties() == 0 ? " - " : getPenalties() + " secs";
	}

	public String getPitStatusDescription() {
		return getPitStatus().getDescription();
	}

	public ResultStatus getResultStatus() {
		return resultStatus;
	}

	public String getResultStatusShort() {
		return resultStatus.getShort();
	}

	public String getS1Description() {
		return formatOptionalSectorTime(getSector1TimeInMS()) + " / "
				+ formatDurationSecMillis(getBestLapSector1TimeInMS()) + " / "
				+ formatLapNumber(getBestOverallS1LapNum()) + formatDurationSecMillis(getBestOverallSector1TimeInMS())
				+ " (" + formatOptionalDeltaMillis(getSector1TimeInMS(), getDeltaS1BestLapS1MS()) + ") ";
	}

	public String getS2Description() {
		return formatOptionalSectorTime(getSector2TimeInMS()) + " / "
				+ formatDurationSecMillis(getBestLapSector2TimeInMS()) + " / "
				+ formatLapNumber(getBestOverallS2LapNum()) + formatDurationSecMillis(getBestOverallSector2TimeInMS())
				+ " (" + formatOptionalDeltaMillis(getSector2TimeInMS(), getDeltaS2BestLapS2MS()) + ") ";
	}

	public String getS3Description() {
		return formatDurationSecMillis(getBestLapSector3TimeInMS()) + " / " + formatLapNumber(getBestOverallS3LapNum())
				+ formatDurationSecMillis(getBestOverallSector3TimeInMS());
	}

	public String getLapDescription() {
		String res = getBestLapNr() == 0 ? " - "
				: durationToMinSecMillis(
						getResultStatus() == ResultStatus.finished ? getLastLapTime() : getCurLapTime()) + " / "
						+ formatLapNumber(getBestLapNr()) + durationToMinSecMillis(getBestLapTime()) + " ("
						+ formatDurationSecMillis(getDeltaLastBestLap()) + ") ";

//		System.out.println(res+" ==> getBestLapNr:"+getBestLapNr()+", getCurLapTime():"+getCurLapTime()+", getBestLapTime():"+getBestLapTime()+", getDeltaLastBestLap():"+getDeltaLastBestLap());

		return res;
	}

	public static String formatOptionalDeltaMillis(Duration secTime, Duration deltaMillis) {
		return secTime.isZero() ? " - " : formatDurationSecMillis(deltaMillis);
	}

	public static String formatOptionalSectorTime(Duration secTime) {
		return secTime.isZero() ? " - " : formatDurationSecMillis(secTime);
	}

	public String formatLapNumber(int lapNr) {
		return String.format("L%02d ", lapNr);
	}

	public static String durationToMinSecMillis(Duration d) {
		if (d.isNegative())
			throw new IllegalArgumentException("Negative durations not supported: " + d);
		String res = String.format("%d : %02d : %03d", d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart());
//		System.out.println("durationToMinSecMillis: " + res + " duration: " + d + " min: " + d.toMinutesPart()
//				+ " sec: " + d.toSecondsPart() + " milli:" + d.toMillisPart());
		return res;
	}

	public Driver getDriverId() {
		return driverId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bestLapNr;
		result = prime * result + ((bestLapS1Time == null) ? 0 : bestLapS1Time.hashCode());
		result = prime * result + ((bestLapS2Time == null) ? 0 : bestLapS2Time.hashCode());
		result = prime * result + ((bestLapS3Time == null) ? 0 : bestLapS3Time.hashCode());
		result = prime * result + ((bestLapTime == null) ? 0 : bestLapTime.hashCode());
		result = prime * result + bestOverallS1LapNum;
		result = prime * result + ((bestOverallS1Time == null) ? 0 : bestOverallS1Time.hashCode());
		result = prime * result + bestOverallS2LapNum;
		result = prime * result + ((bestOverallS2Time == null) ? 0 : bestOverallS2Time.hashCode());
		result = prime * result + bestOverallS3LapNum;
		result = prime * result + ((bestOverallS3Time == null) ? 0 : bestOverallS3Time.hashCode());
		result = prime * result + ((curLapTime == null) ? 0 : curLapTime.hashCode());
		result = prime * result + curPosition;
		result = prime * result + currentLapNum;
		result = prime * result + ((deltaLastBestLap == null) ? 0 : deltaLastBestLap.hashCode());
		result = prime * result + deltaPosition;
		result = prime * result + ((deltaS1Best == null) ? 0 : deltaS1Best.hashCode());
		result = prime * result + ((deltaS1BestLap == null) ? 0 : deltaS1BestLap.hashCode());
		result = prime * result + ((deltaS2Best == null) ? 0 : deltaS2Best.hashCode());
		result = prime * result + ((deltaS2BestLap == null) ? 0 : deltaS2BestLap.hashCode());
		result = prime * result + ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
		result = prime * result + ((lastLapTime == null) ? 0 : lastLapTime.hashCode());
		result = prime * result + penalties;
		result = prime * result + ((pitStatus == null) ? 0 : pitStatus.hashCode());
		result = prime * result + ((resultStatus == null) ? 0 : resultStatus.hashCode());
		result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
		result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
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
		RacePosition other = (RacePosition) obj;
		if (bestLapNr != other.bestLapNr)
			return false;
		if (bestLapS1Time == null) {
			if (other.bestLapS1Time != null)
				return false;
		} else if (!bestLapS1Time.equals(other.bestLapS1Time))
			return false;
		if (bestLapS2Time == null) {
			if (other.bestLapS2Time != null)
				return false;
		} else if (!bestLapS2Time.equals(other.bestLapS2Time))
			return false;
		if (bestLapS3Time == null) {
			if (other.bestLapS3Time != null)
				return false;
		} else if (!bestLapS3Time.equals(other.bestLapS3Time))
			return false;
		if (bestLapTime == null) {
			if (other.bestLapTime != null)
				return false;
		} else if (!bestLapTime.equals(other.bestLapTime))
			return false;
		if (bestOverallS1LapNum != other.bestOverallS1LapNum)
			return false;
		if (bestOverallS1Time == null) {
			if (other.bestOverallS1Time != null)
				return false;
		} else if (!bestOverallS1Time.equals(other.bestOverallS1Time))
			return false;
		if (bestOverallS2LapNum != other.bestOverallS2LapNum)
			return false;
		if (bestOverallS2Time == null) {
			if (other.bestOverallS2Time != null)
				return false;
		} else if (!bestOverallS2Time.equals(other.bestOverallS2Time))
			return false;
		if (bestOverallS3LapNum != other.bestOverallS3LapNum)
			return false;
		if (bestOverallS3Time == null) {
			if (other.bestOverallS3Time != null)
				return false;
		} else if (!bestOverallS3Time.equals(other.bestOverallS3Time))
			return false;
		if (curLapTime == null) {
			if (other.curLapTime != null)
				return false;
		} else if (!curLapTime.equals(other.curLapTime))
			return false;
		if (curPosition != other.curPosition)
			return false;
		if (currentLapNum != other.currentLapNum)
			return false;
		if (deltaLastBestLap == null) {
			if (other.deltaLastBestLap != null)
				return false;
		} else if (!deltaLastBestLap.equals(other.deltaLastBestLap))
			return false;
		if (deltaPosition != other.deltaPosition)
			return false;
		if (deltaS1Best == null) {
			if (other.deltaS1Best != null)
				return false;
		} else if (!deltaS1Best.equals(other.deltaS1Best))
			return false;
		if (deltaS1BestLap == null) {
			if (other.deltaS1BestLap != null)
				return false;
		} else if (!deltaS1BestLap.equals(other.deltaS1BestLap))
			return false;
		if (deltaS2Best == null) {
			if (other.deltaS2Best != null)
				return false;
		} else if (!deltaS2Best.equals(other.deltaS2Best))
			return false;
		if (deltaS2BestLap == null) {
			if (other.deltaS2BestLap != null)
				return false;
		} else if (!deltaS2BestLap.equals(other.deltaS2BestLap))
			return false;
		if (driverId != other.driverId)
			return false;
		if (driverName == null) {
			if (other.driverName != null)
				return false;
		} else if (!driverName.equals(other.driverName))
			return false;
		if (lastLapTime == null) {
			if (other.lastLapTime != null)
				return false;
		} else if (!lastLapTime.equals(other.lastLapTime))
			return false;
		if (penalties != other.penalties)
			return false;
		if (pitStatus != other.pitStatus)
			return false;
		if (resultStatus != other.resultStatus)
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
		return true;
	}

	@Override
	public String toString() {
		return "RacePosition [curPosition=" + curPosition + ", driverName=" + driverName + ", driverId=" + driverId
				+ ", currentLapNum=" + currentLapNum + ", deltaPosition=" + deltaPosition + ", bestLapNr=" + bestLapNr
				+ ", bestOverallS1LapNum=" + bestOverallS1LapNum + ", bestOverallS2LapNum=" + bestOverallS2LapNum
				+ ", bestOverallS3LapNum=" + bestOverallS3LapNum + ", penalties=" + penalties + ", lastLapTime="
				+ lastLapTime + ", bestLapTime=" + bestLapTime + ", deltaLastBestLap=" + deltaLastBestLap
				+ ", curLapTime=" + curLapTime + ", s1=" + s1 + ", s2=" + s2 + ", bestLapS1Time=" + bestLapS1Time
				+ ", bestLapS2Time=" + bestLapS2Time + ", bestLapS3Time=" + bestLapS3Time + ", bestOverallS1Time="
				+ bestOverallS1Time + ", bestOverallS2Time=" + bestOverallS2Time + ", bestOverallS3Time="
				+ bestOverallS3Time + ", deltaS1BestLap=" + deltaS1BestLap + ", deltaS2BestLap=" + deltaS2BestLap
				+ ", deltaS1Best=" + deltaS1Best + ", deltaS2Best=" + deltaS2Best + ", pitStatus=" + pitStatus
				+ ", resultStatus=" + resultStatus + "]";
	}

	public RacePosition finishRace() {
		return new RacePosition(this, ResultStatus.finished);
	}

}
