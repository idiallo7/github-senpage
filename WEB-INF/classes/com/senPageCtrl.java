package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ResourceBundle;
import senpageUtils.SenpageUtils;


public class senpageCtrl extends HttpServlet
{
 private static final long serialVersionUID = 1L;
 private ResourceBundle bundle = ResourceBundle.getBundle("senpage");
 private String indexPage = "/jsp/index.jsp";
 private String request_view = null;
 private ServletContext context;

 //////////////////////////////////// session info////////////////////////////////////////////////

 public HttpSession theSession       = null;
 public String sessionUser           = null;
 public String sessionGroup          = null;
 public String sessionLogOff         = null;
 public String sessionCreationDate   = null;
 public String sessionLastAccessDate = null;

 private SenpageUtils adu           = new SenpageUtils();

 // doGet
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	//System.out.println("ctrl test 39");
  response.setContentType("text/html");
  response.setHeader("Pragma", "No-cache");
  response.setHeader("Cache-Control", "no-cache");

  this.context = this.getServletContext();
  PrintWriter out = response.getWriter();
  request_view = request.getParameter("request_view");

  this.theSession            = request.getSession(true);

  this.sessionUser           = (String)this.theSession.getAttribute("user");
  this.sessionGroup          = (String)this.theSession.getAttribute("sessionGroup");
  this.sessionLogOff         = (String)this.theSession.getAttribute("logOff");
  this.sessionCreationDate   = (String)this.theSession.getAttribute("create");
  this.sessionLastAccessDate = (String)this.theSession.getAttribute("last");

  this.sessionUser = (String)this.theSession.getAttribute("senpageUser");
  this.sessionGroup = (String)this.theSession.getAttribute("senpageGroup");

  SenpageUtils senu = new SenpageUtils();
		//System.out.println("60: res");

  if(request_view.equals("senpageGetLoginInfo"))
  {
   /*this.sessionUser = request.getParameter("user");
   this.sessionGroup = request.getParameter("grp");

   this.theSession.setAttribute("senpageUser", this.sessionUser);
   this.theSession.setAttribute("senpageGroup", this.sessionGroup);*/
  }
		/*else if(request_view.equals("main"))
		{
		 out.print(senu.senpageSetRpt(this.context, request, response, this.theSession));
		}*/
  else if(request_view.equals("senpageAjaxSetRpt"))
  {System.out.println("ctrl 75 - request_view: "+request_view);
   out.print(senu.senpageSetRpt(this.context, request, response, this.theSession));
  }
  else
  {
   System.out.println("senpageCtrl 103: Unlnown Request " + request_view);
  }
 } // end doGet

 /////////////////////////////////// doPost /////////////////////////////////////////////////////

 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
  response.setHeader("Pragma", "No-cache");
  response.setHeader("Cache-Control", "no-cache");
  doGet(request, response);
 }
}

