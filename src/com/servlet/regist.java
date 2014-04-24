package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class regist
 */
@WebServlet("/regist")
public class regist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public regist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method s
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("xh");
		String xm = request.getParameter("xm");
		String pwd = request.getParameter("pwd");
		String proTitle = request.getParameter("proTitle");
		String dh = request.getParameter("dh");
		String email = request.getParameter("email");
		String userdepartment = request.getParameter("userdepartment");
		String habit = request.getParameter("habit");
		String grade = request.getParameter("grade");
		String role = "3";
		
		if (id.startsWith("1")) {
			role = "1";
		} else if (id.startsWith("2")) {
			role = "2";
		} else {
			role = "3";
		}
		String[] sqlparam = {id,xm,pwd,proTitle,dh,email,userdepartment,habit,grade,role};
		int[] WhichInt = {0,0,0,0,0,0,0,0,0,0};
		String sqlString = "insert into login values(?,?,?,?,?,?,?,?,?,?)";
		
		ConnectDB connect = new ConnectDB();
		boolean isSuccess = connect.updatesql(sqlString, sqlparam, WhichInt);
		
		if(isSuccess){
			out.print("true");
		} else {
			out.print("false");
		}
		
	}

}
