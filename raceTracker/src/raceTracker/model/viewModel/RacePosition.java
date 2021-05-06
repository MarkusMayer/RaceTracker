package raceTracker.model.viewModel;

import java.time.Duration;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.PitStatus;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.gameStructs.LapStruct;
import raceTracker.model.gameStructs.Participant;

public final class RacePosition {

	private final int curPosition;
	private final String driverName;
	private final Driver driverId;
	private final int currentLapNum, deltaPosition, bestLapNr, personalBestS1LapNum, personalBestS2LapNum,
			personalBestS3LapNum, penalties;
	private final Duration lastLapTime, bestLapTime, deltaLastBestLap, curLapTime, s1, s2, personalBestLapS1Time, personalBestLapS2Time,
			personalBestLapS3Time, personalBestS1Time, personalBestS2Time, personalBestS3Time, deltaPersonalS1BestLap, deltaPersonalS2BestLap,
			deltaPersonalS1Best, deltaPersonalS2Best;
	private final PitStatus pitStatus;
	private final ResultStatus resultStatus;

	public RacePosition(LapStruct aLap, Participant aParticipant) {
		curPosition = aLap.getCarPosition();
		driverName = aParticipant.getDriverName();
		driverId = aParticipant.getDriverId();
		s1 = aLap.getSector1Time();
		s2 = aLap.getSector2Time();
		currentLapNum = aLap.getCurrentLapNum();
		deltaPosition = aLap.getDeltaPosition();
		lastLapTime = aLap.getLastLapTimeDuration();
		bestLapTime = aLap.getBestLapTimeDuration();
// TODO Bug: In finished last lap RacePos this should be the delta to curLapTime
		deltaLastBestLap = aLap.getDeltaLastLapPersonalBestLapTime();

		bestLapNr = aLap.getBestLapNum();
		curLapTime = aLap.getCurrentLapTimeDuration();

		personalBestLapS1Time = aLap.getBestLapSector1Time();
		personalBestLapS2Time = aLap.getBestLapSector2Time();
		personalBestLapS3Time = aLap.getBestLapSector3Time();
		personalBestS1Time = aLap.getBestOverallSector1Time();
		personalBestS1LapNum = aLap.getBestOverallSector1LapNum();
		personalBestS2Time = aLap.getBestOverallSector2Time();
		personalBestS2LapNum = aLap.getBestOverallSector2LapNum();
		personalBestS3Time = aLap.getBestOverallSector3Time();
		personalBestS3LapNum = aLap.getBestOverallSector3LapNum();
		
		deltaPersonalS1BestLap = aLap.getDeltaPersonalS1BestLap();
		deltaPersonalS2BestLap = aLap.getDeltaPersonalS2BestLap();
		deltaPersonalS1Best = aLap.getDeltaPersonalS1Best();
		deltaPersonalS2Best = aLap.getDeltaPersonalS2Best();
		
		pitStatus = aLap.getPitStatus();
		penalties = aLap.getPenalties();
		resultStatus = aLap.getResultStatus();
	}
	
	public RacePosition(RacePosition aPos, LapStruct aLap, ResultStatus resultStatus) {
		driverName = aPos.getDriverName();
		driverId = aPos.getDriverId();
		s1 = aPos.getS1();
		s2 = aPos.getS2();
		currentLapNum = aPos.getCurrentLapNum();
		curLapTime = aPos.getCurLapTime();

		curPosition = aLap.getCarPosition();
		deltaPosition = aLap.getDeltaPosition();
		lastLapTime = aLap.getLastLapTimeDuration();
		bestLapTime = aLap.getBestLapTimeDuration();
		deltaLastBestLap = aLap.getDeltaLastLapPersonalBestLapTime();
		bestLapNr = aLap.getBestLapNum();

		personalBestLapS1Time = aLap.getBestLapSector1Time();
		personalBestLapS2Time = aLap.getBestLapSector2Time();
		personalBestLapS3Time = aLap.getBestLapSector3Time();

		personalBestS1Time = aLap.getBestOverallSector1Time();
		personalBestS1LapNum = aLap.getBestOverallSector1LapNum();
		personalBestS2Time = aLap.getBestOverallSector2Time();
		personalBestS2LapNum = aLap.getBestOverallSector2LapNum();
		personalBestS3Time = aLap.getBestOverallSector3Time();
		personalBestS3LapNum = aLap.getBestOverallSector3LapNum();

		deltaPersonalS1BestLap = aLap.getDeltaPersonalS1BestLap(s1);
		deltaPersonalS2BestLap = aLap.getDeltaPersonalS2BestLap(s2);
		deltaPersonalS1Best = aLap.getDeltaPersonalS1Best(s1);
		deltaPersonalS2Best = aLap.getDeltaPersonalS2Best(s2);

		pitStatus = aLap.getPitStatus();
		penalties = aLap.getPenalties();

		this.resultStatus = resultStatus;
	}




