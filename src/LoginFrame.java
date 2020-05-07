import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.security.auth.UserPrincipal;





public class LoginFrame extends JFrame{

	public LoginFrame() {
		this.setSize(500,500);
		JLabel userLabel=new JLabel("Username");
		JLabel passLabel= new JLabel("Password");
		JTextField userField=new JTextField();
		JTextField passField=new JTextField();
		JButton loginButton= new JButton("Login");
		JLabel failLabel=new JLabel("Username or Password are invalid. Try Again");
		
		loginButton.setBounds(250, 450, 100, 50);

		
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(failLabel);
		this.add(loginButton);
	
		failLabel.setVisible(false);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Login");
				if(attemptLogin(userField.getText(), passField.getText())) {
					//Make a JFrame where a user can access pets
					UserFrame user=new UserFrame(userField.getText());
					closeFrame();
				}
				else {
					//Unhide JLabel under Password
					failLabel.setVisible(true);
				}
			}

		});
		
	}
	
	private boolean attemptLogin(String text, String text2) {

		//Get connection from main, try to compare input login info to the db
		//if the check comes back approved, return true
		//if the check is rejected, return false 
		
		return false;
	}
	

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}

}
