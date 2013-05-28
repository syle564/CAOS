package SQL_CON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Date;

public class MySQL {

	
	public static void createLoadOperation(String estStartTime,String estEndTime, int suborder_ID,int dock_ID)
	{
		Connection conn = null;
		try {
			String username = "root";
			String password = "parol";
			String url = "jdbc:mysql://localhost:3306/caos_dc";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection established!");

			PreparedStatement s;
			try{
			s = conn.prepareStatement("INSERT INTO loads_tbl (estStartTime,estEndTime,suborder_ID,dock_ID) VALUES(?,?,?,?);");
			s.setString(1, estStartTime);
			s.setString(2, estEndTime);
			s.setInt(3, suborder_ID);
			s.setInt(4,dock_ID);
			s.executeUpdate();
			
			PreparedStatement s2;
			s2= conn.prepareStatement("UPDATE suborders_tbl SET load_ID= (SELECT load_ID FROM loads_tbl WHERE suborder_ID=?) WHERE suborder_ID = ?");
			s2.setInt(1, suborder_ID);
			s2.setInt(2, suborder_ID);
			s2.executeUpdate();
			}
catch(Exception e){
				System.out.println("Error!Incorrect ID.");
			}
			
		} catch (Exception e) {
			System.out.println("Problem establishing Connection");
		} finally {
			if (conn != null) {
				try {
					conn.close();
					System.out.println("Database connection closed.");
				} catch (Exception e) {
					System.out.println("Problem with closing connection.");
				}
			}
		}

	}
}
