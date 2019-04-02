import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ij.process.ImageProcessor;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import java.awt.Component;

public class SelectionGui extends JDialog {

	/**
	 * Generated SerialVersionUID
	 */
	private static final long serialVersionUID = -4612934126745365786L;
	private JPanel contentPane;
	private final ButtonGroup gztGroup = new ButtonGroup();
	/**
	 * Create the frame.
	 */
	public SelectionGui(ImageProcessor ip) {
		setModal(true);
		setTitle("Kreismaske ausw\u00E4hlen");
		setBounds(100, 100, 450, 161);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		//Circle selection
		JComboBox<CircleSize> comboBox = new JComboBox<>();
		comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboBox.setMaximumRowCount(12);
		comboBox.setModel(new DefaultComboBoxModel<CircleSize>(CircleSize.values()));
		contentPane.add(comboBox);
		
		//Algorithm selection
		JRadioButton rdbtnSwt = new JRadioButton("SWT");
		gztGroup.add(rdbtnSwt);
		contentPane.add(rdbtnSwt);
		
		JRadioButton rdbtnGzta = new JRadioButton("GZTA1");
		gztGroup.add(rdbtnGzta);
		contentPane.add(rdbtnGzta);
		
		rdbtnSwt.setSelected(true);
		//gztGroup.setSelected(rdbtnSwt.getModel(), true);
		
		//OK button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new OKActionListener(ip, this, comboBox, rdbtnSwt, rdbtnGzta));
		contentPane.add(btnOk);
	}

}
