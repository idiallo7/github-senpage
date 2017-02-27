package theDeskTests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import theDeskUtils.AccessDBUtils;

/**
 * This is a helper that provides aid on the unit test and code coverage of
 * theDesk application. It was tailored according to the coding style of
 * theDesk, which would provided mocks and wrappers around generic test cases.
 *
 * @author YeeChin, Chiam
 * @since 2016-7-15
 */
public abstract class TestHelper
{
 /**
  * A common implementation for the test cases. This method will be called
  * before every single test cases. Put generic codes that needs to be run on
  * every test, for example the mock declaration, mock initiation and others.
  *
  * <pre>
  * public void setUp() throws Exception
  * {
  *      MockitoAnnotations.initMocks(this);
  *      // other stuffs
  * }
  * </pre>
  *
  */
 @Before
 public abstract void setUp() throws Exception;

 /**
  * By using the reflection API, this method simulates the behavior of
  * AccessDBUtils upon errors. Instead of fetching data from the database, it
  * injects an error message into the dbException, and returning nothing for the
  * results.
  *
  * <pre>
  * this.adbu.accessDB(sql, this.vecParams, "EQ", "desk");
  * if (this.adbu.dbException == null)
  * {
  *      // this will not be covered
  * }
  * else
  * {
  *      // this will be covered
  * }
  * </pre>
  *
  * @param assertionString
  *         The expected portion of string when database exceptions are
  *         encountered.
  * @param adbu
  *         AccessDBUtils mock object that was used in the current test class
  * @param testObject
  *         The class object that are being test
  * @param testMethod
  *         The method object that are being test
  * @param parameters
  *         Parameters needed by the method to be test
  * @throws IllegalAccessException
  *          Unable to call the specified method, might want to check the
  *          specified method signature
  * @throws IllegalArgumentException
  *          Provided arguments might be incorrect, the arguments should be
  *          provided as a vararg
  * @throws InvocationTargetException
  *          Errors of the code which are being test
  */
 public void testDBException(String assertionString, final AccessDBUtils adbu, Object testObject, Method testMethod,
                             Object... parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
 {
  // Inject adbu.dbException when accessDB is invoked
  doAnswer(new Answer<Object>()
           {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
             adbu.dbException = "Error Simulation";
             return null;
            }
           }).when(adbu).accessDB(anyString(), any(Vector.class), anyString(), anyString());

  assertThat((String) testMethod.invoke(testObject, parameters), containsString(assertionString));

  // Revert the injection
  doAnswer(new Answer<Object>()
           {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
             adbu.dbException = null;
             return null;
            }
           }).when(adbu).accessDB(anyString(), any(Vector.class), anyString(), anyString());
 }

 /**
  * By using the reflection API, this method will test for every other
  * possibilities except the normal cases. The combinations in the method are
  * used to get full coverage of the database call codes. <strong>Note that this
  * might modify the declared behavior of the mock objects.</strong>
  *
  * <pre>
  * try
  * {
  *      while (this.adbu.res.next())
  *      {
  *              // do something
  *              // this will not be fully covered
  *      }
  * }
  * catch (Exception ex)
  * {
  *      logger.error(ex);
  *      // this will be covered
  * }
  * finally
  * {
  *      this.adbu.closeConn(this.adbu.conn);
  *      // this will be covered
  * }
  * </pre>
  *
  * @param conn
  *         Connection mock object that was used in the current test class, it's
  *         behavior may change
  * @param res
  *         ResultSet mock object that was used in the current test class, it's
  *         behavior may change
  * @param testObject
  *         The class object that are being test
  * @param testMethod
  *         The method object that are being test
  * @param parameters
  *         Parameters needed by the method to be test
  * @throws IllegalAccessException
  *          Unable to call the specified method, might want to check the
  *          specified method signature
  * @throws IllegalArgumentException
  *          Provided arguments might be incorrect, the arguments should be
  *          provided as a vararg
  * @throws InvocationTargetException
  *          Errors of the code which are being test
  * @throws SQLException
  */
 public void testDBErrorHandling(Connection conn, ResultSet res, Object testObject, Method testMethod,
                                 Object... parameters)
 throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException
 {
  doReturn(false).when(conn).isClosed();
  doReturn(false).when(res).next();
  assertPositive(testObject, testMethod, parameters);

  doReturn(true).when(conn).isClosed();
  doReturn(false).when(res).next();
  assertPositive(testObject, testMethod, parameters);

  doThrow(SQLException.class).when(conn).isClosed();
  doReturn(false).when(res).next();
  assertPositive(testObject, testMethod, parameters);

  // Throw known exceptions to trigger the catch block
  doReturn(false).when(conn).isClosed();
  doThrow(NullPointerException.class).when(res).next();
  assertNegative(testObject, testMethod, parameters);

  doReturn(true).when(conn).isClosed();
  doThrow(NullPointerException.class).when(res).next();
  assertNegative(testObject, testMethod, parameters);

  doThrow(SQLException.class).when(conn).isClosed();
  doThrow(NullPointerException.class).when(res).next();
  assertNegative(testObject, testMethod, parameters);

  // Throw uncaught errors to cover the remaining branches
  doReturn(false).when(conn).isClosed();
  doThrow(Error.class).when(res).next();
  try
  {
   assertNegative(testObject, testMethod, parameters);
  } catch(InvocationTargetException e)
  {
   // Ignored
  }

  doReturn(true).when(conn).isClosed();
  doThrow(Error.class).when(res).next();
  try
  {
   assertNegative(testObject, testMethod, parameters);
  } catch(InvocationTargetException e)
  {
   // Ignored
  }

  doThrow(SQLException.class).when(conn).isClosed();
  doThrow(Error.class).when(res).next();
  try
  {
   assertNegative(testObject, testMethod, parameters);
  } catch(InvocationTargetException e)
  {
   // Ignored
  }
 }

 /**
  * This method is used by <code>testDBException</code> and
  * <code>testDBErrorHandling</code>, to do assertions when the database call do
  * not return an exception.
  *
  * @param testObject
  *         The class object that are being test
  * @param testMethod
  *         The method object that are being test
  * @param parameters
  *         Parameters needed by the method to be test
  * @throws IllegalAccessException
  *          Unable to call the specified method, might want to check the
  *          specified method signature
  * @throws IllegalArgumentException
  *          Provided arguments might be incorrect, the arguments should be
  *          provided as a vararg
  * @throws InvocationTargetException
  *          Errors of the code which are being test
  */
 private void assertPositive(Object testObject, Method testMethod, Object... parameters)
 throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
 {
  assertThat((String) testMethod.invoke(testObject, parameters),
             not(containsString("Application Error and the details are recorded in Application Server log")));
 }

 /**
  * This method is used by <code>testDBException</code> and
  * <code>testDBErrorHandling</code>, to do assertions when the database call
  * returns an exception.
  *
  * @param testObject
  *         The class object that are being test
  * @param testMethod
  *         The method object that are being test
  * @param parameters
  *         Parameters needed by the method to be test
  * @throws IllegalAccessException
  *          Unable to call the specified method, might want to check the
  *          specified method signature
  * @throws IllegalArgumentException
  *          Provided arguments might be incorrect, the arguments should be
  *          provided as a vararg
  * @throws InvocationTargetException
  *          Errors of the code which are being test
  */
 private void assertNegative(Object testObject, Method testMethod, Object... parameters)
 throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
 {
  assertThat((String) testMethod.invoke(testObject, parameters),
             containsString("Application Error and the details are recorded in Application Server log"));
 }

 /**
  * This is an anonymous class that mocks the <code>ServletInputStream</code>.
  * It overrides the default behavior of the class and returns a specified
  * string whenever it is used in a <code>MultipartRequest</code>
  *
  * @author YeeChin, Chiam
  *
  */
 public class FakeServletInputStream extends ServletInputStream
 {
  private InputStream stream;

  public FakeServletInputStream(byte[] content)
  {
   this.stream = new ByteArrayInputStream(content);
  }

  public FakeServletInputStream(InputStream stream)
  {
   this.stream = stream;
  }

  @Override
  public int read() throws IOException
  {
   return stream.read();
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException
  {
   return stream.read(b, off, len);
  }

  @Override
  public int read(byte[] b) throws IOException
  {
   return stream.read(b);
  }
 }

 /**
  * This method generates a fake <code>MultipartRequest</code> object. It take a
  * file as input parameter, and convert the content of the file into the
  * request byte stream.
  *
  * @param file
  *         Input file that needs to be included in the fake request
  * @return Fake MultipartRequest object
  * @throws IOException
  */

 public HttpServletRequest createFakeMultipartRequest(File file) throws IOException
 {
  byte[] data = Files.readAllBytes(file.toPath());
  String boundary = "boundary";
  String contentType = "text/plain";

  String start = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"testFileName\"; filename=\""
                 + file.getAbsolutePath() + "\"\r\n" + "Content-Type: " + contentType + "\r\n\r\n";

  String end = "--" + boundary + "--";

  byte[] content = ArrayUtils.addAll(start.getBytes(), ArrayUtils.addAll(data, end.getBytes()));

  HttpServletRequest request = mock(HttpServletRequest.class);
  doReturn("multipart/form-data; boundary=\"" + boundary + "\"").when(request).getContentType();

  // Arbitrary file size that was hard coded in theDesk, increase if needed
  doReturn(3000000).when(request).getContentLength();
  doReturn(new FakeServletInputStream(content)).when(request).getInputStream();

  return request;
 }
}
