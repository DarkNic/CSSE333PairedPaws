import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class UserOperations {
	public static boolean deleteAccount(String uname) {
		int ret = 0;
		try {
			CallableStatement cs = Main.con.getConnection().prepareCall("{? = call Remove_Person(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, uname);
			cs.execute();
			ret = cs.getInt(1);
			if(ret != 0)
				throw new SQLException();
			cs.close();
			ret = 0;
			CallableStatement cs2 = Main.con.getConnection().prepareCall("{? = call Remove_Contact(?)}");
			cs2.registerOutParameter(1, Types.INTEGER);
			cs2.setString(2, uname);
			cs2.execute();
			ret = cs2.getInt(1);
			if(ret != 0)
				throw new SQLException();
			cs2.close();
			return true;
		} catch (SQLException e) {
			String error = "An error occurred.";
			if(ret == 1)
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
			if(ret != 0)
				throw new SQLException();
			cs.close();
			return true;
		} catch (SQLException exc) {
			String error = "An error occurred.";
			if(ret == 1)
				error = "Must be logged in to edit contact information";
			
			JOptionPane.showMessageDialog(null, error);
		}
		
		return false;
	}
}
