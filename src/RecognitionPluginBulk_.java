import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import abschlussprojekt.gui.SelectionGui;
import abschlussprojekt.util.Colorspace;
import abschlussprojekt.util.MorphologicFilter;
import abschlussprojekt.util.MorphologicFilterChoice;
import abschlussprojekt.util.MorphologicFilterSettings;
import abschlussprojekt.util.Util;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class RecognitionPluginBulk_ implements PlugIn {

	private MorphologicFilterSettings filterSettings = new MorphologicFilterSettings(MorphologicFilterChoice.NONE, 0);
	
	@Override
	public void run(String arg) {		
		List<ImagePlus> imagePluses = Util.getImagesWithDialog();
		
		if(!imagePluses.isEmpty()) {			
			List<ImageProcessor> imageProcessors = get8BitImageProcessors(imagePluses);

			preprocessing(imageProcessors);
			
			SelectionGui gui = new SelectionGui(imageProcessors, filterSettings); //Does this chain make sense?
			gui.setVisible(true);
		}else {
			IJ.error("Keine Testbilder ausgewählt!", "Es müssen Testbilder ausgewählt werden, welche klassifiziert werden sollen.");
			return;
		}
	}
	
	/**
	 * Gets 8-Bit greyscale ImageProcessors (ByteProcessors) for all ImagePlus instances passed
	 * @param imagePluses The ImagePlus instances to get 8-Bit ImageProcessors from
	 * @return A list of the 8-Bit ImageProcessors
	 */
	private List<ImageProcessor> get8BitImageProcessors(List<ImagePlus> imagePluses){
		Colorspace colorspace = Colorspace.BT2020; //TODO selection gui?
		List<ImageProcessor> imageProcessors = new LinkedList<>();
		for (ImagePlus imagePlus : imagePluses) {
			ImageProcessor imageProcessor = Util.get8BitImageProcessor(imagePlus, colorspace);
			imageProcessors.add(imageProcessor);
		}
		return imageProcessors;
	}
	
	/**
	 * Requests, saves and runs potential preprocessing filters on the images
	 * @param imageProcessor The images to preprocess
	 */
	private void preprocessing(List<ImageProcessor> imageProcessors) {
		MorphologicFilterChoice morphChoice = Util.getMorphChoice();
		if(morphChoice != MorphologicFilterChoice.NONE) {
			int matrixSize = (int)IJ.getNumber("Wie groß soll die morphologische Matrix sein?", 3); // Input of matrix dimensions
			if(matrixSize != IJ.CANCELED) {
				this.filterSettings = new MorphologicFilterSettings(morphChoice, matrixSize);
				for (ImageProcessor imageProcessor : imageProcessors) {
					MorphologicFilter.morph(imageProcessor, this.filterSettings);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Morphologischer Filter abgebrochen.");
			}
		}else {
			
		}
	}

}
