package raceTracker.model.viewModel;

import java.util.concurrent.TimeUnit;

import raceTracker.model.enums.PitStatus;
import raceTracker.model.gameStructs.Lap;
import raceTracker.model.gameStructs.Participant;

public final class RacePosition {
	
	private final int curPosition;
	private final String driverName;
	private final int sector1TimeInMS, sector2TimeInMS,currentLapNum,deltaPosition,bestLapNr,bestLapSector1TimeInMS,bestLapSector2TimeInMS,bestLapSector3TimeInMS,bestOverallSector1TimeInMS,bestOverallSector2TimeInMS,bestOverallSector3TimeInMS,deltaSector1PersonalMS,deltaSector2PersonalMS,deltaSector1OverallMS,deltaSector2OverallMS,deltaSector1PersonalBestOverallMS,deltaSector2PersonalBestOverallMS,deltaSector3PersonalBestOverallMS,penalties;
	private final float lastLapTime,bestLapTime,deltaLastBestLap,curLapTime;
	private final PitStatus pitStatus;
	
	
	public RacePosition(Lap aLap, Participant aParticipant) {
		curPosition=aLap.getCarPosition();
		driverName=aParticipant.getDriverName();
		sector1TimeInMS=aLap.getSector1TimeMS();
		sector2TimeInMS=aLap.getSector2TimeMS();
		currentLapNum=aLap.getCurrentLapNum();
		deltaPosition=aLap.getGridPosition()-aLap.getCarPosition();
		lastLapTime=aLap.getLastLapTime();
		bestLapTime=aLap.getBestLapTime();
		deltaLastBestLap=lastLapTime-bestLapTime;
		
		bestLapNr=aLap.getBestLapNum();
		curLapTime=aLap.getCurrentLapTime();
		bestLapSector1TimeInMS=aLap.getBestLapSector1TimeInMS();
		bestLapSector2TimeInMS=aLap.getBestLapSector2TimeInMS();
		bestLapSector3TimeInMS=aLap.getBestLapSector3TimeInMS();
		bestOverallSector1TimeInMS=aLap.getBestOverallSector1TimeInMS();
		bestOverallSector2TimeInMS=aLap.getBestOverallSector2TimeInMS();
		bestOverallSector3TimeInMS=aLap.getBestOverallSector3TimeInMS();
		deltaSector1PersonalMS=sector1TimeInMS-bestLapSector1TimeInMS;
		deltaSector2PersonalMS=sector2TimeInMS-bestLapSector2TimeInMS;
		deltaSector1OverallMS=sector1TimeInMS-bestOverallSector1TimeInMS;
		deltaSector2OverallMS=sector2TimeInMS-bestOverallSector2TimeInMS;
		deltaSector1PersonalBestOverallMS=bestLapSector1TimeInMS-bestOverallSector1TimeInMS;
		deltaSector2PersonalBestOverallMS=bestLapSector2TimeInMS-bestOverallSector2TimeInMS;
		deltaSector3PersonalBestOverallMS=bestLapSector3TimeInMS-bestOverallSector3TimeInMS;
		pitStatus=aLap.getPitStatus();
		penalties=aLap.getPenalties();
	}

	public int getCurPosition() {
		return curPosition;
	}

	public String getDriverName() {
		return driverName;
	}

	public int getSector1TimeInMS() {
		return sector1TimeInMS;
	}
	
	public String getSector1() {
		
		return String.format("%d : %d ", 
			    TimeUnit.MILLISECONDS.toSeconds(sector1TimeInMS), 
			    sector1TimeInMS-TimeUnit.MILLISECONDS.toSeconds(sector1TimeInMS)*1000);
	}

	public int getSector2TimeInMS() {
		return sector2TimeInMS;
	}

