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
import javax.swing.WindowConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
	private JTextField nameCard;
	private JTextField codeZip;

	public CreditCardPage(Connection con2, int animalID) {
		this.curID = animalID;
		init(con2);
	}

	private void init(Connection con2) {
		this.setName("Matched!");
		this.setSize(496, 515);
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
		enterNumLab.setBounds(95, 150, 273, 22);
		add(enterNumLab);

		cardNum = new JPasswordField();
		cardNum.setBounds(22, 173, 445, 34);
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
		lblMonth.setBounds(83, 246, 135, 22);
		add(lblMonth);

		expMonth = new JPasswordField();
		expMonth.setBounds(83, 267, 135, 34);
		add(expMonth);

		expYear = new JPasswordField();
		expYear.setBounds(251, 267, 135, 34);
		add(expYear);

		JLabel lblExpYear = new JLabel("Experation Year");
		lblExpYear.setVerticalAlignment(SwingConstants.TOP);
		lblExpYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpYear.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblExpYear.setBounds(251, 246, 135, 22);
		add(lblExpYear);

		cvcNummy = new JPasswordField();
		cvcNummy.setBounds(83, 359, 135, 34);
		add(cvcNummy);

		JLabel lblCvcNumber = new JLabel("CVC Number");
		lblCvcNumber.setVerticalAlignment(SwingConstants.TOP);
		lblCvcNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblCvcNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblCvcNumber.setBounds(83, 334, 135, 22);
		add(lblCvcNumber);

		JButton btnNewButton = new JButton("Process Transaction");
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnNewButton.setBounds(22, 430, 445, 51);
		add(btnNewButton);

		JLabel zipCode = new JLabel("Zip Code");
		zipCode.setVerticalAlignment(SwingConstants.TOP);
		zipCode.setHorizontalAlignment(SwingConstants.CENTER);
		zipCode.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		zipCode.setBounds(251, 334, 135, 22);
		add(zipCode);

		JLabel nameOnCard = new JLabel("Name on Card");
		nameOnCard.setVerticalAlignment(SwingConstants.TOP);
		nameOnCard.setHorizontalAlignment(SwingConstants.CENTER);
		nameOnCard.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		nameOnCard.setBounds(95, 69, 273, 22);
		add(nameOnCard);

		nameCard = new JTextField();
		nameCard.setBounds(25, 96, 442, 34);
		add(nameCard);
		nameCard.setColumns(10);

		codeZip = new JTextField();
		codeZip.setColumns(10);
		codeZip.setBounds(261, 359, 125, 34);
		add(codeZip);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = validateCreditCard();
				if (!valid) {
					JOptionPane.showMessageDialog(getParent(),
							"Invalid Credit Card Information. " + "" + "Error Number: 1159997114108101116116", "Error ",
							JOptionPane.ERROR_MESSAGE);
				} else {
				}
			}
		});

	}

	public boolean validateCreditCard() {
		/**
		 * This method is used to validate all information given into credit card. It
		 * uses a regex to validate name, luhn's alogrithm to verify number, and other
		 * various length checks.
		 * 
		 * @return boolean on whether the info entered is valid.
		 */
		if (!nameCard.getText().matches(("([A-Za-z])+( [A-Za-z]+)"))) {
			return false;
		}

		if (!codeZip.getText().matches("^[0-9]*$")) {
			return false;
		}

		char[] card = cardNum.getPassword();
		ArrayList<Integer> cardNum = new ArrayList<Integer>();

		for (int r = 0; r < card.length; r++) {
			cardNum.add(Integer.parseInt(String.valueOf(card[r])));
		}

		if (card.length < 13 || card.length > 16) {
			return false;
		}
		if (codeZip.getText().length() != 5) {
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
			if (expMonth.getPassword().length != 2) {
				return false;
			}
			if (expYear.getPassword().length != 4) {
				return false;
			}
			if (cvcNummy.getPassword().length < 2 || cvcNummy.getPassword().length > 6) {
				return false;
			}
			return true;
		} else {
			return false;
		}

	}
}
