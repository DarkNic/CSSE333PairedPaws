import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import DB_connect.ConnectionTHHS;
import javax.swing.SwingConstants;

public class Main {
	private static final int NUMBER_OF_SIMULATIONS = 3;
	private static final int MAX_WIDTH = 600;
	private static final int MAX_HEIGHT = 300;
	private static final Color[] BACKGROUND_COLORS = { Color.RED, Color.BLUE, Color.GREEN };
	static ConnectionTHHS con;
	public static String loggedUser = "";

	// this works, waiting to get guest user
	public static void init() {
		String uname = "", pass = "", name = "", server = "";
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			name = prop.getProperty("db.name");
			uname = prop.getProperty("db.user");
			pass = prop.getProperty("db.pass");
			server = prop.getProperty("db.server");
		} catch (IOException ex) {
			// ex.printStackTrace();
		}
		con = new ConnectionTHHS(server, name);
		System.out.println(con.connect(uname, pass) ? "Connected!" : "Failed to Connect.");
	}

	/**
	 * Starts the application.
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		// need to make own menu bar class and figure out the sub menu. Try:
		// https://www.geeksforgeeks.org/java-swing-jmenubar/
		// ArrayList<SimulationPanel> worlds = constructSimulations();
		init();
		JMenuBar menu = new JMenuBar();
		JMenu account = new JMenu("Account");
		JMenu logOut = new JMenu("Log Out");
		menu.add(account);
		menu.add(logOut);
		account.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame=new JFrame();
				JLabel label=new JLabel("Please log into the project to view your account.");
				frame.getContentPane().add(label);
//				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});
		JFrame welcome = new JFrame("PairedPaws");
		welcome.setSize(1000, 1000);
		welcome.getContentPane().setLayout(null);
		welcome.setJMenuBar(menu);
		welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel doggy1 = new ImagePanel(1);
		welcome.getContentPane().add(doggy1);
		doggy1.setBounds(172, 112, 574, 451);
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		welcome.getContentPane().add(loginButton);
		loginButton.setBounds(246, 750, 150, 75);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginFrame fun = new LoginFrame();
				fun.setJMenuBar(menu);
				fun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fun.setVisible(true);
			}
		});
		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		welcome.getContentPane().add(registerButton);
		registerButton.setBounds(500, 750, 150, 75);
		
		JLabel LabelPair = new JLabel("Paired Paws!");
		LabelPair.setHorizontalAlignment(SwingConstants.CENTER);
		LabelPair.setFont(new Font("Segoe UI", Font.BOLD, 43));
		LabelPair.setBounds(246, 41, 404, 61);
		welcome.getContentPane().add(LabelPair);
		
		JLabel lblByLucusBendzsa = new JLabel("By Lucus Bendzsa, Sam Munro, and Nick Bohner");
		lblByLucusBendzsa.setHorizontalAlignment(SwingConstants.CENTER);
		lblByLucusBendzsa.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblByLucusBendzsa.setBounds(172, 611, 574, 61);
		welcome.getContentPane().add(lblByLucusBendzsa);
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RegisterFrame fun = new RegisterFrame();
				fun.setJMenuBar(menu);
				fun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fun.setVisible(true);
			}
		});
		welcome.repaint();
		welcome.setVisible(true);

	}

	private static ArrayList<Animal> createProfiles(ArrayList<Animal> arrayList) {
		CallableStatement getDogs = null;
		ResultSet rs;
		try {
			getDogs = con.getConnection().prepareCall("{call GetDogInfo()}");
			rs = getDogs.executeQuery();
			while (rs.next()) {
				Dog ruff = new Dog(10110);
				ruff.setName(rs.getString("petName"));
				ruff.setAge(rs.getString("age"));
				ruff.setGender(rs.getString("gender"));
				ruff.setHouseTrained(rs.getBoolean("houseTrained"));
				ruff.setNS(rs.getBoolean("noSmallKids"));
				arrayList.add(ruff);
				// System.out.println(ruff.getName());
			}
			getDogs.close();
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return arrayList;
	}

	public static class ImagePanel extends JPanel {
		private BufferedImage image;

		// might add a second constructor that resizes as well as draws. FYI, this takes
		// too long
		public ImagePanel(int i) {
			String dogNumber = "";
			if (i == 1) {
				dogNumber = "dog-1210559_640.jpg";
			}
			if (i == 2) {
				dogNumber = "dog-2029214_640.jpg";
			}
			if (i == 3) {
				dogNumber = "dog-4372036_640.jpg";
			}
			if (i == 4) {
				dogNumber = "checkmark-png-25954.png";
			}
			if (i == 5) {
				dogNumber = "XMark.png";
			}
			try {
				image = ImageIO.read(new File(dogNumber));
				if (i == 5) {
					image = resize(image, 150, 150);
				}
				if (i == 4) {
					image = resize(image, 150, 150);
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
