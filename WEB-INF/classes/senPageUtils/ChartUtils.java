package senBudgetUtils;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.TimeSeriesCollection;

import java.text.SimpleDateFormat;
import myUtils.MyUtils;
import java.util.*;




import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;

public class ChartUtils
{
 public  static Connection connection = null;
 public  static StringBuffer createPieChartUtilError=null;
 public  static StringBuffer createPieChart3DUtilError=null;
 public  static StringBuffer createBarChartUtilError=null;
 public  static StringBuffer createLineChartUtilError=null;
 public  static StringBuffer createTimeSeriesChartUtilError=null;
 public  static StringBuffer createTimeSeriesCollectionChartUtilError=null;
 public  static JFreeChart pieChart=null;
 private static JFreeChart pieChart3D=null;
 public  static JFreeChart barChart=null;
 private static JFreeChart lineChart=null;
 public  static JFreeChart timeSeriesChart=null;
 public  static JFreeChart timeSeriesCollectionChart=null;
 public  static String pieChartOutFile          =MyUtils.bundle.getString("chartImgDir")+"pieChart.png";
 public  static String pieChart3DOutFile        =MyUtils.bundle.getString("chartImgDir")+"pie3DChart.png";;
 public  static String barChartOutFile          =MyUtils.bundle.getString("chartImgDir")+"barChart.png";
 public  static String lineChartOutFile         =MyUtils.bundle.getString("chartImgDir")+"lineChart.png";
 public  static String timeSeriesChartOutFile   =MyUtils.bundle.getString("chartImgDir")+"timeSeriesChart.png";
 public  static String timeSeriesCollectionChartOutFile  =MyUtils.bundle.getString("chartImgDir")+"resTimeSeries.png";
 public  static StringBuffer dbConnError=null;
 public  static String writeChartUtilsError=null;

 public static PlotOrientation plotOrientation;



 //chartDBConn
 private static Connection chartDBconn()
 {
  dbConnError=new StringBuffer("");
  try
  {
   Class.forName(MyUtils.bundle.getString("driverInfo"));
   try
   {
    connection= DriverManager.getConnection(MyUtils.bundle.getString("deskConnURLProd"), MyUtils.bundle.getString("deskDbUser"), MyUtils.bundle.getString("deskDbPass"));
   }
   catch(SQLException e)
   {
    dbConnError.append("SQL expt: "+e.toString());
   }
  }
  catch(Exception e)
  {
   dbConnError.append("chartDbConn Error: "+e.toString());
  }

  MyUtils.dbException=dbConnError.toString();

  return connection;
 }


 //createPieChartUtil
 public static JFreeChart createPieChartUtil(String sql, String chartTitle, int chartWidth, int chartHeight)
 {
  ChartUtils.createPieChartUtilError=new StringBuffer("");
  try
  {
   connection=chartDBconn();

   JDBCPieDataset pieDataset =new JDBCPieDataset(connection);

   try
   {
    pieDataset.executeQuery(sql);

    pieChart =ChartFactory.createPieChart(chartTitle,
                                         pieDataset,
                                         true,      // legend displayed
                                         true,      // tooltips displayed
                                         false );   // no URLs

    writePngBasedOnChart(pieChart, chartWidth, chartHeight,ChartUtils.pieChartOutFile, createPieChartUtilError);
   }
   catch(SQLException e)
   {
    createPieChartUtilError.append("SQL ERROR: "+e.toString()+"<br>");
    createPieChartUtilError.append("SQLSTATE:   " + e.getSQLState()+"<br>");
    createPieChartUtilError.append("Error Code: " + e.getErrorCode()+"<br>");
   }
  }
  catch(Exception ex)
  {
   dbConnError.append(ex.toString());
  }

  try
  {
   if(connection != null)
   {
    connection.close();
   }
  }
  catch(SQLException e)
  {
   createPieChartUtilError.append("SQL close connection ERROR: "+e.toString());;
  }
  return pieChart;
 }


