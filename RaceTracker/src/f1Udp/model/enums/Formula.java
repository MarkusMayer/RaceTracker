package f1Udp.model.enums;

public enum Formula {
	f1Modern(0), f1Classic(1), f2(2),f1Generic(3);
	
	private final int formulaKey;

	private Formula(int aFormulaKey) {
		this.formulaKey = aFormulaKey;
	}

	public static Formula getTypeByKey(int aKey) {
		for (Formula aType : Formula.values()) {
			if (aType.formulaKey == aKey)
				return aType;
		}
		throw new IllegalArgumentException("No type entry found for " + aKey);
	}
}
