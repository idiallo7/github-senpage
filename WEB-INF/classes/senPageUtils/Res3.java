import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class Res3
{
 public static void main(String[] argv) throws Exception
 {

  // Read properties file.
  Properties properties = new Properties();
  InputStream is = null;
  try
  {
   //properties.load(new FileInputStream("filename.properties"));
   is = this.getClass().getResourceAsStream("/senBudget.properties");
   prop.load(is);
  }
  catch(IOException e)
  {
   System.out.println(e.toString());
  }

  // Write properties file.

 InputStream out = null;

  try
  {
   properties.store(new FileOutputStream("filename.properties"), null);

   out = this.getClass().getResourceAsStream("/senBudget.properties");
   prop.store(out);
  }
  catch(IOException e)
  {
   System.out.println(e.toString());
  }
 }
}
