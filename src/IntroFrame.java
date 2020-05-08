import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javax.swing.border.Border;


public class IntroFrame extends JFrame{

	public IntroFrame(String name, ArrayList<Animal> petProfiles) {
		
	}
	
	public IntroFrame() {
		this.setTitle("PetPage");
		
		
		JMenuBar menu=new JMenuBar();
		JMenu HomePage=new JMenu("Home");
		JMenu wishList=new JMenu("Wish List");
		JMenu account=new JMenu("Account");
		JMenuItem personalProfile=new JMenuItem("My Profile");
		JMenuItem settings=new JMenuItem("Settings");
		JMenuItem logOut=new JMenuItem("Log Out");
		account.add(personalProfile);
		account.add(settings);
		account.add(logOut);
		menu.add(HomePage);
		menu.add(wishList);
		menu.add(account);
		
		this.setSize(500, 1000);
		this.setLayout(null);
		this.setJMenuBar(menu);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		
		JPanel doggy2=new ImagePanel(2);
		this.add(doggy2);
		doggy2.setBounds(25, 50,450,500);
		
		JLabel dogName= new JLabel("Mike");
		dogName.setFont(new Font("Verdana",1, 15));
		this.add(dogName);
		dogName.setBounds(200, 550, 50, 30);
		
		//I will need to put together a program that will make a new string from the input. 
		//It will count up to the nearest space to the width of the bio and insert a <br/> at that point.
		//Then they will continue to loop through the string
		JLabel bioOfAnimal= new JLabel(("<html><div style='text-align: center;'>"+"<html>Age: 5 <br/> "
				+ "Gender: Male <br/>"
				+ "etc:...<br/>"
				+ "Bio:<br/>Hi there! My name is ruff and I am adorable. That's all you need to know.</html>"+"</div></html>"));
		bioOfAnimal.setFont(new Font("Verdana",1, 15));
		this.add(bioOfAnimal);
		bioOfAnimal.setBounds(50, 575, 350, 140);
		
		JButton wishButton = new JButton("Add to Wish List");
		this.add(wishButton);
		wishButton.setBounds(350, 0, 130, 50);
		
		JButton rightNextButton = new JButton(">");
		this.add(rightNextButton);
		rightNextButton.setBounds(450, 550, 45, 45);
		
		JButton leftBackButton = new JButton("<");
		this.add(leftBackButton);
		leftBackButton.setBounds(0, 550, 45, 45);
		
		JPanel check=new ImagePanel(4);
		this.add(check);
		check.setBounds(250, 750,150,150);
		
		
		JPanel xMark=new ImagePanel(5);
		this.add(xMark);
		xMark.setBounds(25, 750,150,150);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        bioOfAnimal.setBorder(border);
		
	}

	
	
	public static class ImagePanel extends JPanel{

	    private BufferedImage image;
//might add a second constructor that resizes as well as draws. FYI, this takes too long
	    public ImagePanel(int i) {
	       String dogNumber="";
	       if(i==1) {
	    	dogNumber="dog-1210559_640.jpg";   
	       }
	       if(i==2) {
	    	dogNumber="dog-2029214_640.jpg";   
	       }
	       if(i==3) {
	    	dogNumber="dog-4372036_640.jpg";   
	       }
	       if(i==4) {
	    	dogNumber="checkmark-png-25954.png";   
	       }
	       if(i==5) {
	    	dogNumber="XMark.png";   
	       }
	    	try {                
	          image = ImageIO.read(new File(dogNumber));
	          if(i==5) {
	        	  image=resize(image, 150,150);
	          }
	          if(i==4) {
	        	  image=resize(image, 150,150);
	          }
	       } catch (IOException ex) {
	            // handle exception...
	       }
	    }
	    

	    
	    
	    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	        Graphics2D g2d = dimg.createGraphics();
	        g2d.drawImage(tmp, 0, 0, null);
	        g2d.dispose();

	        return dimg;
	    }  
	    
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
	    }

	
}
}
	
