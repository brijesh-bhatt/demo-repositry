package com.ccav.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnection {
	Connection conn;
	
	public Connection getConnection() throws NamingException, SQLException {
		if(conn==null) {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:/ccavenuejndi");
			conn=ds.getConnection();
		}
		return conn;
	}
	
	public void closeConnection() throws NamingException, SQLException {
		if(conn!=null) {
			conn.close();
		}
	}
}
