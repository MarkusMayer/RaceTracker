package raceTrackerGUI;

import javafx.scene.paint.Color;
import raceTracker.model.enums.Driver;

public class TrackPosTableEntry {
	private final Color col;
	private final int curPos;
	private final Driver driverId;
	private final String driverName;

	public TrackPosTableEntry(Color col, int curPos, Driver driverId, String driverName) {
		super();
		this.col = col;
		this.curPos=curPos;
		this.driverId = driverId;
		this.driverName = driverName;
	}

	public Color getCol() {
		return col;
	}

	public int getCurPos() {
		return curPos;
	}

	public Driver getDriverId() {
		return driverId;
	}

	public String getDriverName() {
		return driverId.getShortName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result + curPos;
		result = prime * result + ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
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
		TrackPosTableEntry other = (TrackPosTableEntry) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		if (curPos != other.curPos)
			return false;
		if (driverId != other.driverId)
			return false;
		if (driverName == null) {
			if (other.driverName != null)
				return false;
		} else if (!driverName.equals(other.driverName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrackPosTableEntry [col=" + col + ", curPos=" + curPos + ", driverId=" + driverId + ", driverName="
				+ driverName + "]";
	}

}
