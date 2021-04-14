package raceTracker.model.enums;

public enum SafetyCarStatus {
	noSafetyCar(0), fullSafetyCar(1),virtualSafetyCar(2);

	private final int safetyCarStatusKey;

	private SafetyCarStatus(int aSafetyCarStatusKey) {
		this.safetyCarStatusKey = aSafetyCarStatusKey;
	}

	public static SafetyCarStatus getTypeByKey(int aKey) {
		for (SafetyCarStatus aType : SafetyCarStatus.values()) {
			if (aType.safetyCarStatusKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
