package senBudgetUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class PassEncryptor
{
 private KeyGenerator keyGen=null;
 private SecretKey    passKey=null;
 private Cipher       passCipher=null;
 private byte[]       passBytes=null;
 private byte[]       encryptedPass=null;
 private byte[]       decryptedPass=null;
 private String       userPass="";       // user pass parameter sent to constructor from jsp
 public StringBuffer exptBuff=new StringBuffer("");

 //////////////////////////////////////////// constructor ////////////////////////////////////
 public PassEncryptor()
 {
  this.initEncryption();
 }
 /////////////////////////////////////////// initEncryption ///////////////////////////////////
 private void initEncryption()
 {
  try
  {
   this.keyGen=KeyGenerator.getInstance("DES"); //create +generate DES key
   this.passKey=this.keyGen.generateKey();
   passCipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); //create the cipher+padding algo
  }
  catch(NoSuchAlgorithmException e)
  {
   exptBuff.append("DES Algo Error: "+e.toString());
  }
  catch(NoSuchPaddingException e)
  {
   exptBuff.append("Padding Error: "+e.toString());
  }
 }
 ////////////////////////////////////////// doEncryptPass /////////////////////////////////////////
 private void doEncryptPass(String uPass)
 {
  this.userPass=uPass;

  this.passBytes = this.userPass.getBytes();
  try
  {
   this.passCipher.init(Cipher.ENCRYPT_MODE, this.passKey); // init encryption mode
   this.encryptedPass =this.passCipher.doFinal(this.passBytes);
  }
  catch(InvalidKeyException e)
  {
   exptBuff.append("Key gen Error: "+e.toString());
  }
  catch(IllegalBlockSizeException e)
  {
   exptBuff.append("Block Size Error: "+e.toString());
  }
  catch(BadPaddingException e)
  {
   exptBuff.append("Bad Padding Error: "+e.toString());
  }
 }
 ////////////////////////////////// doDecryptPass ///////////////////////////////////////////////
 private void doDecryptPass(byte[] encriptedPass)
 {
  this.encryptedPass=encriptedPass;

  try
  {
   this.passCipher.init(Cipher.DECRYPT_MODE, this.passKey); // init decryption mode
   this.decryptedPass =this.passCipher.doFinal(this.encryptedPass);
  }
  catch(InvalidKeyException e)
  {
   exptBuff.append("Key gen Error: "+e.toString());
  }
  catch(IllegalBlockSizeException e)
  {
   exptBuff.append("Block Size Error: "+e.toString());
  }
  catch(BadPaddingException e)
  {
   exptBuff.append("Bad Padding Error: "+e.toString());
  }
 }

///////////////////////////////////////// doCompare ///////////////////////////////////////////
 public static void doCompare()
 {
 }

 public static void main(String[] args)
 {
  PassEncryptor p=new PassEncryptor();
  p.doEncryptPass(args[0]);
  System.out.println("expt: "+p.exptBuff);
  System.out.println("bytes: "+new String(p.passBytes));
  System.out.println("[Byte Format] : " +p.passBytes);
  System.out.println("Text : " + new String(p.passBytes));
  System.out.println("encryptedPass: "+p.encryptedPass);
  System.out.println("\n");


  p.doDecryptPass(p.encryptedPass);
  System.out.println("Text Decryted : " + new String(p.decryptedPass));
 }
}
