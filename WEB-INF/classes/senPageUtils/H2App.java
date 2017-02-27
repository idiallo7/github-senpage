import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.io.IOException;
import java.util.*;

public class H2App
{
 //public static ResourceBundle bundle = ResourceBundle.getBundle("senBudget");
 //private static final String dbInfo = bundle.getString("senBudgetDb");


 public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
 {
  Class.forName("org.h2.Driver"); // (1)
  //Connection conn = DriverManager.getConnection("jdbc:h2:file:C:/senBudget/tmp/h2Db/senBudgetDb"); // (2)
    Connection conn =DriverManager.getConnection("jdbc:h2:tcp://localhost/~/testez");
    Properties prop = new Properties();
    prop.setProperty("XX", "XXX");

  Statement stat = conn.createStatement(); // (3)
  stat.executeUpdate("create table senBudgetAlluserinfo(id int primary key, name varchar(255))");
  stat.executeUpdate("insert into senBudgetAlluserinfo values(1, 'Hello')");
  stat.executeUpdate("insert into senBudgetAlluserinfo values(2, 'World')");

  ResultSet rs = stat.executeQuery("select * from senBudgetAlluserinfo");
  System.out.println("ResultSet output:");

  while (rs.next())
  {
   System.out.println("> " + rs.getString(1));
  }

  rs.close();
  conn.close();
  System.out.println("All conns are closed");
 }
}

