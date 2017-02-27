package senpageUtils;
import java.util.*;
import java.sql.*;
import javax.servlet.*;

public class AccessDBUtils
{
 public  ResultSet res         =null;
 public  Connection conn       =null;
 public  String dbException    =null;
 public  Statement st          =null;
 public  PreparedStatement ps  =null;
 public  Vector vecParams      =null;
 private ResourceBundle bundle =ResourceBundle.getBundle("senpage");
 private TriplePassEncryptor myEncryptor= new TriplePassEncryptor();


 /////////////////////////////////////////////////////////// accessDB /////////////////////////////////////////////////////////////////
 public synchronized String accessDB(ServletContext context, String myQuery, Vector vecSqlParams, String execType, String runType)
 {
  try
  {
   Class.forName(bundle.getString("driverInfo"));

   if(runType.equals("mysql"))
   {
    conn = DriverManager.getConnection(bundle.getString("mySqlPluConnURL")+bundle.getString("mySqlPluDbName"), myEncryptor.decrypt(bundle.getString("mySqlPluDbUser")), myEncryptor.decrypt(bundle.getString("mySqlPluDbPass")));
   }
   else if(runType.equals("mysqlRemote"))
   {
    conn = DriverManager.getConnection(bundle.getString("mySqlPluRemoteConnURL")+bundle.getString("mySqlPluRemoteDbName"), bundle.getString("mySqlPluDbUser"), bundle.getString("mySqlPluDbPass"));
   }
   else if(runType.equals("mysqlNoPass"))
   {
    conn = DriverManager.getConnection(bundle.getString("mySqlPluConnURL")+bundle.getString("mySqlPluDbName"), bundle.getString("mySqlPluDbUser"), bundle.getString("mySqlPluDbPass"));
   }
   else if(runType.equals("h2"))
   {
    System.out.println(context.getRealPath("/"));
    System.out.println("from accessdbUtils 40 "+bundle.getString("senpageConnURL")+context.getRealPath("/")+bundle.getString("senpageDbName"));
    System.out.println("from accessdbutil 41: "+myEncryptor.decrypt(bundle.getString("senpageDbUser"))+" "+myEncryptor.decrypt(bundle.getString("senpageDbPass")));
    conn = DriverManager.getConnection(bundle.getString("senpageConnURL")+context.getRealPath("/")+bundle.getString("senpageDbName"), myEncryptor.decrypt(bundle.getString("senpageDbUser")), myEncryptor.decrypt(bundle.getString("senpageDbPass")));
   }
   else
   {
    dbException="Invalid run type";
   }

   if(dbException == null || myEncryptor.dbException==null)
   {
    st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    if(vecSqlParams.isEmpty())
    {
     if(execType.equals("EQ"))
     {
      res=st.executeQuery(myQuery);
     }
     else
     {
      st.executeUpdate(myQuery);
     }
    }
    else //preparedStatement
    {
     PreparedStatement ps=conn.prepareStatement(myQuery, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
     int z=0;

     for(int i=0; i<vecSqlParams.size(); i++)
     {
      z+=1;
      ps.setString(z, (String)vecSqlParams.elementAt(i));
     }

     if(execType.equals("EQ"))
     {
      res=ps.executeQuery();
     }
     else
     {
      try
      {
       ps.executeUpdate();
      }
      catch(SQLException sqlEx)
      {
       dbException=sqlEx.toString();
      }
      catch(Exception ex)
      {
       dbException="Encryptor: "+myEncryptor.dbException+"\ndbException: "+ex.toString();
      }
     }
    }
   }//end new IF
  }
  catch(Exception e)
  {
   dbException=e.toString();
  }
  return dbException+" error in accessDB";
 }

 /////////////////////////////////////////////////////////// accessDB overloaded with vecUserInfo /////////////////////////////////////////////////////////////////
 public synchronized String accessDB(String myQuery, Vector vecSqlParams, String execType, String runType, Vector vecUserInfo)
 {
  String db  =vecUserInfo.elementAt(0).toString();
  String user=vecUserInfo.elementAt(1).toString();
  String pass=vecUserInfo.elementAt(2).toString();

  try
  {
   Class.forName(bundle.getString("driverInfo"));

   if(runType.equals("mysql"))
   {
    conn = DriverManager.getConnection(bundle.getString("mySqlPluConnURL")+bundle.getString("mySqlPluDbName"), myEncryptor.decrypt(bundle.getString("mySqlPluDbUser")), myEncryptor.decrypt(bundle.getString("mySqlPluDbPass")));
   }
   else if(runType.equals("mysqlRemote"))
   {
    conn = DriverManager.getConnection(bundle.getString("mySqlPluRemoteConnURL")+bundle.getString("mySqlPluRemoteDbName"), bundle.getString("mySqlPluDbUser"), bundle.getString("mySqlPluDbPass"));
   }
   else if(runType.equals("mysqlNoPass"))
   {
    conn = DriverManager.getConnection(bundle.getString("mySqlPluConnURL")+bundle.getString("mySqlPluDbName"), bundle.getString("mySqlPluDbUser"), bundle.getString("mySqlPluDbPass"));
   }
   else if(runType.equals("h2"))
   {
    conn = DriverManager.getConnection(bundle.getString("senPageConnURL")+bundle.getString("senPageDbName"));
   }
   else
   {
    dbException="Invalid run type";
   }

   if(dbException == null)
   {
    st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    if(vecSqlParams.isEmpty())
    {
     if(execType.equals("EQ"))
     {
      res=st.executeQuery(myQuery);
     }
     else
     {
      st.executeUpdate(myQuery);
     }
    }
    else //preparedStatement
    {
     PreparedStatement ps=conn.prepareStatement(myQuery, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
     int z=0;

     for(int i=0; i<vecSqlParams.size(); i++)
     {
      z+=1;

      ps.setString(z, (String)vecSqlParams.elementAt(i));
     }

     if(execType.equals("EQ"))
     {
      res=ps.executeQuery();
     }
     else
     {
      try
      {
       ps.executeUpdate();
      }
      catch(SQLException sqlEx)
      {
       dbException=sqlEx.toString();
      }
      catch(Exception ex)
      {
       dbException=ex.toString();
      }
     }
    }
   }//end new IF
  }
  catch(Exception e)
  {
   dbException=e.toString();
  }
  return dbException+" error in accessDB";
 }
 /////////////////////////////////////////////////////////// closeAllConns /////////////////////////////////////////////////////////////////
 public synchronized void closeAllConns()
 {
  try
  {
   if(res!=null)
   {
    res.close();
   }
   if(st!=null)
   {
    st.close();
   }
   if(conn!=null)
   {
    conn.close();
   }
  }
  catch(Exception ex)
  {
   this.dbException="Error closing connections: "+ex.toString();
  }
 }

 /////////////////////////////////////////////////////////// closeConn /////////////////////////////////////////////////////////////////
 public synchronized void closeConn(Connection conn)
 {
  try
  {
   if(conn!=null)
   {
    conn.close();
   }
  }
  catch(Exception ex)
  {
   this.dbException="Error closing connections: "+ex.toString();
  }
 }


 /////////////////////////////////////////////////////////// main /////////////////////////////////////////////////////////////////
 public static void main(String[] args)
 {
  AccessDBUtils adbu=new AccessDBUtils();
		//ServletContext context=
//System.out.println(context.getRealPath("/"));
  adbu.vecParams=new Vector();

  String sql="select * from senPageAlluserinfo";
		//System.out.println(sql);
  //adbu.accessDB(sql, adbu.vecParams, "EQ","h2");

  /*String sql="insert into senPageAlluserinfo values(3, 'resuser', 'respass')";
  adbu.accessDB(sql, adbu.vecParams, "EU","h2");
  //String sql="create table senPageAlluserinfo(id int primary key, username varchar(30) NOT NULL, pass varchar(50) NOT NULL)";
  //adbu.accessDB(sql, adbu.vecParams, "EU","h2");

  StringBuffer rptBuff=new StringBuffer("");

  if(adbu.dbException==null)
  {
   rptBuff.append("<font color='green'>Success</font>");
  }
  else
  {
   rptBuff.append("<font color='red'>Error (1291): "+adbu.dbException+"</font>");
   adbu.closeAllConns();
  }

  //System.out.println(rptBuff);
 */

  if(adbu.dbException==null)
  {
   try
   {
    while(adbu.res.next())
    {
     System.out.println(adbu.res.getString(1)+" | "+adbu.res.getString(2)+" | "+adbu.res.getString(3));
    }
   }
   catch(Exception ex)
   {
    System.out.println(ex.toString());
   }
   finally
   {
    adbu.closeAllConns();
    System.out.println("done!");
   }
  }
  else
  {
   adbu.closeAllConns();
   System.out.println(adbu.dbException);
  }
 }
}
