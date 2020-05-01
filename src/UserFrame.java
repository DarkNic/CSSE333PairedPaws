import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserFrame extends JFrame{

	private String userName;
	private Person user;
	
	public UserFrame(String userName) {
		
		this.userName=userName;
		
		createFrame();
		//use the connection to load in the information of the person unless they are stored somewhere else
		//this.person=con...(Select person. Add whatever extra parameter you need to get the user)
	}

	private void createFrame() {
		JLabel userLabel=new JLabel(this.userName);
		
		//Add all the necessary info about the person into a appropriate view
		
		JButton queryButton=new JButton("Start a new search");
		queryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SearchFrame newsearch=new SearchFrame();
				newsearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				newsearch.setVisible(true);				}
		});
		
		
		this.add(userLabel);
		this.add(queryButton);
	}
	
}
