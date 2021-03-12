package f1Udp.model.enums;

public enum Track {
	 melbourne(0),
	 paulRicard(1),
	 shanghai(2),
	 sakhirBahrain(3),
	 barcelona(4),
	 monaco(5),
	 montreal(6),
	 silverstone(7),
	 hockenheim(8),
	 hungaroring(9),
	 spa(10),
	 monza(11),
	 singapore(12),
	 suzuka(13),
	 abuDhabi(14),
	 texas(15),
	 interlagos(16),
	 spielberg(17),
	 sochi(18),
	 mexicoCity(19),
	 baku(20),
	 sakhirShort(21),
	 silverstoneShort(22),
	 texasShort(23),
	 suzukaShort(24),
	 hanoi(25),
	 zandvoort(26); 
	
	
	
	private final int trackKey;

	private Track(int aTrackKey) {
		this.trackKey = aTrackKey;
	}

	public static Track getTypeByKey(int aKey) {
		for (Track aType : Track.values()) {
			if (aType.trackKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
