package senpageUtils;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// 03/22/2011
//  Author: I. Diallo
// TriplePassEncryptor is based on DES Encryption to DESede also known as Triple DES.
// Triple DES is the common name for the Triple Data Encryption Algorithm (TDEA) block cipher.
// It applies the Data Encryption Standard (DES) cipher algorithm three times to each data block.
// Triple DES provides a relatively simple method of increasing the key size of DES to protect against brute force attacks,
// without requiring a completely new block cipher algorithm.
// Please note that TriplePassEncryptor requires the same theDeskKey to decrypted encrypted data
/////////////////////////////////////////////////////////////////////////////////////////////////////////
public class TriplePassEncryptor
{
 private static final String UNICODE_FORMAT = "UTF8";
 public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
 private KeySpec myKeySpec;
 private SecretKeyFactory mySecretKeyFactory;
 private Cipher cipher;
 byte[] keyAsBytes;
 public String senBudgetKey=null;
 private String myEncryptionScheme;
 private SecretKey key=null;
 public String dbException=null;

 public TriplePassEncryptor()
 {
  try
  {
   senBudgetKey = "@senBudgetSecretEncryptionKey2015";
   myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
   keyAsBytes = senBudgetKey.getBytes(UNICODE_FORMAT);
   myKeySpec = new DESedeKeySpec(keyAsBytes);
   mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
   cipher = Cipher.getInstance(myEncryptionScheme);
   key = mySecretKeyFactory.generateSecret(myKeySpec);
  }
  catch(Exception ex)
  {
   this.dbException="46 - TriplePassEncrypor Error: "+ex;
  }
 }

 public String encrypt(String unencryptedString)
 {
  String encryptedString = null;
  try
  {
   cipher.init(Cipher.ENCRYPT_MODE, key);
   byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
   byte[] encryptedText = cipher.doFinal(plainText);
   BASE64Encoder base64encoder = new BASE64Encoder();
   encryptedString = base64encoder.encode(encryptedText);
  }
  catch(Exception e)
  {
   this.dbException="63 - TriplePassEncrypor Error: "+e;
  }
  return encryptedString;
 }

 public String decrypt(String encryptedString)
 {
  String decryptedText=null;
  try
  {
   cipher.init(Cipher.DECRYPT_MODE, key);
   BASE64Decoder base64decoder = new BASE64Decoder();
   byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
   byte[] plainText = cipher.doFinal(encryptedText);
   decryptedText= bytes2String(plainText);
  }
  catch(Exception e)
  {
   this.dbException="81 - TriplePassEncrypor Error: "+e;
  }
  return decryptedText;
 }


 private static String bytes2String(byte[] bytes)
 {
  StringBuffer stringBuffer = new StringBuffer();
  for (int i = 0; i <bytes.length; i++)
  {
   stringBuffer.append((char) bytes[i]);
  }
  return stringBuffer.toString();
 }

 public static void main(String args []) throws Exception
 {
  TriplePassEncryptor myEncryptor= new TriplePassEncryptor();

  int encType=0; // 0=encrypt 1=decrypt

  encType=Integer.parseInt(args[0]);
  String encrypted="";
  String decrypted="";

  if(encType==0)
  {
   //encrypted=myEncryptor.encrypt(args[1]+"@pluKey"); // add if key suffix
   encrypted=myEncryptor.encrypt(args[1]);
   System.out.println("Based on key:      "+myEncryptor.senBudgetKey);
   System.out.println("String To Encrypt: "+args[1]);
   System.out.println("Encrypted Value :" + encrypted);
  }
  else
  {
   decrypted=myEncryptor.decrypt(args[1]);
   System.out.println("Based on key:      "+myEncryptor.senBudgetKey);
   System.out.println("String To decrypt: "+args[1]);
   System.out.println("Decrypted Value :"+decrypted);
  }

  //myEncryptor.theDeskKey=args[0];//"gM0jZv66Sk8E1OFSP95pYA==";
 }

}

