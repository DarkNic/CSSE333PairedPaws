import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DBModifyingFrame  extends JFrame{

	//needs dynamic set up. Dialogues currently only have a jlabel in them but can be easily updated to take input from boxes and call stored procedures
	
	public DBModifyingFrame() {
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
				//System.out.println("Action confirmed"+actionString);
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
						//System.out.println("Please select a entity to continue");
					}
			}
		});
		

		
		
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setVisible(true);
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
	
}
