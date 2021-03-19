package raceTracker.model.enums;

public enum Flag {
	invalidUnknown(-1), none(0), green(1), blue(2), yellow(3), red(4);

	private final int flagKey;

	private Flag(int aFlagKey) {
		this.flagKey = aFlagKey;
	}

	public static Flag getTypeByKey(int aKey) {
		for (Flag aType : Flag.values()) {
			if (aType.flagKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
