
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
import java.sql.Statement;
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

public class PetPageTest {
	private static final int NUMBER_OF_SIMULATIONS = 3;
	private static final int MAX_WIDTH = 600;
	private static final int MAX_HEIGHT = 300;
	private static final Color[] BACKGROUND_COLORS = { Color.RED, Color.BLUE, Color.GREEN };
	static ConnectionTHHS con;

	// this works, waiting to get guest user
	public static void init() {
		con = new ConnectionTHHS("golem.csse.rose-hulman.edu", "THHS_AS");
		System.out.println(con.connect("THHS30", "Password123") ? "Connected!" : "Failed to Connect.");
	}

	/**
	 * Starts the application.
	 * 
	 * @param args ignored
	 */

	public static void main(String[] args) {
		// need to make own menu bar class and figure out the sub menu. Try:
		// https://www.geeksforgeeks.org/java-swing-jmenubar/

		// ArrayList<SimulationPanel> worlds = constructSimulations();
		init();

		Connection scarlett = con.getConnection();
		try {
			Statement state = scarlett.createStatement();
			ResultSet rs = state.executeQuery("Select AnimalID From Dog");
			while (rs.next()) {
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

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
		sampleFrame.setLayout(null);
		sampleFrame.setJMenuBar(menu);
//		sampleFrame.add(new PetPage(scarlett));
		sampleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sampleFrame.setVisible(true);

	}
}
