import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class RecognitionPlugin_ implements PlugInFilter {

	private final int FLAGS = DOES_8G;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return FLAGS;
	}

	@Override
	public void run(ImageProcessor ip) {
		//Generate simple boolean circle
		boolean[][] a = BresenhamCircle.generateCircleBoolean(11);
		System.out.println(Arrays.deepToString(a).replace("true", "1").replace("false", "0").replace("], [", "\r\n").replace("[[", "").replace("]]", ""));
		
		//Generate weighted integer circle with center-axis coordinate reuse on odd diameters
		int[][] b = BresenhamCircle.generateCircleInteger(11);
		System.out.println(Arrays.deepToString(b).replace("], [", "\r\n").replace("[[", "").replace("]]", ""));
	}

}
