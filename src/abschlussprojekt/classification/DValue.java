package abschlussprojekt.classification;

public enum DValue {
	TWO(2), FOUR(4), EIGHT(8);
	
	private int value;
	
	private DValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return Integer.valueOf(value).toString();
	}

}
