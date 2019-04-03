package abschlussprojekt.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;

public class FilePicker extends JDialog {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 3488427228645202995L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FilePicker(List<ImagePlus> images, File[] path) {
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 520);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		if(path[0] == null) {
			System.out.println("Path is null");
		}else {
			System.out.println(path[0].getAbsolutePath());
		}
		JFileChooser fileChooser = new JFileChooser(path[0]);
		fileChooser.addActionListener(new FileActionListener(this, fileChooser, images, path));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		contentPane.add(fileChooser, BorderLayout.CENTER);
	}
	
	

}
