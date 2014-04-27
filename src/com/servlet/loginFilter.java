package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class loginFilter
 */
@WebFilter("/loginFilter")
public class loginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public loginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		StringBuffer pathString = ((HttpServletRequest)request).getRequestURL();
		System.out.println(pathString.toString());
		if (pathString.toString().endsWith("Login.html") || pathString.toString().endsWith("AdminRegister.html")
				|| pathString.toString().endsWith("LibRecommendSys/") || pathString.toString().endsWith("Register.html")){
			chain.doFilter(request, response);
		}else {
			// pass the request along the filter chain
			HttpSession session = ((HttpServletRequest)request).getSession();
			//System.out.println(session.getAttribute("user"));
			if(session.getAttribute("pid") == null){
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				System.out.println("no login");
				out.print("<script language=javascript>alert('Äú»¹Î´µÇÂ½');window.location.href='/LibRecommendSys/html/Login.html';</script>");
			}else{
			   chain.doFilter(request, response);
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
