package f1Udp.model.enums;

public enum PacketType {

	motion(0), session(1), lapData(2), event(3), participants(4), carSetups(5), carTelemetry(6), carStatus(7),
	finalClassification(8), lobbyInfo(9);

	private final int packetKey;

	private PacketType(int packetKey) {
		this.packetKey = packetKey;
	}

	public static PacketType getTypeByKey(int aKey) {
		for (PacketType aType : PacketType.values()) {
			if (aType.packetKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for "+aKey);
	}
}
