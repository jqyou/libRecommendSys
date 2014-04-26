package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class userDetail
 */
@WebServlet("/userDetail")
public class userDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String useridString = request.getParameter("userid");
		String[] param = {bookid};
		int[] which = {0};
		String sqlString = "select * from libbook where bookid = ?";
		try {
			ConnectDB connectDB = new ConnectDB();
			ResultSet record = connectDB.executeQuery(sqlString,param,which);
		
			if (record.next()){
				JSONObject json = new JSONObject();  
		        JSONArray array = new JSONArray();
		        JSONObject member = new JSONObject();  
		        
		        member.put("id", record.getString("bookid"));
		        member.put("name", record.getString("bookname"));
	            member.put("author", record.getString("bookauthor"));
	            member.put("type", record.getString("booktype"));
	            member.put("text", record.getString("brief"));
	            member.put("isborrow", record.getString("isborrow"));
	            array.add(member);  
	            
	            json.put("bookdetail", array);
	            out.print(json.toString());
			} else {
				out.print("no");
			}
		} catch (SQLException e) {
			out.print("error");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
