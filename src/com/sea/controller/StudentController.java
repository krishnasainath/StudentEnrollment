package com.sea.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sea.repository.StudentRepository;

@WebServlet("StudentController")
public class StudentController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StudentRepository studentRepository;
	
	private static String STUDENT_SIGNUP = "content/signup.jsp";
	private static String STUDENT_LOGIN = "content/login.jsp";
	private static String LOGIN_SUCCESS = "content/success.jsp";
	private static String LOGIN_FAILURE = "content/failure.jsp";
	
	public StudentController() {
		super();
		studentRepository = new StudentRepository();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = STUDENT_SIGNUP;
		RequestDispatcher view = request.getRequestDispatcher(forward);
	    view.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
	          HttpServletResponse response) throws ServletException, IOException {
	      String pageName = request.getParameter("Signup.jsp");
	      String forward = "";
	      
	      if (studentRepository != null) {
	          if (pageName.equals("signup")) {
	              if (studentRepository.findByUserName(request
	                      .getParameter("userName"))) {
	                  request.setAttribute("message", "User Name exists. Try another user name");
	                  forward = STUDENT_SIGNUP;
	                  RequestDispatcher view = request
	                          .getRequestDispatcher(forward);
	                  view.forward(request, response);
	                  return;
	              }

	              studentRepository.save(request.getParameter("userName"),
	                      request.getParameter("password"),
	                      request.getParameter("firstName"),
	                      request.getParameter("lastName"),
	                      request.getParameter("dateOfBirth"),
	                      request.getParameter("emailAddress"));
	              forward = STUDENT_LOGIN;
	          } else if (pageName.equals("login")) {
	              boolean result = studentRepository.findByLogin(
	                      request.getParameter("userName"),
	                      request.getParameter("password"));
	              if (result == true) {
	                  forward = LOGIN_SUCCESS;
	              } else {
	                  forward = LOGIN_FAILURE;
	              }
	          }
	      }
	      RequestDispatcher view = request.getRequestDispatcher(forward);
	      view.forward(request, response);
	  }
}
