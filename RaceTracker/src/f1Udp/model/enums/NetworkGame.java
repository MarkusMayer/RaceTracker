package f1Udp.model.enums;

public enum NetworkGame {
	
	offline(0), online(1);
	
	private final int networkGameKey;

	private NetworkGame(int aNetworkGameKey) {
		this.networkGameKey = aNetworkGameKey;
	}

	public static NetworkGame getTypeByKey(int aKey) {
		for (NetworkGame aType : NetworkGame.values()) {
			if (aType.networkGameKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
