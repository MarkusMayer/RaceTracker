package raceTracker.model.enums;

public enum ResultStatus {
	invalid(0), inactive(1), active(2), finished(3), disqualified(4), notClassified(5),retired(6),unkownMaybeInvalidOrRetiredDriver(7);

	private final int resultStatusKey;

	private ResultStatus(int aResultStatusKey) {
		this.resultStatusKey = aResultStatusKey;
	}

	public static ResultStatus getTypeByKey(int aKey) {
		for (ResultStatus aType : ResultStatus.values()) {
			if (aType.resultStatusKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
