package abschlussprojekt.util;

import java.util.Arrays;
import java.util.List;

public class Util {
	public static int[][] deepCopy2D(int[][] source){
		if(source == null) {
			return null;
		}
		
		int[][] copy = new int[source.length][];
		for(int cnt = 0; cnt < source.length; cnt++) {
			copy[cnt] = Arrays.copyOf(source[cnt], source[cnt].length);
		}
		return copy;
	}
	
	public static int log2(int x) {
		int l2 = 0;
		while((x >>= 1) > 0) {
			l2++;
		}
		return l2;
	}
	
	public static void printListOfIntArrays(List<int[]> toPrint) {
		for(int[] arr : toPrint) {
			printArray(arr);
		}
	}
	
	
	public static void printListOfBooleanArrays(List<boolean[]> toPrint) {
		for(boolean[] arr : toPrint) {
			printArray(arr);
		}
	}
	
	public static void printArrayOfArrays(int[][] toPrint) {
		for(int[] arr : toPrint) {
			printArray(arr);
		}
	}
	
	public static void printArray(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	
	public static void printArray(boolean[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	
	public static double arraySumme(double[] array) {
		double res = 0;
		for (double d : array) {
			res += d;
		}
		return res;
	}
	
	public static int[] addVectors(int[] lhs, int[] rhs) {
		if(lhs.length != rhs.length) {
			throw new IllegalArgumentException("Vectors must be equal in length!");
		}
		int[] res = new int[lhs.length];
		for(int cnt = 0; cnt < lhs.length; cnt++) {
			res[cnt] = lhs[cnt] + rhs[cnt];
		}
		return res;
	}
	
	public static void reverseArray(double[] arr) {
		for(int cnt = 0; cnt < arr.length/2; cnt++) {
			double x = arr[cnt];
			arr[cnt] = arr[arr.length - 1 - cnt];
			arr[arr.length - 1 - cnt] = x;
		}
	}
}
