import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class PetPage extends JComponent {
	/**
	 * @wbp.nonvisual location=73,274
	 */
	Connection con;
	ArrayList<Dog> dogs;
	private ArrayList<Integer> ids;
	int counter;
	int max;
	// had to put these here since actionlisteners can't reference local vars
	int i;
	String s;
	private Integer curID;
	private String query;
	private Connection scarlett;
	public static final String exampleString = "<html><div style='text-align: center;'>" + "<html>" + "Name: R1 <br/>"
			+ "Gender: R2 <br/>" + "Fixed: R3 <br/>" + "Stage: R4 <br/>" + "Intake Date: R6 <br/>" + "Size: R7 <br/>"
			+ "Age: R9 <br/>" + "</div></html>";

	public PetPage(Connection scarlett, String query) {
		init(scarlett, query);
		getAnimals(query);
		this.max = ids.size();
		loadNext();
	}

	private void init(Connection scarlett, String query) {
		this.scarlett = scarlett;
		this.setName("Find Your Furry Friend");
		// https: //
		// docs.google.com/document/d/1CBohG8vH6jYpjYVnP_w9MS0tSiatB1wPLKmHkh1NUiQ/edit
		this.setSize(496, 794);
		this.con = scarlett;
		this.dogs = new ArrayList<Dog>();
		this.ids = new ArrayList<Integer>();
		this.counter = 0;
		this.query = query;
	}

	private void loadNext() {
		if (counter >= max) {
			counter = 0;
		}
		curID = ids.get(counter);
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
		counter++;
		// System.out.println(name + house + fixed + stage + intake + gender + age);
		try {
			makeGUI(curID, name, house, fixed == 1 ? "Yes" : "No", stage, intake, gender == 1 ? "Male" : "Female", age,
					size);
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	private void closeFrames() {
		((JFrame) this.getParent()).setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void makeGUI(int curID, String name, int house, String fixed, String stage, String intake, String gender,
			String age, String size) throws IOException {
		i = curID;
		s = name;
		JPanel doggy2 = new ImagePanel(curID);
		this.add(doggy2);
		doggy2.setBounds(25, 50, 450, 421);
		JButton wishView = new JButton("View Wish List");
		wishView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WishList(Main.con.getConnection(), "Exec [getWishList] " + Main.loggedUser);
			}
		});
		// Wish Button
		this.add(wishView);
		wishView.setBounds(25, 10, 138, 38);
		JButton rightNextButton = new JButton("LOVE");
		rightNextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdoptionPage(con, curID);
			}
		});
		rightNextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				match();
			}
		});
		this.add(rightNextButton);
		rightNextButton.setBounds(337, 494, 138, 45);
		JButton leftBackButton = new JButton("Not Mine");
		this.add(leftBackButton);
		leftBackButton.setBounds(23, 494, 121, 45);
		leftBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				counter++;
				if (counter >= max) {
					counter = 1;
				}
				loadNext();
			}
		});
		// Making the HTML in this block
		String toInsert = exampleString.replaceFirst("R1", name);
		String newy = toInsert.replaceFirst("R2", String.valueOf(gender));
		String scarlett = newy.replaceFirst("R3", String.valueOf(fixed));
		String blackWidow = scarlett.replaceFirst("R4", stage);
		String glock = blackWidow.replaceFirst("R6", intake);
		String cap = glock.replaceFirst("R7", size);
		String hope = cap.replaceFirst("R9", age);
		JLabel bioOfAnimal = new JLabel(hope);
		bioOfAnimal.setOpaque(true);
		bioOfAnimal.setBackground(new Color(150, 150, 150));
		add(bioOfAnimal);
		bioOfAnimal.setBounds(154, 494, 173, 300);
		bioOfAnimal.setFont(new Font("Verdana", 1, 15));
		JButton wishButton_1 = new JButton("Add to Wish List");
		wishButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (WishListOperations.add(Main.loggedUser, i)) {
					JOptionPane.showMessageDialog(null, s + " added to Wish List!");
				}
			}
		});
		wishButton_1.setBounds(337, 10, 138, 38);
		add(wishButton_1);
		JButton btnResetPreferences = new JButton("Reset Preferences");
		btnResetPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ids.clear();
				getAnimals(null);
				max = ids.size();
			}
		});
		btnResetPreferences.setBounds(173, 10, 154, 38);
		add(btnResetPreferences);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	}

	private void match() {
		Window win = SwingUtilities.getWindowAncestor(this);
		win.dispose();
		JFrame sampleFrame = new JFrame();
		sampleFrame.setSize(600, 1000);
		sampleFrame.getContentPane().setLayout(null);
		sampleFrame.getContentPane().add(new MatchPage(con, curID));
		sampleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		sampleFrame.setVisible(true);
	}

	private void getAnimals(String query) {
		Connection scarlett = con;
		try {
			CallableStatement state = scarlett.prepareCall(query);
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt("AnimalID"));
			}
			if (ids.size() == 0) {
				throw new Exception();
			}
			// System.out.println("Loaded query");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Did not load query");
			try {
				CallableStatement state = scarlett.prepareCall("{call Get_anIDs}");
				ResultSet rs = state.executeQuery();
				while (rs.next()) {
					ids.add(rs.getInt("AnimalID"));
				}
				// System.out.println("Loaded alternate");
			} catch (SQLException r) {
				// r.printStackTrace();
			}
			// System.out.println(ids);
		}
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
