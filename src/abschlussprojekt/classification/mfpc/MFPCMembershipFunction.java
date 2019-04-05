package abschlussprojekt.classification.mfpc;

import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.MembershipFunction;
import abschlussprojekt.util.Util;

public class MFPCMembershipFunction extends MembershipFunction{
	
	public MFPCMembershipFunction(DValue D, double pce, List<int[]> featureVectors) {
		super(D, pce, featureVectors);
	}
	
	/**
	 * Calculates the class membership value of a feature vector
	 * @param featureVector The feature vector to calculate the class membership of
	 * @return The class membership value of the feature vector
	 */
	@Override
	public double membership(int[] featureVector) {
		if(featureVector.length != vectorSize) {
			throw new IllegalArgumentException("The feature vector does not match the learning data vectors in size.");
		}
		double[] distances = getDistances(featureVector);
		
		return Math.pow(2.0, (-1.0 * Util.arraySumme(distances)) / vectorSize);
	}
	
}
