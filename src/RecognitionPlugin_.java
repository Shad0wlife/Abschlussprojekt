import abschlussprojekt.gui.SelectionGui;
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
		SelectionGui gui = new SelectionGui(ip); //Does this chain make sense?
		gui.setVisible(true);
	}
		
}
