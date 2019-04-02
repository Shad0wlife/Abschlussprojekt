import java.util.LinkedList;
import java.util.List;

public class MembershipFunction {
	DValue D;
	double pce;
	double C;
	FeatureMembershipFunction[] fmfs;
	final int vectorSize;
	
	public MembershipFunction(DValue D, double pce, List<int[]> featureVectors) {
		if(pce < 0.0 || pce > 1.0) {
			throw new IllegalArgumentException("pce must be in the interval [0,1]");
		}
		this.pce = pce;
		this.D = D;
		vectorSize = featureVectors.get(0).length;
		this.learn(featureVectors);
	}
	
	private void learn(List<int[]> featureVectors) {
		fmfs = new FeatureMembershipFunction[vectorSize];
		
		@SuppressWarnings("unchecked")
		List<Integer>[] featureLists = new LinkedList[vectorSize];
		for (int cnt = 0; cnt < featureLists.length; cnt++) {
			featureLists[cnt] = new LinkedList<Integer>();
		}
		
		featureVectors.forEach(arr -> {
			for(int cnt = 0; cnt < arr.length; cnt++) {
				featureLists[cnt].add(arr[cnt]);
			}
		});
		
		for (int cnt = 0; cnt < fmfs.length; cnt++) {
			fmfs[cnt] = new FeatureMembershipFunction(D, pce, featureLists[cnt]);
		}
	}
	
	public double membership(int[] featureVector) {
		if(featureVector.length != vectorSize) {
			throw new IllegalArgumentException("The feature vector does not match the learning data vectors in size.");
		}
		double[] distances = new double[vectorSize];
		for(int cnt = 0; cnt < vectorSize; cnt++) {
			distances[cnt] =  fmfs[cnt].distance(featureVector[cnt]);
		}
		return Math.pow(2.0, (-1.0/vectorSize) * Util.arraySumme(distances));
	}
	
}
