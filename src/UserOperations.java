import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserOperations {
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();

	public static boolean deleteAccount(String uname) {
		int ret = 0;
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Remove_Person(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, uname);
			cs.execute();
			ret = cs.getInt(1);
			if (ret != 0)
				throw new SQLException();
			cs.close();
			ret = 0;
			CallableStatement cs2 = Main.con.getConnection().prepareCall("{? = call Remove_Contact(?)}");
			cs2.registerOutParameter(1, Types.INTEGER);
			cs2.setString(2, uname);
			cs2.execute();
			ret = cs2.getInt(1);
			if (ret != 0)
				throw new SQLException();
			cs2.close();
			return true;
		} catch (SQLException e) {
			String error = "An error occurred.";
			if (ret == 1)
				error = "Must be logged in to delete an account";
			JOptionPane.showMessageDialog(null, error);
		}
		return false;
	}

	public static boolean editContact(String uname, String p, String e, String a, String z) {
		int ret = 0;
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Edit_Contact(?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, uname);
			cs.setString(3, p);
			cs.setString(4, e);
			cs.setString(5, a);
			cs.setString(6, z);
			ret = cs.getInt(1);
			if (ret != 0)
				throw new SQLException();
			cs.close();
			return true;
		} catch (SQLException exc) {
			String error = "An error occurred.";
			if (ret == 1)
				error = "Must be logged in to edit contact information";
			JOptionPane.showMessageDialog(null, error);
		}
		return false;
	}

	public static boolean addPaypal(String email) {
		int ret = 0;
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Add_Payment(?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, Main.loggedUser);
			cs.setString(3, "Paypal");
			cs.execute();
			ret = cs.getInt(1);
			if (ret != 0)
				throw new SQLException();
			cs.close();
			CallableStatement cs2 = Main.con.getConnection().prepareCall("{call Add_Paypal(?)}");
			cs2.setString(1, email);
			cs2.execute();
			cs2.close();
			return true;
		} catch (SQLException e) {
			String error = "An error occurred.";
			if (ret == 1)
				error = "Must be logged in to add a payment method";
			JOptionPane.showMessageDialog(null, error);
		}
		return false;
	}

	public static boolean addCredit(String uname, String num, String exp, String cvc, String zip, String oname) {
		int ret = 0;
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Add_Payment(?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, Main.loggedUser);
			cs.setString(3, "Paypal");
			cs.execute();
			ret = cs.getInt(1);
			if (ret != 0)
				throw new SQLException();
			cs.close();
			CallableStatement cs2 = Main.con.getConnection().prepareCall("{call Add_Credit(?,?,?,?,?)}");
			cs2.setString(1, hashCredit(uname, num));
			cs2.setString(2, hashCredit(uname, exp));
			cs2.setString(3, hashCredit(uname, cvc));
			cs2.setString(4, hashCredit(uname, zip));
			cs2.setString(5, hashCredit(uname, oname));
			cs2.execute();
			cs2.close();
			return true;
		} catch (SQLException e) {
			String error = "An error occurred.";
			if (ret == 1)
				error = "Must be logged in to add a payment method";
			JOptionPane.showMessageDialog(null, error);
		}
		return false;
	}

	public static String hashCredit(String uname, String info) {
		uname = uname.toLowerCase();
		KeySpec spec = new PBEKeySpec(info.toCharArray(), dec.decode(uname), 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// e.printStackTrace();
		}
		return enc.encodeToString(hash);
	}
}
