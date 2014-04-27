package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.javabean.ConnectDB;

/**
 * Servlet implementation class searchUser
 */
@WebServlet("/searchUser")
public class searchUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchUser() {
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
		String countsqlString= "select count(*) as B from login";
		String rssqlString="select * from login where id like '%"+keywordString+"%' or username like '%"+keywordString+"%' or professionalTitle like '%"+keywordString+"%' "
				+" or phone like '%"+keywordString+"%'"
				+" or email like '%"+keywordString+"%'"
				+" or department like '%"+keywordString+"%'"
				+" or intrest like '%"+keywordString+"%'"
				+" or grade like '%"+keywordString+"%'";
		System.out.println(rssqlString);
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
		    	member.put("yhm", rsdev.getString("id"));
	            member.put("name", rsdev.getString("username"));
	            member.put("xy", rsdev.getString("department"));
	            member.put("grade", rsdev.getString("grade"));
	            array.add(member);  
		    }
		    member = new JSONObject();
    		member.put("totalSet", totalrecord);
            member.put("totalPage", totalPage);  
            member.put("curPage",requestPage);      //���͵ĵڼ�ҳ
            array.add(member);System.out.println(totalrecord);
		    json.put("book", array);
		    out.print(json.toString());System.out.println(json.toString());
		    connectDB.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
