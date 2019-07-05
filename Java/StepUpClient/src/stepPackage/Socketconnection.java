/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

/**
 * The class manage sending and reciving data to and form Step Up sever using socket connection.
 * @author Suha
 */
public class Socketconnection {

/**
   * used to create a socket connection with the step up server to send and receive data.
   */
StreamConnection conn;
/**
  * Represent the socket connection address.
  * <p> for example url="socket://127.0.0.1:8101"
  * <p> socket: is the protocol used.
  * <p> 127.0.0.1: is the standard IP address used for a loopback network connection. In other words, the connection is made to the same device.
  * <p> 8101: is the port number where the c++ daemon is listening for connection.
 */
private static String url = "socket://213.42.247.111:1111";//CESC server through internet

  /**
   * a reference to a form in the class that created this object. 
   */
private Form viewForm;
/**
 * Reference to the main class midlet.
*/
private AAAStepUp stepupMidlet;
/**
 * Step Up server response.
 */
String response;
/**
 * The constructor saves reference to the main class.
 * @param su Reference to the main class midlet.
 * @param ng Reference to the main class midlet.
 */
  Socketconnection(AAAStepUp su,Form ng) {
   viewForm=ng;
    stepupMidlet=su;

  }
/**
 * Sends and receive data to and from step up server using socket connection.
 * @param infotoSend
 * @return the server response
 */
 String  SendRecive(String infotoSend)
  {
 

       try{
           //establish connection
    StreamConnection conn = (StreamConnection)Connector.open(url);

            OutputStream out = conn.openOutputStream();

          // "20091128:160000.0"
          byte[] buf =infotoSend.getBytes();
            out.write(buf, 0, buf.length);
            out.write("\r\n".getBytes());
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();


            int actualLength = in.read(data);



             response = new String(data, 0, actualLength);
            in.close();
            conn.close();
   

      }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("StepUp server", "Unable to connect to StepUp server",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,viewForm);
          response="F";
            }//catch
return response;

  }//requestcalllog


}//end of class
