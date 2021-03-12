package f1Udp.model.enums;

public enum PitStatus {

	none(0), pitting(1), inPitArea(2);

	private final int pitStatusKey;

	private PitStatus(int aPitStatusKey) {
		this.pitStatusKey = aPitStatusKey;
	}

	public static PitStatus getTypeByKey(int aKey) {
		for (PitStatus aType : PitStatus.values()) {
			if (aType.pitStatusKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
