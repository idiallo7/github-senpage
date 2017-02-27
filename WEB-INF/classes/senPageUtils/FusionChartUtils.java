package senBudgetUtils;

import javax.servlet.*;
import java.sql.*;
import java.util.*;
import com.google.gson.*;
import senBudgetUtils.FusionCharts;
import senBudgetUtils.SenBudgetUtils;
import senBudgetUtils.AccessDBUtils;

public class FusionChartUtils
{
 private ResourceBundle bundle = ResourceBundle.getBundle("senBudget");
 private static ServletContext context =null;

 private Map<String, String> chartobj = new HashMap<String, String>();
 private ArrayList arrData = new ArrayList();
 private Map<String, String> dataMap = new LinkedHashMap<String, String>();
 private Gson gson = new Gson();

 private AccessDBUtils adbu=new AccessDBUtils();
 private SenBudgetUtils sen=new SenBudgetUtils();

 /////////////////////////////////////////////////////////////// fusionChartSetRpt ////////////////////////////////////////////////////
 public synchronized String fusionChartSetRpt(ServletContext context, String sqlProp)
 {
  this.context=context;

  adbu.dbException = null;
  adbu.vecParams = new Vector();

  String sql = sen.readFile(context.getRealPath(bundle.getString(sqlProp)));
  adbu.accessDB(context, sql, adbu.vecParams, "EQ", "h2");

  if(adbu.dbException == null)
  {
   try
   {
    while (adbu.res.next())
    {
     Map<String, String> lv = new HashMap<String, String>();
     lv.put("label", adbu.res.getString(3));
     lv.put("value", adbu.res.getString(4));

     arrData.add(lv);
    }
   }
   catch (Exception ex)
   {
    System.out.println("Error 46: "+adbu.dbException);
   }
   finally
   {
    adbu.closeAllConns();
   }
  }
  else
  {
   System.out.println("Error 46: "+adbu.dbException);
  }

  return gson.toJson(arrData).toString() ;
 }

 public static void main(String[] args)
 {

 }
}
