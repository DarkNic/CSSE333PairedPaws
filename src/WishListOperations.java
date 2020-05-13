import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class WishListOperations {
	public static boolean add(String uname, int anId) {
		CallableStatement addWish;
		int ret = 0;
		try {
			addWish = Main.con.getConnection().prepareCall("{? = call AddToWishList(?,?)}");
			addWish.registerOutParameter(1, Types.INTEGER);
			addWish.setString(2, uname);
			addWish.setInt(3, anId);	
			addWish.execute();
			ret = addWish.getInt(1);
			if(ret != 0)
				throw new SQLException();
			addWish.close();
			
			return true;
		} catch (SQLException e) {
			String error = "An error occurred";
			if(ret == 1)
				error = "One or more pieces of information are incorrect.";
			if(ret == 2)
				error = "This pet has already been added to your wish list.";
			
			JOptionPane.showMessageDialog(null, error);
		}
		
		return false;
	}
	public static boolean remove(String uname, int anId) {
		CallableStatement removeWish;
		int ret = 0;
		try {
			removeWish = Main.con.getConnection().prepareCall("{? = call RemoveFromWishList(?,?)}");
			removeWish.registerOutParameter(1, Types.INTEGER);
			removeWish.setString(2, uname);
			removeWish.setInt(3, anId);	
			removeWish.execute();
			ret = removeWish.getInt(1);
			if(ret != 0)
				throw new SQLException();
			removeWish.close();
			return true;
		} catch (SQLException e) {
			String error = "An error occurred";
			if(ret == 1)
				error = "One or more pieces of information are incorrect.";
			if(ret == 2)
				error = "This pet has already been removed from your wish list.";
			
			JOptionPane.showMessageDialog(null, error);
		}
		return false;
	}
}
