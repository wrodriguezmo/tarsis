package com.tarsis;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imsglobal.lti.launch.LtiOauthVerifier;
import org.imsglobal.lti.launch.LtiVerificationException;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.imsglobal.lti.launch.LtiVerifier;
import org.imsglobal.pox.IMSPOXRequest;

import oauth.signpost.exception.OAuthException;

public class urlCoursera extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853895113249053138L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		LtiVerifier ltiVerifier = new LtiOauthVerifier();
		String key = request.getParameter("oauth_consumer_key");
		//Verifica que sea un request
		try {
			LtiVerificationResult ltiResult = ltiVerifier.verify(request, "secret");
			if(ltiResult.getSuccess()) {
				try {
					IMSPOXRequest.sendReplaceResult(request.getParameter("lis_outcome_service_url"), key, "secret", request.getParameter("lis_result_sourcedid"), "0.2");
					response.getWriter().println("Hello World.!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OAuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (LtiVerificationException e) {
			e.printStackTrace();
		}
			
//		LtiVerifier ltiVerifier = new LtiOauthVerifier();
//		String key = request.getParameter("oauth_consumer_key");
//		String secret = // retrieve corresponding secret for key from db
//		LtiVerificationResult ltiResult = ltiVerifier.verify(request, secret);
//		LtiClass.manageRequest(request);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.getWriter().println("Bye World.!");
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
