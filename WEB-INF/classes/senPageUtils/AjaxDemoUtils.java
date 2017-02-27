package senPageUtils;

import java.util.*;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.text.*;
import com.google.gson.Gson;


public class SenPageUtils
{
 public ResourceBundle bundle = ResourceBundle.getBundle("ajaxDemo");
 private String tmpPage = "/jsp/tmp.jsp";
 private ServletContext context = null;
 public AccessDBUtils adbu = new AccessDBUtils();
 public String readFileExpt = null;
 public String writeFileExpt = null;
 private String pageInfo = null;


 ///////////////////////////////////////////// ajaxDemoSetRpt /////////////////////////////////////////
 public synchronized String ajaxDemoSetRpt(ServletContext context, HttpServletRequest request, HttpServletResponse response, HttpSession ajaxDemoSession)
 {
  this.context = context;

  // this.sessionNav =(String)rtcmSession.getAttribute("sessionNav");
  String sessionUser = (String)ajaxDemoSession.getAttribute("ajaxDemoUser");
  String sessionGrp = (String)ajaxDemoSession.getAttribute("ajaxDemoGroup");

  String requestType = request.getParameter("requestType");
  StringBuffer buff = new StringBuffer("");
  System.out.println("requestType curr request_type: " + requestType);
  if(requestType.equals("main"))
  {
   buff.append(requestType);
   //buff.append(this.setPageData("ajaxDemoSplash"));
  }
  else if(requestType.equals("ajaxDemoGetJasonOut"))
  {
   buff.append(this.ajaxDemoUtilSsetJasonOut(request.getParameter("category")));
   //buff.append(this.setPageData("latticeBrand"));
  }
  else if(requestType.equals("ajaxDemoCurrUser"))
  {
   buff.append(sessionUser + "~~" + sessionGrp);
  }
  else if(requestType.equals("ajaxDemoSetPageInfo"))
  {
   buff.append(this.pageInfo);
  }
  else if(requestType.equals("ajaxDemoSignOff"))
  {
   ajaxDemoSession.removeAttribute("ajaxDemoUser");
   ajaxDemoSession.invalidate();
  }
  else
  {
   buff.append("384: Unkn request - " + requestType);
  }

  return buff.toString();
 }
 /*/////////////////////////////////////////////////////// //setJsonout*/ ///////////////////////////////
 public synchronized String ajaxDemoUtilSsetJasonOut(String category)
 {
  List<String> result = new ArrayList<String>();

  if(category.equalsIgnoreCase("serial"))
  {
   result.add("Game Of Throme");
   result.add("Prison Break");
   result.add("Breaking Bad");
   result.add("Sherlok Home");
   result.add("Suits");
  }
  else if(category.equalsIgnoreCase("movies"))
  {
   result.add("Inception");
   result.add("War Horse");
   result.add("Avatar");
   result.add("Titanic");
   result.add("Life is Beautiful");
  }
  else if(category.equalsIgnoreCase("sports"))
  {
   result.add("Basket Ball");
   result.add("Football");
   result.add("Tennis");
   result.add("Rugby");
   result.add("Cricket");
  }
  else
  {
   result.add("unkn category");
   //result.add(this.setWarningPage("96: unkn"));
  }

  String json = new Gson().toJson(result);
  //response.setContentType("application/json");
  //response.getWriter().write(json);
  return json;
 }
 //  setFwdDispatcher//////////////////////////////////////////////////
 public synchronized void setFwdDispatcher(HttpServletRequest req, HttpServletResponse res, ServletContext context, String fwdUrl)
 {
  try
  {
   RequestDispatcher dispatcher = context.getRequestDispatcher(fwdUrl);
   dispatcher.forward(req, res);
  } catch (Exception ex)
  {
   ex.printStackTrace();
  }
 }

 /////////////////////////////////// setFwdDispatcher overloaded with theSession////////////////////////////////////////
 public void setFwdDispatcher(HttpServletRequest req, HttpServletResponse res, ServletContext context, HttpSession theSession, String fwdUrl)
 {
  try
  {
   RequestDispatcher dispatcher = context.getRequestDispatcher(fwdUrl);
   dispatcher.forward(req, res);
  } catch (Exception ex)
  {
   ex.printStackTrace();
   // logger.error(ex);
  }
 }

