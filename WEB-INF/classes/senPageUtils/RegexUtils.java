package senBudgetUtils;
import myUtils.MyUtils;
import java.util.regex.*;
import pluUtils.RegexUtils;

public class RegexUtils
{
 private static final String strSearched="\\d+\\.\\d{1,}|-\\d+\\.0{1,}|\\d+|-\\d+";

 public static synchronized boolean isNumber(String str)
 {
  //check if number is formatted with [ $,comma ]
  String rplStr=replaceAllPattern(str, "\\$|\\,", "");
         //rplStr=replaceAllPattern(rplStr, "^0{2,}", " 000");


  Pattern pat=Pattern.compile(strSearched);

  Matcher mat=pat.matcher(rplStr);
  //System.out.println("rplStr: "+rplStr);

  return mat.matches();
 }

 public static synchronized String replaceAllPattern(String inputStr, String patternStr, String replacementStr)
 {
  Pattern pat=Pattern.compile(patternStr);
  Matcher mat=pat.matcher(inputStr);

  return  mat.replaceAll(replacementStr);
 }

 public static synchronized boolean setCaseInsensitive(String inputStr, String patternStr)
 {
  Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
  Matcher matcher = pattern.matcher(inputStr);
  boolean matchFound = matcher.matches();    // true

  return matcher.matches();

 }

  //patMatch
 public static boolean patMatch(String strPat, String editType)
 {
  Pattern p = Pattern.compile(strPat, Pattern.CASE_INSENSITIVE);
  Matcher m = p.matcher(editType);
  boolean b = m.find();
  return b;
 }




 //test
 public static void main(String[] args)
 {
  String str        ="dealer Total eee";

  System.out.println(MyUtils.patMatch("TOTAL", str));

 }
}

