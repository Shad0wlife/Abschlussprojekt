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

	private List<MorphologicFilterSettings> filterSettings = new LinkedList<MorphologicFilterSettings>();
	
	@Override
	public void run(String arg) {		
		List<ImagePlus> imagePluses = Util.getImagesWithDialog("Bitte den Ordner mit den Testbildern wählen.");
		
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
		do {
			MorphologicFilterChoice morphChoice = Util.getMorphChoice();
			if(morphChoice != MorphologicFilterChoice.NONE) {
				int matrixSize = (int)IJ.getNumber("Wie groß soll die morphologische Matrix sein?", 3); // Input of matrix dimensions
				if(matrixSize != IJ.CANCELED) {
					MorphologicFilterSettings currentSettings = new MorphologicFilterSettings(morphChoice, matrixSize);
					this.filterSettings.add(currentSettings); //add settings to ordered list to reproduce order on learning data
					for (ImageProcessor imageProcessor : imageProcessors) {
						MorphologicFilter.morph(imageProcessor, currentSettings);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Morphologischer Filter abgebrochen und nicht gespeichert.");
				}
			}else {
				break;
			}
		}while(Util.checkForNextPreprocessing());
	}

}
