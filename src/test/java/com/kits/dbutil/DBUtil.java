package com.kits.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/DB";

	private static Connection connection = null;
	static {
		try {
			Class.forName(DB_DRIVER_CLASS);
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
	
	public static void cleanup(Connection con, Statement st, ResultSet rs) {
		  try {
		    // 7.Release the Resources 
		    if (rs != null)
		        rs.close();
		    if (st != null)
		        st.close(); 
		    if (con != null)
		        con.close();
		   } catch (Exception e) { 
		        e.printStackTrace();
		   }
		 }
		public static void cleanup(Connection con, Statement st) { 
		  try {
		    // 7.Release the Resources 
		    if (st != null)
		        st.close(); 
		    if (con != null)
		        con.close();
		  } catch (Exception e) { 
		        e.printStackTrace();
		  }
		 }
}