<%@ page language="java" import="java.util.*, senPageUtils.AccessDBUtils" %>
<%
 ResourceBundle bundle = ResourceBundle.getBundle("senPage");
	ServletContext context = request.getServletContext();
 
	//out.print(bundle.getString("senPageConnURL"));
	
	AccessDBUtils adbu=new AccessDBUtils();
 //out.println(request.getRealPath("/"));
 adbu.vecParams=new Vector();
	

 String sql="select * from senpageTest";
 adbu.accessDB(context, sql, adbu.vecParams, "EQ","h2");
 out.print(adbu.dbException);
  
		
		//String sql="insert into senPageAlluserinfo values(3, 'resuser', 'respass')";
  //adbu.accessDB(sql, adbu.vecParams, "EU","h2");
  //String sql="create table senPageAlluserinfo(id int primary key, username varchar(30) NOT NULL, pass varchar(50) NOT NULL)";
  //adbu.accessDB(sql, adbu.vecParams, "EU","h2");

  /*StringBuffer rptBuff=new StringBuffer("");

  if(adbu.dbException==null)
  {
   rptBuff.append("<font color='green'>Success</font>");
  }
  else
  {
   rptBuff.append("<font color='red'>Error (1291): "+adbu.dbException+"</font>");
   adbu.closeAllConns();
  }

  out.print(rptBuff);
 */

  if(adbu.dbException==null)
  {
   try
   {
    while(adbu.res.next())
    {
     out.println(adbu.res.getString(1)+" <br/> "+adbu.res.getString(2));
    }
   }
   catch(Exception ex)
   {
    out.print(ex.toString());
   }
   finally
   {
    adbu.closeAllConns();
    out.println("done!");
   }
  }
  else
  {
   adbu.closeAllConns();
   out.println(adbu.dbException);
  }
 


/*String sPath=resource.getString("forum.path");
String sName=resource.getString("forum.database");

out.println("sPath: "+sPath);
out.println("sName: "+sName);*/

%>
