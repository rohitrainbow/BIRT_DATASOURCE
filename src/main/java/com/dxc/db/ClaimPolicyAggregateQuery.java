package com.dxc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dxc.util.Constants;

public class ClaimPolicyAggregateQuery {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	static final String DB_URL = "jdbc:db2://20.15.86.32:50002/ACCELAIG";

	// Database credentials
	static final String USER = "uniauser1";
	static final String PASS = "UniaU$er1";
	ResultSet rs=null;
	public void connection(String inputUI) {
		Connection conn = null;
		Statement stmt = null;
		try {
			String input=inputUI;
			// STEP 2: Register JDBC driver
			Class.forName("com.ibm.db2.jcc.DB2Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = Constants.query1+input;
			rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String first = rs.getString("POLICYNUMBER");


				// Display values
				System.out.print("First: " + first);

			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
		
	}// end main
		// end FirstExample
}
