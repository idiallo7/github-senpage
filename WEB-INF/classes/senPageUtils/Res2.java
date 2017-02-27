//package com.java2novice.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Res2
{
 private Properties prop = null;
 private InputStream is = null;
 private InputStream out = null;

 public Res2()
 {
  try
  {
   this.prop = new Properties();
   is = this.getClass().getResourceAsStream("/senBudget.properties");
   prop.load(is);

   out = this.getClass().getResourceAsStream("/senBudget.properties");
   prop.store(out);
  }
  catch(FileNotFoundException e)
  {
   e.printStackTrace();
  }
  catch(IOException e)
  {
   e.printStackTrace();
  }
 }

 public String getPropertyValue(String key)
 {
  return this.prop.getProperty(key);
 }

 public void setPropertyValue(Properties prop, String key, String val)
 {
  try
  {
   prop.setProperty(key, val);
   FileOutputStream out = new FileOutputStream("prog.properties");

   try
   {
    prop.store(out, "add a comment");
   }
   finally
   {
    out.close();
   }
  }
  catch (IOException ex)
  {
   System.out.println(ex.toString());
  }
 }






 public static void main(String a[])
 {
  Res2 mpc = new Res2();
  //System.out.println(mpc.getPropertyValue("itemMaison"));

  mpc.setPropertyValue(mpc.prop, "res1", "res2");
  //System.out.println("res1: "+mpc.getPropertyValue("res1"));
 }
}

