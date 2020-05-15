
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
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import DB_connect.ConnectionTHHS;

public class UserFrame extends JFrame {

	ConnectionTHHS con;

	private String userName;

	private Person user;

	public UserFrame(String userName, ConnectionTHHS con) {

		this.userName = userName;
		this.user = new Person();
		this.user.setUserName(userName);

		init(con);

		loadUser();

		createFrame();

//use the connection to load in the information of the person unless they are stored somewhere else

//this.person=con...(Select person. Add whatever extra parameter you need to get the user)

	}

	private void init(ConnectionTHHS con) {

		this.setName("User Profile");

		this.con = con;

	}

	public void loadUser() {

		Connection connection = con.getConnection();

		try {

			CallableStatement state = connection.prepareCall("{call get_User_info(?)}");

			state.setString(1, this.userName);

			ResultSet rs = state.executeQuery();

			while (rs.next()) {

				this.user.setName(rs.getString("name"));

				this.user.setContactInfo(rs.getString("phone"), rs.getString("email"), rs.getString("streetAddress"),
						rs.getString("zipcode"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	private void createFrame() {

		JLabel userLabel = new JLabel(this.userName);

//Add all the necessary info about the person into a appropriate view

		ContactInformation info = this.user.getContact();

		String generalInfo = "<html> Name: " + this.userName + " <br/>"

				+ "Phone Number: " + info.getPhone() + " <br/>"

				+ " Email Address: " + info.getEmail() + " <br/>"

				+ "Address: " + info.getAddress() + " <br/>"

				+ "Zip code: " + info.getZip() + "</html>";

		JLabel infoLabel = new JLabel(generalInfo);

		JButton queryButton = new JButton("Start a new search");

		queryButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

// TODO Auto-generated method stub

				SearchFrame newsearch = new SearchFrame();

				newsearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				newsearch.setVisible(true);
			}

		});

		getContentPane().add(userLabel);

		getContentPane().add(infoLabel);

		getContentPane().add(queryButton);

		adminButtons();

	}

	private void adminButtons() {

		JButton deleteAccountButton = new JButton("Delete Account");

		JButton editContactInfoButton = new JButton("Edit Contact Information");

		JButton prefButton = new JButton("Preferences");

		Connection connection = con.getConnection();

		deleteAccountButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				JFrame delFrame = new JFrame();

				JLabel use = new JLabel("UserName");

				JTextField textbox = new JTextField("i.e. Mason");

				JButton deleteButton = new JButton("Delete");

				delFrame.getContentPane().add(use);

				delFrame.getContentPane().add(textbox);

				delFrame.getContentPane().add(deleteButton);

				delFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				delFrame.setVisible(true);

				deleteButton.addActionListener(new ActionListener() {

					@Override

					public void actionPerformed(ActionEvent e) {

						String text = textbox.getText();

						try {

							CallableStatement state = connection.prepareCall("{call remove_Person(?)}");

							state.setString(1, text);

							state.executeQuery();

						} catch (Exception e2) {

							e2.printStackTrace();

						}

						delFrame.setVisible(false);

					}

				});

			}

		});

		editContactInfoButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				JFrame editFrame = new JFrame();

				JLabel use = new JLabel("UserName");

				JTextField textbox = new JTextField("i.e. Mason");

				JLabel phone = new JLabel("Phone");

				JTextField textbox2 = new JTextField("i.e. 239-129-2849");

				JLabel em = new JLabel("Email");

				JTextField textbox3 = new JTextField("i.e. sample@gmail.com");

				JLabel addr = new JLabel("Address");

				JTextField textbox4 = new JTextField("i.e. 51st terre haute, IN");

				JLabel zip = new JLabel("Zipcode");

				JTextField textbox5 = new JTextField("i.e. 47803");

				JButton editButton = new JButton("Edit");

				editFrame.getContentPane().add(use);

				editFrame.getContentPane().add(textbox);

				editFrame.getContentPane().add(phone);

				editFrame.getContentPane().add(textbox2);

				editFrame.getContentPane().add(em);

				editFrame.getContentPane().add(textbox3);

				editFrame.getContentPane().add(addr);

				editFrame.getContentPane().add(textbox4);

				editFrame.getContentPane().add(zip);

				editFrame.getContentPane().add(textbox5);

				editFrame.getContentPane().add(editButton);

				editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				editFrame.setVisible(true);

				editButton.addActionListener(new ActionListener() {

					@Override

					public void actionPerformed(ActionEvent e) {

						String text = textbox.getText();

						String text2 = textbox2.getText();

						String text3 = textbox3.getText();

						String text4 = textbox4.getText();

						String text5 = textbox5.getText();

						try {

							CallableStatement state = connection.prepareCall("{call edit_Contact(?, ?, ?, ?, ?)}");

							state.setString(1, text);

							state.setString(2, text2);

							state.setString(3, text3);

							state.setString(4, text4);

							state.setString(5, text5);

							state.executeQuery();

						} catch (Exception e2) {

							e2.printStackTrace();

						}

						editFrame.setVisible(false);

					}

				});

			}

		});

		prefButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

// TODO Auto-generated method stub

				JFrame sampleFrame = new JFrame();

				sampleFrame.setSize(450, 450);

				sampleFrame.getContentPane().setLayout(null);

				sampleFrame.getContentPane().add(new Preferences());

				sampleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				sampleFrame.setVisible(true);

			}

		});

		getContentPane().add(deleteAccountButton);

		getContentPane().add(editContactInfoButton);

		getContentPane().add(prefButton);

	}

}