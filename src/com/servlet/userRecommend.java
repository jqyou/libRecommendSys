package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.javabean.ConnectDB;
import com.sun.org.apache.xerces.internal.xs.StringList;

/**
 * Servlet implementation class userRecommend
 */
@WebServlet("/userRecommend")
public class userRecommend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userRecommend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String idString = request.getSession().getAttribute("pid").toString();System.out.println("pid:"+idString);
		String xqString = request.getSession().getAttribute("xq").toString();
		String borrowSqlString= "select bookid from libBorrowRec where borrowerID = ?";
		String[] param = {idString};
		int[] which = {0};
	
		String recommendSqlString ="select * from libmini where exists ( select * from  libborrowrec where borrowerID=? and libmini.onebook = bookID  or libmini.twobook = bookID  or libmini.threebook = bookID or libmini.fourbook = bookID or libmini.fivebook = bookID or libmini.sixbook = bookID or libmini.sevenbook = bookID)";		
		int requestPage = Integer.parseInt(request.getParameter("requestPage"));        //得到当前页面是第几页
		int onePageRec = 10;
		try{
			List<String> borrowList = new LinkedList<String>();
			List<String> recommendList = new LinkedList<String>();
			StringBuffer recommendBuffer = new StringBuffer();
			
			//得到用户已经借过的书籍列表
			ConnectDB connectDB = new ConnectDB();
			ResultSet rsborrow = connectDB.executeQuery(borrowSqlString, param, which);
			while (rsborrow.next()) {
				borrowList.add(rsborrow.getString("bookid"));
			}
			
			ResultSet rsRecommend = connectDB.executeQuery(recommendSqlString,param,which);
			while (rsRecommend.next()) {
				String frequency = rsRecommend.getString("numbers");
		    	String one = rsRecommend.getString("onebook");
		    	String two = rsRecommend.getString("twobook");
		    	String three = rsRecommend.getString("threebook");
		    	String four = rsRecommend.getString("fourbook");
		    	String five = rsRecommend.getString("fivebook");
		    	String six = rsRecommend.getString("sixbook");
		    	String seven = rsRecommend.getString("sevenbook");
		    	
		    	if( (!one.isEmpty()) && one != null && !borrowList.contains(one)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(one);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}
		    	if( (!two.isEmpty()) && two != null && !borrowList.contains(two)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(two);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}
		    	if( (!three.isEmpty()) && three != null && !borrowList.contains(three)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(three);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}
		    	if( (!four.isEmpty()) && four != null && !borrowList.contains(four)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(four);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}
		    	if( (!five.isEmpty()) && five != null && !borrowList.contains(five)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(five);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}
		    	if( (!six.isEmpty()) && six != null && !borrowList.contains(six)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(six);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}
		    	if( (!seven.isEmpty()) && seven != null && !borrowList.contains(seven)) {
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(seven);
		    		recommendBuffer.append("'");
		    		recommendBuffer.append(",");
		    	}	
			}
			System.out.println(recommendBuffer.toString());
			recommendBuffer.deleteCharAt(recommendBuffer.length() - 1);
			System.out.println(recommendBuffer.toString());
			String recommedString = recommendBuffer.toString();
			
			String recommedBookInfoString = "select * from libbook where bookid in ("+recommedString+")";
			String countRecommedBookInfoString = "select count(*) as B from libbook where bookid in ("+recommedString+")";
			
			ResultSet rstotalRecord = connectDB.executeQuery(countRecommedBookInfoString,new String[0],new int[0]);
			int totalrecord=0;
			int totalPage=0;     //获取总共的页数
		    if(rstotalRecord.next()){
		  	  totalrecord=rstotalRecord.getInt("B");
		  	  totalPage = totalrecord/onePageRec+1;
		    }
		    ResultSet rsdev = connectDB.executeQuery(recommedBookInfoString,new String[0],new int[0]);
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
