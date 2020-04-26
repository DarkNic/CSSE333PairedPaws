

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import DB_connect.ConnectionTHHS;
 
public class Main {
	private static final int NUMBER_OF_SIMULATIONS = 3;
	private static final int MAX_WIDTH = 600;
	private static final int MAX_HEIGHT = 300;
	private static final Color[] BACKGROUND_COLORS = { Color.RED,
			Color.BLUE, Color.GREEN };
	static ConnectionTHHS con;

	
	//this works, waiting to get guest user
	public static void init() {
		con = new ConnectionTHHS("golem.csse.rose-hulman.edu", "THHS_AS");
		System.out.println(con.connect("THHS30","Password123") ? "Connected!" : "Failed to Connect.");
	}
	/**
	 * Starts the application.
	 * 
	 * @param args
	 *            ignored
	 */
	
	
	public static void main(String[] args) {
		//need to make own menu bar class and figure out the sub menu. Try: https://www.geeksforgeeks.org/java-swing-jmenubar/

//		ArrayList<SimulationPanel> worlds = constructSimulations();
		init();

		
		int index=0;
		ArrayList<Animal>petProfiles=new ArrayList<>();
		petProfiles=createProfiles(new ArrayList<>());
		
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
		
		
		JPanel welcomeBanner =new JPanel();
		JLabel jlabel = new JLabel("Welcome!");
	    jlabel.setFont(new Font("Verdana",1,60));
	    welcomeBanner.add(jlabel);
		
		
		JFrame welcome = new JFrame("PairedPaws");
		welcome.setSize(1000, 1000);
		welcome.setLayout(null);
		welcome.setJMenuBar(menu);

		
		welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcome.setVisible(true);	
		welcome.add(welcomeBanner);
		welcomeBanner.setBounds(300, 100,325,100);
		welcome.repaint();
		
		JPanel doggy1=new ImagePanel(1);
		welcome.add(doggy1);
		doggy1.setBounds(250, 200,500,400);
		
		JLabel randomQuote=new JLabel("We're happy to see you!");
		welcome.add(randomQuote);
		randomQuote.setFont(new Font("Verdana",1,15));
		randomQuote.setBounds(375, 600, 200, 50);
		
		JButton loginButton = new JButton("Login");
		welcome.add(loginButton);
		loginButton.setBounds(300, 750, 150, 75);

		JButton regsiterButton = new JButton("Register");
		welcome.add(regsiterButton);
		regsiterButton.setBounds(500, 750, 150, 75);	
//_________________________________________________________________________________________
		//Profile Page
		
		JFrame profileFrame=new JFrame("UserName");
		profileFrame.setSize(500, 1000);
		profileFrame.setLayout(null);
		profileFrame.setJMenuBar(menu);
		profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		profileFrame.setVisible(true);

		
		JPanel doggy2=new ImagePanel(2);
		profileFrame.add(doggy2);
		doggy2.setBounds(25, 50,450,500);
		
		JLabel dogName= new JLabel(petProfiles.get(index).getName());
		dogName.setFont(new Font("Verdana",1, 15));
		profileFrame.add(dogName);
		dogName.setBounds(200, 550, 50, 30);
		
		//I will need to put together a program that will make a new string from the input. 
		//It will count up to the nearest space to the width of the bio and insert a <br/> at that point.
		//Then they will continue to loop through the string
		JLabel bioOfAnimal= new JLabel(("<html><div style='text-align: center;'>"+"<html>Age: 5 <br/> "
				+ "Gender: Male <br/>"
				+ "etc:...<br/>"
				+ "Bio:<br/>Hi there! My name is ruff and I am adorable. That's all you need to know.</html>"+"</div></html>"));
		bioOfAnimal.setFont(new Font("Verdana",1, 15));
		profileFrame.add(bioOfAnimal);
		bioOfAnimal.setBounds(50, 575, 350, 140);
		
		JButton wishButton = new JButton("Add to Wish List");
		profileFrame.add(wishButton);
		wishButton.setBounds(350, 0, 130, 50);
		
		JButton rightNextButton = new JButton(">");
		profileFrame.add(rightNextButton);
		rightNextButton.setBounds(450, 550, 45, 45);
		
		JButton leftBackButton = new JButton("<");
		profileFrame.add(leftBackButton);
		leftBackButton.setBounds(0, 550, 45, 45);
		
		JPanel check=new ImagePanel(4);
		profileFrame.add(check);
		check.setBounds(250, 750,150,150);
		
		
		JPanel xMark=new ImagePanel(5);
		profileFrame.add(xMark);
		xMark.setBounds(25, 750,150,150);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        bioOfAnimal.setBorder(border);
		
		
		
//		JFrame frame = new BallWorldsFrame(worlds);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);

		}

	private static ArrayList<Animal> createProfiles(ArrayList<Animal> arrayList) {

		CallableStatement getDogs = null;
		ResultSet rs;
		try {
			getDogs = con.getConnection().prepareCall("{call GetInfo()}");
			rs = getDogs.executeQuery();
			
			while(rs.next()) {
				Dog ruff=new Dog(10110);
				ruff.setName(rs.getString("petName"));
				ruff.setAge(rs.getInt("age"));
				ruff.setGender(rs.getString("gender"));
				ruff.setHouseTrained(rs.getBoolean("houseTrained"));
				ruff.setNS(rs.getBoolean("noSmallKids"));
				arrayList.add(ruff);
				//System.out.println(arrayList.get(0));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayList;
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
	/**
	 * This helper method constructs a list of simulation objects that will each
	 * simulate a world of bouncing balls.
	 * 
	 * @return a list of worlds
	 */
	private static ArrayList<SimulationPanel> constructSimulations() {
		ArrayList<SimulationPanel> result = new ArrayList<SimulationPanel>();
		for (int i = 0; i < NUMBER_OF_SIMULATIONS; i++) {
			int width = MAX_WIDTH;
			int height =  MAX_HEIGHT;
			Color c = BACKGROUND_COLORS[i % BACKGROUND_COLORS.length];
			SimulationPanel sp = new SimulationPanel(width, height, c);
			result.add(sp);
		}
		return result;
	}

}
