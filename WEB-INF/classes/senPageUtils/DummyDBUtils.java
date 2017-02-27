package ajaxDemoUtils;
import java.util.*;
import java.sql.*;
import javax.servlet.*;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;

public class DummyDBUtils 
{
	private int totalCountries;
	private String data = "Afghanistan,	Albania, Zimbabwe, Res";
	private List<String> countries;
	
	public DummyDBUtils() 
	{
		countries = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(data, ",");
		
		while(st.hasMoreTokens()) {
			countries.add(st.nextToken().trim());
		}
		totalCountries = countries.size();
	}
	
	public List<String> getData(String query) 
	{
		String country = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		for(int i=0; i<totalCountries; i++) {
			country = countries.get(i).toLowerCase();
			if(country.startsWith(query)) {
				matched.add(countries.get(i));
			}
		}
		return matched;
	}
}