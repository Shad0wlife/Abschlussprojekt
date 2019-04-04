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
import abschlussprojekt.util.GZT;
import abschlussprojekt.util.Util;
import abschlussprojekt.util.math.ImageSpectrum;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class OKActionListener implements ActionListener{

	private List<ImageProcessor> ips;
	private JComboBox<CircleSize> comboBox;
	private JDialog parent;
	private ButtonGroup gztGroup;
	private ButtonGroup classifierGroup;
	private JComboBox<DValue> dComboBox;
	private double pce;
	
	public OKActionListener(List<ImageProcessor> ips, JDialog parent, JComboBox<CircleSize> cb, ButtonGroup gztGroup, ButtonGroup classifierGroup, JComboBox<DValue> dval, double pce) {
		this.ips = ips;
		this.parent = parent;
		this.comboBox = cb;
		this.gztGroup = gztGroup;
		this.classifierGroup = classifierGroup;
		this.dComboBox = dval;
		this.pce = pce;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		CircleSize size = (CircleSize)comboBox.getSelectedItem();
		Classifier classifier = null;
		DValue D = (DValue)dComboBox.getSelectedItem();
		GZT gzt = null;
		
		System.out.println("OKAction performed");
		Enumeration<AbstractButton> cGroupElements = classifierGroup.getElements();
		while(cGroupElements.hasMoreElements()) {
			JRadioButton button = (JRadioButton)cGroupElements.nextElement();
			if(button.isSelected()) {
				classifier = Classifier.valueOf(button.getText());
			}
			System.out.println("LOOOP");
		}
		
		Enumeration<AbstractButton> gGroupElements = gztGroup.getElements();
		while(gGroupElements.hasMoreElements()) {
			JRadioButton button = (JRadioButton)gGroupElements.nextElement();
			if(button.isSelected()) {
				gzt = GZT.valueOf(button.getText());
			}
		}
		
		if(classifier == null) {
			System.err.println("No Classifier chosen! Aborting.");
			return;
		}
		if(gzt == null) {
			System.err.println("No GZT chosen! Aborting.");
			return;
		}
		
		System.out.println("Classifier: " + classifier.name());
		System.out.println("GZT: " + gzt.name());
		
		List<List<int[]>> classData = new ArrayList<>();
		
		boolean next = false;
		File[] path = {null}; //TODO das muss doch auch schöner ohne das wrapper 1-element array gehen für reference passing

		Colorspace space = Colorspace.BT2020; //TODO Gui picker dafür anstatt es im code zu machen?
		String[] options = {"Ja.", "Nein."};
		
		do {
			System.out.println("Starting learn loop.");
			
			List<ImagePlus> imagePluses = new LinkedList<>();
			List<int[]> spectrums = new LinkedList<>();
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
					int[] imageSpectrum = ImageSpectrum.getImageGSpectrum(imageProcessor, size, gzt);
					spectrums.add(imageSpectrum);
				}
				
				classData.add(spectrums);
			}
			
			int selected = JOptionPane.showOptionDialog(parent, "Eine weitere Klasse einführen?", "Weitere Klasse?", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if(selected == JOptionPane.YES_OPTION) {
				next = true;
			}else {
				next = false;
			}
		}while(next);
		
		FPC_Derivate classifierObj;
		double[] weights;
		OAMFPCWeightSlider sliderGui;
		switch(classifier) {
		case MFPC:
			classifierObj = new MFPC(D, this.pce, classData);
			break;
		case OAMFPC:
			weights = new double[size.getSpectrumSize()];
			sliderGui = new OAMFPCWeightSlider(weights);
			sliderGui.setVisible(true);
			classifierObj = new OAMFPC(D, this.pce, classData, weights);
			break;
		default:
			return;
		}
		
		System.out.println("\r\nTarget Spectrums");
		List<int[]> classifications = new LinkedList<>();
		for(int cnt = 0; cnt < ips.size(); cnt++) {
			int[] ags = ImageSpectrum.getImageGSpectrum(this.ips.get(cnt), size, gzt);

			classifications.add(classifierObj.classify(ags));
		}
		
		Util.printListOfIntArrays(classifications);
		
		JFileChooser fileSaver = new JFileChooser(path[0]);
		switch(fileSaver.showSaveDialog(parent)) {
		case JFileChooser.APPROVE_OPTION:
			File selected = fileSaver.getSelectedFile();
			CSVWriter writer = new CSVWriter(selected);
			writer.write(classifications);
			JOptionPane.showMessageDialog(parent, "Ergebnisse gespeichert.");
			break;
		default:
			JOptionPane.showMessageDialog(parent, "Ergebnisse NICHT gespeichert.");
			break;
		}

		this.parent.dispose();
	}
	
}
