package f1Udp.model.enums;

public enum CarController {
	human(0), ai(1);

	private final int carControllerKey;

	private CarController(int aCarControllerKey) {
		this.carControllerKey = aCarControllerKey;
	}

	public static CarController getTypeByKey(int aKey) {
		for (CarController aType : CarController.values()) {
			if (aType.carControllerKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
