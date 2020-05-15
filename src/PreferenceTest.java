
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

public class PreferenceTest {
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
		
		JFrame sampleFrame = new JFrame();
		sampleFrame.setSize(450, 450);
		sampleFrame.setLayout(null);
		sampleFrame.add(new Preferences());
		sampleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sampleFrame.setVisible(true);

	}
}
