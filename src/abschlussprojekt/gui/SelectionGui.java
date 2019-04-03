package abschlussprojekt.gui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import abschlussprojekt.classification.FPC_Derivate;
import abschlussprojekt.util.CircleSize;
import abschlussprojekt.util.Classifier;
import abschlussprojekt.util.GZT;
import ij.process.ImageProcessor;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import abschlussprojekt.classification.DValue;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SelectionGui extends JDialog {

	/**
	 * Generated SerialVersionUID
	 */
	private static final long serialVersionUID = -4612934126745365786L;
	private JPanel contentPane;
	private final ButtonGroup gztGroup = new ButtonGroup();
	private final ButtonGroup classifierGroup = new ButtonGroup();
	private double sliderValue = 0.5;
	/**
	 * Create the frame.
	 */
	public SelectionGui(ImageProcessor ip) {
		setModal(true);
		setTitle("Einstellungen ausw\u00E4hlen");
		setBounds(100, 100, 300, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		//Classifier selection
		JPanel classifierPane = new JPanel();
		classifierPane.setBorder(new EmptyBorder(0, 0, 5, 0));
		classifierPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(classifierPane);
		classifierPane.setLayout(new BoxLayout(classifierPane, BoxLayout.Y_AXIS));
		
		JLabel lblClassifierSelect = new JLabel("Klassifizierer w\u00E4hlen");
		classifierPane.add(lblClassifierSelect);
		
		JRadioButton rdbtnMFPC = new JRadioButton(Classifier.MFPC.name());
		classifierPane.add(rdbtnMFPC);
		classifierGroup.add(rdbtnMFPC);
		
		rdbtnMFPC.setSelected(true);
		
		JRadioButton rdbtnOAMFPC = new JRadioButton(Classifier.OAMFPC.name());
		classifierPane.add(rdbtnOAMFPC);
		classifierGroup.add(rdbtnOAMFPC);
		
		JSeparator separator_0 = new JSeparator();
		contentPane.add(separator_0);
		
		//Classifier Settings
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(settingsPanel);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		
		JLabel lblClassifierExponent = new JLabel("Exponenten f\u00FCr Klassifizierer w\u00E4hlen");
		settingsPanel.add(lblClassifierExponent);
		
		JComboBox<DValue> dComboBox = new JComboBox<>();
		dComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		dComboBox.setModel(new DefaultComboBoxModel<>(DValue.values()));
		settingsPanel.add(dComboBox);
		
		JLabel lblClassifierPCE = new JLabel("Prozentuale Elementarunsch\u00E4rfe w\u00E4hlen");
		settingsPanel.add(lblClassifierPCE);
		
		JSlider pceSlider = new JSlider();
		JLabel lblSliderValue = new JLabel(String.format("PCE = %.2f", sliderValue));
		pceSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sliderValue = pceSlider.getValue()/100.0;
				lblSliderValue.setText(String.format("PCE = %.2f", sliderValue));
				settingsPanel.repaint();
			}
		});
		settingsPanel.add(pceSlider);
		pceSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		settingsPanel.add(lblSliderValue);
		
		JSeparator separator_1 = new JSeparator();
		contentPane.add(separator_1);
		
		//Circle Mask selection
		JPanel maskPane = new JPanel();
		maskPane.setBorder(new EmptyBorder(5, 0, 5, 0));
		maskPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(maskPane);
		maskPane.setLayout(new BoxLayout(maskPane, BoxLayout.Y_AXIS));
		
		JLabel lblMaskSelect = new JLabel("Kreismaske w\u00E4hlen");
		maskPane.add(lblMaskSelect);
		
		JComboBox<CircleSize> comboBox = new JComboBox<>();
		maskPane.add(comboBox);
		comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboBox.setMaximumRowCount(12);
		comboBox.setModel(new DefaultComboBoxModel<>(CircleSize.values()));
		
		JSeparator separator_2 = new JSeparator();
		contentPane.add(separator_2);

		//Algorithm selection
		JPanel gztPane = new JPanel();
		gztPane.setBorder(new EmptyBorder(5, 0, 5, 0));
		gztPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(gztPane);
		gztPane.setLayout(new BoxLayout(gztPane, BoxLayout.Y_AXIS));
		
		JLabel lblGZTSelect = new JLabel("Zirkulartransformationsmatrix w\u00E4hlen");
		gztPane.add(lblGZTSelect);
		
		JRadioButton rdbtnSwt = new JRadioButton(GZT.SWT.name());
		gztPane.add(rdbtnSwt);
		gztGroup.add(rdbtnSwt);
		
		rdbtnSwt.setSelected(true);
		
		JRadioButton rdbtnGzta = new JRadioButton(GZT.GZTA1.name());
		gztPane.add(rdbtnGzta);
		gztGroup.add(rdbtnGzta);
		
		JSeparator separator_3 = new JSeparator();
		contentPane.add(separator_3);
		
		//OK button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new OKActionListener(ip, this, comboBox, gztGroup, classifierGroup, dComboBox, sliderValue));
		contentPane.add(btnOk);
	}

}
