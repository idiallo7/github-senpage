package ajaxDemoUtils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class JqueryAutocompleteAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	 private static final String[] MONTH = new String[] {
		 "January","Febuary","March","April","May","June",
         "July","August","September","October","November","December"
       };


   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
       PrintWriter out = response.getWriter();
       response.setContentType("text/html");
     
       JSONArray arrayObj=new JSONArray();
       
       String query = request.getParameter("term");
       System.out.println(query);
       query = query.toLowerCase();
       for(int i=0; i<MONTH.length; i++) {
           String month = MONTH[i].toLowerCase();
           if(month.startsWith(query)) {
               arrayObj.put(MONTH[i]);
               
           }
       }
       
       out.println(arrayObj.toString());
       out.close();
       
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //Do something      
   }


}
