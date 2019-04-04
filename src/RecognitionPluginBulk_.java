import java.io.File;
import java.util.LinkedList;
import java.util.List;

import abschlussprojekt.gui.FilePicker;
import abschlussprojekt.gui.SelectionGui;
import abschlussprojekt.util.Colorspace;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class RecognitionPluginBulk_ implements PlugIn {

	@Override
	public void run(String arg) {
		File[] path = {null}; //TODO das muss doch auch schöner ohne das wrapper 1-element array gehen für reference passing

		Colorspace space = Colorspace.BT2020; //TODO Gui picker dafür anstatt es im code zu machen?
		
		List<ImagePlus> imagePluses = new LinkedList<>();
		List<ImageProcessor> imageProcessors = new LinkedList<>();
		FilePicker picker = new FilePicker(imagePluses, path);
		picker.setVisible(true);
		
		if(!imagePluses.isEmpty()) {			
			for (ImagePlus imagePlus : imagePluses) {
				ImageProcessor imageProcessor = imagePlus.getProcessor();
				if(!(imageProcessor instanceof ByteProcessor)) {
					if(imageProcessor instanceof ColorProcessor) {
						((ColorProcessor)imageProcessor).setRGBWeights(space.getFactorR(), space.getFactorG(), space.getFactorB());
					}
					imageProcessor = imageProcessor.convertToByteProcessor(true);
					System.out.println("Converted image!");
				}
				imageProcessors.add(imageProcessor);
			}
		}else {
			IJ.error("Keine Testbilder ausgewählt!", "Es müssen Testbilder ausgewählt werden, welche klassifiziert werden sollen.");
			return;
		}
		
		SelectionGui gui = new SelectionGui(imageProcessors); //Does this chain make sense?
		gui.setVisible(true);

	}

}