	public String getSector2() {
		return String.format("%d : %d ", 
			    TimeUnit.MILLISECONDS.toSeconds(sector2TimeInMS), 
			    sector2TimeInMS-TimeUnit.MILLISECONDS.toSeconds(sector2TimeInMS)*1000);
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

	public int getBestLapSector1TimeInMS() {
		return bestLapSector1TimeInMS;
	}

	public int getBestLapSector2TimeInMS() {
		return bestLapSector2TimeInMS;
	}

	public int getBestLapSector3TimeInMS() {
		return bestLapSector3TimeInMS;
	}

	public int getBestOverallSector1TimeInMS() {
		return bestOverallSector1TimeInMS;
	}

	public int getBestOverallSector2TimeInMS() {
		return bestOverallSector2TimeInMS;
	}

	public int getBestOverallSector3TimeInMS() {
		return bestOverallSector3TimeInMS;
	}

	public int getDeltaSector1PersonalMS() {
		return deltaSector1PersonalMS;
	}

	public int getDeltaSector2PersonalMS() {
		return deltaSector2PersonalMS;
	}

	public int getDeltaSector1OverallMS() {
		return deltaSector1OverallMS;
	}

	public int getDeltaSector2OverallMS() {
		return deltaSector2OverallMS;
	}

	public int getDeltaSector1PersonalBestOverallMS() {
		return deltaSector1PersonalBestOverallMS;
	}

	public int getDeltaSector2PersonalBestOverallMS() {
		return deltaSector2PersonalBestOverallMS;
	}

	public int getDeltaSector3PersonalBestOverallMS() {
		return deltaSector3PersonalBestOverallMS;
	}

	public int getPenalties() {
		return penalties;
	}

	public float getLastLapTime() {
		return lastLapTime;
	}

	public float getBestLapTime() {
		return bestLapTime;
	}

	public float getCurLapTime() {
		return curLapTime;
	}

	public PitStatus getPitStatus() {
		return pitStatus;
	}

	public float getDeltaLastBestLap() {
		return deltaLastBestLap;
	}

	@Override
	public String toString() {
		return "RacePosition [curPosition=" + curPosition + ", driverName=" + driverName + ", sector1TimeInMS="
				+ sector1TimeInMS + ", sector2TimeInMS=" + sector2TimeInMS + ", currentLapNum=" + currentLapNum
				+ ", deltaPosition=" + deltaPosition + ", bestLapNr=" + bestLapNr + ", bestLapSector1TimeInMS="
				+ bestLapSector1TimeInMS + ", bestLapSector2TimeInMS=" + bestLapSector2TimeInMS
				+ ", bestLapSector3TimeInMS=" + bestLapSector3TimeInMS + ", bestOverallSector1TimeInMS="
				+ bestOverallSector1TimeInMS + ", bestOverallSector2TimeInMS=" + bestOverallSector2TimeInMS
				+ ", bestOverallSector3TimeInMS=" + bestOverallSector3TimeInMS + ", deltaSector1PersonalMS="
				+ deltaSector1PersonalMS + ", deltaSector2PersonalMS=" + deltaSector2PersonalMS
				+ ", deltaSector1OverallMS=" + deltaSector1OverallMS + ", deltaSector2OverallMS="
				+ deltaSector2OverallMS + ", deltaSector1PersonalBestOverallMS=" + deltaSector1PersonalBestOverallMS
				+ ", deltaSector2PersonalBestOverallMS=" + deltaSector2PersonalBestOverallMS
				+ ", deltaSector3PersonalBestOverallMS=" + deltaSector3PersonalBestOverallMS + ", penalties="
				+ penalties + ", lastLapTime=" + lastLapTime + ", bestLapTime=" + bestLapTime + ", deltaLastBestLap="
				+ deltaLastBestLap + ", curLapTime=" + curLapTime + ", pitStatus=" + pitStatus + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bestLapNr;
		result = prime * result + bestLapSector1TimeInMS;
		result = prime * result + bestLapSector2TimeInMS;
		result = prime * result + bestLapSector3TimeInMS;
		result = prime * result + Float.floatToIntBits(bestLapTime);
		result = prime * result + bestOverallSector1TimeInMS;
		result = prime * result + bestOverallSector2TimeInMS;
		result = prime * result + bestOverallSector3TimeInMS;
		result = prime * result + Float.floatToIntBits(curLapTime);
		result = prime * result + curPosition;
		result = prime * result + currentLapNum;
		result = prime * result + Float.floatToIntBits(deltaLastBestLap);
		result = prime * result + deltaPosition;
		result = prime * result + deltaSector1OverallMS;
		result = prime * result + deltaSector1PersonalBestOverallMS;
		result = prime * result + deltaSector1PersonalMS;
		result = prime * result + deltaSector2OverallMS;
		result = prime * result + deltaSector2PersonalBestOverallMS;
		result = prime * result + deltaSector2PersonalMS;
		result = prime * result + deltaSector3PersonalBestOverallMS;
		result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
		result = prime * result + Float.floatToIntBits(lastLapTime);
		result = prime * result + penalties;
		result = prime * result + ((pitStatus == null) ? 0 : pitStatus.hashCode());
		result = prime * result + sector1TimeInMS;
		result = prime * result + sector2TimeInMS;
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
		if (bestLapSector1TimeInMS != other.bestLapSector1TimeInMS)
			return false;
		if (bestLapSector2TimeInMS != other.bestLapSector2TimeInMS)
			return false;
		if (bestLapSector3TimeInMS != other.bestLapSector3TimeInMS)
			return false;
		if (Float.floatToIntBits(bestLapTime) != Float.floatToIntBits(other.bestLapTime))
			return false;
		if (bestOverallSector1TimeInMS != other.bestOverallSector1TimeInMS)
			return false;
		if (bestOverallSector2TimeInMS != other.bestOverallSector2TimeInMS)
			return false;
		if (bestOverallSector3TimeInMS != other.bestOverallSector3TimeInMS)
			return false;
		if (Float.floatToIntBits(curLapTime) != Float.floatToIntBits(other.curLapTime))
			return false;
		if (curPosition != other.curPosition)
			return false;
		if (currentLapNum != other.currentLapNum)
			return false;
		if (Float.floatToIntBits(deltaLastBestLap) != Float.floatToIntBits(other.deltaLastBestLap))
			return false;
		if (deltaPosition != other.deltaPosition)
			return false;
		if (deltaSector1OverallMS != other.deltaSector1OverallMS)
			return false;
		if (deltaSector1PersonalBestOverallMS != other.deltaSector1PersonalBestOverallMS)
			return false;
		if (deltaSector1PersonalMS != other.deltaSector1PersonalMS)
			return false;
		if (deltaSector2OverallMS != other.deltaSector2OverallMS)
			return false;
		if (deltaSector2PersonalBestOverallMS != other.deltaSector2PersonalBestOverallMS)
			return false;
		if (deltaSector2PersonalMS != other.deltaSector2PersonalMS)
			return false;
		if (deltaSector3PersonalBestOverallMS != other.deltaSector3PersonalBestOverallMS)
			return false;
		if (driverName == null) {
			if (other.driverName != null)
				return false;
		} else if (!driverName.equals(other.driverName))
			return false;
		if (Float.floatToIntBits(lastLapTime) != Float.floatToIntBits(other.lastLapTime))
			return false;
		if (penalties != other.penalties)
			return false;
		if (pitStatus != other.pitStatus)
			return false;
		if (sector1TimeInMS != other.sector1TimeInMS)
			return false;
		if (sector2TimeInMS != other.sector2TimeInMS)
			return false;
		return true;
	}
	
}
