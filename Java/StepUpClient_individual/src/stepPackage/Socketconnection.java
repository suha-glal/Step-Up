/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

/**
 *
 * @author Suha
 */
public class Socketconnection {


StreamConnection conn;

private static String url = "socket://213.42.247.111:1111";//CESC server through internet
//private static String url = "socket://192.168.254.1:1111";//CESC server through internet

  boolean finish;
private Form viewForm;
private AAAStepUp stepupMidlet;
String response;
  Socketconnection(AAAStepUp su,Form ng) {
    viewForm=ng;
    stepupMidlet=su;

  }

 String  SendRecive(String infotoSend)
  {
      finish=false;

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
