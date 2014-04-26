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
 * Servlet implementation class usermanagent
 */
@WebServlet("/usermanagent")
public class usermanagent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public usermanagent() {
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
			String idString = request.getParameter("yhm");
			String sqlString = "delete from login where id = ?";
			String[] param = {idString};
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
			String oldidString = request.getParameter("oldyhm");
			String newidString = request.getParameter("yhm");
			String xmString = request.getParameter("xm");
			String mmString = request.getParameter("mm");
			String zcString = request.getParameter("zc");
			String dhString = request.getParameter("dh");
			String yxString = request.getParameter("yx");
			String xyString = request.getParameter("xy");
			String xqString = request.getParameter("xq");
			String njString = request.getParameter("nj");
			String role = "3";
			
			if (newidString.startsWith("1")) {
				role = "1";   //管理员
			} else if (newidString.startsWith("2")) {
				role = "2";   //教师
			} else {
				role = "3";   //学生
			}
			
			String sqlString = "update login set id=?,username=?,pwd=?,professionalTitle=?,phone=?,email=?,department=?,intrest=?,grade=?,role=? where id = ?";
			String[] param = {newidString,xmString,mmString,zcString,dhString,yxString,xyString,xqString,njString,role,oldidString};
			int[] which = {0,0,0,0,0,0,0,0,0,0,0};
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
			String idString = request.getParameter("yhm");
			String xmString = request.getParameter("xm");
			String mmString = request.getParameter("mm");
			String zcString = request.getParameter("zc");
			String dhString = request.getParameter("dh");
			String yxString = request.getParameter("yx");
			String xyString = request.getParameter("xy");
			String xqString = request.getParameter("xq");
			String njString = request.getParameter("nj");
			String role = "3";
			
			if (idString.startsWith("1")) {
				role = "1";   //管理员
			} else if (idString.startsWith("2")) {
				role = "2";   //教师
			} else {
				role = "3";   //学生
			}
			
			String sqlString = "insert into login values(?,?,?,?,?,?,?,?,?,?)";
			String[] param = {idString,xmString,mmString,zcString,dhString,yxString,xyString,xqString,njString,role};
			int[] which = {0,0,0,0,0,0,0,0,0,0};
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
