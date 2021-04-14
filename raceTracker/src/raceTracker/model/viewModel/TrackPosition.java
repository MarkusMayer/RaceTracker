package raceTracker.model.viewModel;

import raceTracker.model.enums.Driver;

public class TrackPosition {

	private final Driver driverId;
	private final float posX, posY;

	public TrackPosition(Driver driverId, float posX, float posY) {
		super();
		this.driverId = driverId;
		this.posX = posX;
		this.posY = posY;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result + Float.floatToIntBits(posX);
		result = prime * result + Float.floatToIntBits(posY);
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
		if (Float.floatToIntBits(posX) != Float.floatToIntBits(other.posX))
			return false;
		if (Float.floatToIntBits(posY) != Float.floatToIntBits(other.posY))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrackPosition [driverId=" + driverId + ", posX=" + posX + ", posY=" + posY + "]";
	}

}
