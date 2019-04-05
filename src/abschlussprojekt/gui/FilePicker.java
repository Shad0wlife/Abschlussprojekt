package abschlussprojekt.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ij.ImagePlus;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.List;

public class FilePicker extends JDialog {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 3488427228645202995L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FilePicker(List<ImagePlus> images, File[] defaultOpenPathPointer) {
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 520);
		setTitle("Bitte einen Ordner mit den Zielbildern w\u00E4hlen.");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		//DEBUG
		if(defaultOpenPathPointer[0] == null) {
			System.out.println("Path is null");
		}else {
			System.out.println(defaultOpenPathPointer[0].getAbsolutePath());
		}
		JFileChooser fileChooser = new JFileChooser(defaultOpenPathPointer[0]);
		fileChooser.addActionListener(new FileActionListener(this, fileChooser, images, defaultOpenPathPointer));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		contentPane.add(fileChooser, BorderLayout.CENTER);
	}
	
	

}