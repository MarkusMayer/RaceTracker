package raceTracker.model.enums;

public enum SurfaceType {
	tarmac(0), rumbleStrip(1), concrete(2), rock(3), gravel(4), mud(5), sand(6), grass(7), water(8), cobblestone(9),
	metal(10), ridged(11), unkown (12);

	private final int surfaceKey;

	private SurfaceType(int aSurfaceKey) {
		this.surfaceKey = aSurfaceKey;
	}

	public static SurfaceType getTypeByKey(int aKey) {
		for (SurfaceType aType : SurfaceType.values()) {
			if (aType.surfaceKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
