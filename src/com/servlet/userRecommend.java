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
			
			//deal the book type through the user habit
			String queryIsexist = "select * from libhabit where idnum = ?";
			String[] paramExist = {idString};
			int[] whichExist = {0};
			boolean exist = false;
			StringBuffer sqlMaybe = new StringBuffer();
			ResultSet rSet = connectDB.executeQuery(queryIsexist, paramExist, whichExist);
		    if (rSet.next()){
		        String A = rSet.getString("A");
		        String B = rSet.getString("B");
		        String C = rSet.getString("C");
		        String D = rSet.getString("D");
		        String E = rSet.getString("E");
		        String F = rSet.getString("F");
		        String G = rSet.getString("G");
		        String H = rSet.getString("H");
		        String I = rSet.getString("I");
		        String J = rSet.getString("J");
		        String K = rSet.getString("K");
		        String N = rSet.getString("N");
		        String O = rSet.getString("O");
		        String P = rSet.getString("P");
		        String Q = rSet.getString("Q");
		        String R = rSet.getString("R");
		        String S = rSet.getString("S");
		        String T = rSet.getString("T");
		        String U = rSet.getString("U");
		        String V = rSet.getString("V");
		        String X = rSet.getString("X");
		        String Z = rSet.getString("Z");
		        
		        sqlMaybe = sqlMaybe.append(" and booktype in (");
		        int originalLen = sqlMaybe.length();
		        if (A.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("A");
		        	sqlMaybe.append("',");
		        }
		        if (B.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("B");
		        	sqlMaybe.append("',");
		        }
		        if (C.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("C");
		        	sqlMaybe.append("',");
		        }
		        if (D.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("D");
		        	sqlMaybe.append("',");
		        }
		        if (E.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("E");
		        	sqlMaybe.append("',");
		        }
		        if (F.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("F");
		        	sqlMaybe.append("',");
		        }
		        if (G.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("G");
		        	sqlMaybe.append("',");
		        }
		        if (H.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("H");
		        	sqlMaybe.append("',");
		        }
		        if (I.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("I");
		        	sqlMaybe.append("',");
		        }
		        if (J.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("J");
		        	sqlMaybe.append("',");
		        }
		        if (K.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("K");
		        	sqlMaybe.append("',");
		        }
		        if (N.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("N");
		        	sqlMaybe.append("',");
		        }
		        if (O.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("O");
		        	sqlMaybe.append("',");
		        }
		        if (P.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("P");
		        	sqlMaybe.append("',");
		        }
		        if (Q.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("Q");
		        	sqlMaybe.append("',");
		        }
		        if (R.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("R");
		        	sqlMaybe.append("',");
		        }
		        if (S.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("S");
		        	sqlMaybe.append("',");
		        }
		        if (T.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("T");
		        	sqlMaybe.append("',");
		        }
		        if (U.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("U");
		        	sqlMaybe.append("',");
		        }
		        if (V.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("V");
		        	sqlMaybe.append("',");
		        }
		        if (X.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("X");
		        	sqlMaybe.append("',");
		        }
		        if (Z.equals("1")) {
		        	sqlMaybe.append("'");
		        	sqlMaybe.append("Z");
		        	sqlMaybe.append("',");
		        }
		        
		        if (sqlMaybe.length() == originalLen) {
		        	sqlMaybe.delete(0, originalLen);
		        } else {
		        	sqlMaybe.deleteCharAt(sqlMaybe.length() - 1);
		        	sqlMaybe.append(")");
		        }
		    }
			
			
		    //SELECT THE BOOK RECOMMENDED
			String recommedBookInfoString = "select * from libbook where bookid in ("+recommedString+")" + sqlMaybe.toString();
			String countRecommedBookInfoString = "select count(*) as B from libbook where bookid in ("+recommedString+")" +sqlMaybe.toString();
			
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
