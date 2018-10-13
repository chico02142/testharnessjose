package com.bcbsma.testautomation.bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleJdbcConnect {
	
	Connection connection;
	
	//Local Connection Info
	private String user = "automation";
	private String passwd  = "automation";
	private String host = "localhost:1521/xe";

	
	//Work Connection Info
	/*private String user = "AUTOMATION";
	private String passwd  = "tstaut018";
	private String host = "borad01va.bcbsma.com:1531/DTDMTX.BCBSMA.COM";*/

	public OracleJdbcConnect() {
	}
	
	public OracleJdbcConnect(String db) {
		//connectToDatabaseorDie();
    }
	
	public Connection connectToDatabaseorDie() {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
        }
        System.out.println("Oracle JDBC Driver Registered!");
  
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:"+user+"/"+passwd+"@//"+host);
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        
        return connection;		
	}

	
	public Connection getConnection() {
		return connection;
	}
	
	public static void main(String[] argv) {
	//	OracleJdbcConnect conn = new OracleJdbcConnect("db");
	//	conn.getConnection();		
	}
}
