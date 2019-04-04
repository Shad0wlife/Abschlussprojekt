package abschlussprojekt.util.math;

import java.util.List;

import abschlussprojekt.util.Util;
import abschlussprojekt.util.GZT;

public class GZTMatrixGenerator {

	public static int[][] getGZT(GZT gzt, int exponent){
		switch(gzt) {
		case SWT:
			return swt(exponent);
		case GZTA1:
			return gzta1(exponent);
		}
		return null;
	}
	
	/**
	 * Creates an SWT Matrix of dimension 2^exponent x 2^exponent
	 * @param exponent the exponent of 2 by which the matrix dimension is defined
	 * @return The SWT matrix
	 */
	public static int[][] swt(int exponent) {
		int currentSubBlockDimension = (int)Math.pow(2, exponent);
		final int dimension = currentSubBlockDimension;
		int matSWT[][] = new int[currentSubBlockDimension][currentSubBlockDimension];
		
		//Create sub-blocks of the matrix, fill them in, and reduce block size on each iteration
		while(currentSubBlockDimension > 1) {
			currentSubBlockDimension /= 2;
			int[] tempLineArray = new int[currentSubBlockDimension];
			
			//Create first line of sub-block
			for(int cnt = 0; cnt < currentSubBlockDimension; cnt++) {
				tempLineArray[cnt] = Integer.signum(cnt - dimension + currentSubBlockDimension);
			}
			
			//Copy first line and shift right and switch rotated elements' sign
			for(int cntRow = dimension - 2 * currentSubBlockDimension; cntRow < dimension - currentSubBlockDimension; cntRow++) {
				for(int cntColumn = 0; cntColumn < tempLineArray.length; cntColumn++) {
					matSWT[cntRow][cntColumn] = tempLineArray[cntColumn];
				}
				tempLineArray = rotateArrayRightSign(tempLineArray);
				int signFactor = -1;
				
				//Fill in repeated sub-block with alternating sign
				for(int cnt = 1; cnt < dimension/currentSubBlockDimension; cnt++) {
					for(int cnt2 = 0; cnt2 < tempLineArray.length; cnt2++) {
						matSWT[cntRow][cnt * currentSubBlockDimension + cnt2] = signFactor * matSWT[cntRow][cnt2];
					}
					signFactor = -signFactor;
				}
			}
		}
		
		//Fill last row with -1
		for(int cnt = 0; cnt < dimension; cnt++) {
			matSWT[dimension-1][cnt] = -1;
		}
		
		return matSWT;
	}
	
	/**
	 * Creates an GZTA1 Matrix of dimension 2^exponent x 2^exponent
	 * @param exponent the exponent of 2 by which the matrix dimension is defined
	 * @return The GZTA1 matrix
	 */
	public static int[][] gzta1(int exponent) {
		int currentSubBlockDimension = (int)Math.pow(2, exponent);
		final int dimension = currentSubBlockDimension;
		int matGZTA1[][] = new int[currentSubBlockDimension][currentSubBlockDimension];
		
		//Create sub-blocks of the matrix, fill them in, and reduce block size on each iteration
		while(currentSubBlockDimension > 1) {
			currentSubBlockDimension /= 2;
			int[] tempLineArray = new int[currentSubBlockDimension];
			
			if(currentSubBlockDimension >= 4) {
				
				//Create first line of sub-block
				for(int cnt = 0; cnt < currentSubBlockDimension; cnt++) {
					if(cnt == 0) {
						tempLineArray[cnt] = 1;
					}else if(cnt == currentSubBlockDimension - 1) {
						tempLineArray[cnt] = -1;
					}else {
						tempLineArray[cnt] = 0;
					}
				}
			//TODO Flip this for Lohweg/Dipl core difference
			}else if(currentSubBlockDimension == 2) {
				tempLineArray[0] = -1;
				tempLineArray[1] = 0;
			}else {
				tempLineArray[0] = -1;
			}
			
			//Copy first line and shift right and switch rotated elements' sign
			for(int cntRow = dimension - 2 * currentSubBlockDimension; cntRow < dimension - currentSubBlockDimension; cntRow++) {
				for(int cntColumn = 0; cntColumn < tempLineArray.length; cntColumn++) {
					matGZTA1[cntRow][cntColumn] = tempLineArray[cntColumn];
				}
				tempLineArray = rotateArrayRightSign(tempLineArray);
				int signFactor = -1;
				
				//Fill in repeated sub-block with alternating sign
				for(int cnt = 1; cnt < dimension/currentSubBlockDimension; cnt++) {
					for(int cnt2 = 0; cnt2 < tempLineArray.length; cnt2++) {
						matGZTA1[cntRow][cnt * currentSubBlockDimension + cnt2] = signFactor * matGZTA1[cntRow][cnt2];
					}
					signFactor = -signFactor;
				}
			}
		}
		
		//Fill last row with -1
		for(int cnt = 0; cnt < dimension; cnt++) {
			matGZTA1[dimension-1][cnt] = 1;
		}
		
		return matGZTA1;
	}
	
	/**
	 * Rotates an array's elements right, and negates the sign of the rotated elements
	 * @param arr The array to rotate with sign change
	 * @return The rotated/sign changed array
	 */
	public static int[] rotateArrayRightSign(int[] arr) {
		int[] ret = new int[arr.length];
		for(int cnt = 0; cnt < (arr.length - 1); cnt++) {
			ret[(cnt+1)] = arr[cnt];
		}
		ret[0] = -arr[arr.length - 1];
		return ret;
	}
	
	/**
	 * 
	 * @param transformed
	 * @return
	 */
	public static int[] gSpectrum(int[] transformed) {
		int inLength = transformed.length;
		int section = inLength;
		if((inLength & (inLength - 1)) != 0) {
			return null;
		}
		int log2 = Util.log2(inLength);
		int[] spectrum = new int[log2 + 1];
		int idx = 0;
		while(inLength > 1) {
			section /= 2;
			int sum = 0;
			for(int cnt = 0; cnt < section; cnt++) {
				sum += Math.abs(transformed[cnt + inLength - 2 * section]);
			}
			spectrum[idx++] = sum;
			inLength -= section;
		}
		spectrum[idx] = Math.abs(transformed[inLength - 1]);
		
		return spectrum;
	}
	
	public static int[] averageGSpectrum(List<int[]> spectrums) {
		int[] res = new int[spectrums.get(0).length];
		for (int[] arr : spectrums) {
			res = Util.addVectors(res, arr);
		}
		return res;
	}
	
}
