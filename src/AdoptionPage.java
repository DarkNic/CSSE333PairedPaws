import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javax.swing.border.Border;

public class AdoptionPage extends JComponent {
	/**
	 * @wbp.nonvisual location=73,274
	 */
	Connection con;
	// had to put these here since actionlisteners can't reference local vars
	int i;
	String s;
	int curID;
	public static final String exampleString = "<html><div style='text-align: center;'>" + "<html>" + "Name: R1 <br/>"
			+ "Gender: R2 <br/>" + "Fixed: R3 <br/>" + "Stage: R4 <br/>" + "Intake Date: R6 <br/>" + "Size: R7 <br/>"
			+ "Age: R9 <br/>" + "</div></html>";

	public AdoptionPage(Connection con2, int animalID) {
		this.curID = animalID;
		System.out.println("Curry: " + curID);
		init(con2);
		loadNext();
	}

	private void init(Connection con2) {
		this.setName("Matched!");
		this.setSize(496, 794);
		this.con = con2;
	}

	private void loadNext() {
		Connection scarlett = con;
		String name = null;
		int house = 0;
		int fixed = 0;
		String stage = null;
		String intake = null;
		int gender = 0;
		String age = null;
		String size = null;
		try {
			System.out.println("CurHere: " + curID);
			CallableStatement state = scarlett.prepareCall("{call Get_Specific_Pet_Info(?)}");
			state.setInt(1, curID);
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				name = rs.getString("petName");
				house = rs.getInt("houseTrained");
				fixed = rs.getInt("fixed");
				stage = rs.getString("stage");
				intake = rs.getString("intakeDate").replace("00:00:00.0", "");
				gender = rs.getInt("gender");
				age = rs.getString("age");
				size = rs.getString("size");
			}
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		System.out.println(name + house + fixed + stage + intake + gender + age);
		try {
			makeGUI(name, house, fixed == 1 ? "Yes" : "No", stage, intake, gender == 1 ? "Female" : "Male", age, size);
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	private void makeGUI(String name, int house, String fixed, String stage, String intake, String gender, String age,
			String size) throws IOException {
		i = curID;
		s = name;
		JPanel doggy2 = new ImagePanel(curID);
		this.add(doggy2);
		doggy2.setBounds(136, 30, 204, 208);
		// Making the HTML in this block
		String toInsert = exampleString.replaceFirst("R1", name);
		String newy = toInsert.replaceFirst("R2", String.valueOf(gender));
		String johansson = newy.replaceFirst("R3", String.valueOf(fixed));
		String blackWidow = johansson.replaceFirst("R4", stage);
		String glock = blackWidow.replaceFirst("R6", intake);
		String cap = glock.replaceFirst("R7", size);
		String hope = cap.replaceFirst("R9", age);
		JLabel congrats = new JLabel("Your furry friend");
		congrats.setFont(new Font("Segoe UI", Font.BOLD, 20));
		congrats.setBounds(10, 293, 293, 42);
		add(congrats);
		JLabel match = new JLabel("is waiting on you!");
		match.setFont(new Font("Segoe UI", Font.BOLD, 20));
		match.setBounds(10, 371, 211, 42);
		add(match);
		JButton creditButt = new JButton("Credit Card");
		creditButt.setFont(new Font("Segoe UI", Font.BOLD, 16));
		creditButt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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
				JFrame sampleFrame = new JFrame();
				sampleFrame.setSize(600, 1000);
				sampleFrame.getContentPane().setLayout(null);
				sampleFrame.setJMenuBar(menu);
				sampleFrame.getContentPane().add(new CreditCardPage(con, curID));
				sampleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				sampleFrame.setVisible(true);
				System.out.println("Here");
				
			}
		});
		creditButt.setBounds(37, 621, 184, 53);
		add(creditButt);
		JLabel petName = new JLabel(name);
		petName.setFont(new Font("Segoe UI", Font.BOLD, 20));
		petName.setBounds(10, 334, 211, 42);
		add(petName);
		
		JLabel checkoutTitle = new JLabel("Check Out Now!");
		checkoutTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		checkoutTitle.setBounds(159, 569, 211, 42);
		add(checkoutTitle);
		
		JButton PayPal = new JButton("PayPal");
		PayPal.setFont(new Font("Segoe UI", Font.BOLD, 16));
		PayPal.setBounds(260, 621, 184, 53);
		add(PayPal);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	}

	public static class ImagePanel extends JPanel {
		private BufferedImage image;

		// might add a second constructor that resizes as well as draws. FYI, this takes
		// too long
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
}
