package org.foxbpm.web.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 全局环境初始化、工作流引擎初始化等等
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class InitializeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitializeServlet() {
		super();
	}

	/**
	 * 初始化方法
	 * 
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}
}