 //////////////////////////////////////////////////////// setRedirectDispatcher/////////////////////////////////////////////////
 public synchronized void setRedirectDispatcher(HttpServletRequest req, HttpServletResponse res, ServletContext context, String fwdUrl)
 {
  try
  {
   String myUrl = res.encodeRedirectURL(fwdUrl);
   res.sendRedirect(myUrl);
  } catch (Exception ex)
  {
   ex.printStackTrace();
  }
 }

 ///////////////////////////////////////////////////////////// setUserInfo///////////////////////////////////////////////
 public synchronized String setUserInfo(String sessionUser)
 {
  String userVal = "";
  if(sessionUser == null)
  {
   userVal = "null";
  }
  else
  {
   userVal = sessionUser;
  }
  return userVal.toString();
 }

 //////////////////////////////////////////////////////////////// setPageData //////////////////////////////////////////////
 public synchronized String setPageData(String pageName)
 {
  StringBuffer pageDataBuff = new StringBuffer("");
  String fileData = "";
  try
  {
   fileData = this.readFile(this.context.getRealPath(this.bundle.getString(pageName)));
  } catch (Exception ex)
  {
   this.readFileExpt = ex.toString();
  }

  if(this.readFileExpt != null)
  {
   pageDataBuff.append(this.setErrorPage(this.readFileExpt));
  }
  else
  {
   pageDataBuff.append(fileData);
  }
  return pageDataBuff.toString();
 }

 ////////////////////////////////// setPageData overloaded with context and bundle//////////////////////
 public synchronized String setPageData(ServletContext context, ResourceBundle bundle, String pageName)
 {
  StringBuffer pageDataBuff = new StringBuffer("");
  String fileData = "";
  try
  {
   fileData = this.readFile(context.getRealPath(bundle.getString(pageName)));
  } catch (Exception ex)
  {
   this.readFileExpt = ex.toString();
  }

  if(this.readFileExpt != null)
  {
   pageDataBuff.append(this.setErrorPage(this.readFileExpt));
  }
  else
  {
   pageDataBuff.append(fileData);
  }

  return pageDataBuff.toString();
 }

 /////////////////////////////////////////////// setErrorPage////////////////////////////////////////
 public synchronized String setErrorPage(String msg)
 {
  StringBuffer errorPageBuff = new StringBuffer("");

  errorPageBuff.append("<div class=\"alert alert-danger alert-error\">");
  errorPageBuff.append(" <a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>");
  errorPageBuff.append("   <h5><strong>Error!</strong> " + msg + "</h5>");
  errorPageBuff.append("</div>");

  return errorPageBuff.toString();
 }

 //////////////////////////////////////////// setWarningPage////////////////////////////////////
 public synchronized String setWarningPage(String msg)
 {
  StringBuffer errorPageBuff = new StringBuffer("");

  errorPageBuff.append("<div class=\"alert alert-warning\">");
  errorPageBuff.append(" <a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>");
  errorPageBuff.append("   <strong>Warning!</strong> " + msg);
  errorPageBuff.append("</div>");

  return errorPageBuff.toString();
 }

 /////////////////////////////////////////// setSuccessPage//////////////////////////////////////
 public synchronized String setSuccessPage(String msg)
 {
  StringBuffer errorPageBuff = new StringBuffer("");

  errorPageBuff.append("<div class=\"alert alert-success\">");
  errorPageBuff.append(" <a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>");
  errorPageBuff.append("   <h5>" + msg + "</h5>");
  errorPageBuff.append("</div>");

  return errorPageBuff.toString();
 }

 ///////////////////////////////////////////// insertNewTrans//////////////////////////////////
 public synchronized void insertNewTrans(Vector<String> vecParams, String sqlProp, String transType)
 {
  new StringBuffer("");
  String sql = this.readFile(this.context.getRealPath(this.bundle.getString(sqlProp)));
  this.adbu.vecParams = vecParams;

  /*
   * for(int i=0; i<vecParams.size(); i++) {
   * System.out.println(vecParams.elementAt(i)); }
   */
  this.excecUpdtQry(sql, this.adbu.vecParams);

  if(this.adbu.dbException == null)
  {
   this.pageInfo = this.setSuccessPage("New " + transType + " successfully added.");
  }
  else
  {
   this.pageInfo = this.setErrorPage("Error 562 " + this.adbu.dbException);
  }
 }

