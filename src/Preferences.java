import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;

public class Preferences extends JComponent {
	public Preferences() {
		setBackground(Color.RED);
		this.setSize(400, 400);

		loadButtons();

	}

	private void loadButtons() {
		JRadioButton newest = new JRadioButton("Newest in First");
		newest.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		newest.setBounds(198, 63, 145, 39);
		add(newest);

		JLabel header = new JLabel("Preferences Page");
		header.setToolTipText("");
		header.setBackground(new Color(255, 0, 0));
		header.setForeground(Color.RED);
		header.setEnabled(false);
		header.setFont(new Font("Segoe UI", Font.BOLD, 23));
		header.setBounds(121, 21, 264, 36);
		add(header);

		JRadioButton dog_Butt = new JRadioButton("Dogs");
		dog_Butt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		dog_Butt.setBounds(51, 98, 145, 39);
		add(dog_Butt);

		JRadioButton inTakeDesc = new JRadioButton("Cats");
		inTakeDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		inTakeDesc.setBounds(51, 63, 145, 39);
		add(inTakeDesc);

		JRadioButton oldest = new JRadioButton("Oldest In FIrst");
		oldest.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		oldest.setBounds(198, 98, 145, 39);
		add(oldest);

		JRadioButton male = new JRadioButton("Male");
		male.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		male.setBounds(51, 159, 145, 39);
		add(male);

		JRadioButton female = new JRadioButton("Female");
		female.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		female.setBounds(51, 200, 145, 39);
		add(female);

		JRadioButton fixed = new JRadioButton("Fixed");
		fixed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		fixed.setBounds(198, 159, 145, 39);
		add(fixed);

		JRadioButton breedable = new JRadioButton("Breedable");
		breedable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		breedable.setBounds(198, 200, 145, 39);
		add(breedable);

		JButton submit = new JButton("Submit");
		submit.setFont(new Font("Segoe UI", Font.BOLD, 18));
		submit.setBounds(25, 245, 360, 44);
		add(submit);

	}
}
