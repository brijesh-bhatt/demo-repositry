package com.ccav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ccav.form.User;
import com.ccav.security.util.SecurityUtil;
import com.ccav.util.DatabaseConnection;

@Repository
public class UserDAO {
	
	@Autowired
	private SecurityUtil sUtil;
	private DatabaseConnection dbConn;
	
	public boolean verifyUser(User user) {
		SecurityUtil securityUtil=new SecurityUtil();
		String username=null, password=null;
		Hashtable<String, String> merInfo = null;
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		boolean isLoginError = true;
		merInfo = new Hashtable<String, String>(1);

		if(user.getUsername()!=null && user.getPassword()!=null) {
			try {
				username = user.getUsername();
				System.out.println("username: "+username);
				password = securityUtil.encrypt(user.getPassword());
				conn = dbConn.getConnection();
				String sql="select m.user_id,m.password,m.approval,m.type,c.setupfees from MERCHANT_REGISTRATION m,commission c where m.user_name=?  and m.type=c.type";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, username);
				rset=pstmt.executeQuery();
				if (rset != null && rset.next()) {
					String dbPassword = "" + rset.getString("password");
					System.out.println("dbPassword: "+dbPassword);
					System.out.println("password: "+password);
					if(dbPassword.equals(password))
						isLoginError=false;
					
					merInfo.put("userId", ""+rset.getString("user_id"));
					merInfo.put("PSW", ""+rset.getString("password"));
					merInfo.put("approval", ""+rset.getString("approval"));
					merInfo.put("type", ""+rset.getString("type"));
					merInfo.put("setupFees", ""+rset.getString("setupfees"));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}	
		}
		return isLoginError;
	}
}
