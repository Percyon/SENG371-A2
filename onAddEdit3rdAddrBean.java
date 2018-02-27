import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import oscar.*;
import java.text.*;
import java.net.*;
import oscar.oscarBilling.ca.on.data.*;
import org.apache.commons.lang.StringEscapeUtils;

public class onAddEdit3rdAddrBean
{
	private JspWriter out;
	private HttpServletRequest request;
	private boolean authed;
	private String roleName$;
	private boolean authed;
	
	public void init( HttpServletRequest requestArg, JspWriter outArg){
		out = outArg;
		request = requestArg;
	}

	public void roleNameSetter() throws IOException{
		roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");
	}

	public string roleNameGetter(){
		return roleName$;
	}

	public void setAuthed(boolean value){
        authed=value;
    }

	public boolean getAuthed() {
        return authed;
    }

    public void checkSessionForLogout() throws IOException{
        if (session.getAttribute("user") == null) {
            response.sendRedirect("../logout.jsp");
        }
        String user_no = (String) session.getAttribute("user");
    }

    
    

    public void public void handleRequestSubmit() throws IOExecution{
    	int serviceCodeLen = 5;
		String msg = "Type in a name and search first to see if it is available.";
		String action = "search"; // add/edit
		//BillingServiceCode serviceCodeObj = new BillingServiceCode();
		Properties prop = new Properties();
		JdbcBilling3rdPartImpl dbObj = new JdbcBilling3rdPartImpl();
		if (request.getParameter("submit") != null && request.getParameter("submit").equals("Save")) {
			if (request.getParameter("action").startsWith("edit")) {
				// update the service code
				String company_name = request.getParameter("company_name");
				if (company_name.equals(request.getParameter("action").substring("edit".length()))) {
					String list = "";
					Properties val = new Properties();
					val.setProperty("id", request.getParameter("id"));
					val.setProperty("attention", request.getParameter("attention"));
					val.setProperty("company_name", request.getParameter("company_name"));
					val.setProperty("address", request.getParameter("address"));
					val.setProperty("city", request.getParameter("city"));
					val.setProperty("province", request.getParameter("province"));
					val.setProperty("postcode", request.getParameter("postcode"));
					val.setProperty("telephone", request.getParameter("telephone"));
					val.setProperty("fax", request.getParameter("fax"));

					boolean ni = dbObj.update3rdAddr(request.getParameter("id"), val);
					if (ni) {
						msg = company_name + " is updated.<br>"
								+ "Type in a name and search first to see if it is available.";
						action = "search";
						prop.setProperty("company_name", company_name);
					} else {
						msg = company_name + " is <font color='red'>NOT</font> updated. Action failed! Try edit it again.";
						action = "edit" + company_name;
						prop.setProperty("company_name", company_name);
						prop.setProperty("id", request.getParameter("id"));
						prop.setProperty("attention", request.getParameter("attention"));
						prop.setProperty("company_name", request.getParameter("company_name"));
						prop.setProperty("address", request.getParameter("address"));
						prop.setProperty("city", request.getParameter("city"));
						prop.setProperty("province", request.getParameter("province"));
						prop.setProperty("postcode", request.getParameter("postcode"));
						prop.setProperty("telephone", request.getParameter("telephone"));
						prop.setProperty("fax", request.getParameter("fax"));
					}
				} else {
					msg = "You can <font color='red'>NOT</font> save the name - " + company_name
							+ ". Please search the name first.";
					action = "search";
					prop.setProperty("company_name", company_name);
				}

			} else if (request.getParameter("action").startsWith("add")) {
				String company_name = request.getParameter("company_name");
				if (company_name.equals(request.getParameter("action").substring("add".length()))) {
					Properties val = new Properties();
					val.setProperty("attention", request.getParameter("attention"));
					val.setProperty("company_name", request.getParameter("company_name"));
					val.setProperty("address", request.getParameter("address"));
					val.setProperty("city", request.getParameter("city"));
					val.setProperty("province", request.getParameter("province"));
					val.setProperty("postcode", request.getParameter("postcode"));
					val.setProperty("telephone", request.getParameter("telephone"));
					val.setProperty("fax", request.getParameter("fax"));
					int ni = dbObj.addOne3rdAddrRecord(val);
					if (ni > 0) {
						msg = company_name + " is added.<br>"
								+ "Type in a name and search first to see if it is available.";
						action = "search";
						prop.setProperty("company_name", company_name);
					} else {
						msg = company_name + " is <font color='red'>NOT</font> added. Action failed! Try edit it again.";
						action = "add" + company_name;
						prop.setProperty("company_name", company_name);
						prop.setProperty("attention", request.getParameter("attention"));
						prop.setProperty("company_name", request.getParameter("company_name"));
						prop.setProperty("address", request.getParameter("address"));
						prop.setProperty("city", request.getParameter("city"));
						prop.setProperty("province", request.getParameter("province"));
						prop.setProperty("postcode", request.getParameter("postcode"));
						prop.setProperty("telephone", request.getParameter("telephone"));
						prop.setProperty("fax", request.getParameter("fax"));
					}
				} else {
					msg = "You can <font color='red'>NOT</font> save the name - " + company_name
							+ ". Please search the name first.";
					action = "search";
					prop.setProperty("company_name", company_name);
				}
			} else {
				msg = "You can <font color='red'>NOT</font> save the name. Please search the name first.";
			}
		} else if (request.getParameter("submit") != null && request.getParameter("submit").equals("Search")) {
			// check the input data
			if (request.getParameter("company_name") == null) {
				msg = "Please type in a right name.";
			} else {
				String company_name = request.getParameter("company_name");
				Properties ni = dbObj.get3rdAddrProp(company_name);
				if (!ni.getProperty("company_name", "").equals("")) {
					prop = ni; //.setProperty("company_name", company_name);
					int n = 0;
					msg = "You can edit the name.";
					action = "edit" + company_name;

				} else {
					prop.setProperty("company_name", company_name);
					msg = "It is a NEW name. You can add it.";
					action = "add" + company_name;
				}
			}
		}
			
    }

}