import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.gui.SelectionGui;
import abschlussprojekt.util.Colorspace;
import abschlussprojekt.util.MorphologicFilter;
import abschlussprojekt.util.MorphologicFilterChoice;
import abschlussprojekt.util.Util;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class RecognitionPluginBulk_ implements PlugIn {

	@Override
	public void run(String arg) {		
		List<ImagePlus> imagePluses = Util.getImagesWithDialog();
		
		if(!imagePluses.isEmpty()) {			
			List<ImageProcessor> imageProcessors = get8BitImageProcessors(imagePluses);

			//Preprocessing
			MorphologicFilterChoice morphChoice = Util.getMorphChoice();
			for (ImageProcessor imageProcessor : imageProcessors) {
				MorphologicFilter.morph(imageProcessor, morphChoice);
			}
			
			SelectionGui gui = new SelectionGui(imageProcessors); //Does this chain make sense?
			gui.setVisible(true);
		}else {
			IJ.error("Keine Testbilder ausgewählt!", "Es müssen Testbilder ausgewählt werden, welche klassifiziert werden sollen.");
			return;
		}
	}
	
	private List<ImageProcessor> get8BitImageProcessors(List<ImagePlus> imagePluses){
		Colorspace colorspace = Colorspace.BT2020; //TODO selection gui?
		List<ImageProcessor> imageProcessors = new LinkedList<>();
		for (ImagePlus imagePlus : imagePluses) {
			ImageProcessor imageProcessor = Util.get8BitImageProcessor(imagePlus, colorspace);
			imageProcessors.add(imageProcessor);
		}
		return imageProcessors;
	}

}
