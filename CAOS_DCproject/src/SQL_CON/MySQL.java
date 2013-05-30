package SQL_CON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.LoadInfo;


public class MySQL {
	
	
	private static Connection con;
	
	private static String username,password,url;
	

	

	private MySQL()
	{
	}
	
	
	public static Connection getInstance()
	{ 
		if(con==null)
		{
			try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Connection established!");
			return con;
			}
			
			catch(Exception e){
				System.out.println("Error establishing cnnnection!");
				e.printStackTrace();
			}
		}
		
		else return con;
		return null;
	}

	
	public static void createLoadOperation(String estStartTime,String estEndTime, int suborder_ID,int dock_ID)
	{
		Connection conn = null;
		try {
			 conn=MySQL.getInstance();

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
		} 

	}
	
	
	public static ArrayList<LoadInfo> getDatesForDock(String date)
	{
		Connection conn = null;
		try {
			 conn=MySQL.getInstance();
			 ArrayList<LoadInfo> loadInfos=new ArrayList<LoadInfo>();

			String statement="SELECT * ";
			statement+="FROM loads_tbl LEFT JOIN loadingdocks_tbl on(loads_tbl.dock_ID=loadingdocks_tbl.dock_ID) ";
			statement+="LEFT JOIN suborders_tbl on(loads_tbl.suborder_ID=suborders_tbl.suborder_ID) ";
			statement+="WHERE DATE(loads_tbl.estEndTime)=" +date + ";";

			Statement s = conn.createStatement();
			s.executeQuery(statement);
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				Timestamp estEndTime = rs.getTimestamp("loads_tbl.estEndTime");
				Timestamp estStartTime = rs.getTimestamp("loads_tbl.estStartTime");
				int dock_ID=rs.getInt("loads_tbl.dock_ID");
				String status=rs.getString("loadingdocks_tbl.status");
				int tr_ID=rs.getInt("suborders_tbl.tr_ID");
				if (rs.wasNull()) {
					System.out.println("Its null!");
				}
				loadInfos.add(new LoadInfo(estStartTime,estEndTime,dock_ID,status,tr_ID));

			}
			rs.close();
			s.close();
			return loadInfos;

		} catch (Exception e) {
		e.printStackTrace();
			return null;
		} 

	}
	
	
	public static void calculateAndCreateLoad(int subrder_ID,int dock_ID)
	{
		Connection conn = null;
		try {
			
			
			
			 conn=MySQL.getInstance();
			 conn.setAutoCommit(false);
			 ArrayList<Timestamp> endTimes=new ArrayList<Timestamp>();

			String statement="SELECT * FROM loads_tbl WHERE dock_ID="+dock_ID+" FOR UPDATE;";
			
			Statement s = conn.createStatement();
			s.executeQuery(statement);
			ResultSet rs = s.getResultSet();
			while (rs.next()) {		
				Timestamp estEndTime = rs.getTimestamp("estEndTime");
				if (rs.wasNull()) {
					System.out.println("Its null!");
				}
				endTimes.add(estEndTime);

			}
			rs.close();
			s.close();
			Timestamp ts= endTimes.get(0);
			for (Timestamp t : endTimes) {
				if(ts.compareTo(t)<0)
	        		ts=t;
			}
		//	Thread.sleep(3000);
			PreparedStatement ps;
			ps= conn.prepareStatement("INSERT INTO loads_tbl (estStartTime,estEndTime,suborder_ID,dock_ID) VALUES(?, DATE_ADD(?,INTERVAL 40 MINUTE),?,?);");
			ps.setTimestamp(1, ts);
			ps.setTimestamp(2, ts);
			ps.setInt(3, subrder_ID);
			ps.setInt(4, dock_ID);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
		e.printStackTrace();
		} 
	}
	
	
	public static void closeConnection() 
	{
		try {
			con.close();
			System.out.println("Database connection closed.");
		} catch (SQLException e) {
			System.out.println("Problem cloasing connection!");
			e.printStackTrace();
		}
	}
	
	
	public static String getUsername() {
		return username;
	}


	public static void setUsername(String username) {
		MySQL.username = username;
	}


	public static String getPassword() {
		return password;
	}


	public static void setPassword(String password) {
		MySQL.password = password;
	}


	public static String getUrl() {
		return url;
	}


	public static void setUrl(String url) {
		MySQL.url = url;
	}

	

}