 //createLineChartUtil
 public static JFreeChart createLineChartUtil(String sql, String chartTitle, String xLabel, String yLabel, int chartWidth, int chartHeight)
 {
  ChartUtils.createLineChartUtilError=new StringBuffer("");

  try
  {
   connection=chartDBconn();

   JDBCXYDataset  xyDataset =new  JDBCXYDataset(connection);

   try
   {
    xyDataset.executeQuery(sql);
    ChartUtils.lineChart = ChartFactory.createXYLineChart(chartTitle,
                                                    xLabel,
                                                    yLabel,
                                                    xyDataset,
                                                    ChartUtils.plotOrientation.VERTICAL,
                                                    true,
                                                    true,
                                                    false);
    writePngBasedOnChart(pieChart, chartWidth, chartHeight,ChartUtils.lineChartOutFile, createLineChartUtilError);
   }
   catch(SQLException e)
   {
    createLineChartUtilError.append("SQL ERROR: "+e.toString()+"<br>");
    createLineChartUtilError.append("SQLSTATE:   " + e.getSQLState()+"<br>");
    createLineChartUtilError.append("Error Code: " + e.getErrorCode()+"<br>");
   }
  }
  catch(Exception ex)
  {
   dbConnError.append(ex.toString());
  }

  try
  {
   if(connection != null)
   {
    connection.close();
   }
  }
  catch(SQLException e)
  {
   createLineChartUtilError.append("SQL close connection ERROR: "+e.toString());;
  }
  return lineChart;
 }



 //createTimeSeriesChartUtil
 public static JFreeChart createTimeSeriesChartUtil(String sql, String chartTitle, String xLabel, String yLabel, int chartWidth, int chartHeight)
 {
  ChartUtils.createTimeSeriesChartUtilError=new StringBuffer("");

  try
  {
   connection=chartDBconn();

   JDBCXYDataset  xyDataset =new  JDBCXYDataset(connection);

   try
   {
    xyDataset.executeQuery(sql);

    ChartUtils.timeSeriesChart = ChartFactory.createTimeSeriesChart(chartTitle,
                                                                    xLabel,
                                                                    yLabel,
                                                                    xyDataset,
                                                                    true,
                                                                    true,
                                                                    false);

    XYPlot plot = timeSeriesChart.getXYPlot();


    Font    labelFont = new Font("Helvetica", Font.PLAIN, 10);
    Font    titleFont = new Font("Helvetica", Font.BOLD, 14);

    plot.setBackgroundPaint(Color.black);
    plot.setOutlinePaint(Color.white);
    //plot.setOrientation(PlotOrientation.VERTICAL);

    timeSeriesChart = new JFreeChart(chartTitle, titleFont, plot, true);
    //DateAxis axis = (DateAxis) plot.getDomainAxis();
    //axis.setDateFormatOverride(new SimpleDateFormat("MM/dd/yy"));

    writePngBasedOnChart(ChartUtils.timeSeriesChart, chartWidth, chartHeight,ChartUtils.timeSeriesChartOutFile, createTimeSeriesChartUtilError);
   }
   catch(SQLException e)
   {
    createTimeSeriesChartUtilError.append("SQL ERROR: "+e.toString()+"<br>");
    createTimeSeriesChartUtilError.append("SQLSTATE:   " + e.getSQLState()+"<br>");
    createTimeSeriesChartUtilError.append("Error Code: " + e.getErrorCode()+"<br>");
   }
  }
  catch(Exception ex)
  {
   dbConnError.append(ex.toString());
  }

  try
  {
   if(connection != null)
   {
    connection.close();
   }
  }
  catch(SQLException e)
  {
   createTimeSeriesChartUtilError.append("SQL close connection ERROR: "+e.toString());;
  }
  return timeSeriesChart;
 }


