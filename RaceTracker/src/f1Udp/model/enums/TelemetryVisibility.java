package f1Udp.model.enums;

public enum TelemetryVisibility {
	restricted(0), publicVisibility(1);

	private final int telemetryVisibilityKey;

	private TelemetryVisibility(int aTelemetryVisibilityKey) {
		this.telemetryVisibilityKey = aTelemetryVisibilityKey;
	}

	public static TelemetryVisibility getTypeByKey(int aKey) {
		for (TelemetryVisibility aType : TelemetryVisibility.values()) {
			if (aType.telemetryVisibilityKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
