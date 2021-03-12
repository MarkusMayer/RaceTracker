package f1Udp.model.enums;

public enum SessionType {

	unknown(0), practice1(1), practice2(2), practice3(3), shortPractive(4), quali1(5), quali2(6), quali3(7),
	shortQuali(8), osq(9), race(10), race2(11), timeTrial(12);

	private final int sessionTypeKey;

	private SessionType(int aSessionTypeKey) {
		this.sessionTypeKey = aSessionTypeKey;
	}

	public static SessionType getTypeByKey(int aKey) {
		for (SessionType aType : SessionType.values()) {
			if (aType.sessionTypeKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
