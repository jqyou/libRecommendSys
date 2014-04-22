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
import javax.servlet.http.HttpSession;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class ConnectDB
 */
@WebServlet("/ConnectDB")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			boolean isexist = checkUser(request, response);
			if(isexist){
				out.print("true");
			} else {
				out.print("false");
			}
			
		} catch (SQLException e) {
			out.print("false");
			e.printStackTrace();
		}
		
	}
	
	boolean checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
//		PrintWriter out = response.getWriter();
		String userid = request.getParameter("Userid");
		String password =request.getParameter("UserPassword");
		System.out.println(userid);
		System.out.println(password);
		HttpSession session = request.getSession();
		ConnectDB connect = new ConnectDB();
		String[] sqlparam = {userid,password};
		int[] WhichInt = {0,0};
		ResultSet rsUserID= connect.executeQuery("select id from login where id=? and pwd=?",sqlparam, WhichInt);
		if(rsUserID.next()) {
			return true;
		} else {
			session.setAttribute("pid",userid );
			return false;
		}
//			
//			String refuri = request.getParameter("refuri");
//			System.out.println(refuri);
//			if(refuri.equals("1")){
//				refuri = "/Bys-Website/PersonMainPage.jsp?userID="+userID+"";
//			}
//			if(refuri.equals("http://www.bys.com")){
//				refuri="/Bys-Website/PersonMainPage.jsp?userID="+userID+"";
//			}
//			out.print(refuri);
			//out.print(session.getAttribute("pid").toString());
			//response.sendRedirect(refuri);
	}

}
