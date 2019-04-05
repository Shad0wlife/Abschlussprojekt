package abschlussprojekt.classification.oamfpc;

import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.FPC_Derivate;
import abschlussprojekt.classification.oamfpc.OAMFPCMembershipFunction;

public class OAMFPC extends FPC_Derivate {
	
	public OAMFPC(DValue D, double pce, List<List<int[]>> learningVectors, double[] weights){
		learn(D, pce, learningVectors, weights);
	}

	/**
	 * Learns the membership functions for the feature vectors
	 * @param D The {@link DValue} used in the membership function
	 * @param pce The pce used in the membership function
	 * @param learningVectors The vectors containing the learning data
	 * @param weights The weights for the membership function
	 */
	private void learn(DValue D, double pce, List<List<int[]>> learningVectors, double[] weights) {
		for(List<int[]> learningData : learningVectors) {
			membershipFunctions.add(new OAMFPCMembershipFunction(D, pce, learningData, weights));
		}
		
	}

}
