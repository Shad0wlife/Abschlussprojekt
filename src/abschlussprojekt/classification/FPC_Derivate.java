package abschlussprojekt.classification;

import java.util.LinkedList;
import java.util.List;

public abstract class FPC_Derivate {

	protected List<MembershipFunction> membershipFunctions;
	
	protected FPC_Derivate() {
		membershipFunctions = new LinkedList<>();
	}
	
	/**
	 * Classifies a given feature vector
	 * @param featureVector The feature vector to classify
	 * @return The classification, where a 1 denotes the chosen class
	 */
	public int[] classify(int[] featureVector) {
		int size = membershipFunctions.size();
		double[] memberships = new double[size]; //DEBUG
		double max = Double.NEGATIVE_INFINITY;
		int maxIdx = -1;
		int[] result = new int[size];
		
		for(int cnt = 0; cnt < size; cnt++) {
			double membership = membershipFunctions.get(cnt).membership(featureVector);
			memberships[cnt] = membership; //DEBUG
			
			if(membership > max) {
				max = membership;
				maxIdx = cnt;
			}
		}
		result[maxIdx] = 1;
		
		return result;
	}
}
