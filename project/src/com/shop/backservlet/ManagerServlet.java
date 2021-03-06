package com.shop.backservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.beans.Manager;
import com.shop.dao.ManagerDao;
import com.shop.daoimpl.ManagerDaoImpl;

public class ManagerServlet extends BaseServlet {

	// 1.登录
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 接收数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 处理请求
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		Manager manager = new Manager();
		manager.setM_name(username);
		manager.setM_password(password);
		manager = managerDaoImpl.managerLogin(manager);
		// 发回响应
		if (manager == null) {
			request.getRequestDispatcher("backmanger/adminLogin1.jsp").forward(
					request, response);
		} else {
			request.getSession().setAttribute("manager", manager);
			request.getRequestDispatcher("backmanger/index1.jsp").forward(
					request, response);
		}
	}

	// 2.安全退出
	public void exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 移除session中的数据
		request.getSession().removeAttribute("manager");
		// request.getRequestDispatcher("backmanger/adminLogin.jsp").forward(
		// request, response);
		String path = request.getContextPath();
		// System.out.println(path);
		response.sendRedirect(path + "/backmanger/adminLogin.jsp");//
	}

	// 3.验证密码
	public void pwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// 获取页面的密码
		String password = request.getParameter("oldpassword");
		Manager manager = (Manager) request.getSession()
				.getAttribute("manager");
		String realpwd = manager.getM_password();
		if (password.equals(realpwd)) {
			out.print(1);
		} else {
			out.print(2);
		}
		out.flush();
		out.close();
	}

	// 4.修改密码
	public void m_pwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// 获取页面的数据
		String password = request.getParameter("newpassword");
		Manager manager = (Manager) request.getSession()
				.getAttribute("manager");
		manager.setM_password(password);
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		boolean flag = managerDaoImpl.managerUpdatePwd(manager);
		if (flag == true) {
			out.print(1);
		} else {
			out.print(2);
		}
		out.flush();
		out.close();
	}

	// 5.管理员列表页面
	public void managerFindAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Manager manager = (Manager) request.getSession()
				.getAttribute("manager");
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		List<Manager> list = managerDaoImpl.managerFindAll(manager.getM_id());
		request.setAttribute("list", list);
		request.getRequestDispatcher("backmanger/admin_list.jsp").forward(
				request, response);
	}

	// 6.添加管理员
	public void add_manager(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String m_name = request.getParameter("m_name");
		String m_password = request.getParameter("m_password");
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		Manager manager = new Manager();
		manager.setM_name(m_name);
		manager.setM_password(m_password);
		manager.setRole(2);
		boolean flag = managerDaoImpl.managerAdd(manager);
		if (flag == true) {
			out.print(1);
		} else {
			out.print(2);
		}
		out.flush();
		out.close();
	}

	// 7.修改管理员权限
	public void revise_manager(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String mid = request.getParameter("m_id");
		int m_id = Integer.parseInt(mid);
		String role = request.getParameter("m_role");
		int m_role = Integer.parseInt(role);
		//System.out.println(m_role);
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		boolean flag = managerDaoImpl.managerUpdate(m_id, m_role);
		if (flag == true) {
			out.print(1);
		} else {
			out.print(2);
		}
		out.flush();
		out.close();
	}

	// 8.删除管理员
	public void del_manager(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String mid = request.getParameter("m_id");
		int m_id = Integer.parseInt(mid);
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		boolean flag = managerDaoImpl.managerDel(m_id);
		if (flag == true) {
			out.print(1);
		} else {
			out.print(2);
		}
		out.flush();
		out.close();
	}

	// 9.添加管理员账号验证
	public void m_name(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String m_name = request.getParameter("m_name");
		ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
		boolean flag = managerDaoImpl.managerAddNameVerify(m_name);
		if (flag == true) {
			out.print(1);
		} else {
			out.print(2);
		}
		out.flush();
		out.close();
	}

}
