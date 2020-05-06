import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class ApplicationLogin {
	
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	
	public boolean login(String uname, String pass) {
		
	}
	
	public boolean register(String uname, String pass) {
		String hash = hashPW(uname, pass);
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Register(?,?,?,?,?,?)}");
		} catch (SQLException e) {
			//error switcher here 
		}
	}
	
	public String hashPW(String uname, String pass) {
		
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
