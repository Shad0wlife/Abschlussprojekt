import javax.swing.JOptionPane;

import abschlussprojekt.gui.SelectionGui;
import abschlussprojekt.util.MorphologicFilter;
import abschlussprojekt.util.MorphologicFilterChoice;
import abschlussprojekt.util.MorphologicFilterSettings;
import abschlussprojekt.util.Util;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class RecognitionPlugin_ implements PlugInFilter {

	private final int FLAGS = DOES_8G;
	private MorphologicFilterSettings filterSettings = new MorphologicFilterSettings(MorphologicFilterChoice.NONE, 0);
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return FLAGS;
	}

	@Override
	public void run(ImageProcessor ip) {
		preprocessing(ip);
		
		SelectionGui gui = new SelectionGui(ip, this.filterSettings); //Does this chain make sense?
		gui.setVisible(true);
	}
	
	/**
	 * Requests, saves and runs potential preprocessing filters on the image
	 * @param imageProcessor The image to preprocess
	 */
	private void preprocessing(ImageProcessor imageProcessor) {
		MorphologicFilterChoice morphChoice = Util.getMorphChoice();
		if(morphChoice != MorphologicFilterChoice.NONE) {
			int matrixSize = (int)IJ.getNumber("Wie groﬂ soll die morphologische Matrix sein?", 3); // Input of matrix dimensions
			if(matrixSize != IJ.CANCELED) {
				this.filterSettings = new MorphologicFilterSettings(morphChoice, matrixSize);
				MorphologicFilter.morph(imageProcessor, filterSettings);
			}else {
				JOptionPane.showMessageDialog(null, "Morphologischer Filter abgebrochen.");
			}
		}else {
			
		}
	}
		
}
