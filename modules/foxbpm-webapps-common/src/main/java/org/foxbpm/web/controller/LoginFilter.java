package org.foxbpm.web.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.entity.UserEntity;

public class LoginFilter implements Filter {

	 
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		UserEntity user = (UserEntity)req.getSession().getAttribute("user");
		
		String path = req.getContextPath();
		if(user == null){
			res.sendRedirect(path+"/login.html");
		}else{
			chain.doFilter(request, response);   
		}
	}

	 
	public void destroy() {

	}

}
