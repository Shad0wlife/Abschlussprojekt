package abschlussprojekt.util.math;

public class BresenhamCircle {
	
	/**
	 * Generates a new Circular mask of booleans where fields visited by the algorithm are true, all others are false
	 * @param diameter The diameter of the circular mask to generate
	 * @return A 2-D boolean[] containing the circular mask
	 */
	public static boolean[][] generateCircleBoolean(int diameter){
		int[][] intCircle = generateCircleInteger(diameter);
		boolean[][] data = new boolean[diameter][diameter];
		
		for(int cntRow = 0; cntRow < intCircle.length; cntRow++) {
			for(int cntColumn = 0; cntColumn < intCircle[cntRow].length; cntColumn++) {
				data[cntRow][cntColumn] = intCircle[cntRow][cntColumn] > 0;
			}
		}
		
		return data;
	}
	
	/**
	 * Generates a new Circular mask of integers, where the value in a field denotes how often the algorithm visited that field
	 * @param diameter The diameter of the circular mask to generate
	 * @return A 2-D int[] containing the circular mask
	 */
	public static int[][] generateCircleInteger(int diameter){
		
		if(diameter < 1) {
			throw new IllegalArgumentException("The passed diameter must be larger than 0.");
		}
		
		int[][] data = new int[diameter][diameter];
		
		double radius = diameter/2.0;
		double center = radius - 0.5; //due to 0-indexed arrays. The center of a 10-size circle is between index 4 and 5
		
		double error = radius;
		double offsetX = radius - 0.5; //align with integer numbers and 0-indexed adressing
		double offsetY; //even circle (default) start at next pixel (current index points to pixel border)
		
		boolean odd = diameter%2 == 1;
		
		if(odd) {
			offsetY = 0.0;
		}else {
			offsetY = 0.5;
		}
		
		do {
			placeMirroredLocations(offsetX, offsetY, center, data);
			
			//calculate next pixel position(s)
			double dY = offsetY * 2.0 + 1.0;
			offsetY ++;
			error -= dY;
			if(error < 0) {
				double dX = 1.0 - (offsetX * 2.0);
				offsetX--;
				error -= dX;
			}			
		}while(offsetY <= offsetX); //use <= due to pixels on diagonal being left out otherwise (with odd diameters)
		
		return data;
	}
	
	/**
	 * Places/increments all 8 mirrorable locations based on the passed calculated position
	 * @param offsetX The x offset from the circle's center
	 * @param offsetY The y offset from the circle's center
	 * @param center The circles center coordinate (with x == y, thus only one value neccessary)
	 * @param data The target array to write the the locations to
	 */
	private static void placeMirroredLocations(double offsetX, double offsetY, double center, int[][] data) {
		data[(int)(center + offsetY)][(int)(center + offsetX)]++;
		data[(int)(center - offsetY)][(int)(center + offsetX)]++;
		data[(int)(center + offsetY)][(int)(center - offsetX)]++;
		data[(int)(center - offsetY)][(int)(center - offsetX)]++;

		data[(int)(center + offsetX)][(int)(center + offsetY)]++;
		data[(int)(center - offsetX)][(int)(center + offsetY)]++;
		data[(int)(center + offsetX)][(int)(center - offsetY)]++;
		data[(int)(center - offsetX)][(int)(center - offsetY)]++;
	}

}