 ////////////////////////////////////////////// excecUpdtQry////////////////////////////////
 public synchronized void excecUpdtQry(String sql, Vector<String> vecParams)
 {
  this.adbu.dbException = null;
  this.adbu.vecParams = new Vector<String>();
  this.adbu.vecParams = vecParams;

  this.adbu.accessDB(this.context, sql, this.adbu.vecParams, "EU", "desk");
  adbu.closeAllConns();
 }

 ////////////////////////////////////////////////////// excecQry ////////////////////////////////////
 public synchronized Vector<String> excecQry(String sql, int fields)
 {
  Vector<String> vecDataRes = new Vector<String>();
  this.adbu.dbException = null;
  this.adbu.vecParams = new Vector<String>();
  this.adbu.accessDB(this.context, sql, this.adbu.vecParams, "EQ", "desk");

  if(adbu.dbException == null)
  {
   try
   {
    while (this.adbu.res.next())
    {
     for (int i = 1; i < fields + 1; i++)
     {
      vecDataRes.add(this.adbu.res.getString(i));
     }
    }
   } catch (Exception ex)
   {
    vecDataRes.add(ex.toString());
   } finally
   {
    adbu.closeAllConns();
   }
  }
  else
  {
   System.out.println(this.adbu.dbException);
  }
  return vecDataRes;
 }

 /////////////////////////////////////////////// excecQry////////////////////////////////////
 public synchronized Vector<String> excecQry(String sql, Vector<String> vecParams, int fields)
 {
  Vector<String> vecDataRes = new Vector<String>();
  this.adbu.dbException = null;
  this.adbu.vecParams = vecParams;
  this.adbu.accessDB(this.context, sql, this.adbu.vecParams, "EQ", "desk");
  if(adbu.dbException == null)
  {
   try
   {
    while (this.adbu.res.next())
    {
     for (int i = 1; i < fields + 1; i++)
     {
      vecDataRes.add(this.adbu.res.getString(i));
     }
    }
   } catch (Exception ex)
   {
    vecDataRes.add(ex.toString());
   } finally
   {
    this.adbu.closeAllConns();
   }
  }
  else
  {
   System.out.println(this.adbu.dbException);
  }

  return vecDataRes;
 }

 ////////////////////////////////////////////////////// excecQryTr////////////////////////////////////////////////
 public synchronized Vector<Object> excecQryTr(String sql, Vector<String> vecParams, int fields)
 {
  Vector<Object> vecAll = new Vector<>();
  Vector<String> vecDataRes = new Vector<>();
  this.adbu.dbException = null;
  this.adbu.vecParams = vecParams;
  this.adbu.accessDB(this.context, sql, this.adbu.vecParams, "EQ", "desk");
  if(adbu.dbException == null)
  {
   try
   {
    this.adbu.res.last();
    int rows = this.adbu.res.getRow();
    this.adbu.res.beforeFirst();

    vecAll.add(rows); // vecDataRes elementAt(0) is <tr> rows

    while (this.adbu.res.next())
    {
     for (int i = 1; i < fields + 1; i++)
     {
      vecDataRes.add(this.adbu.res.getString(i));
     }
    }
   } catch (Exception ex)
   {
    vecDataRes.add(ex.toString());
   } finally
   {
    this.adbu.closeAllConns();
   }
  }
  else
  {
   System.out.println(this.adbu.dbException);
  }
  vecAll.add(vecDataRes);

  return vecAll;
 }



 //////////////////////////////////////////////// delRec //////////////////////////////////
 public synchronized String delRec(String pk, String sqlProp)
 {
  StringBuffer buff = new StringBuffer("");
  String sql = this.readFile(this.context.getRealPath(this.bundle.getString(sqlProp)));
  this.adbu.vecParams = new Vector<String>();
  this.adbu.vecParams.add(pk);

  this.excecUpdtQry(sql, this.adbu.vecParams);

  if(this.adbu.dbException == null)
  {
   this.pageInfo = this.setSuccessPage("Record " + pk + " successfully removed");
   // buff.append(this.setTransactionPage("editLastTrans"));
  }
  else
  {
   this.pageInfo = this.setErrorPage("Error 1121: " + this.adbu.dbException);
  }

  return buff.toString();
 }

