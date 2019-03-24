public class CircleGenerator {
	private int minValue;
	private int maxValue;
	
	/**
	 * Creates a new CircleGenerator instance.
	 * @param minValue The minimum value the mask should be filled with.
	 * @param maxValue The maximum value the mask should be filled with.
	 */
	public CircleGenerator(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	/**
	 * Generates a 2D-int-Array with a circle represented inside
	 * @param maskSize The size of the resulting array.
	 * @param diameter The diameter of the circle inside.
	 * @param filled Whether the circle should be filled (or the surrounding area in case of a negative mask)
	 * @param negative Whether the resulting array should be inverted (negative mask)
	 * @return The array resulting from the given settings.
	 */
	public int[][] generate(int maskSize, int diameter, boolean filled, boolean negative) {
		int[][] data = new int[maskSize][maskSize];
		
		double radius = diameter/2.0;
		System.out.println("Radius: " + radius);
		
		double center = maskSize/2.0 - 0.5;
		
		for(int cntX = 0; cntX < maskSize; cntX++) {
			for(int cntY = 0; cntY < maskSize; cntY++) {
				double distance = Math.hypot((double)cntX - center, (double)cntY - center);
				//DEBUGGING
				//System.out.println("Distance for (" + cntX + "/" + cntY + ") is " + distance);
				if(distance > radius) {
					if(negative) {
						data[cntX][cntY] = maxValue;
					}else {
						data[cntX][cntY] = minValue;
					}
				}else {
					if(negative) {
						data[cntX][cntY] = minValue;
					}else {
						data[cntX][cntY] = maxValue;
					}
				}
			}
		}
		
		//Clear out max value field that do not border to a min value field
		if(!filled) {
			int[][] border = new int[data.length][data[0].length];
			for(int cntX = 0; cntX < maskSize; cntX++) {
				for(int cntY = 0; cntY < maskSize; cntY++) {
					if(data[cntX][cntY] == maxValue && !testNeighbours(data, cntX, cntY)) {
							border[cntX][cntY] = maxValue;
					}
				}
			}
			data = border;
		}
		
		return data;
	}
	
	/**
	 * Tests whether field is only surrounded by maximum-value fields.
	 * @param data The array to test in.
	 * @param x The x coordinate of the target field
	 * @param y The y coordinate of the target field
	 * @return Whether the target field is only surroundet by maximum-value fields.
	 */
	private boolean testNeighbours(int[][] data, int x, int y) {
		int[] neighbours = getNeighbours(data, x, y);
		int sumNeighbours = 0;
		for (int i : neighbours) {
			sumNeighbours += i;
		}
		
		//DEBUGGING
		//System.out.println("X: " + x + ", Y: " + y + " Hat nachbarn: " + Arrays.toString(neighbours) + " der Summe " + sumNeighbours);
		
		return (sumNeighbours / neighbours.length) == maxValue;
	}
	
	/**
	 * Gets all the neighbours of a given field. Is border-aware.
	 * @param data The array to test in.
	 * @param x The x coordinate of the target field
	 * @param y The y coordinate of the target field
	 * @return An array of the neighbouring values.
	 */
	private int[] getNeighbours(int[][] data, int x, int y) {
		int size = 4;
		if(x == 0 || x == data.length-1) {
			size--;
		}
		if(y == 0 || y == data.length-1) {
			size --;
		}
		
		int[] neighbours = new int[size];
		
		int cnt = 0;
		
		if(x>0) {
			neighbours[cnt] = data[x-1][y];
			cnt++;
		}
		if(y>0) {
			neighbours[cnt] = data[x][y-1];
			cnt++;
		}
		if(x<(data.length-1)) {
			neighbours[cnt] = data[x+1][y];
			cnt++;
		}
		if(y<(data.length-1)) {
			neighbours[cnt] = data[x][y+1];
		}
		
		return neighbours;
	}
	
}
