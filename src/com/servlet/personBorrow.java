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
 * Servlet implementation class personBorrow
 */
@WebServlet("/personBorrow")
public class personBorrow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public personBorrow() {
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
		String useridString = request.getSession().getAttribute("pid").toString();
		
		String countsqlString = "";
		String rssqlString = "";
		String[] param = {useridString};
		int[] which = {0};
		if (methodsString.equals("history")) {
			countsqlString= "select count(*) as B from libBorrowRec left join libbook on libBorrowRec.bookID = libbook.bookid where libBorrowRec.borrowerID = ?";
			rssqlString= "select * from libBorrowRec left join libbook on libBorrowRec.bookID = libbook.bookid where libBorrowRec.borrowerID = ?";
			System.out.println(rssqlString);
						
		} else if (methodsString.equals("notReturn")) {
			countsqlString= "select count(*) as B from libBorrowRec left join libbook on libBorrowRec.bookID = libbook.bookid where libBorrowRec.borrowerID = ? and libBorrowRec.isReturn = 'δ��'";
			rssqlString= "select * from libBorrowRec left join libbook on libBorrowRec.bookID = libbook.bookid where libBorrowRec.borrowerID = ? and libBorrowRec.isReturn = 'δ��'";
		} else {
			return;
		}
		
		int requestPage = Integer.parseInt(request.getParameter("requestPage"));        //�õ���ǰҳ���ǵڼ�ҳ
		int onePageRec = 10;
		try{
			ConnectDB connectDB = new ConnectDB();
			ResultSet rstotalRecord = connectDB.executeQuery(countsqlString,param,which);
			int totalrecord=0;
			int totalPage=0;     //��ȡ�ܹ���ҳ��
		    if(rstotalRecord.next()){
		  	  totalrecord=rstotalRecord.getInt("B");
		  	  totalPage = totalrecord/onePageRec+1;
		    }
		    ResultSet rsdev = connectDB.executeQuery(rssqlString,param,which);
		    int i=0;
		    if(requestPage!=1){
		    	while(rsdev.next()){
			    	i++;
			    	if(i>((requestPage-1)*(onePageRec -1)-1))
			    		break;
				}
		    }
		    
		    //Json��ֵ
		    int k =1;
		    JSONObject json = new JSONObject();  
	        JSONArray array = new JSONArray();
	        JSONObject member = null;  
		    while(rsdev.next()){
		    	k++;
		    	if(k>onePageRec){
		    		break;
		    	}
		    	member = new JSONObject();  
		    	member.put("id", rsdev.getString("bookid"));
		        member.put("name", rsdev.getString("bookname"));
	            member.put("author", rsdev.getString("bookauthor"));
	            member.put("type", rsdev.getString("booktype"));
	            array.add(member);  
		    }
		    member = new JSONObject();
    		member.put("totalSet", totalrecord);
            member.put("totalPage", totalPage);  
            member.put("curPage",requestPage);      //���͵ĵڼ�ҳ
            array.add(member);System.out.println(totalrecord);
		    json.put("bookhistory", array);
		    out.print(json.toString());System.out.println(json.toString());
		    connectDB.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
