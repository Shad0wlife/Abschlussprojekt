package abschlussprojekt.util;

import java.util.Arrays;
import java.util.List;

public class Debug {

	/**
	 * Prints a list of integer arrays to the console, where each array gets printed as one line
	 * @param toPrint The List<int[]> to print
	 */
	public static void printListOfIntArrays(List<int[]> toPrint) {
		for(int[] arr : toPrint) {
			Debug.printArray(arr);
		}
	}

	/**
	 * Prints a list of boolean arrays to the console, where each array gets printed as one line
	 * @param toPrint The List<boolean[]> to print
	 */
	public static void printListOfBooleanArrays(List<boolean[]> toPrint) {
		for(boolean[] arr : toPrint) {
			Debug.printArray(arr);
		}
	}

	/**
	 * Prints a 2D integer array to the console
	 * @param toPrint
	 */
	public static void printArrayOfArrays(int[][] toPrint) {
		for(int[] arr : toPrint) {
			Debug.printArray(arr);
		}
	}

	/**
	 * Prints an integer array to the console in the format [x, ...]
	 * @param arr The array to print
	 */
	public static void printArray(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * Prints an boolean array to the console in the format [x, ...]
	 * @param arr The array to print
	 */
	public static void printArray(boolean[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	
}
