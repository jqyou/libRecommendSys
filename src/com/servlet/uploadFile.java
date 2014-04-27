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
        //�ж��ύ�����ı��Ƿ�Ϊ�ļ��ϴ��˵�  
        boolean isMultipart= ServletFileUpload.isMultipartContent(request);  
        if(isMultipart){  
            //����һ���ļ��ϴ��������  
            FileItemFactory factory = new DiskFileItemFactory();  
            ServletFileUpload upload = new ServletFileUpload(factory);  
  
            Iterator items;  
            try{  
                //���������ύ�������ļ�����  
                items = upload.parseRequest(request).iterator();  
                while(items.hasNext()){  
                    FileItem item = (FileItem) items.next();  
                    if(!item.isFormField()){  
                        //ȡ���ϴ��ļ����ļ�����  
                        String name = item.getName();  
                        //ȡ���ϴ��ļ��Ժ�Ĵ洢·��  
                        String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());  
                        //�ϴ��ļ��Ժ�Ĵ洢·��  
                        String saveDir = this.getServletContext().getRealPath("/upload/");
                        System.out.println("realpath:"+saveDir);
                        if (!(new File(saveDir).isDirectory())){  
                            new File(saveDir).mkdir();  
                        }  
                        String path= saveDir + File.separatorChar + fileName;  
  
                        //�ϴ��ļ�  
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
