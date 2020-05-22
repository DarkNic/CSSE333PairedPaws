import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SearchFrame extends JFrame{

	public SearchFrame() {
		constructFrame();
		
	}

	private void constructFrame() {
		//Make a similar search table to the lab although looking at DBModifyingFrame is pretty close as well		
		JMenuBar menu = new JMenuBar();
		JMenu account = new JMenu("Account");
		JMenu logOut = new JMenu("Log Out");
		menu.add(account);
		menu.add(logOut);
		logOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Close all JFrames and open a new main
				
				////////////////////////////////////////
				}
		});
		
		account.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//set current frame visibility to false and open new account
				UserFrame user=new UserFrame(Main.loggedUser, Main.con);
				user.setVisible(true);
				closeFrame();
			}
		});
		
		this.setSize(1000, 1000);
		String[] entities= new String[] {"None, Person, Cat, Dog"};
		
		JRadioButton addButton=new JRadioButton("Add");
		addButton.setSelected(true);
		JRadioButton deleteButton=new JRadioButton("Delete");
		JRadioButton updateButton=new JRadioButton("Update");

		
	    ArrayList<JRadioButton> group = new ArrayList<JRadioButton>();
	    group.add(addButton);
	    group.add(deleteButton);
	    group.add(updateButton);
	    
	    this.add(addButton);
	    this.add(deleteButton);
	    this.add(updateButton);
	    this.setJMenuBar(menu);
	    
		
		JComboBox<String> entityBox = new JComboBox<>(entities);
		this.add(entityBox);
		
		JButton actionButton=new JButton("Go!");
		actionButton.setSelected(false);
		this.add(actionButton);
		
		actionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String actionString="";
				for(JRadioButton button:group) {
					if(button.isSelected()) {
						actionString=button.getText();
					}
				}
				//Ideally, this inputs the action into a method that calls the proper action. the other approach is to have if statements
				System.out.println("Action confirmed"+actionString);
			}
		});
		
		entityBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!entityBox.getSelectedItem().equals(null)) {
					if(entityBox.getSelectedItem().equals("Person")) {
						createPersonDialogue();
					}
					if(entityBox.getSelectedItem().equals("Cat")) {
						createCatDialogue();
					}
					if(entityBox.getSelectedItem().equals("Dog")) {
						createDogDialogue();
					}
					actionButton.setSelected(true);
					
					}
					else {
						System.out.println("Please select a entity to continue");
					}
			}
		});
		
	}

	
	public void createPersonDialogue() {
		PersonDialouge dialouge=new PersonDialouge();
		this.add((JPanel) dialouge);
	}
	
	public void createCatDialogue() {
		CatDialouge dialouge=new CatDialouge();
		this.add(dialouge);
	}
	
	public void createDogDialogue() {
		DogDialouge dialouge=new DogDialouge();
		this.add(dialouge);
	
	}
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
