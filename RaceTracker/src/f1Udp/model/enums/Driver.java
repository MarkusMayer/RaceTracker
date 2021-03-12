package f1Udp.model.enums;

public enum Driver {
	carlosSainz(0),
	peterBelousov(37),
	rashidNair(70),
	daniilKvyat(1),
	klimekMichalski(38),
	jackTremblay(71),
	danielRicciardo(2),
	santiagoMoreno(39),
	antonioGiovinazzi(74),
	kimiRaeikkoenen(6),
	benjaminCoppens(40),
	robertKubica(70),
	lewisHamilton(7),
	noahVisser(41),
	nobuharuMatsushita(78),
	maxVerstappen(9),
	gertWaldmuller(42),
	nikitaMazepin(79),
	nicoHulkenburg(10),
	julianQuesada(43),
	guanyaZhou(80),
	kevinMagnussen(11),
	danielJones(44),
	mickSchumacher(81),
	romainGrosjean(12),
	artemMarkelov(45),
	callumIlott(82),
	sebastianVettel(13),
	tadasukeMakino(46),
	juanManuelCorrea(83),
	sergioPerez(14),
	seanGelael(47),
	jordanKing(84),
	valtteriBottas(15),
	nyckDeVries(48),
	mahaveerRaghunathan(85),
	estebanOcon(17),
	jackAitken(49),
	tatianaCalderon(86),
	lanceStroll(19),
	georgeRussell(50),
	anthoineHubert(87),
	arronBarnes(20),
	maximilianGuenther(51),
	guilianoAlesi(88),
	martinGiles(21),
	nireiFukuzumi(52),
	ralphBoschung(89),
	alexMurray(22),
	lucaGhiotto(53),
	lucasRoth(23),
	landoNorris(54),
	igorCorreia(24),
	sérgioSetteCâmara(55),
	sophieLevasseur(25),
	louisDelétraz(56),
	jonasSchiffer(26),
	antonioFuoco(57),
	alainForest(27),
	charlesLeclerc(58),
	jayLetourneau(28),
	pierreGasly(59),
	estoSaari(29),
	alexanderAlbon(62),
	yasarAtiyeh(30),
	nicholasLatifi(63),
	callistoCalabresi(31),
	dorianBoccolacci(64),
	naotaIzum(32),
	nikoKari(65),
	howardClarke(33),
	robertoMerhi(66),
	wilheimKaufmann(34),
	arjunMaini(67),
	marieLaursen(35),
	alessioLorandi(68),
	flavioNieves(36),
	rubenMeijer(69),
	unkown(100);

	private final int driverKey;

	private Driver(int aDriverKey) {
		this.driverKey = aDriverKey;
	}

	public static Driver getTypeByKey(int aKey) {
		for (Driver aType : Driver.values()) {
			if (aType.driverKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
