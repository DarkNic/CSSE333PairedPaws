import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		// use the connection to load in the information of the person unless they are
		// stored somewhere else
		// this.person=con...(Select person. Add whatever extra parameter you need to
		// get the user)
	}

	private void init(ConnectionTHHS con) {
		this.setName("User Profile");
		this.setSize(1000, 250);
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
		JPanel panel = new JPanel();
		// Add all the necessary info about the person into a appropriate view
		ContactInformation info = this.user.getContact();
		String generalInfo = "<html> Name: " + this.user.getName() + " <br/>" + "Phone Number: " + info.getPhone()
				+ " <br/>" + " Email Address: " + info.getEmail() + " <br/>" + "Address: " + info.getAddress()
				+ " <br/>" + "Zip code: " + info.getZip() + "</html>";
		JLabel infoLabel = new JLabel(generalInfo);
		panel.add(userLabel);
		panel.add(infoLabel);
		this.add(panel);
		adminButtons(panel);
	}

	private void adminButtons(JPanel pan) {
		JButton deleteAccountButton = new JButton("Delete Account");
		deleteAccountButton.setBounds(500, 500, 50, 50);
		JButton editContactInfoButton = new JButton("Edit Contact Information");
		editContactInfoButton.setBounds(600, 500, 50, 50);
		JButton prefButton = new JButton("Start a New Search");
		prefButton.setBounds(800, 500, 50, 50);
		Connection connection = con.getConnection();
		deleteAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame delFrame = new JFrame();
				delFrame.setSize(300, 100);
				JPanel panel2 = new JPanel();
				JLabel use = new JLabel("Are you sure you want to delete your account?");
				JButton deleteButton = new JButton("Delete");
				JButton cancelButton = new JButton("Cancel");
				panel2.add(use);
				panel2.add(deleteButton);
				panel2.add(cancelButton);
				delFrame.add(panel2);
				delFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				delFrame.setVisible(true);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						delFrame.dispose();
					}
				});
				deleteButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							CallableStatement state = connection.prepareCall("{call remove_Person(?)}");
							state.setString(1, Main.loggedUser);
							state.execute();
							Main.loggedUser = "";
							state.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						delFrame.setVisible(false);
						UserFrame.this.dispose();
					}
				});
			}
		});
		editContactInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame editFrame = new JFrame();
				editFrame.setSize(700, 100);
				JPanel panel3 = new JPanel();
				JLabel phone = new JLabel("Phone");
				JTextField textbox2 = new JTextField("i.e. 239-129-2849", 15);
				JLabel em = new JLabel("Email");
				JTextField textbox3 = new JTextField("i.e. sample@gmail.com", 15);
				JLabel addr = new JLabel("Address");
				JTextField textbox4 = new JTextField("i.e. 51st terre haute, IN", 15);
				JLabel zip = new JLabel("Zipcode");
				JTextField textbox5 = new JTextField("i.e. 47803", 10);
				JButton editButton = new JButton("Edit");
				panel3.add(phone);
				panel3.add(textbox2);
				panel3.add(em);
				panel3.add(textbox3);
				panel3.add(addr);
				panel3.add(textbox4);
				panel3.add(zip);
				panel3.add(textbox5);
				panel3.add(editButton);
				editFrame.add(panel3);
				editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				editFrame.setVisible(true);
				editButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String text2 = textbox2.getText();
						String text3 = textbox3.getText();
						String text4 = textbox4.getText();
						String text5 = textbox5.getText();
						try {
							CallableStatement state = connection.prepareCall("{call edit_Contact( ?,?, ?, ?, ?)}");
							state.setString(1, Main.loggedUser);
							state.setString(2, text2);
							state.setString(3, text3);
							state.setString(4, text4);
							state.setString(5, text5);
							state.execute();
							state.close();
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
				sampleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				sampleFrame.setVisible(true);
			}
		});
		deleteAccountButton.setBounds(600, 500, 50, 50);
		editContactInfoButton.setBounds(700, 500, 50, 50);
		prefButton.setBounds(800, 500, 50, 50);
		pan.add(deleteAccountButton);
		pan.add(editContactInfoButton);
		pan.add(prefButton);
		this.add(pan);
	}
}