package abschlussprojekt.util.math;

import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.util.Util;
import abschlussprojekt.util.CircleSize;
import abschlussprojekt.util.GZT;
import ij.process.ImageProcessor;

public class ImageSpectrum {
	
	/**
	 * Gets the summed G-Spectrum of an image by its ImageProcessor, a given circle mask size and a given type of GZT
	 * @param ip The ImageProcessor containing the image data to generate the spectrum for
	 * @param size The {@link CircleSize} to be used for the circular mask in the transformation
	 * @param gzt The {@link GZT} to be used in the transformation
	 * @param drawCircles Whether the circles used for spectrum generation should be drawn into the ImageProcessor
	 * @return The image's G-Spectrum as an int[]
	 */
	public static int[] getImageGSpectrum(ImageProcessor ip, CircleSize size, GZT gzt, boolean drawCircles) {
		int[][] mask = BresenhamCircle.generateCircleInteger(size.getDiameter());
		List<int[]> vectors = new LinkedList<>();
		List<int[]> spectrums = new LinkedList<>();
		int offset = 0;
		
		//Trace all circles in the image
		for(int indexY = offset; (indexY + mask.length) <= ip.getHeight(); indexY += mask.length) {
			for(int indexX = offset; (indexX + mask.length) <= ip.getWidth(); indexX += mask.length) {
				vectors.add(walkCircle(ip, mask, size, indexX, indexY, drawCircles));
			}
		}
		
		int[][] gztMatrix = GZTMatrixGenerator.getGZT(gzt, Util.log2(size.getCircumference()));
		
		vectors.forEach(vector -> spectrums.add(GZTMatrixGenerator.gSpectrum(MatrixOperations.matrixVectorProduct(gztMatrix, vector))));
		
		int[] imageSpectrum = GZTMatrixGenerator.cumulativeGSpectrum(spectrums);
		
		return imageSpectrum;
	}
	
	/**
	 * Moves along a circular mask in an image and adds all matched pixels to a vector. 
	 * If a pixel is matched multiple times, it is added multiple times in a row.
	 * @param mask The circular mask as an int[][]
	 * @param size The CircleSize matching the circle in the mask, giving both the diameter and selected pixel count (as circumference)
	 * @param offsetX The x-offset in the picture the mask should be read from
	 * @param offsetY The y-offset in the picture the mask should be read from
	 * @param drawCircles Whether the traced circles should be drawn into the ImageProcessor
	 * @return An array of the values on the circle, read clockwise from the top in the middle.
	 */
	private static int[] walkCircle(ImageProcessor ip, int[][] mask, CircleSize size, int offsetX, int offsetY, boolean drawCircles) {
		int[] vector = new int[size.getCircumference()];
		int[][] maskCopy = Util.deepCopy2D(mask);
		int vectorIndex = 0;
		int startX, indexX, indexY;
		//Start in top, middle
		startX = indexX = (int)(mask.length / 2.0 - 0.5);
		indexY = 0;
		
		//First Quarter
		while(indexY <= (int)(maskCopy.length / 2.0 - 0.5)) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.getPixel(offsetX + indexX, offsetY + indexY);
				if(drawCircles) {
					ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
				}
				vectorIndex++;
				maskCopy[indexY][indexX]--;
			}
			indexX++;
			if(indexX >= mask.length || maskCopy[indexY][indexX] == 0) {
				indexY++;
				indexX--; //move straight down, not diagonally, after testing next pixel in line
			}
		}
		
		//Second quarter
		indexX = mask.length - 1;
		while(indexY < maskCopy.length) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.getPixel(offsetX + indexX, offsetY + indexY);
				if(drawCircles) {
					ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
				}
				vectorIndex++;
				maskCopy[indexY][indexX]--;
			}
			indexX--;
			if(indexX <= startX || maskCopy[indexY][indexX] == 0) {
				indexY++;
				indexX++; //move straight down, not diagonally, after testing next pixel in line
			}
		}
		
		//Third quarter
		indexY = mask.length - 1;
		indexX = startX;
		while(indexY > (int)(maskCopy.length / 2.0 - 0.5)) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.getPixel(offsetX + indexX, offsetY + indexY);
				if(drawCircles) {
					ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
				}
				vectorIndex++;
				maskCopy[indexY][indexX]--;
			}
			indexX--;
			if(indexX < 0 || maskCopy[indexY][indexX] == 0) {
				indexY--;
				indexX++; //move straight up, not diagonally, after testing next pixel in line
			}
		}
		
		//Fourth quarter
		indexX = 0;
		while(indexY >= 0) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.getPixel(offsetX + indexX, offsetY + indexY);
				if(drawCircles) {
					ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
				}
				vectorIndex++;
				maskCopy[indexY][indexX]--;
			}
			indexX++;
			if(indexX >= startX || maskCopy[indexY][indexX] == 0) {
				indexY--;
				indexX--; //move straight up, not diagonally, after testing next pixel in line
			}
		}
		
		return vector;
	}
}
