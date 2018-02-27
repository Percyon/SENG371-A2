import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
<%@ page errorPage="../../../appointment/errorpage.jsp"
	import="java.util.*,java.sql.*,oscar.*,java.text.*,java.net.*"%>
<%@ page import="oscar.oscarBilling.ca.on.data.*"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

public class onAddEdit3rdAddrBean
{
	private JspWriter out;
	private HttpServletRequest request;
	public void init( HttpServletRequest requestArg, JspWriter outArg)
	{
		out = outArg;
		request = requestArg;
	}

	public void doSomething() throws IOException
	{

	/%Authentation%/

		out.println(" <%@ taglib uri=\"/WEB-INF/security.tld\" prefix=\"security\"%> ");
      		String roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");
      		boolean authed=true;


		out.println("<security:oscarSec roleName="  + roleName$ +  "objectName=\"_billing\" rights=\"w\" reverse=" + true + ">");
	  		authed=false;
	  		response.sendRedirect("../../../securityError.jsp?type=_billing");
		out.println</security:oscarSec>

		if(!authed) {
			return;
		}


		if (session.getAttribute("user") == null) {
			response.sendRedirect("../logout.jsp");
		}
			String user_no = (String) session.getAttribute("user");
	

	}


}