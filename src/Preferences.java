import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Preferences extends JComponent {
	private JRadioButton newest;
	private JComponent header;
	private JRadioButton dog_Butt;
	private JRadioButton cat_Butt;
	private JRadioButton oldest;
	private JRadioButton male;
	private JRadioButton female;
	private JRadioButton fixed;
	private JRadioButton breedable;
	private JButton submit;
	private boolean newbool;
	private boolean dogbool;
	private boolean catbool;
	private boolean oldbool;
	private boolean malebool;
	private boolean fixedbool;
	private boolean femalebool;
	private boolean breedbool;
	private Connection scarlett;

	public Preferences() {
		scarlett = Main.con.getConnection();
		setBackground(Color.RED);
		this.setSize(400, 400);
		loadButtons();

		submit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getBools();

				String query = "exec Get_anIDs_with_prefs, " + boolToString(oldbool) + ", " + boolToString(dogbool)
						+ ", " + boolToString(catbool) + ", " + boolToString(malebool) + ", " + boolToString(femalebool)
						+ ", " + boolToString(fixedbool) + ", " + boolToString(breedbool);
				newWindow(query);
				System.out.println(query);
			}

		});

	}

	private String boolToString(boolean booly) {
		if (booly) {
			return "1";
		} else {
			return "0";
		}
	}

	private void newWindow(String query) {

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
		sampleFrame.add(new PetPage(scarlett, query));
		sampleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sampleFrame.setVisible(true);
	}

	private void getBools() {
		newbool = newest.isSelected();
		oldbool = oldest.isSelected();
		dogbool = dog_Butt.isSelected();
		catbool = cat_Butt.isSelected();
		malebool = male.isSelected();
		femalebool = female.isSelected();
		fixedbool = fixed.isSelected();
		breedbool = breedable.isSelected();

	}

	private void loadButtons() {
		header = new JLabel("Preferences Page");
		header.setToolTipText("");
		header.setBackground(new Color(255, 0, 0));
		header.setForeground(Color.RED);
		header.setEnabled(false);
		header.setFont(new Font("Segoe UI", Font.BOLD, 23));
		header.setBounds(121, 21, 264, 36);
		add(header);

		newest = new JRadioButton("Newest in First");
		newest.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		newest.setBounds(198, 63, 145, 39);
		add(newest);

		dog_Butt = new JRadioButton("Dogs");
		dog_Butt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		dog_Butt.setBounds(51, 98, 145, 39);
		add(dog_Butt);

		cat_Butt = new JRadioButton("Cats");
		cat_Butt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cat_Butt.setBounds(51, 63, 145, 39);
		add(cat_Butt);

		oldest = new JRadioButton("Oldest In FIrst");
		oldest.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		oldest.setBounds(198, 98, 145, 39);
		add(oldest);

		male = new JRadioButton("Male");
		male.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		male.setBounds(51, 159, 145, 39);
		add(male);

		female = new JRadioButton("Female");
		female.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		female.setBounds(51, 200, 145, 39);
		add(female);

		fixed = new JRadioButton("Fixed");
		fixed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		fixed.setBounds(198, 159, 145, 39);
		add(fixed);

		breedable = new JRadioButton("Breedable");
		breedable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		breedable.setBounds(198, 200, 145, 39);
		add(breedable);

		submit = new JButton("Submit");

		submit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		submit.setBounds(25, 245, 360, 44);
		add(submit);

	}
}
