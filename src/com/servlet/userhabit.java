package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.DocFlavor.STRING;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class updateHabit
 */
@WebServlet("/userhabit")
public class userhabit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userhabit() {
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
		
		String id = request.getSession().getAttribute("pid").toString();
		if (id.isEmpty() || id == null) {
			return;
		}
		try {
			String queryIsexist = "select * from libhabit where idnum = "+id;
			boolean exist = false;
			ConnectDB connectDB = new ConnectDB();
			ResultSet rSet = connectDB.executeQuery(queryIsexist, new String[0], new int[0]);
			if (rSet.next()) {
				JSONObject json = new JSONObject();  
		        JSONArray array = new JSONArray();
		        JSONObject member = new JSONObject();  
		        
		        member.put("A", rSet.getString("A"));
		        member.put("B", rSet.getString("B"));
	            member.put("C", rSet.getString("C"));
	            member.put("D", rSet.getString("D"));
	            member.put("E", rSet.getString("E"));
	            member.put("F", rSet.getString("F"));
	            member.put("G", rSet.getString("G"));
	            member.put("H", rSet.getString("H"));
	            member.put("I", rSet.getString("I"));
	            member.put("J", rSet.getString("J"));
	            member.put("K", rSet.getString("K"));
	            member.put("N", rSet.getString("N"));
	            member.put("O", rSet.getString("O"));
	            member.put("P", rSet.getString("P"));
	            member.put("Q", rSet.getString("Q"));
	            member.put("R", rSet.getString("R"));
	            member.put("S", rSet.getString("S"));
	            member.put("T", rSet.getString("T"));
	            member.put("U", rSet.getString("U"));
	            member.put("V", rSet.getString("V"));
	            member.put("X", rSet.getString("X"));
	            member.put("Z", rSet.getString("Z"));
	            array.add(member);  	            
	            json.put("habitdetail", array);
	            out.print(json.toString());
			} else {
				out.print("no");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getSession().getAttribute("pid").toString();
		if (id.isEmpty() || id == null) {
			return;
		}
		
		String A = request.getParameter("A");
		String B = request.getParameter("B");
		String C = request.getParameter("C");
		String D = request.getParameter("D");
		String E = request.getParameter("E");
		String F = request.getParameter("F");
		String G = request.getParameter("G");
		String H = request.getParameter("H");
		String I = request.getParameter("I");
		String J = request.getParameter("J");
		String K = request.getParameter("K");
		String N = request.getParameter("N");
		String O = request.getParameter("O");
		String P = request.getParameter("P");
		String Q = request.getParameter("Q");
		String R = request.getParameter("R");
		String S = request.getParameter("S");
		String T = request.getParameter("T");
		String U = request.getParameter("U");
		String V = request.getParameter("V");
		String X = request.getParameter("X");
		String Z = request.getParameter("Z");
		
		try {
			String queryIsexist = "select * from libhabit where idnum = ?";
			String[] param = {id};
			int[] which = {0};
			boolean exist = false;
			ConnectDB connectDB = new ConnectDB();
			ResultSet rSet = connectDB.executeQuery(queryIsexist, param, which);
			if (rSet.next()) {
				exist = true;
			} else {
				exist = false;
			}
			
			String sqlString;
			String[] habitparam = null;
			int[] habitwhich = null; 
			if(exist) {
				sqlString = "update libhabit set A=?,B=?,C=?,D=?,E=?,F=?,G=?,H=?,I=?,J=?,K=?,N=?,O=?,P=?,Q=?,R=?,S=?,T=?,U=?,V=?,X=?,Z=? where idnum = ?";
			 	String[] temphabitparam = {A,B,C,D,E,F,G,H,I,J,K,N,O,P,Q,R,S,T,U,V,X,Z,id};
				int[] temphabitwhich = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				habitparam = temphabitparam;
				habitwhich = temphabitwhich;
			} else {
				sqlString = "insert into libhabit(A,B,C,D,E,F,G,H,I,J,K,N,O,P,Q,R,S,T,U,V,X,Z) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String[] temphabitparam = {A,B,C,D,E,F,G,H,I,J,K,N,O,P,Q,R,S,T,U,V,X,Z};
				int[] temphabitwhich = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				habitparam = temphabitparam;
				habitwhich = temphabitwhich;
			}
			boolean isSuccess = connectDB.updatesql(sqlString, habitparam, habitwhich);
			
			if (isSuccess) {
				out.print("success");
			} else {
				out.print("failure");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
