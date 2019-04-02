import java.util.List;

public class FeatureMembershipFunction {
	int D;
	double pce;
	double C;
	double S;
	
	public FeatureMembershipFunction(DValue D, double pce, List<Integer> features) {
		if(pce < 0.0 || pce > 1.0) {
			throw new IllegalArgumentException("pce must be in the interval [0,1]");
		}
		this.pce = pce;
		this.D = D.getValue();
	}
	
	public void learn(List<Integer> features) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		double m;
		
		for(Integer feature : features) {
			if(feature < min) {
				min = feature;
			}
			if(feature > max) {
				max = feature;
			}
		}
		m = (max - min)/2.0;
		this.S = m + (double)min;
		
		C = (1.0 + (2.0 * pce)) * m;
	}
	
	public double distance(int feature) {
		return Math.pow((Math.abs(feature - S) / C), D);
	}
}
