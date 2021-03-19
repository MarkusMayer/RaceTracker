package raceTracker.model.enums;

public enum Weather {
	clear(0), lightCloud(1), overcast(2), lightRain(3), heavyRain(4), storm(5);

	private final int weatherKey;

	private Weather(int aWeatherKey) {
		this.weatherKey = aWeatherKey;
	}

	public static Weather getTypeByKey(int aKey) {
		for (Weather aType : Weather.values()) {
			if (aType.weatherKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
