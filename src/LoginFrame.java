import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{

	public LoginFrame() {
		this.setSize(200,200);
		this.setLayout(new FlowLayout());
		JLabel userLabel=new JLabel("Username");
		JLabel passLabel= new JLabel("Password");
		JTextField userField=new JTextField(10);
		JTextField passField=new JPasswordField(10);
		JButton loginButton= new JButton("Login");
		
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(loginButton);

		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Login");
				if(attemptLogin(userField.getText(), passField.getText())) {
					UserFrame user=new UserFrame(userField.getText());
					Main.loggedUser = userField.getText();
					user.setVisible(true);
					closeFrame();
				}
			}

		});
		
	}
	
	private boolean attemptLogin(String uname, String pass) {
		return ApplicationLogin.login(uname, pass);
	}
	

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}

}
