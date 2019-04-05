package abschlussprojekt.util;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.gui.FilePicker;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class Util {
	
	/**
	 * Creates a deep copy of a 2D-int array
	 * @param source The 2D-int array to copy
	 * @return The deep copy
	 */
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
	
	/**
	 * Calculates the integer result for log2 by bitshifting to the right and counting the shifts until the value is 0.
	 * @param x The value to calculate log2 from
	 * @return log2(x) as an integer
	 */
	public static int log2(int x) {
		int l2 = 0;
		while((x >>= 1) > 0) {
			l2++;
		}
		return l2;
	}
	
	/**
	 * Sums up all values in a double array and returns the sum
	 * @param array The array to sum the values of
	 * @return The sum of the array's values
	 */
	public static double arraySumme(double[] array) {
		double res = 0;
		for (double d : array) {
			res += d;
		}
		return res;
	}
	
	/**
	 * Adds two vectors by adding their correspnding elements
	 * @param lhs The first summand
	 * @param rhs The second summand
	 * @return The resulting sum
	 */
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
	
	/**
	 * Reverses the elements of an array in-place.
	 * eg.: [0, 1, 2] -> [2, 1, 0]
	 * @param arr The array to reverse
	 */
	public static void reverseArray(double[] arr) {
		for(int cnt = 0; cnt < arr.length/2; cnt++) {
			double x = arr[cnt];
			arr[cnt] = arr[arr.length - 1 - cnt];
			arr[arr.length - 1 - cnt] = x;
		}
	}
	
	/**
	 * Opens a file picker to open a folder containing image files and returns the list of ImagePluses of the images in the chosen folder
	 * @param startPathPointer A 1-element File array containing the File object of the folder the dialog should open in by default
	 * @return The list of images in the chosen folder as List of ImagePlus
	 */
	public static List<ImagePlus> getImagesWithDialog(File[] startPathPointer){ //TODO das muss doch auch schöner ohne das wrapper 1-element array gehen für reference passing
		List<ImagePlus> imagePluses = new LinkedList<>();
		FilePicker picker = new FilePicker(imagePluses, startPathPointer);
		picker.setVisible(true);
		return imagePluses;
	}
	
	/**
	 * Opens a file picker to open a folder containing image files and returns the list of ImagePluses of the images in the chosen folder
	 * @return The list of images in the chosen folder as List of ImagePlus
	 */
	public static List<ImagePlus> getImagesWithDialog(){
		return getImagesWithDialog(new File[] {null}); //TODO das muss doch auch schöner ohne das wrapper 1-element array gehen für reference passing
	}
	
	/**
	 * Returns the ImageProcessor of a passed ImagePlus as an 8Bit ImageProcessor (ByteProcessor).
	 * A Colorspace enum value can be passed to define the RGB to Greyscale calculation luma coefficients
	 * @param imagePlus The ImagePlus of which the ImageProcessor should be returned
	 * @param colorspace The colorspace that should be used when converting from RGB
	 * @return The ImageProcessor as a 8Bit Greyscale image
	 */
	public static ImageProcessor get8BitImageProcessor(ImagePlus imagePlus, Colorspace colorspace) {
		ImageProcessor imageProcessor = imagePlus.getProcessor();
		if(!(imageProcessor instanceof ByteProcessor)) {
			if(imageProcessor instanceof ColorProcessor) {
				((ColorProcessor)imageProcessor).setRGBWeights(colorspace.getFactorR(), colorspace.getFactorG(), colorspace.getFactorB());
			}
			imageProcessor = imageProcessor.convertToByteProcessor(true);
			System.out.println("Converted image!"); //DEBUG
		}
		return imageProcessor;
	}
}
