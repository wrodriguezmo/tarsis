package com.tarsis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class urlCoursera extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		LtiVerifier ltiVerifier = new LtiOauthVerifier();
		String key = request.getParameter("oauth_consumer_key");
		String secret = // retrieve corresponding secret for key from db
		LtiVerificationResult ltiResult = ltiVerifier.verify(request, secret);
		response.getWriter().println("Hello World!");
	}
	@Override
	public void init() throws ServletException {
		System.out.println("Servlet " + this.getServletName() + " has started");
	}
	@Override
	public void destroy() {
		System.out.println("Servlet " + this.getServletName() + " has stopped");
	}

}
