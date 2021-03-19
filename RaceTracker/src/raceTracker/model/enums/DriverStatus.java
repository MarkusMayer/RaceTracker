package raceTracker.model.enums;

public enum DriverStatus {
	 inGarage(0),flyingLap(1),inLap(2), outLap(3), onTrack(4);

	private final int driverStatusKey;

	private DriverStatus(int aDriverStatusKey) {
		this.driverStatusKey = aDriverStatusKey;
	}

	public static DriverStatus getTypeByKey(int aKey) {
		for (DriverStatus aType : DriverStatus.values()) {
			if (aType.driverStatusKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
