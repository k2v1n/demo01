package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java1234.model.User;
import com.java1234.util.StringUtil;

public class UserDao {

	public User login(Connection con,User user) throws Exception{
		User currentUser=null;
		String sql="select * from t_user where userName=? and password=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs=pstmt.executeQuery();
		
		if(rs.next()){
			currentUser = new User();
			currentUser.setUserName(rs.getString("userName"));
			currentUser.setPassword(rs.getString("password"));
		}
		return currentUser;
	}
}
