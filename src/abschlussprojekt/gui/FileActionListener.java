package abschlussprojekt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import ij.IJ;
import ij.ImagePlus;

public class FileActionListener implements ActionListener {

	private JDialog parent;
	private JFileChooser fileChooser;
	private List<ImagePlus> images;
	private File[] path;
	
	public FileActionListener(JDialog parent, JFileChooser fileChooser, List<ImagePlus> images, File[] path) {
		this.parent = parent;
		this.fileChooser = fileChooser;
		this.images = images;
		this.path = path;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		File selected;
		
		switch(event.getActionCommand()) {
		case JFileChooser.APPROVE_SELECTION:
			selected = fileChooser.getSelectedFile();
			break;
		case JFileChooser.CANCEL_SELECTION:
			this.parent.dispose();
			return;
		default:
			return;
		}
		
		if(!selected.isDirectory()) {
			return;
		}
		
		path[0] = selected.getParentFile();
		
		File[] contents = selected.listFiles();
		
		for (File file : contents) {
			if(file.isFile()) {
				ImagePlus image = IJ.openImage(file.getAbsolutePath());
				System.out.println("Image " + file.getName() + " opened!");
				if(image != null) {
					this.images.add(image);
				}
			}
		}

		this.parent.dispose();
	}

}
