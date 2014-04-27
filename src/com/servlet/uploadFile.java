package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class uploadFile
 */
@WebServlet("/uploadFile")
public class uploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadFile() {
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
		request.setCharacterEncoding("utf-8");  
        //判断提交过来的表单是否为文件上传菜单  
        boolean isMultipart= ServletFileUpload.isMultipartContent(request);  
        if(isMultipart){  
            //构造一个文件上传处理对象  
            FileItemFactory factory = new DiskFileItemFactory();  
            ServletFileUpload upload = new ServletFileUpload(factory);  
  
            Iterator items;  
            try{  
                //解析表单中提交的所有文件内容  
                items = upload.parseRequest(request).iterator();  
                while(items.hasNext()){  
                    FileItem item = (FileItem) items.next();  
                    if(!item.isFormField()){  
                        //取出上传文件的文件名称  
                        String name = item.getName();  
                        //取得上传文件以后的存储路径  
                        String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());  
                        //上传文件以后的存储路径  
                        String saveDir = this.getServletContext().getRealPath("/upload/");
                        System.out.println("realpath:"+saveDir);
                        if (!(new File(saveDir).isDirectory())){  
                            new File(saveDir).mkdir();  
                        }  
                        String path= saveDir + File.separatorChar + fileName;  
  
                        //上传文件  
                        File uploaderFile = new File(path);  
                        item.write(uploaderFile);  
                    }  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);    
                return;    
            }  
            response.setStatus(HttpServletResponse.SC_OK);  
            response.getWriter().append("Success Upload");  
        }   
    }   

}
