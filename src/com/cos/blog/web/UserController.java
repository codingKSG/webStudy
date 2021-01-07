package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;

//http://localhost:8080/blog/user
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		
		UserService userService = new UserService();
		// http://localhost:8080/blog/user?cmd=loginForm
		
		if(cmd.equals("loginForm")) {
			// ���� ȣ��
			response.sendRedirect("user/loginForm.jsp");
		} else if(cmd.equals("login")) {
			// ���� ȣ��
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			User userEntity = userService.�α���(dto);
			 if(userEntity != null) {
				 HttpSession session = request.getSession();
				 session.setAttribute("principal", userEntity);
				 response.sendRedirect("index.jsp");
			 } else {
				 Script.back(response, "�α��� ����");
			 }
			
		} else if(cmd.equals("joinForm")) {
			response.sendRedirect("user/joinForm.jsp");
		} else if(cmd.equals("join")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			dto.setAddress(address);
			int result = userService.ȸ������(dto);
			
			if(result == 1) {
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "ȸ������ ����");
			}
		} else if(cmd.equals("usernameCheck")) {
			BufferedReader br = request.getReader();
			String username = br.readLine();
			System.out.println(username);
			
			int result = userService.���������ߺ�üũ(username);
			PrintWriter out = response.getWriter();
			if(result == 1) {
				out.print("ok");
			} else {
				out.print("fail");
			}
			out.flush();
		} else if(cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
		
	}
}