 //////////////////////////////////////////////////////// readFile//////////////////////////////////
 public synchronized String readFile(String fileName)
 {
  FileReader fr;
  BufferedReader br;
  String line;
  String qry = "";

  new StringBuffer("");

  try
  {
   fr = new FileReader(fileName);
   br = new BufferedReader(fr);

   while (true)
   {
    line = br.readLine();
    if(line == null) break;
    qry += line + " ";
    line += line;
   }
   br.close();
   fr.close();
  } catch (IOException ex)
  {
   this.readFileExpt = "ReadFile Exception: " + ex.toString();
  }

  return qry;
 }

 ////////////////////////////////////// utilFormatter////////////////////////////////////////
 public synchronized String utilFormatter(String nbr, String fType, String decimalPlaces)
 {
  String s = "";
  NumberFormat formatter;
  // System.out.println("fType: "+fType);
  if(fType.equals("round"))
  {
   formatter = new DecimalFormat("##");
   s = formatter.format(Double.parseDouble(nbr)); // -1235
  }
  else if(fType.equals("decimalPlaces"))
  {
   if(decimalPlaces.equals("0.0"))
   {
    formatter = new DecimalFormat("0.0");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals(".00"))
   {
    formatter = new DecimalFormat(".00");
    s = formatter.format(Double.parseDouble(nbr));
   } // -.57
   else if(decimalPlaces.equals("0.00"))
   {
    formatter = new DecimalFormat("0.00");
    s = formatter.format(Double.parseDouble(nbr));
   } // -.57
   else if(decimalPlaces.equals("1dp"))
   {
    formatter = new DecimalFormat("#.#");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals("2dp"))
   {
    formatter = new DecimalFormat("#.##");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals("3dp"))
   {
    formatter = new DecimalFormat("#.###");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals("4dp"))
   {
    formatter = new DecimalFormat("#.####");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals("5dp"))
   {
    formatter = new DecimalFormat("#.#####");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals("6dp"))
   {
    formatter = new DecimalFormat("#.######");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else if(decimalPlaces.equals("7dp"))
   {
    formatter = new DecimalFormat("#.#######");
    s = formatter.format(Double.parseDouble(nbr));
   }
  }
  else if(fType.equals("commas"))
  {
   if(decimalPlaces.equals("0dp"))
   {
    // The , symbol is used to group numbers
    formatter = new DecimalFormat("###,###,###,###,###,###; (###,###,###,###,###,###)");
    s = formatter.format(Double.parseDouble(nbr));
   }
   else
   {
    // The , symbol is used to group numbers
    formatter = new DecimalFormat("#,###,###.########; (#,###,###.########)");
    s = formatter.format(Double.parseDouble(nbr));
   }
  }

  return s;
 }

 ////////////////////////////////////////////////// sumVecField//////////////////////////////////////////////////////
 public synchronized double sumVecField(Vector<String> vecVals)
 {
  double sum = 0.00;
  for (int i = 0; i < vecVals.size(); i++)
  {
   sum += Double.parseDouble(vecVals.elementAt(i).toString());
  }
  return sum;
 }

 ////////////////////////////////////////////////////// writeFile//////////////////////////////////////////////////////
 public synchronized void writeFile(String fileName, String data, boolean appendTF)
 {
  try
  {
   PrintWriter out = new PrintWriter(new FileWriter(fileName, appendTF));
   out.println(data);
   this.writeFileExpt = null;
   out.close();
   // Thread.sleep(500);
  } catch (Exception ex)
  {
   // System.out.println("dbException: "+SenPageUtils.dbException);
   this.writeFileExpt = "Failed to write to " + fileName + "\\n" + ex.toString();
  }
 }

 // createTmpPage
 public synchronized String createTmpPage(String data, ServletContext context)
 {
  writeFile(context.getRealPath(this.bundle.getString("tmpPage")), data, false);

  return tmpPage;
 }


 /////////////////////////////////////////////////// main /////////////////////////////////////////////////////////////
 public static void main(String[] args)
 {
  new SenPageUtils();
 }
} // end MyUtils

