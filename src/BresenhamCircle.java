
public class BresenhamCircle {
	
	public static boolean[][] generateCircleBoolean(int diameter){
		
		if(diameter < 1) {
			throw new IllegalArgumentException("The passed diameter must be larger than 0.");
		}
		
		boolean[][] data = new boolean[diameter][diameter];
		
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
			//place all 8 mirrored pixels
			data[(int)(center + offsetY)][(int)(center + offsetX)] = true;
			data[(int)(center - offsetY)][(int)(center + offsetX)] = true;
			data[(int)(center + offsetY)][(int)(center - offsetX)] = true;
			data[(int)(center - offsetY)][(int)(center - offsetX)] = true;

			data[(int)(center + offsetX)][(int)(center + offsetY)] = true;
			data[(int)(center - offsetX)][(int)(center + offsetY)] = true;
			data[(int)(center + offsetX)][(int)(center - offsetY)] = true;
			data[(int)(center - offsetX)][(int)(center - offsetY)] = true;
			
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
			//place all 8 mirrored pixels
			data[(int)(center + offsetY)][(int)(center + offsetX)]++;
			data[(int)(center - offsetY)][(int)(center + offsetX)]++;
			data[(int)(center + offsetY)][(int)(center - offsetX)]++;
			data[(int)(center - offsetY)][(int)(center - offsetX)]++;

			data[(int)(center + offsetX)][(int)(center + offsetY)]++;
			data[(int)(center - offsetX)][(int)(center + offsetY)]++;
			data[(int)(center + offsetX)][(int)(center - offsetY)]++;
			data[(int)(center - offsetX)][(int)(center - offsetY)]++;
			
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

}
