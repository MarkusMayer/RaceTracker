package raceTracker.model.enums;

public enum PitStatus {

	none(0," - "), pitting(1,"Pit Lane"), inPitArea(2,"Pitting");

	private final int pitStatusKey;
	private final String description;

	private PitStatus(int aPitStatusKey,String aDescription) {
		this.pitStatusKey = aPitStatusKey;
		this.description = aDescription;
	}

	public static PitStatus getTypeByKey(int aKey) {
		for (PitStatus aType : PitStatus.values()) {
			if (aType.pitStatusKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
	
	public String getDescription() {
		return description;
	}
}
