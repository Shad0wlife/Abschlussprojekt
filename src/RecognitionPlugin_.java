import abschlussprojekt.gui.SelectionGui;
import abschlussprojekt.util.MorphologicFilter;
import abschlussprojekt.util.MorphologicFilterChoice;
import abschlussprojekt.util.Util;
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
		//Preprocessing
		MorphologicFilterChoice morphChoice = Util.getMorphChoice();
		MorphologicFilter.morph(ip, morphChoice);
		
		SelectionGui gui = new SelectionGui(ip, morphChoice); //Does this chain make sense?
		gui.setVisible(true);
	}
		
}
