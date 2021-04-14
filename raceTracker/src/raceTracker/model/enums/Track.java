package raceTracker.model.enums;

public enum Track {
	 melbourne(0,"Melbourne Albert Park"),
	 paulRicard(1,"Circuit Paul Ricard"),
	 shanghai(2,"Shanghai International Circuit"),
	 sakhirBahrain(3, "Bahrain International Circuit"),
	 barcelona(4,"Circuit de Barcelona-Catalunya"),
	 monaco(5, "Circuit de Monaco"),
	 montreal(6, "Circuit Gilles-Villeneuve"),
	 silverstone(7, "Silverstone Circuit"),
	 hockenheim(8, "Hockenheimring"),
	 hungaroring(9, "Hungaroring"),
	 spa(10, "Circuit de Spa-Francorchamps"),
	 monza(11, "Autodromo Nazionale Monza"),
	 singapore(12,"Marina Bay Street Circuit"),
	 suzuka(13,"Suzuka International Racing Course"),
	 abuDhabi(14,"Yas Marina Circuit"),
	 texas(15,"Circuit of The Americas"),
	 interlagos(16,"Autódromo José Carlos Pace"),
	 spielberg(17,"Red Bull Ring"),
	 sochi(18,"Sochi Autodrom"),
	 mexicoCity(19,"Autódromo Hermanos Rodríguez"),
	 baku(20,"Baku City Circuit"),
	 sakhirShort(21,"Bahrain International Circuit - Short"),
	 silverstoneShort(22,"Silverstone Circuit - Short"),
	 texasShort(23,"Circuit of The Americas - Short"),
	 suzukaShort(24,"Suzuka International Racing Course - Short"),
	 hanoi(25,"Hanoi Street Circuit"),
	 zandvoort(26,"Circuit Park Zandvoort"); 
	
	
	
	private final int trackKey;
	private final String trackName;

	private Track(int aTrackKey,String aTrackName) {
		this.trackKey = aTrackKey;
		this.trackName=aTrackName;
	}

	public static Track getTypeByKey(int aKey) {
		for (Track aType : Track.values()) {
			if (aType.trackKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
	
	public String getTrackName() {
		return trackName;
	}
}
