package abschlussprojekt.classification.oamfpc;

import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.FPC_Derivate;
import abschlussprojekt.classification.oamfpc.OAMFPCMembershipFunction;
import abschlussprojekt.gui.OAMFPCWeightSlider;

public class OAMFPC implements FPC_Derivate {
	
	private List<OAMFPCMembershipFunction> membershipFunctions;
	private double[] weights;
	
	public OAMFPC(DValue D, double pce, List<List<int[]>> learningVectors, double[] weights){
		membershipFunctions = new LinkedList<>();
		this.weights = weights;
		learn(D, pce, learningVectors);
	}

	@Override
	public int[] classify(int[] featureVector) {
		int size = membershipFunctions.size();
		double[] memberships = new double[size]; //DEBUG
		double max = Double.NEGATIVE_INFINITY;
		int maxIdx = -1;
		int[] result = new int[size];
		
		for(int cnt = 0; cnt < size; cnt++) {
			double membership = membershipFunctions.get(cnt).membership(featureVector, this.weights);
			memberships[cnt] = membership; //DEBUG
			
			if(membership > max) {
				max = membership;
				maxIdx = cnt;
			}
		}
		result[maxIdx] = 1;
		
		return result;
	}

	private void learn(DValue D, double pce, List<List<int[]>> learningVectors) {
		for(List<int[]> learningData : learningVectors) {
			membershipFunctions.add(new OAMFPCMembershipFunction(D, pce, learningData));
		}
		
	}

}
