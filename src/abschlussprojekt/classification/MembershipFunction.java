package abschlussprojekt.classification;

import java.util.LinkedList;
import java.util.List;

public abstract class MembershipFunction {
	
	protected final DValue D;
	protected final double pce;
	protected double C;
	protected final int vectorSize;
	protected FeatureMembershipFunction[] fmfs;
	
	public MembershipFunction(DValue D, double pce, List<int[]> featureVectors) {
		if(pce < 0.0 || pce > 1.0) {
			throw new IllegalArgumentException("pce must be in the interval [0,1]");
		}
		this.pce = pce;
		this.D = D;
		vectorSize = featureVectors.get(0).length;
		this.learn(featureVectors);
	}
	
	/**
	 * Learns the Membership functions for each feature from a list of feature vectors
	 * @param featureVectors The feature vectors of the learning data
	 */
	private void learn(List<int[]> featureVectors) {
		fmfs = new FeatureMembershipFunction[vectorSize];
		
		List<Integer>[] featureLists = this.remapFeatureVectorsToLists(featureVectors);
		
		for (int cnt = 0; cnt < fmfs.length; cnt++) {
			fmfs[cnt] = new FeatureMembershipFunction(D, pce, featureLists[cnt]);
		}
	}
	
	/**
	 * Remaps a List of Vectors to a Vector of Lists, so a list of all values for each feature can easily be accessed
	 * @param featureVectors The list of featureVectors to be remapped
	 * @return An Array of feature lists
	 */
	protected List<Integer>[] remapFeatureVectorsToLists(List<int[]> featureVectors){
		int vectorSize = featureVectors.get(0).length;
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] featureLists = new LinkedList[vectorSize];
		for (int cnt = 0; cnt < featureLists.length; cnt++) {
			featureLists[cnt] = new LinkedList<Integer>();
		}
		
		featureVectors.forEach(arr -> {
			for(int cnt = 0; cnt < arr.length; cnt++) {
				featureLists[cnt].add(Integer.valueOf(arr[cnt]));
			}
		});
		return featureLists;
	}
	
	/**
	 * Calculates the class membership value of a feature vector
	 * @param featureVector The feature vector to calculate the class membership of
	 * @return The class membership value of the feature vector
	 */
	public abstract double membership(int[] featureVector);
	
}
