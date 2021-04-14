package raceTracker.model.enums;

public enum Sector {
	sector1(0), sector2(1), sector3(2);

	private final int sectorKey;

	private Sector(int aSectorKey) {
		this.sectorKey = aSectorKey;
	}

	public static Sector getTypeByKey(int aKey) {
		for (Sector aType : Sector.values()) {
			if (aType.sectorKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
