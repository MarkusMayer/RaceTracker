package raceTracker.model.gameStructs;

public final class WheelDataFloat {
	
	private final float rearLeft,rearRight,frontLeft,frontRight;

	public WheelDataFloat(float rearLeft, float rearRight, float frontLeft, float frontRight) {
		super();
		this.rearLeft = rearLeft;
		this.rearRight = rearRight;
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
	}

	public float getRearLeft() {
		return rearLeft;
	}

	public float getRearRight() {
		return rearRight;
	}

	public float getFrontLeft() {
		return frontLeft;
	}

	public float getFrontRight() {
		return frontRight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(frontLeft);
		result = prime * result + Float.floatToIntBits(frontRight);
		result = prime * result + Float.floatToIntBits(rearLeft);
		result = prime * result + Float.floatToIntBits(rearRight);
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
		WheelDataFloat other = (WheelDataFloat) obj;
		if (Float.floatToIntBits(frontLeft) != Float.floatToIntBits(other.frontLeft))
			return false;
		if (Float.floatToIntBits(frontRight) != Float.floatToIntBits(other.frontRight))
			return false;
		if (Float.floatToIntBits(rearLeft) != Float.floatToIntBits(other.rearLeft))
			return false;
		if (Float.floatToIntBits(rearRight) != Float.floatToIntBits(other.rearRight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WheelDataFloat [rearLeft=" + rearLeft + ", rearRight=" + rearRight + ", frontLeft=" + frontLeft
				+ ", frontRight=" + frontRight + "]";
	}
	
	

}
