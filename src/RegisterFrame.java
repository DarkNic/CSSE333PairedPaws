import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterFrame extends JFrame{
	
	public RegisterFrame() {
		this.setSize(150,470);
		this.setLayout(new FlowLayout());
		JLabel userLabel=new JLabel("Username");
		JLabel passLabel= new JLabel("Password");
		JLabel confLabel = new JLabel("Confirm Password");
		JLabel nameLabel = new JLabel("Name");
		JLabel phoneLabel = new JLabel("Phone Number");
		JLabel emailLabel = new JLabel("Email");
		JLabel addrLabel = new JLabel("Address");
		JLabel zipLabel = new JLabel("Zipcode");
		
		JTextField userField=new JTextField(10);
		JTextField passField=new JPasswordField(10);
		JTextField confField=new JPasswordField(10);
		JTextField nameField = new JTextField(10);
		JTextField phoneField=new JTextField(10);
		JTextField emailField=new JTextField(10);
		JTextField addrField=new JTextField(10);
		JTextField zipField=new JTextField(10);
		
		JButton registerButton= new JButton("Register");
		
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(confLabel);
		this.add(confField);
		this.add(nameLabel);
		this.add(nameField);
		this.add(phoneLabel);
		this.add(phoneField);
		this.add(emailLabel);
		this.add(emailField);
		this.add(addrLabel);
		this.add(addrField);
		this.add(zipLabel);
		this.add(zipField);
		
		this.add(registerButton);

		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!passField.getText().equals(confField.getText())) 
					JOptionPane.showMessageDialog(null, "Password Fields must Match");
				
				if(passField.getText().equals(confField.getText()) && 
						attemptRegister(userField.getText(), passField.getText(),
						phoneField.getText(), emailField.getText(), addrField.getText(),
						zipField.getText(), nameField.getText())) {
					closeFrame();
				}
			}

		});
		
	}
	
	private boolean attemptRegister(String uname, String pass, String phone, String email, String addr, String zip, String name) {
		return ApplicationLogin.register(uname, pass, phone, email, addr, zip, name);
	}
	

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
