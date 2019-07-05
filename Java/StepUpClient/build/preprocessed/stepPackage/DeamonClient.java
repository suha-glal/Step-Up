package stepPackage;



import stepPackage.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
/**
 * This class is responsible of contacting step up deamon.
 * @author Suha
 */
class DeamonClient {
  
  /**
   * used to create a socket connection with the step up daemon to send and receive data.
   */
StreamConnection conn;
/**
  * Represent the socket connection address.
  * <p> for example url="socket://127.0.0.1:8101"
  * <p> socket: is the protocol used.
  * <p> 127.0.0.1: is the standard IP address used for a loopback network connection. In other words, the connection is made to the same device.
  * <p> 8101: is the port number where the c++ daemon is listening for connection.
 */
 private static String url = "socket://127.0.0.1:8567";
 /**
   * Reference to the midlet class.
   */
 AAAStepUp stepmidlet;
/**
 * The constructor save reference to the main class midlet.
 * @param sm
 */
  DeamonClient(AAAStepUp sm) {
    
     stepmidlet=sm;
    
  }
 /**
  * It alerts the step up deamon to starts the step counter.
  */
  public void startdeamon()
  {
     
       try{

    StreamConnection conn = (StreamConnection)Connector.open(url);

            OutputStream out = conn.openOutputStream();

          // "20091128:160000.0"
          byte[] buf ="s".getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();

            int actualLength = in.read(data);



            String response = new String(data, 0, actualLength);
            

            in.close();
            conn.close();

           }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("", ioe.getMessage(),null,AlertType.WARNING);
           a.setTimeout(Alert.FOREVER);
           stepmidlet.display.setCurrent(a, stepmidlet.startlist);
          
            }//catch

  }//requestcalllog
  /**
  * It alerts the step up deamon to stop the step counter.
  */
  public void enddeamon()
  {

       try{

    StreamConnection conn = (StreamConnection)Connector.open(url);

            OutputStream out = conn.openOutputStream();

          // "20091128:160000.0"
          byte[] buf ="e".getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();

            int actualLength = in.read(data);



            String response = new String(data, 0, actualLength);
           // in.close();
            //conn.close();
//             drvm.s.setText(response);
  }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("Warning", "Unable to connect to stepup deamon!",null,AlertType.WARNING);

           stepmidlet.display.setCurrent(a, stepmidlet.startlist);
          
            }//catch


  }//endcalllog

  /**
   * It alerts the step up deamon that the midlet will close.
   */
 public void close() {

     try{

    StreamConnection conn = (StreamConnection)Connector.open(url);

            OutputStream out = conn.openOutputStream();
          byte[] buf = "c".getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data);
            String response = new String(data, 0, actualLength);
            in.close();
            conn.close();

           }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("Warning", "Unable to connect to stepup deamon!",null,AlertType.WARNING);

           stepmidlet.display.setCurrent(a, stepmidlet.startlist);

            }//catch

      }
        
   
}//end of class
