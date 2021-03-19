package raceTracker.model.enums;

public enum Team {
	mercedes(0), redBull2010(21), artGP2019(42), ferrari(1), ferrari1976(22), campos2019(43), redBullRacing(2),
	artGrandPrix(23), carlin2019(44), williams(3), camposVexatecRacing(24), sauberJuniorCharouz2019(45), racingPoint(4),
	carlin(25), dams2019(46), renault(5), charouzRacingSystem(26), uniVirtuosi2019(47), alphaTauri(6), dams(27),
	mpMotorsport2019(48), haas(7), russianTime(28), prema2019(49), mcLaren(8), mpMotorsport(29), trident2019(50),
	alfaRomeo(9), pertamina(30), arden2019(51), mcLaren1988(10), mcLaren1990(31), benetton1994(53), mcLaren1991(11),
	trident(32), benetton1995(54), williams1992(12), bwtArden(33), ferrari2000(55), ferrari1995(13), mcLaren1976(34),
	jordan1991(56), williams1996(14), lotus1972(35), mcLaren1998(15), ferrari1979(36), ferrari2002(16), mcLaren1982(37),
	ferrari2004(17), williams2003(38), renault2006(18), brawn2009(39), ferrari2007(19), lotus1978(40), mcLaren2008(20),
	f1GenericCar(41), myTeam(255);

	private final int teamKey;

	private Team(int aTeamKey) {
		this.teamKey = aTeamKey;
	}

	public static Team getTypeByKey(int aKey) {
		for (Team aType : Team.values()) {
			if (aType.teamKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
