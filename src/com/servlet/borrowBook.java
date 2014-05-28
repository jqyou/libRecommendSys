package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class borrowBook
 */
@WebServlet("/borrowBook")
public class borrowBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public borrowBook() {
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
		
		String idString = request.getParameter("id");
		String useridString = request.getSession().getAttribute("pid").toString();
		String insertRecString = "insert into libborrowrec(borrowerID, borrowTime , bookID, isReturn) values(?,?,?,'Î´»¹')";
		SimpleDateFormat timeformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		String[] insertparam = { useridString,Long.toString(c.getTimeInMillis()) ,idString};
		int[] insertwhich = {0,3,0};
		ConnectDB insertconnectDB = new ConnectDB();
		boolean isInsertSuccess = insertconnectDB.updatesql(insertRecString, insertparam, insertwhich);
		if (isInsertSuccess) {
			String sqlString = "update libbook set isborrow = 'ÒÑ½è' where bookid = ?";
			String[] param = {idString};
			int[] which = {0};
			ConnectDB connectDB = new ConnectDB();
			boolean isSuccess = connectDB.updatesql(sqlString, param, which);
			if (isSuccess) {
				out.print("success");
				System.out.println("rent success");
			} else {
				out.print("failure");
				System.out.println("rent failure");
			}
		} else {
			out.print("failure");
			System.out.println("rent failure");
		}
		
		
		
	}

}
