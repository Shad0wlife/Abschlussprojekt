package abschlussprojekt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import abschlussprojekt.classification.DValue;
import abschlussprojekt.classification.FPC_Derivate;
import abschlussprojekt.classification.mfpc.MFPC;
import abschlussprojekt.classification.oamfpc.OAMFPC;
import abschlussprojekt.util.CSVWriter;
import abschlussprojekt.util.CircleSize;
import abschlussprojekt.util.Classifier;
import abschlussprojekt.util.Colorspace;
import abschlussprojekt.util.Debug;
import abschlussprojekt.util.GZT;
import abschlussprojekt.util.MorphologicFilter;
import abschlussprojekt.util.MorphologicFilterSettings;
import abschlussprojekt.util.Util;
import abschlussprojekt.util.math.ImageSpectrum;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class OKActionListener implements ActionListener{

	private List<ImageProcessor> imageProcessors;
	private MorphologicFilterSettings preprocessingSettings;
	private JComboBox<CircleSize> sizeComboBox;
	private JDialog parent;
	private ButtonGroup gztGroup;
	private ButtonGroup classifierGroup;
	private JComboBox<DValue> dComboBox;
	private double pce;
	
	public OKActionListener(List<ImageProcessor> imageProcessors, MorphologicFilterSettings preprocessingSettings, JDialog parent, JComboBox<CircleSize> sizeComboBox, ButtonGroup gztGroup, ButtonGroup classifierGroup, JComboBox<DValue> dComboBox, double pce) {
		this.imageProcessors = imageProcessors;
		this.preprocessingSettings = preprocessingSettings;
		this.parent = parent;
		this.sizeComboBox = sizeComboBox;
		this.gztGroup = gztGroup;
		this.classifierGroup = classifierGroup;
		this.dComboBox = dComboBox;
		this.pce = pce;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		//Get settings from GUI elements
		CircleSize size = (CircleSize)sizeComboBox.getSelectedItem();
		Classifier classifierType = null;
		DValue D = (DValue)dComboBox.getSelectedItem();
		GZT gzt = null;
		
		classifierType = getEnumChoiceFromButtonGroup(Classifier.class, classifierGroup);
		if(classifierType == null) {
			System.err.println("No Classifier chosen! Aborting.");
			return;
		}
		
		gzt = getEnumChoiceFromButtonGroup(GZT.class, gztGroup);
		if(gzt == null) {
			System.err.println("No GZT chosen! Aborting.");
			return;
		}
		
		//DEBUG
		System.out.println("Classifier: " + classifierType.name());
		System.out.println("GZT: " + gzt.name());
		
		//Get classification data from the learning images
		List<List<int[]>> classData = new ArrayList<>();
		File[] defaultOpenPathPointer = {null}; //TODO das muss doch auch schöner ohne das wrapper 1-element array gehen für reference passing
		Colorspace space = Colorspace.BT2020; //TODO Gui picker dafür anstatt es im code zu machen?
		
		do {
			System.out.println("Starting learning data loop."); //DEBUG
			
			List<ImagePlus> imagePluses = Util.getImagesWithDialog(defaultOpenPathPointer);
			
			if(!imagePluses.isEmpty()) {		
				List<int[]> spectrums = this.getSpectrumsFromImagePluses(imagePluses, size, gzt, space, preprocessingSettings);
				classData.add(spectrums);
			}
		}while(checkForNextClass());
		
		//Prepare and do Classification
		FPC_Derivate classifierObj = this.getClassifierInstance(classifierType, D, this.pce, size, classData);
		
		List<int[]> classifications = classifyImages(imageProcessors, classifierObj, size, gzt);
		
		//DEBUG
		Debug.printListOfIntArrays(classifications);
		
		//Save results
		saveDataToCSV(defaultOpenPathPointer[0], classifications);

		this.parent.dispose();
	}
	
	
	/**
	 * Gets the selected {@link JRadioButton} from a {@link ButtonGroup}
	 * @param group The ButtonGroup to return the selection from.
	 * @return The selected JRadioButton
	 */
	private JRadioButton getSelectedButtonFromGroup(ButtonGroup group) {
		Enumeration<AbstractButton> groupElements = group.getElements();
		while(groupElements.hasMoreElements()) {
			JRadioButton button = (JRadioButton)groupElements.nextElement();
			if(button.isSelected()) {
				return button;
			}
		}
		return null;
	}
	
	/**
	 * Gets a list of image spectrums from a List of Images with given settings.
	 * @param imagePluses The list of images to get the spectrums from.
	 * @param size The {@link CircleSize} selection used for the transformation
	 * @param gzt The {@link GZT} selection used for the transformation
	 * @param space The {@link Colorspace} that should be used in case of RGB Images in the selection
	 * @param preprocessingSettings The {@link MorphologicFilterSettings} containing the preprocessing filter choice and the corresponding matrix size given by the user
	 * @return The list of the image spectrums as a List of int[]
	 */
	private List<int[]> getSpectrumsFromImagePluses(List<ImagePlus> imagePluses, CircleSize size, GZT gzt, Colorspace space, MorphologicFilterSettings preprocessingSettings){
		List<int[]> spectrums = new LinkedList<>();
		for (ImagePlus imagePlus : imagePluses) {
			spectrums.add(this.getSpectrumFromImagePlus(imagePlus, size, gzt, space, preprocessingSettings));
		}
		return spectrums;
	}
	
	/**
	 * Gets an image spectrum form an Image with given settings
	 * @param imagePlus The image to get the spectrum from
	 * @param size The {@link CircleSize} selection used for the transformation
	 * @param gzt The {@link GZT} selection used for the transformation
	 * @param space The {@link Colorspace} that should be used in case of RGB Images in the selection
	 * @param preprocessingSettings The {@link MorphologicFilterSettings} containing the preprocessing filter choice and the corresponding matrix size given by the user
	 * @return The image spectrum as an int[]
	 */
	private int[] getSpectrumFromImagePlus(ImagePlus imagePlus, CircleSize size, GZT gzt, Colorspace space, MorphologicFilterSettings preprocessingSettings) {
		ImageProcessor imageProcessor = Util.get8BitImageProcessor(imagePlus, space);
		
		MorphologicFilter.morph(imageProcessor, preprocessingSettings);
		
		return ImageSpectrum.getImageGSpectrum(imageProcessor, size, gzt);
	}
	
	/**
	 * Asks the User whether another class of learning data should be created
	 * @return The user's choice
	 */
	private boolean checkForNextClass() {
		final String[] options = {"Ja.", "Nein."};
		int selected = JOptionPane.showOptionDialog(parent, "Eine weitere Klasse einführen?", "Weitere Klasse?", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(selected == JOptionPane.YES_OPTION) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Opens a user dialog to create the weight vector for the OAMFPC classification
	 * @param size The Circlesize used for feature generation and thus feature vector size.
	 * @return The vector of generated OAMFPC weights as a double[]
	 */
	private double[] getOAMFPCWeights(CircleSize size) {
		double[] weights = new double[size.getSpectrumSize()];
		OAMFPCWeightSlider sliderGui = new OAMFPCWeightSlider(weights);
		sliderGui.setVisible(true);
		return weights;
	}
	
	/**
	 * Requests the user to specify a file to save the classification results to and saves the results.
	 * @param startPath The file path the user dialog should open in by default
	 * @param classifications The classifications to save to the selected file.
	 */
	private void saveDataToCSV(File startPath, List<int[]> classifications) {
		JFileChooser fileSaver = new JFileChooser(startPath);
		switch(fileSaver.showSaveDialog(parent)) {
			case JFileChooser.APPROVE_OPTION:
				File selected = fileSaver.getSelectedFile();
				CSVWriter writer = new CSVWriter(selected, ';'); //TODO character for excel? Potentially with a gui as a picker?
				writer.write(classifications);
				JOptionPane.showMessageDialog(parent, "Ergebnisse gespeichert.");
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Ergebnisse NICHT gespeichert.");
				break;
		}
	}
	
	/**
	 * Gets an instance of a chosen classifier with given settings and given learning data.
	 * @param classifier The type of {@link Classifier} that should be returned
	 * @param D The {@link DValue} that the classifier should use
	 * @param pce The pce the classifier should use (permitted values in [0,1])
	 * @param size The {@link CircleSize} used in the transformation, used for creating weights for OAMFPC
	 * @param learningData The learning data for the Classifier as a list of Lists, with each sub-list containing a learning dataset for one class
	 * @return The created classifier instance, ready for classifying data
	 */
	private FPC_Derivate getClassifierInstance(Classifier classifier, DValue D, double pce, CircleSize size, List<List<int[]>> learningData) {
		switch(classifier) {
			case MFPC:
				return new MFPC(D, pce, learningData);
			case OAMFPC:
				return new OAMFPC(D, pce, learningData, getOAMFPCWeights(size));
			default:
				return null;
		}
	}
	
	/**
	 * Classifies the data from a list of images
	 * @param imageProcessors A list of ImageProcessors containing the image data to be classified
	 * @param classifierObj The Classifier instance as a {@link FPC_Derivate}
	 * @param size The {@link CircleSize} used in feature generation from the image data
	 * @param gzt The {@link GZT} used in feature generation from the image data
	 * @return The classifications of the image data as a List of int[]
	 */
	private List<int[]> classifyImages(List<ImageProcessor> imageProcessors, FPC_Derivate classifierObj, CircleSize size, GZT gzt){
		List<int[]> classifications = new LinkedList<>();
		for(int cnt = 0; cnt < imageProcessors.size(); cnt++) {
			int[] imageSpectrum = ImageSpectrum.getImageGSpectrum(this.imageProcessors.get(cnt), size, gzt);

			classifications.add(classifierObj.classify(imageSpectrum));
		}
		return classifications;
	}
	
	/**
	 * Gets the selected enum value from a ButtonGroup that has RadioButtons corresponding to the options of an enum
	 * @param enumClass The class of the enum to get the values from
	 * @param group The {@link ButtonGroup} containing the selection.
	 * @return The selected enum value
	 */
	private <T extends Enum<T>> T getEnumChoiceFromButtonGroup(Class<T> enumClass, ButtonGroup group){
		return Enum.valueOf(enumClass, this.getSelectedButtonFromGroup(group).getText());
	}
	
}
