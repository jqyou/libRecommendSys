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
 * Servlet Filter implementation class roleFilter
 */
@WebFilter("/roleFilter")
public class roleFilter implements Filter {

    /**
     * Default constructor. 
     */
    public roleFilter() {
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
		
		// pass the request along the filter chain
		HttpSession session = ((HttpServletRequest)request).getSession();
		//System.out.println(session.getAttribute("user"));
		if(session.getAttribute("user")==null){
			PrintWriter out = response.getWriter();
			System.out.println("no login");
			out.print("<script language=javascript>alert('hahahhah');window.location.href='Log_in.jsp';</script>");
		}else{
		   chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