 //createTimeSeriesChartUtil overloaded with png output file
 public static JFreeChart createTimeSeriesChartUtil(String sql, String chartTitle, String xLabel, String yLabel, int chartWidth, int chartHeight, String pngFileOut)
 {
  ChartUtils.createTimeSeriesChartUtilError=new StringBuffer("");

  //System.out.println("276: "+pngFileOut);
  try
  {
   connection=chartDBconn();

   JDBCXYDataset  xyDataset =new  JDBCXYDataset(connection);

   try
   {
    xyDataset.executeQuery(sql);
    ChartUtils.timeSeriesChart = ChartFactory.createTimeSeriesChart(chartTitle,
                                                                    xLabel,
                                                                    yLabel,
                                                                    xyDataset,
                                                                    true,
                                                                    true,
                                                                    false);

    XYPlot plot = timeSeriesChart.getXYPlot();


    Font    labelFont = new Font("Helvetica", Font.PLAIN, 10);
    Font    titleFont = new Font("Helvetica", Font.BOLD, 14);

    plot.setBackgroundPaint(Color.black);
    plot.setOutlinePaint(Color.white);
    //plot.setOrientation(PlotOrientation.VERTICAL);

    timeSeriesChart = new JFreeChart(chartTitle, titleFont, plot, true);
    //DateAxis axis = (DateAxis) plot.getDomainAxis();
    //axis.setDateFormatOverride(new SimpleDateFormat("MM/dd/yy"));
    try
    {
     writePngBasedOnChart(ChartUtils.timeSeriesChart, chartWidth, chartHeight, pngFileOut, createTimeSeriesChartUtilError);
    }
    catch(Exception ex)
    {
     ChartUtils.createTimeSeriesChartUtilError.append(ex.toString());
    }

   }
   catch(SQLException e)
   {
    createTimeSeriesChartUtilError.append("SQL ERROR: "+e.toString()+"<br>");
    createTimeSeriesChartUtilError.append("SQLSTATE:   " + e.getSQLState()+"<br>");
    createTimeSeriesChartUtilError.append("Error Code: " + e.getErrorCode()+"<br>");
   }
  }
  catch(Exception ex)
  {
   dbConnError.append(ex.toString());
  }

  try
  {
   if(connection != null)
   {
    connection.close();
   }
  }
  catch(SQLException e)
  {
   createTimeSeriesChartUtilError.append("SQL close connection ERROR: "+e.toString());;
  }
  return timeSeriesChart;
 }




 //createBarChartUtil
 public static JFreeChart createBarChartUtil(String sql, String chartTitle, String xLabel, String yLabel, int chartWidth, int chartHeight)
 {
  ChartUtils.createBarChartUtilError=new StringBuffer("");
  //ChartUtils.connection=ChartUtils.chartDBconn();
  try
  {
   ChartUtils.connection=ChartUtils.chartDBconn();

   JDBCCategoryDataset barDataset =new JDBCCategoryDataset(ChartUtils.connection);

   try
   {
    barDataset.executeQuery(sql);
    ChartUtils.barChart =ChartFactory.createBarChart(chartTitle,
                                         xLabel,
                                         yLabel,
                                         barDataset,
                                         ChartUtils.plotOrientation.VERTICAL,
                                         true,      // legend displayed
                                         true,      // tooltips displayed
                                         false );   // no URLs

    ChartUtils.writePngBasedOnChart(barChart, chartWidth, chartHeight,ChartUtils.barChartOutFile, ChartUtils.createBarChartUtilError);
   }
   catch(SQLException e)
   {
    ChartUtils.createBarChartUtilError.append("SQL ERROR: "+e.toString()+"<br>");
    ChartUtils.createBarChartUtilError.append("SQLSTATE:   " + e.getSQLState()+"<br>");
    ChartUtils.createBarChartUtilError.append("Error Code: " + e.getErrorCode()+"<br>");
   }
  }
  catch(Exception ex)
  {
   ChartUtils.dbConnError.append(ex.toString());
  }


  try
  {
   if(connection != null)
   {
    connection.close();
   }
  }
  catch(SQLException e)
  {
   ChartUtils.createBarChartUtilError.append("SQL close connection ERROR: "+e.toString());;
  }

  return barChart;
 }