	public int getPersonalBestS1LapNum() {
		return personalBestS1LapNum;
	}

	public int getPersonalBestS2LapNum() {
		return personalBestS2LapNum;
	}

	public int getPersonalBestS3LapNum() {
		return personalBestS3LapNum;
	}

	public Duration getPersonalBestLapS1Time() {
		return personalBestLapS1Time;
	}

	public Duration getPersonalBestLapS2Time() {
		return personalBestLapS2Time;
	}

	public Duration getPersonalBestLapS3Time() {
		return personalBestLapS3Time;
	}

	public Duration getPersonalBestS1Time() {
		return personalBestS1Time;
	}

	public Duration getPersonalBestS2Time() {
		return personalBestS2Time;
	}

	public Duration getPersonalBestS3Time() {
		return personalBestS3Time;
	}

	public Duration getDeltaPersonalS1BestLap() {
		return deltaPersonalS1BestLap;
	}

	public Duration getDeltaPersonalS2BestLap() {
		return deltaPersonalS2BestLap;
	}

	public Duration getDeltaPersonalS1Best() {
		return deltaPersonalS1Best;
	}

	public Duration getDeltaPersonalS2Best() {
		return deltaPersonalS2Best;
	}


	public int getCurPosition() {
		return curPosition;
	}

	public String getDriverName() {
		return driverName;
	}

	public Duration getS1() {
		return s1;
	}

	public static String formatDurationSecMillis(Duration d) {
		return String.format("%s%d : %03d ", d.isNegative() ? "-" : "",
				d.isNegative() ? d.toSecondsPart() + 1 : d.toSecondsPart(),
				d.isNegative() ? 1000 - d.toMillisPart() : d.toMillisPart());
	}

	public Duration getS2() {
		return s2;
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
		return formatOptionalSectorTime(getS1()) + " / "
				+ formatDurationSecMillis(getPersonalBestLapS1Time()) + " / "
				+ formatLapNumber(getPersonalBestS1LapNum()) + formatDurationSecMillis(getPersonalBestS1Time())
				+ " (" + formatOptionalDeltaMillis(getS1(), getDeltaPersonalS1Best()) + ") ";
	}

	public String getS2Description() {
		return formatOptionalSectorTime(getS2()) + " / "
				+ formatDurationSecMillis(getPersonalBestLapS2Time()) + " / "
				+ formatLapNumber(getPersonalBestS2LapNum()) + formatDurationSecMillis(getPersonalBestS2Time())
				+ " (" + formatOptionalDeltaMillis(getS2(), getDeltaPersonalS2Best()) + ") ";
	}

	public String getS3Description() {
		return formatDurationSecMillis(getPersonalBestLapS3Time()) + " / " + formatLapNumber(getPersonalBestS3LapNum())
				+ formatDurationSecMillis(getPersonalBestS3Time());
	}

