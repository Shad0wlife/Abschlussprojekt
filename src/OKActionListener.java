import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

import ij.process.ImageProcessor;

public class OKActionListener implements ActionListener{

	ImageProcessor ip;
	JComboBox<CircleSize> comboBox;
	JDialog parent;
	JRadioButton swt;
	JRadioButton gzta1;
	
	public OKActionListener(ImageProcessor ip, JDialog parent, JComboBox<CircleSize> cb, JRadioButton swt, JRadioButton gzta1) {
		this.ip = ip;
		this.parent = parent;
		this.comboBox = cb;
		this.swt = swt;
		this.gzta1 = gzta1;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CircleSize size = (CircleSize)comboBox.getSelectedItem();
		int[][] mask = BresenhamCircle.generateCircleInteger(size.getDiameter());
		List<int[]> vectors = new LinkedList<>();
		List<int[]> spectrums = new LinkedList<>();
		int offset = 0;
		
		//Zeilenweises auslesen der Maskenpixel
		for(int indexY = offset; (indexY + mask.length) <= ip.getHeight(); indexY += mask.length) {
			for(int indexX = offset; (indexX + mask.length) <= ip.getWidth(); indexX += mask.length) {
				vectors.add(walkCircle(mask, size, indexX, indexY));
			}
		}
		
		int[][] gztMatrix;
		if(this.swt.isSelected()) {
			System.out.println("SWT");
			gztMatrix = GZTMatrixGenerator.swt(Util.log2(size.getCircumference()));
		}else if(this.gzta1.isSelected()){
			System.out.println("GZTA1");
			gztMatrix = GZTMatrixGenerator.gzta1(Util.log2(size.getCircumference()));
		}else {
			System.out.println("No GZT chosen! Aborting.");
			return;
		}
		//DEBUG
		//Util.printArrayOfArrays(gztMatrix);
		
		vectors.forEach(vector -> spectrums.add(GZTMatrixGenerator.gSpectrum(MatrixOperations.matrixVectorProduct(gztMatrix, vector))));
		
		//DEBUG
		//Util.printListOfArrays(spectrums);
		
		int[] ags = GZTMatrixGenerator.averageGSpectrum(spectrums);
		
		Util.printArray(ags);

		this.parent.dispose();
	}
	
	/**
	 * Moves along a circular mask in an image and adds all matched pixels to a vector. 
	 * If a pixel is matched multiple times, it is added multiple times in a row.
	 * @param mask The circular mask as an int[][]
	 * @param size The CircleSize matching the circle in the mask, giving both the diameter and selected pixel count (as circumference)
	 * @param offsetX The x-offset in the picture the mask should be read from
	 * @param offsetY The y-offset in the picture the mask should be read from
	 * @return An array of the values on the circle, read clockwise from the top in the middle.
	 */
	private int[] walkCircle(int[][] mask, CircleSize size, int offsetX, int offsetY) {
		int[] vector = new int[size.getCircumference()];
		int[][] maskCopy = Util.deepCopy2D(mask);
		int vectorIndex = 0;
		int startX, indexX, indexY;
		//Start in top, middle
		startX = indexX = (int)(mask.length / 2.0 - 0.5);
		indexY = 0;
		
		//First Quarter
		//System.out.println("Starting Q1 with: " + indexX + ", " + indexY + "at: " + offsetX + ", " + offsetY);
		while(indexY <= (int)(maskCopy.length / 2.0 - 0.5)) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.get(offsetX + indexX, offsetY + indexY);
				ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
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
		//System.out.println("Starting Q2 with: " + indexX + ", " + indexY + "at: " + offsetX + ", " + offsetY);
		indexX = mask.length - 1;
		while(indexY < maskCopy.length) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.get(offsetX + indexX, offsetY + indexY);
				ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
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
		//System.out.println("Starting Q3 with: " + indexX + ", " + indexY + "at: " + offsetX + ", " + offsetY);
		indexY = mask.length - 1;
		indexX = startX;
		while(indexY > (int)(maskCopy.length / 2.0 - 0.5)) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.get(offsetX + indexX, offsetY + indexY);
				ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
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
		//System.out.println("Starting Q4 with: " + indexX + ", " + indexY + "at: " + offsetX + ", " + offsetY);
		indexX = 0;
		while(indexY >= 0) {
			while(maskCopy[indexY][indexX] > 0) {
				vector[vectorIndex] = ip.get(offsetX + indexX, offsetY + indexY);
				ip.putPixel(offsetX + indexX, offsetY + indexY, 127);
				vectorIndex++;
				maskCopy[indexY][indexX]--;
			}
			indexX++;
			if(indexX >= startX || maskCopy[indexY][indexX] == 0) { //TODO > or >=? should be right this way but keep an eye out
				indexY--;
				indexX--; //move straight up, not diagonally, after testing next pixel in line
			}
		}
		
		return vector;
	}
}
