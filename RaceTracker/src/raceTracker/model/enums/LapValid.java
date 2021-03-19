package raceTracker.model.enums;

public enum LapValid {
	valid(0), invalid(1);

	private final int lapValidKey;

	private LapValid(int aLapValidKey) {
		this.lapValidKey = aLapValidKey;
	}

	public static LapValid getTypeByKey(int aKey) {
		for (LapValid aType : LapValid.values()) {
			if (aType.lapValidKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
