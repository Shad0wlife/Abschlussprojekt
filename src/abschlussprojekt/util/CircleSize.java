package abschlussprojekt.util;

public enum CircleSize {
	D6_16(6, 16),
	D9_32(9, 32),
	D10_32(10, 32),
	D11_32(11, 32),
	D12_32(12, 32),
	D21_64(21, 64),
	D22_64(22, 64),
	D43_128(43, 128),
	D44_128(44, 128),
	D45_128(45, 128),
	D46_128(46, 128);
	
	private final int diameter;
	private final int circumference;
	
	private CircleSize(int diameter, int circumference) {
		this.diameter = diameter;
		this.circumference = circumference;
	}

	public int getDiameter() {
		return this.diameter;
	}
	
	public int getCircumference() {
		return this.circumference;
	}
	
	/**
	 * Returns the G-Spectrum size resulting from the selected size's circumference
	 * @return
	 */
	public int getSpectrumSize() {
		return Util.log2(this.getCircumference()) + 1;
	}
	
	@Override
	public String toString() {
		return "Durchmesser: " + this.getDiameter() + ", Umfang: " + this.getCircumference();
	}
}
