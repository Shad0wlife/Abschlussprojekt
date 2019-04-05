package abschlussprojekt.classification.oamfpc;

import java.util.Arrays;
import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.MembershipFunction;
import abschlussprojekt.util.Util;

public class OAMFPCMembershipFunction extends MembershipFunction{
	
	private double[] weights;
	
	public OAMFPCMembershipFunction(DValue D, double pce, List<int[]> featureVectors, double[] weights) {
		super(D, pce, featureVectors);
		this.weights = weights;
	}
	
	/**
	 * Calculates the class membership value of a feature vector with given weights
	 * @param featureVector The feature vector to calculate the class membership of
	 * @return The class membership value of the feature vector
	 */
	@Override
	public double membership(int[] featureVector) {
		if(featureVector.length != vectorSize) {
			throw new IllegalArgumentException("The feature vector does not match the learning data vectors in size.");
		}
		
		double[] unweightedDistances = getDistances(featureVector);
		
		Arrays.sort(unweightedDistances);
		Util.reverseArray(unweightedDistances);
		
		double[] weighted = weightSortedDistances(unweightedDistances, this.weights);
		
		return Util.arraySumme(weighted);
	}
	
	/**
	 * Weights a vector of distances with a vector of weights
	 * @param distances The vector of distances as a double[]
	 * @param weights The vector of weights as a double[]
	 * @return The vector of the weighted distances as a double[]
	 */
	private double[] weightSortedDistances(double[] distances, double[] weights) {
		double[] weighted = new double[vectorSize];
		for(int cnt = 0; cnt < vectorSize; cnt++) {
			weighted[cnt] = weights[cnt] * distances[cnt];
			System.out.println("Weighted feature index " + cnt + " has value: " + weighted[cnt]); //DEBUG
		}
		return weighted;
	}
	
}
