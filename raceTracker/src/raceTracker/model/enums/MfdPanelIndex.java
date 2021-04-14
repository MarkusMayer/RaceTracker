package raceTracker.model.enums;

public enum MfdPanelIndex {
	mfdClosed(255), carSetup(0), pits(1), damage(2), engine(3), temperatures(4);

	private final int mfdPanelKey;

	private MfdPanelIndex(int aMfdPanelKey) {
		this.mfdPanelKey = aMfdPanelKey;
	}

	public static MfdPanelIndex getTypeByKey(int aKey) {
		for (MfdPanelIndex aType : MfdPanelIndex.values()) {
			if (aType.mfdPanelKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
