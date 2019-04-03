package abschlussprojekt.classification.mfpc;

import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.FPC_Derivate;

public class MFPC implements FPC_Derivate {
	
	private List<MFPCMembershipFunction> membershipFunctions;
	
	public MFPC(DValue D, double pce, List<List<int[]>> learningVectors){
		membershipFunctions = new LinkedList<>();
		learn(D, pce, learningVectors);
	}

	@Override
	public boolean[] classify(int[] featureVector) {
		int size = membershipFunctions.size();
		double[] memberships = new double[size]; //DEBUG
		double max = Double.NEGATIVE_INFINITY;
		int maxIdx = -1;
		boolean[] result = new boolean[size];
		
		for(int cnt = 0; cnt < size; cnt++) {
			double membership = membershipFunctions.get(cnt).membership(featureVector);
			System.out.println("Membership " + cnt + ": " + membership);
			memberships[cnt] = membership; //DEBUG
			
			if(membership > max) {
				max = membership;
				maxIdx = cnt;
			}
		}
		result[maxIdx] = true;
		
		return result;
	}

	private void learn(DValue D, double pce, List<List<int[]>> learningVectors) {
		for(List<int[]> learningData : learningVectors) {
			membershipFunctions.add(new MFPCMembershipFunction(D, pce, learningData));
		}
		
	}

}
