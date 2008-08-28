package gui;

import java.awt.Rectangle;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.LookAndFeel;
import logica.Cliente;

public class WaitingEnemy extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton cancelButton = null;
	private JLabel jLabel = null;
	private String nickName;  //  @jve:decl-index=0:
	private Cliente client;
	private ObjectInputStream input = null;
	private String packet = null;
	private ImageIcon icone = new ImageIcon("src/images/icon.gif");

	/**
	 * @param owner
	 */
	public WaitingEnemy() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		JFrame frame = new JFrame();
	    frame.setIconImage(new ImageIcon("src/images/icon.gif").getImage());
	    JDialog dialog = new JDialog(frame);  //  <-- or whatever your dialog is...
	    dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);
		dialog.setSize(309, 109);
		dialog.setTitle("Waiting Enemy");
		dialog.setContentPane(getJContentPane());
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 7, 275, 16));
			jLabel.setText("Please, wait while your enemy don't show up...");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCancelButton(), null);
			jContentPane.add(jLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cancelButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(107, 40, 73, 26));
			cancelButton.setText("Exit");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return cancelButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"