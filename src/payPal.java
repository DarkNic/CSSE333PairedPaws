import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class payPal extends JComponent {
	/**
	 * @wbp.nonvisual location=73,274
	 */
	Connection con;
	// had to put these here since actionlisteners can't reference local vars
	int i;
	String s;
	int curID;
	private String loggedUser;
	public static final String exampleString = "<html><div style='text-align: center;'>" + "<html>" + "Name: R1 <br/>"
			+ "Gender: R2 <br/>" + "Fixed: R3 <br/>" + "Stage: R4 <br/>" + "Intake Date: R6 <br/>" + "Size: R7 <br/>"
			+ "Age: R9 <br/>" + "</div></html>";
	private JTextField email;

	public payPal(Connection con2, int animalID) {
		this.curID = animalID;
		init(con2);
	}

	private void init(Connection con2) {
		this.setName("Matched!");
		this.setSize(496, 328);
		this.con = con2;
		this.loggedUser = Main.loggedUser;
		this.con = con2;
		loadNext();
	}

	private void loadNext() {
		JLabel pageTitle_1 = new JLabel("Enter Paypal Info");
		pageTitle_1.setVerticalAlignment(SwingConstants.TOP);
		pageTitle_1.setHorizontalAlignment(SwingConstants.CENTER);
		pageTitle_1.setFont(new Font("Segoe UI", Font.BOLD, 24));
		pageTitle_1.setBounds(95, 15, 273, 54);
		add(pageTitle_1);
		JButton btnNewButton = new JButton("Process Transaction");
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnNewButton.setBounds(22, 233, 445, 51);
		add(btnNewButton);
		JLabel titleEmail = new JLabel("PayPal Email");
		titleEmail.setVerticalAlignment(SwingConstants.TOP);
		titleEmail.setHorizontalAlignment(SwingConstants.CENTER);
		titleEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		titleEmail.setBounds(95, 69, 273, 22);
		add(titleEmail);
		email = new JTextField();
		email.setBounds(25, 96, 442, 34);
		add(email);
		email.setColumns(10);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UserOperations.addPaypal(email.getText());
					UserOperations.addAdoption(Main.loggedUser, payPal.this.curID);
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
				}
				JOptionPane.showMessageDialog(getParent(), "Success, pick up your pet within 3 days.");
			}
		});
	}
}