	public String getLapDescription() {
		String res = String.format("L%d / ",getCurrentLapNum())+(getBestLapNr() == 0 ? " - "
				: durationToMinSecMillis(
						getLastLapTime())) + " / "
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
		result = prime * result + ((personalBestLapS1Time == null) ? 0 : personalBestLapS1Time.hashCode());
		result = prime * result + ((personalBestLapS2Time == null) ? 0 : personalBestLapS2Time.hashCode());
		result = prime * result + ((personalBestLapS3Time == null) ? 0 : personalBestLapS3Time.hashCode());
		result = prime * result + ((bestLapTime == null) ? 0 : bestLapTime.hashCode());
		result = prime * result + personalBestS1LapNum;
		result = prime * result + ((personalBestS1Time == null) ? 0 : personalBestS1Time.hashCode());
		result = prime * result + personalBestS2LapNum;
		result = prime * result + ((personalBestS2Time == null) ? 0 : personalBestS2Time.hashCode());
		result = prime * result + personalBestS3LapNum;
		result = prime * result + ((personalBestS3Time == null) ? 0 : personalBestS3Time.hashCode());
		result = prime * result + ((curLapTime == null) ? 0 : curLapTime.hashCode());
		result = prime * result + curPosition;
		result = prime * result + currentLapNum;
		result = prime * result + ((deltaLastBestLap == null) ? 0 : deltaLastBestLap.hashCode());
		result = prime * result + deltaPosition;
		result = prime * result + ((deltaPersonalS1Best == null) ? 0 : deltaPersonalS1Best.hashCode());
		result = prime * result + ((deltaPersonalS1BestLap == null) ? 0 : deltaPersonalS1BestLap.hashCode());
		result = prime * result + ((deltaPersonalS2Best == null) ? 0 : deltaPersonalS2Best.hashCode());
		result = prime * result + ((deltaPersonalS2BestLap == null) ? 0 : deltaPersonalS2BestLap.hashCode());
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
		if (personalBestLapS1Time == null) {
			if (other.personalBestLapS1Time != null)
				return false;
		} else if (!personalBestLapS1Time.equals(other.personalBestLapS1Time))
			return false;
		if (personalBestLapS2Time == null) {
			if (other.personalBestLapS2Time != null)
				return false;
		} else if (!personalBestLapS2Time.equals(other.personalBestLapS2Time))
			return false;
		if (personalBestLapS3Time == null) {
			if (other.personalBestLapS3Time != null)
				return false;
		} else if (!personalBestLapS3Time.equals(other.personalBestLapS3Time))
			return false;
		if (bestLapTime == null) {
			if (other.bestLapTime != null)
				return false;
		} else if (!bestLapTime.equals(other.bestLapTime))
			return false;
		if (personalBestS1LapNum != other.personalBestS1LapNum)
			return false;
		if (personalBestS1Time == null) {
			if (other.personalBestS1Time != null)
				return false;
		} else if (!personalBestS1Time.equals(other.personalBestS1Time))
			return false;
		if (personalBestS2LapNum != other.personalBestS2LapNum)
			return false;
		if (personalBestS2Time == null) {
			if (other.personalBestS2Time != null)
				return false;
		} else if (!personalBestS2Time.equals(other.personalBestS2Time))
			return false;
		if (personalBestS3LapNum != other.personalBestS3LapNum)
			return false;
		if (personalBestS3Time == null) {
			if (other.personalBestS3Time != null)
				return false;
		} else if (!personalBestS3Time.equals(other.personalBestS3Time))
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
		if (deltaPersonalS1Best == null) {
			if (other.deltaPersonalS1Best != null)
				return false;
		} else if (!deltaPersonalS1Best.equals(other.deltaPersonalS1Best))
			return false;
		if (deltaPersonalS1BestLap == null) {
			if (other.deltaPersonalS1BestLap != null)
				return false;
		} else if (!deltaPersonalS1BestLap.equals(other.deltaPersonalS1BestLap))
			return false;
		if (deltaPersonalS2Best == null) {
			if (other.deltaPersonalS2Best != null)
				return false;
		} else if (!deltaPersonalS2Best.equals(other.deltaPersonalS2Best))
			return false;
		if (deltaPersonalS2BestLap == null) {
			if (other.deltaPersonalS2BestLap != null)
				return false;
		} else if (!deltaPersonalS2BestLap.equals(other.deltaPersonalS2BestLap))
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
				+ ", bestOverallS1LapNum=" + personalBestS1LapNum + ", bestOverallS2LapNum=" + personalBestS2LapNum
				+ ", bestOverallS3LapNum=" + personalBestS3LapNum + ", penalties=" + penalties + ", lastLapTime="
				+ lastLapTime + ", bestLapTime=" + bestLapTime + ", deltaLastBestLap=" + deltaLastBestLap
				+ ", curLapTime=" + curLapTime + ", s1=" + s1 + ", s2=" + s2 + ", bestLapS1Time=" + personalBestLapS1Time
				+ ", bestLapS2Time=" + personalBestLapS2Time + ", bestLapS3Time=" + personalBestLapS3Time + ", bestOverallS1Time="
				+ personalBestS1Time + ", bestOverallS2Time=" + personalBestS2Time + ", bestOverallS3Time="
				+ personalBestS3Time + ", deltaS1BestLap=" + deltaPersonalS1BestLap + ", deltaS2BestLap=" + deltaPersonalS2BestLap
				+ ", deltaS1Best=" + deltaPersonalS1Best + ", deltaS2Best=" + deltaPersonalS2Best + ", pitStatus=" + pitStatus
				+ ", resultStatus=" + resultStatus + "]";
	}

	public RacePosition finishRace(LapStruct aLap) {
		return new RacePosition(this, aLap,ResultStatus.finished);
	}

}
