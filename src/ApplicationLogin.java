import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class ApplicationLogin {
	
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	
	public static boolean login(String uname, String pass) {
		uname = uname.toLowerCase();
		CallableStatement getLog;
		ResultSet rs;
		try {
			getLog = Main.con.getConnection().prepareCall("{call infoCheck()}");
			rs = getLog.executeQuery();
			String userChallenge = getLog.getString("username");
			String passChallenge = getLog.getString("password");
			
			if(!uname.equals(userChallenge) || 
					!hashPW(pass, uname).equals(passChallenge))
				throw new SQLException();
			
			getLog.close();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Login Failed");
		}
		
		return false;
	}
	
	public static boolean register(String uname, String pass, String phone, 
			String email, String addr, String zip, String name) {
		uname = uname.toLowerCase();
		String hash = "";
		try {
			hash = hashPW(uname, pass);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Registration Failed");
		}
		int ret = 0;
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Register(?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, uname);
			cs.setString(3, hash);
			cs.setString(4, phone);
			cs.setString(5, email);
			cs.setString(6, addr);
			cs.setString(7, zip);
			cs.execute();
			ret = cs.getInt(1);
			if(ret != 0)
				throw new SQLException();
			cs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			String error = "An error occured";
			if(ret == 1)
				error = "All fields are required. Please fill out the missing fields";
			if(ret == 2)
				error = "That username is already taken. Please try another.";
			
			JOptionPane.showMessageDialog(null, error);
				
		}
		return false;
	}
	
	public static String hashPW(String uname, String pass) {
		
		KeySpec spec = new PBEKeySpec(pass.toCharArray(), dec.decode(pass), 65536, 128); 
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return enc.encodeToString(hash);
	}
}
