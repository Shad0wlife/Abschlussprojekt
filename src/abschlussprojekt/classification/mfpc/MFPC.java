package abschlussprojekt.classification.mfpc;

import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.FPC_Derivate;

public class MFPC extends FPC_Derivate {
	
	public MFPC(DValue D, double pce, List<List<int[]>> learningVectors){
		super();
		learn(D, pce, learningVectors);
	}

	/**
	 * Learns the membership functions for the feature vectors
	 * @param D The {@link DValue} used in the membership function
	 * @param pce The pce used in the membership function
	 * @param learningVectors The vectors containing the learning data
	 */
	private void learn(DValue D, double pce, List<List<int[]>> learningVectors) {
		for(List<int[]> learningData : learningVectors) {
			membershipFunctions.add(new MFPCMembershipFunction(D, pce, learningData));
		}
		
	}

}
