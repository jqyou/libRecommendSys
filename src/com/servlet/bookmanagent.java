package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class bookmanagent
 */
@WebServlet("/bookmanagent")
public class bookmanagent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookmanagent() {
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
		
		String methodsString = request.getParameter("method");
		System.out.print(methodsString);

		if (methodsString.equals("delete")) {
			String bookidsString = request.getParameter("id");
			String sqlString = "delete from libbook where bookid = ?";
			String[] param = {bookidsString};
			int[] which = {0};
			ConnectDB connectDB = new ConnectDB();
			boolean isSuccess = connectDB.updatesql(sqlString, param, which);
			if (isSuccess) {
				out.print("success");
				System.out.println("del success");
			} else {
				out.print("failure");
				System.out.println("del failure");
			}
		} else if (methodsString.equals("modify")) {
			String oldbookidsString = request.getParameter("oldid");
			String newbookidsString = request.getParameter("newid");
			String booknameString = request.getParameter("name");
			String bookauthurString = request.getParameter("author");
			String bookbriefString = request.getParameter("jianjie");
			String bookisborrowString = request.getParameter("isborrow");
			String booktypesString = request.getParameter("leixing");
			System.out.println();
			String sqlString = "update libbook set bookname=?, bookauthor=?,brief=?,isborrow=?,booktype=? ,bookid=? where bookid = ?";
			String[] param = {booknameString,bookauthurString,bookbriefString,bookisborrowString,booktypesString ,newbookidsString,oldbookidsString};
			int[] which = {0,0,0,0,0,0,0};
			ConnectDB connectDB = new ConnectDB();
			boolean isSuccess = connectDB.updatesql(sqlString, param, which);
			if (isSuccess) {
				out.print("success");
				System.out.println("modify success");
			} else {
				out.print("failure");
				System.out.println("modify failure");
			}
		} else if (methodsString.equals("add")) {
			String bookidsString = request.getParameter("id");
//			String bookidsString = UUID.randomUUID().toString();
			String booknameString = request.getParameter("name");
			String bookauthurString = request.getParameter("author");
			String bookbriefString = request.getParameter("jianjie");
			String bookisborrowString = request.getParameter("isborrow");
			String booktypesString = request.getParameter("leixing");
			String bookBorrowCountString = "0";
			
			String sqlString = "insert into libbook values(?,?,?,?,?,?,?)";
			String[] param = {bookidsString,booknameString,bookauthurString,booktypesString,bookBorrowCountString,bookisborrowString,bookbriefString};
			int[] which = {0,0,0,0,0,0,0};
			ConnectDB connectDB = new ConnectDB();
			boolean isSuccess = connectDB.updatesql(sqlString, param, which);
			if (isSuccess) {
				out.print("success");
				System.out.println("add success");
			} else {
				out.print("failure");
				System.out.println("add failure");
			}
		}
		
	}

}
