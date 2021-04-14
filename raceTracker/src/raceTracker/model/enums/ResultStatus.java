package raceTracker.model.enums;

public enum ResultStatus {
	invalid(0,"-"), inactive(1,"-"), active(2,""), finished(3,"FIN"), disqualified(4,"DIS"), notClassified(5,"NC"),retired(6,"DNF"),unkownMaybeInvalidOrRetiredDriver(7,"UNKOWN");

	private final int resultStatusKey;
	private final String resultStatusShort;

	private ResultStatus(int aResultStatusKey,String aResultStatusShort) {
		this.resultStatusKey = aResultStatusKey;
		this.resultStatusShort=aResultStatusShort;
	}

	public static ResultStatus getTypeByKey(int aKey) {
		for (ResultStatus aType : ResultStatus.values()) {
			if (aType.resultStatusKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
	
	public String getShort() {
		return resultStatusShort;
	}
}
