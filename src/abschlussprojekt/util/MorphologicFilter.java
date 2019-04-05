package abschlussprojekt.util;

import ij.process.ImageProcessor;

public class MorphologicFilter {

	/**
	 * Executes a morphologic filter on the ImageProcessor, leaving a black margin
	 * @param ip The ImageProcessor to morph
	 * @param filterSettings The {@link MorphologicFilterSettings} containing the filter choice and the corresponding matrix size
	 */
	public static void morph(ImageProcessor ip, MorphologicFilterSettings filterSettings) {
		int[][] pixelArrayManipulated;
		switch(filterSettings.getFilterChoice()) {
		case EROSION:
			pixelArrayManipulated = morphPixelArray(ip, true, filterSettings.getMatrixSize());
			break;
		case DILATION:
			pixelArrayManipulated = morphPixelArray(ip, false, filterSettings.getMatrixSize());
			break;
		default:
			return;
		}
		writeArrayToImageProcessor(ip, pixelArrayManipulated);
	}
	
	/**
	 * Executes the morphologic operator by writing the results into a new pixel array of type int[][]
	 * @param ip The ImageProcessor containing the original pixel data.
	 * @param erode whether to erode or dilate the image
	 * @return An int[][] with the morphed pixel data
	 */
	private static int[][] morphPixelArray(ImageProcessor ip, boolean erode, int matrixSize){
		int width = ip.getWidth();
		int height = ip.getHeight();
		int[][] pixelArrayManipulated = new int[width][height];
		
		int min, max;
		for (int w = (matrixSize/2); w < width-(matrixSize/2); w++) {
			for (int h = (matrixSize/2); h < height-(matrixSize/2); h++) {
				max = Integer.MIN_VALUE;
				min = Integer.MAX_VALUE;
				for (int wOfMatrix = w-(matrixSize/2); wOfMatrix <= w+(matrixSize/2); wOfMatrix++) {
					for (int hOfMatrix = h-(matrixSize/2); hOfMatrix <= h+(matrixSize/2); hOfMatrix++) {
						int pixelValue = ip.get(wOfMatrix, wOfMatrix);
						if (pixelValue > max) {
							max = pixelValue;
						}
						if (pixelValue < min) {
							min = pixelValue;
						}
					}
				}
				if(erode) {
					//Erosion
					pixelArrayManipulated[w][h] = min;
				}else {
					//Dilation
					pixelArrayManipulated[w][h] = max;
				}
			}
		}
		return pixelArrayManipulated;
	}
	
	/**
	 * Writes image data from a 2D array to an ImageProcessor.
	 * @param ip The ImageProcessor to write into
	 * @param pixelArray The int[][] to read pixel data from
	 */
	private static void writeArrayToImageProcessor(ImageProcessor ip, int[][] pixelArray) {
		int width = ip.getWidth();
		int height  = ip.getHeight();
		for (int w = 0; w < width ; w++) {
			for (int h = 0; h < height ; h++) {
				ip.putPixel(w, h, pixelArray[w][h]);
			}
		}
	}
}
