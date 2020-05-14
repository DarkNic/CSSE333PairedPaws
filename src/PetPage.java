import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import DB_connect.ConnectionTHHS;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class PetPage extends JComponent {
	/**
	 * @wbp.nonvisual location=73,274
	 */
	ConnectionTHHS con;
	ArrayList<Dog> dogs;
	private ArrayList<Integer> ids;
	int counter;
	int max;
	public static final String exampleString = "<html><div style='text-align: center;'>" + "<html>Age: 5 <br/> "
			+ "Name: R1 <br/>" + "Gender: R2 <br/>" + "Fixed: R3 <br/>" + "Stage: R4 <br/>" + "Intake Date: R6 <br/>"
			+ "Size: R7 <br/>" + "Age: R9 <br/>" + "</div></html>";

	public PetPage(ConnectionTHHS con) {
		init(con);
		getAnimals();
		this.max = ids.size();
		loadNext();
	}

	private void init(ConnectionTHHS con) {
		this.setName("Find Your Furry Friend");
		this.setSize(496, 794);
		this.con = con;
		this.dogs = new ArrayList<Dog>();
		this.ids = new ArrayList<Integer>();
		this.counter = 0;
	}

	private void loadNext() {

		if (counter >= max) {
			counter = 0;
		}
		int curID = ids.get(counter);
		Connection scarlett = con.getConnection();
		String name = null;
		int house = 0;
		int fixed = 0;
		String stage = null;
		String intake = null;
		int gender = 0;
		String age = null;
		String size = null;
		try {
			Statement state = scarlett.createStatement();
			ResultSet rs = state.executeQuery("Select animalID, houseTrained,"
					+ "fixed, stage, petName, intakeDate, gender, size, age From Pet Where " + "animalID =  " + curID);
			while (rs.next()) {
				name = rs.getString("petName");
				house = rs.getInt("houseTrained");
				fixed = rs.getInt("fixed");
				stage = rs.getString("stage");
				intake = rs.getString("intakeDate");
				gender = rs.getInt("gender");
				age = rs.getString("age");
				size = rs.getString("size");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		counter++;
		System.out.println(name + house + fixed + stage + intake + gender + age);
		try {
			makeGUI(curID, name, house, fixed, stage, intake, gender, age, size);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void makeGUI(int curID, String name, int house, int fixed, String stage, String intake, int gender,
			String age, String size) throws IOException {

		JPanel doggy2 = new ImagePanel(curID);
		this.add(doggy2);
		doggy2.setBounds(25, 50, 450, 421);

		JButton wishButton = new JButton("Add to Wish List");
		this.add(wishButton);
		wishButton.setBounds(357, 10, 118, 38);

		JButton rightNextButton = new JButton(">");
		rightNextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadNext();
			}
		});
		this.add(rightNextButton);
		rightNextButton.setBounds(441, 619, 45, 45);

		JButton leftBackButton = new JButton("<");
		this.add(leftBackButton);
		leftBackButton.setBounds(10, 619, 45, 45);

		leftBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				counter -= 2;
				if (counter < 0) {
					counter = max -1;
				}
				loadNext();
			}
		});

		String toInsert = exampleString.replaceFirst("R1", name);
		String newy = toInsert.replaceFirst("R2", String.valueOf(gender));
		String scarlett = newy.replaceFirst("R3", String.valueOf(fixed));
		String blackWidow = scarlett.replaceFirst("R4", stage);
		String glock = blackWidow.replaceFirst("R6", intake);
		String cap = glock.replaceFirst("R7", size);
		String hope = cap.replaceFirst("R9", age);

		JLabel bioOfAnimal = new JLabel(hope);
		bioOfAnimal.setOpaque(true);

		bioOfAnimal.setBackground(Color.RED);
		add(bioOfAnimal);
		bioOfAnimal.setBounds(154, 494, 173, 300);
		bioOfAnimal.setFont(new Font("Verdana", 1, 15));

		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	}

	private void getAnimals() {
		Connection scarlett = con.getConnection();
		try {
			Statement state = scarlett.createStatement();
			ResultSet rs = state.executeQuery("Select AnimalID From Dog");
			while (rs.next()) {
				ids.add(rs.getInt("AnimalID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(ids);
	}

	public static class ImagePanel extends JPanel {

		private BufferedImage image;

//might add a second constructor that resizes as well as draws. FYI, this takes too long
		public ImagePanel(int animalID) {

			String filepath = ("images/" + animalID + ".png");

			try {
				image = ImageIO.read(new File(filepath));

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

	private void setWindowBar() {
		JMenuBar menu = new JMenuBar();
		JMenu HomePage = new JMenu("Home");
		JMenu wishList = new JMenu("Wish List");
		JMenu account = new JMenu("Account");
		JMenuItem personalProfile = new JMenuItem("My Profile");
		JMenuItem settings = new JMenuItem("Settings");
		JMenuItem logOut = new JMenuItem("Log Out");
		account.add(personalProfile);
		account.add(settings);
		account.add(logOut);
		menu.add(HomePage);
		menu.add(wishList);
		menu.add(account);
	}
//	public static class ImagePants extends JPanel {
//
//		private BufferedImage image;
//		private int id;
//
//		ImagePants(int id) {
//			this.id = id;
//			String sid = ("/images/" + id + ".png");
//			try {
//				image = ImageIO.read(new File(sid));
//			} catch (Exception ex) {
//				System.out.println("Image did not load.");
//			}
//		}
//
//		public ImagePants(String string) {
//			try {
//				image = ImageIO.read(new File(string));
//				System.out.println("Image loaded.");
//			} catch (Exception ex) {
//				System.out.println("Image did not load. String");
//			}
//		}
//
//		public static BufferedImage resize(BufferedImage img, int newW, int newH) {
//			Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
//			BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
//
//			Graphics2D g2d = dimg.createGraphics();
//			g2d.drawImage(tmp, 0, 0, null);
//			g2d.dispose();
//
//			return dimg;
//		}
//
//		@Override
//		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
//		}
}
