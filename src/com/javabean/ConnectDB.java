package com.javabean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
	private final String url = "jdbc:mysql://localhost/recommentlib?characterEncoding=utf8";
	private final String userName = "root";
	private final String password= "123456";
    private Connection con = null;
    private PreparedStatement stmtToCheck=null;   // function  isHasDatas() will use this
    private PreparedStatement stmt_update =null;
    private PreparedStatement stmt_query=null;
	public ConnectDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception ex){
			System.out.print("error:" + ex.getMessage());
		}
	}
	public boolean Connection(){
		try{
			con = DriverManager.getConnection(url,userName,password);
			System.out.println("mysql connection is established.");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return true;
	}
	
	
	public void closeConnection(){
			try{
				if(stmt_update!=null){
					stmt_update.close();
				}
				if(stmtToCheck!=null){
					stmtToCheck.close();
				}
				if(stmt_query!=null){
					stmt_query.cancel();
				}
				if(con!=null){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}finally{
				con=null;
				stmtToCheck=null;
				stmt_update=null;
				stmt_query=null;
			}
		
	}
	public ResultSet executeQuery(String sql,String[] SqlParam,int[] WhichInt) {
		ResultSet rs = null;
		try {
			if(con==null){
				if(!Connection())
					return null;
			}
			stmt_query = con.prepareStatement(sql);
			int count = SqlParam.length;
			for(int j=0;j<count;j++){
				if(WhichInt[j]==0){
					stmt_query.setString(j+1, SqlParam[j]);
				}				
				else if (WhichInt[j] == 1) {
					stmt_query.setInt(j+1, Integer.parseInt(SqlParam[j]));
				}
				else if(WhichInt[j]==2){
					stmt_query.setDate(j+1, Date.valueOf(SqlParam[j]));
				}
				else if(WhichInt[j]==3){
					stmt_query.setLong(j+1,Long.parseLong(SqlParam[j].trim()));
				}
			}
			rs = stmt_query.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return rs;
	}

	public boolean updatesql(String sql,String[] SqlParam,int[] WhichInt){               //SQL update delete insert
		boolean flag = false;
		try{
			if(con==null){
				Connection();
			}
			stmt_update = con.prepareStatement(sql);
			int count = SqlParam.length;
			for(int j=0;j<count;j++){
				if(WhichInt[j]==0){
					stmt_update.setString(j+1, SqlParam[j]);
				}
				else if (WhichInt[j] == 1) {
					stmt_query.setInt(j+1, Integer.parseInt(SqlParam[j]));
				}
				else if(WhichInt[j]==2){
					stmt_update.setLong(j+1,Long.parseLong(SqlParam[j].trim()));
				}
				else if(WhichInt[j]==3){
					stmt_update.setDate(j+1, Date.valueOf(SqlParam[j]));
				}
			}
			stmt_update.execute();
			flag = true;
		}catch(SQLException ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return flag;
			
		}finally{
			closeConnection();
		}
		return flag;
	}
		
	 public boolean isHasDatas(String sql,String[] SqlParam,int[] WhichInt){
		boolean flag = false;
		try {
			if (con == null) {
				if (!Connection())
					return flag;
			}
			stmtToCheck = con.prepareStatement(sql);
			try {
				int count = SqlParam.length;
				for(int j=0;j<count;j++){
					if(WhichInt[j]==0){
						stmtToCheck.setString(j+1, SqlParam[j]);
					}
					else if (WhichInt[j] == 1) {
						stmt_query.setInt(j+1, Integer.parseInt(SqlParam[j]));
					}
					else if(WhichInt[j]==2){
						stmtToCheck.setLong(j+1,Long.parseLong(SqlParam[j].trim()));
					}
					else if(WhichInt[j]==3){
						stmtToCheck.setDate(j+1, Date.valueOf(SqlParam[j]));
					}
				}
				ResultSet rs =stmtToCheck.executeQuery();
				while (rs.next()) {
					if (rs.getString(1) != null&& rs.getString(1).length() != 0) {
						flag = true;
					}
				}
				closeConnection();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return flag;
	}

}
