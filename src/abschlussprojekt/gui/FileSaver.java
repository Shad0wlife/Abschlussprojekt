package abschlussprojekt.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FileSaver extends JDialog {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 1135156175205026392L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FileSaver() {
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 520);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		contentPane.add(fileChooser, BorderLayout.CENTER);
	}

}
