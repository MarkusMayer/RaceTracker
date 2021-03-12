package f1Udp.model.viewModel;

import f1Udp.model.gameStructs.Lap;
import f1Udp.model.gameStructs.Participant;

public final class RacePosition {
	
	private final int curPosition;
	private final String driverName;
	private final int sector1TimeInMS, sector2TimeInMS,currentLapNum;
	
	public RacePosition(Lap aLap, Participant aParticipant) {
		curPosition=aLap.getCarPosition();
		driverName=aParticipant.getDriverName();
		sector1TimeInMS=aLap.getSector1TimeMS();
		sector2TimeInMS=aLap.getSector2TimeMS();
		currentLapNum=aLap.getCurrentLapNum();
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

	public int getSector2TimeInMS() {
		return sector2TimeInMS;
	}

	public int getCurrentLapNum() {
		return currentLapNum;
	}

	@Override
	public String toString() {
		return "P"+curPosition + " - " + driverName + " S1: "
				+ sector1TimeInMS + " S2: " + sector2TimeInMS + " Lap " + currentLapNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + curPosition;
		result = prime * result + currentLapNum;
		result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
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
		if (curPosition != other.curPosition)
			return false;
		if (currentLapNum != other.currentLapNum)
			return false;
		if (driverName == null) {
			if (other.driverName != null)
				return false;
		} else if (!driverName.equals(other.driverName))
			return false;
		if (sector1TimeInMS != other.sector1TimeInMS)
			return false;
		if (sector2TimeInMS != other.sector2TimeInMS)
			return false;
		return true;
	}
	
	
	
}
