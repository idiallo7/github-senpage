import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class HTTPXMLTest
{
 public static void main(String[] args)
 {
  try
  {
   new HTTPXMLTest().start();
  } catch(Exception e)
  {
   e.printStackTrace();
  }
 }

 private void start() throws Exception
 {
  String urlStr="http://query.yahooapis.com/v1/public/yql?q=select * from yahoo.finance.xchange where pair in ('USDEUR')&env=store://datatables.org/alltableswithkeys";
  URL url = new URL(urlStr);
  URLConnection connection = url.openConnection();

  Document doc = parseXML(connection.getInputStream());
  NodeList descNodes = doc.getElementsByTagName("description");

  for(int i=0; i<descNodes.getLength();i++)
  {
   System.out.println(descNodes.item(i).getTextContent());
  }
 }

 private Document parseXML(InputStream stream)
 throws Exception
 {
  DocumentBuilderFactory objDocumentBuilderFactory = null;
  DocumentBuilder objDocumentBuilder = null;
  Document doc = null;
  try
  {
   objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
   objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

   doc = objDocumentBuilder.parse(stream);
  } catch(Exception ex)
  {
   throw ex;
  }

  return doc;
 }
}
