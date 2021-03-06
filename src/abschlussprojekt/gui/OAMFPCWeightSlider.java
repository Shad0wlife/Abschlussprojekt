package abschlussprojekt.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.Component;
import javax.swing.event.ChangeListener;

import abschlussprojekt.util.Util;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OAMFPCWeightSlider extends JDialog {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -5891252839326238587L;
	private final JPanel contentPane;
	private double sliderValue = 0.5;
	private OAMFPCWeightSlider self; //used for self referential access from anonymous inner classes

	/**
	 * Create the dialog.
	 */
	public OAMFPCWeightSlider(double[] weights) {
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 380, 150);
		setTitle("Modifikator f�r Gewichtsfunktion");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel settingPanel = new JPanel();
		settingPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
		settingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(settingPanel);
		settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.Y_AXIS));
		
		JLabel lblModifier = new JLabel("Gewichtsmodifikator einstellen:");
		settingPanel.add(lblModifier);
		
		JSlider slider = new JSlider();
		slider.setMaximum(99); //Avoid division by zero caused by default maximum of 100
		settingPanel.add(slider);
		slider.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblModifikator = new JLabel(String.format("Modifikator = %.2f", sliderValue));
		settingPanel.add(lblModifikator);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sliderValue = slider.getValue()/100.0;
				lblModifikator.setText(String.format("Modifikator = %.2f", sliderValue));
				settingPanel.repaint();
			}
		});
		
		JCheckBox flipBox = new JCheckBox("Gewichtsvektor umkehren?");
		flipBox.setSelected(false);
		settingPanel.add(flipBox);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double exponent = sliderValue / (1.0 - sliderValue);
				int size = weights.length;
				
				for(int cnt = 0; cnt < size; cnt++) {
					int idx = cnt + 1;
					//equation proposed by Lohweg et al. at MLRTA 2009
					weights[cnt] = Math.pow(((double)idx)/size, exponent) - Math.pow(((double)cnt)/size, exponent);
				}
				if(flipBox.isSelected()) {
					Util.reverseArray(weights);
				}
				self.dispose();
			}
		});
		contentPane.add(btnOk);
		
		self = this;
	}

}
