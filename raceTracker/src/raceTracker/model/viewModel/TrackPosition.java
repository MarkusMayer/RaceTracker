package raceTracker.model.viewModel;

import raceTracker.model.enums.Driver;

public class TrackPosition {

	private final int idx,racePos; 
	private final Driver driverId;
	private final float posX, posY,posZ;

	public TrackPosition(int idx,int racePos,Driver driverId, float posX, float posY, float posZ) {
		super();
		this.idx=idx;
		this.racePos=racePos;
		this.driverId = driverId;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public Driver getDriverId() {
		return driverId;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getPosZ() {
		return posZ;
	}

	public int getIdx() {
		return idx;
	}

	public int getRacePos() {
		return racePos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result + idx;
		result = prime * result + Float.floatToIntBits(posX);
		result = prime * result + Float.floatToIntBits(posY);
		result = prime * result + Float.floatToIntBits(posZ);
		result = prime * result + racePos;
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
		TrackPosition other = (TrackPosition) obj;
		if (driverId != other.driverId)
			return false;
		if (idx != other.idx)
			return false;
		if (Float.floatToIntBits(posX) != Float.floatToIntBits(other.posX))
			return false;
		if (Float.floatToIntBits(posY) != Float.floatToIntBits(other.posY))
			return false;
		if (Float.floatToIntBits(posZ) != Float.floatToIntBits(other.posZ))
			return false;
		if (racePos != other.racePos)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrackPosition [idx=" + idx + ", racePos=" + racePos + ", driverId=" + driverId + ", posX=" + posX
				+ ", posY=" + posY + ", posZ=" + posZ + "]";
	}
	
	
}
