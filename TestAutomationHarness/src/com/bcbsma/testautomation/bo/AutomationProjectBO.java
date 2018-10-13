package com.bcbsma.testautomation.bo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bcbsma.testautomation.bo.OracleJdbcConnect;

public class AutomationProjectBO {

	private Connection conn = null;
	OracleJdbcConnect ora = new OracleJdbcConnect("db");

	public AutomationProjectBO() {

	}

	public List<String> returnColumnFromDB(String sql) {

		Statement statement = null;
		List<String> tmpList = new ArrayList<String>();

		String select = sql;

		try {
			conn = ora.connectToDatabaseorDie();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(select);
			
			if (!rs.isBeforeFirst() ) {    
			    System.out.println("No data");
			    tmpList.add("No Data");
			} 
			while (rs.next()) {
				tmpList.add(rs.getString(1));
			}


		} catch (SQLException e) {

			System.out.println(e.getMessage());
			tmpList.add("NA");

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		}

		return tmpList;
	}

	public int insertOneRowIntoDB(String sql) {

		PreparedStatement preparedStatement = null;
		
		try {
			conn = ora.connectToDatabaseorDie();

			System.out.println("Creating prepared statement...");
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//preparedStatement.setString(1, project_name);

			// execute insert SQL statement
			Integer affectedRows = preparedStatement.executeUpdate();

			//Long idNewRow;
			if (affectedRows == 0) {
				throw new SQLException("Creating row failed, no rows affected.");
			}

			preparedStatement.close();
			//conn.commit();
			conn.close();
			return affectedRows;
			
		} catch ( SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error inserting New Project\n"+e.getMessage());
			e.printStackTrace();
			return 0;
		} finally {
			// finally block used to close resources
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}

	}

	public static void main(String[] argv) {
		// AutomationProjectBO conn = new AutomationProjectBO();

	}
}
