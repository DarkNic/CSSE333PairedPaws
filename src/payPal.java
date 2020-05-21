import java.awt.Color;
import java.awt.Desktop;
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
	private JPasswordField cardNum;
	private JTextField nameCard;

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
		JLabel labelPass = new JLabel("PayPal Password");
		labelPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelPass.setHorizontalAlignment(SwingConstants.CENTER);
		labelPass.setVerticalAlignment(SwingConstants.TOP);
		labelPass.setBounds(95, 150, 273, 22);
		add(labelPass);

		cardNum = new JPasswordField();
		cardNum.setBounds(22, 173, 445, 34);
		add(cardNum);

		JLabel pageTitle_1 = new JLabel("Enter Credit Card Info");
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

		nameCard = new JTextField();
		nameCard.setBounds(25, 96, 442, 34);
		add(nameCard);
		nameCard.setColumns(10);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getParent(), "Success, pick up your pet within 3 days.");
			}
		});

	}

}
