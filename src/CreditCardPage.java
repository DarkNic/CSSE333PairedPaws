import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class CreditCardPage extends JComponent {
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
	private JPasswordField cardNum;
	private JPasswordField expMonth;
	private JPasswordField expYear;
	private JPasswordField cvcNummy;

	public CreditCardPage(Connection con2, int animalID) {
		this.curID = animalID;
		init(con2);
	}

	private void init(Connection con2) {
		this.setName("Matched!");
		this.setSize(496, 432);
		this.con = con2;
		this.loggedUser = Main.loggedUser;
		this.con = con2;
		loadNext();
	}

	private void loadNext() {
		JLabel enterNumLab = new JLabel("Card Number");
		enterNumLab.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		enterNumLab.setHorizontalAlignment(SwingConstants.CENTER);
		enterNumLab.setVerticalAlignment(SwingConstants.TOP);
		enterNumLab.setBounds(95, 79, 273, 22);
		add(enterNumLab);

		cardNum = new JPasswordField();
		cardNum.setBounds(22, 102, 445, 34);
		add(cardNum);

		JLabel pageTitle_1 = new JLabel("Enter Credit Card Info");
		pageTitle_1.setVerticalAlignment(SwingConstants.TOP);
		pageTitle_1.setHorizontalAlignment(SwingConstants.CENTER);
		pageTitle_1.setFont(new Font("Segoe UI", Font.BOLD, 24));
		pageTitle_1.setBounds(95, 15, 273, 54);
		add(pageTitle_1);

		JLabel lblMonth = new JLabel("Experation Month");
		lblMonth.setVerticalAlignment(SwingConstants.TOP);
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonth.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblMonth.setBounds(83, 175, 135, 22);
		add(lblMonth);

		expMonth = new JPasswordField();
		expMonth.setBounds(83, 196, 135, 34);
		add(expMonth);

		expYear = new JPasswordField();
		expYear.setBounds(251, 196, 135, 34);
		add(expYear);

		JLabel lblExpYear = new JLabel("Experation Year");
		lblExpYear.setVerticalAlignment(SwingConstants.TOP);
		lblExpYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpYear.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblExpYear.setBounds(251, 175, 135, 22);
		add(lblExpYear);

		cvcNummy = new JPasswordField();
		cvcNummy.setBounds(167, 282, 135, 34);
		add(cvcNummy);

		JLabel lblCvcNumber = new JLabel("CVC Number");
		lblCvcNumber.setVerticalAlignment(SwingConstants.TOP);
		lblCvcNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblCvcNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblCvcNumber.setBounds(167, 261, 135, 22);
		add(lblCvcNumber);

		JButton btnNewButton = new JButton("Process Transaction");
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnNewButton.setBounds(22, 358, 445, 51);
		add(btnNewButton);
		System.out.println("Curry: " + curID);
		init(con);
		loadNext();

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = validateCreditCard();
				if (!valid) {
					JFrame error = new JFrame();
					JOptionPane.showMessageDialog(error,
							"Invalid Credit Card Number. " + "" + 
					"Error Number 1159997114108101116116", "Error ",
							JOptionPane.ERROR_MESSAGE);
					error.setVisible(true);
				}
			}
		});

	}

	public boolean validateCreditCard() {

		char[] card = cardNum.getPassword();
		ArrayList<Integer> cardNum = new ArrayList<Integer>();

		for (int r = 0; r < card.length; r++) {
			cardNum.add(Integer.parseInt(String.valueOf(card[r])));
		}

		if (card.length < 13 || card.length > 16) {
			return false;
		}

		for (int f = 0; f < cardNum.size(); f++) {
			int temp = cardNum.get(f);
			if (f % 2 == 0) {
				cardNum.set(f, temp * 2);
			}
		}

		for (int f = 0; f < cardNum.size(); f++) {
			int temp = cardNum.get(f);
			if (temp > 9) {
				int first = temp / 10;
				int second = temp % 10;
				cardNum.set(f, first + second);
			}
		}
		int sum = 0;
		for (int u : cardNum) {
			sum += u;
		}

		if (sum % 10 == 0) {
			return true;
		} else {
			return false;
		}

	}
}
