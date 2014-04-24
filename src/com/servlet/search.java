package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javabean.ConnectDB;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
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
		
		String keywordString = request.getParameter("keyword");
		System.out.println(keywordString);
		String[] param =new String[0]; 
		int[] which = new int[0];
		String countsqlString= "select count(*) as B from libbook";
		String rssqlString="select * from libbook where bookid like '%"+keywordString+"%' or bookname like '%"+keywordString+"%' or bookauthor like '%"+keywordString+"%'";
		System.out.println(rssqlString);
		int requestPage = Integer.parseInt(request.getParameter("requestPage"));        //得到当前页面是第几页
		int onePageRec = 10;
		try{
			ConnectDB connectDB = new ConnectDB();
			ResultSet rstotalRecord = connectDB.executeQuery(countsqlString,param,which);
			int totalrecord=0;
			int totalPage=0;     //获取总共的页数
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
		    
		    //Json赋值
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
            member.put("curPage",requestPage);      //发送的第几页
            array.add(member);System.out.println(totalrecord);
		    json.put("book", array);
		    out.print(json.toString());System.out.println(json.toString());
		    connectDB.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