 //createBarChartUtil orverloaded with barFileOut
 public static JFreeChart createBarChartUtil(String sql, String chartTitle, String xLabel, String yLabel, int chartWidth, int chartHeight, String barFileOut)
 {
  ChartUtils.createBarChartUtilError=new StringBuffer("");
  //System.out.println("406: "+barFileOut);
  try
  {
   ChartUtils.connection=ChartUtils.chartDBconn();

   JDBCCategoryDataset barDataset =new JDBCCategoryDataset(ChartUtils.connection);

   try
   {
    barDataset.executeQuery(sql);
    ChartUtils.barChart =ChartFactory.createBarChart(chartTitle,
                                         xLabel,
                                         yLabel,
                                         barDataset,
                                         ChartUtils.plotOrientation.VERTICAL,
                                         true,      // legend displayed
                                         true,      // tooltips displayed
                                         false );   // no URLs

   // XYPlot plot = barChart.getXYPlot();

// disable bar outlines...
    //BarRenderer renderer = (BarRenderer) plot.getRenderer();
    //DecimalFormat decimalformat1 = new DecimalFormat("$##,###.00");
    //renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1)); //i added your line here.
    //renderer.setItemLabelsVisible(true);
    //renderer.setBaseItemLabelsVisible(true);


    //barChart.getCategoryPlot().setRenderer(renderer);


    //plot.setLabelsVisible(true);


    //final CategoryPlot plot = barChart.getCategoryPlot();
    //final BarRenderer renderer = (BarRenderer) plot.getRenderer();
    //final GradientPaint gradient =new GradientPaint( 0.0f, 0.0f, Color.RED.brighter(), 0.0f, 0.0f, Color.WHITE );
    //renderer.setSeriesPaint(0, gradient);

    ChartUtils.writePngBasedOnChart(barChart, chartWidth, chartHeight, barFileOut, ChartUtils.createBarChartUtilError);
   }
   catch(SQLException e)
   {
    ChartUtils.createBarChartUtilError.append("SQL ERROR: "+e.toString()+"<br>");
    ChartUtils.createBarChartUtilError.append("SQLSTATE:   " + e.getSQLState()+"<br>");
    ChartUtils.createBarChartUtilError.append("Error Code: " + e.getErrorCode()+"<br>");
   }
  }
  catch(Exception ex)
  {
   ChartUtils.dbConnError.append(ex.toString());
  }

  try
  {
   if(connection != null)
   {
    connection.close();
   }
  }
  catch(SQLException e)
  {
   ChartUtils.createBarChartUtilError.append("SQL close connection ERROR: "+e.toString());;
  }

  return barChart;
 }



 //writePngBasedOnChart
 public static void writePngBasedOnChart(JFreeChart aChart, int aWidth, int aHeight, String aFileName, StringBuffer chartWriterErrorBuff)
 {
  try
  {
   //System.out.println("482: "+aFileName);
   ChartUtilities.writeChartAsPNG(new FileOutputStream(aFileName), aChart, aWidth, aHeight);
  }
  catch (IOException ioEx)
  {
   ChartUtils.writeChartUtilsError="Error writing PNG file " + aFileName+"<br>"+ioEx;
   System.out.println("writeChartUtilsError: "+ChartUtils.writeChartUtilsError);
  }
 }



 public static void main(String[] args)
 {
  //ChartUtils.createTimeSeriesChartUtilError=new StringBuffer("");

  //String chartSql=MyUtils.dsgAllCpnRangePrepareStmt("/hybridGrid/"+MyUtils.bundle.getString("dsgAllCpnRange"), "02/01/08", "02/19/08");


  //ChartUtils.createTimeSeriesChartUtil(MyUtils.dsgRplMtr(chartSql, "@mtr", "60"),"3/1 per cpn avg spreads", "Price Date", "n_spreads", 200, 200);
  //System.out.println(MyUtils.dsgRplMtr(chartSql, "@mtr", "60"));
  //System.out.println("err1: "+ChartUtils.dbConnError);
  //System.out.println("err2: "+ChartUtils.createTimeSeriesChartUtilError);


  //String chartSql=MyUtils.dsgAllCpnRangePrepareStmt("/hybridGrid/"+MyUtils.bundle.getString("dsgAllCpnRange"), "02/01/08", "02/19/08");
  String chartSql=MyUtils.dsgAllCpnRangePrepareStmt("/hybridGrid/"+MyUtils.bundle.getString("dsgDealerHiLoAllCpn"), "02/01/08", "02/19/08");

  //ChartUtils.createBarChartUtilError=new StringBuffer("");
  //System.out.println(MyUtils.dsgRplMtr(chartSql, "@mtr", "60"));
  ChartUtils.createBarChartUtil(MyUtils.dsgRplMtr(chartSql, "@mtr", "60"),"Bar Chart title", "Dealer", "n_spread", 300, 300, "/hybridGrid/"+MyUtils.bundle.getString("chartImgDir")+"dsgDealerHiLoBarPngMtr5.png");
  System.out.println("err1: "+ChartUtils.dbConnError);
  System.out.println("err2: "+ChartUtils.createBarChartUtilError);

  //ChartFrame frame=new ChartFrame("JChartTest", ChartUtils.timeSeriesChart);
  ChartFrame frame=new ChartFrame("JChartTest", ChartUtils.barChart);
  frame.setPreferredSize(new java.awt.Dimension(600, 600));
  frame.pack();
  frame.setVisible(true);
 }
}
