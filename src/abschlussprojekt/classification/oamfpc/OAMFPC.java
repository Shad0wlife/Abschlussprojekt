package abschlussprojekt.classification.oamfpc;

import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.FPC_Derivate;
import abschlussprojekt.classification.oamfpc.OAMFPCMembershipFunction;
import abschlussprojekt.gui.OAMFPCWeightSlider;

public class OAMFPC implements FPC_Derivate {
	
	private List<OAMFPCMembershipFunction> membershipFunctions;
	
	public OAMFPC(DValue D, double pce, List<List<int[]>> learningVectors){
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
		
		double[] weights = new double[featureVector.length];
		OAMFPCWeightSlider sliderGui = new OAMFPCWeightSlider(weights);
		sliderGui.setVisible(true);
		
		for(int cnt = 0; cnt < size; cnt++) {
			double membership = membershipFunctions.get(cnt).membership(featureVector, weights);
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
			membershipFunctions.add(new OAMFPCMembershipFunction(D, pce, learningData));
		}
		
	}

}
