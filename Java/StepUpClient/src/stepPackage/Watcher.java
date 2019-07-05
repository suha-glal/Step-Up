package stepPackage;


import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
/**
 * The class is responsible of commnunicating with Step Up watcher.
 * @author Suha
 */
class Watcher {

/**
 * used to create a socket connection with the step up daemon to send and receive data.
 */
StreamConnection conn;
 /**
  * Represents the socket connection address.
  * <p> for example url="socket://127.0.0.1:8555"
  * <p> socket: is the protocol used.
  * <p> 127.0.0.1: is the standard IP address used for a loopback network connection. In other words, the connection is made to the same device.
  * <p> 8555: is the port number where the c++ daemon is listening for connection.
 */
 private static String url = "socket://127.0.0.1:8555";
/**
 * Reference to the main class midlet.
 */
AAAStepUp drvm;
/**
 * the constructor save reference to the main class midlet.
 * @param dm reference to the main class midlet
 */
  Watcher(AAAStepUp dm) {

    drvm= dm;

  }
/**
 * The function notify Step up watcher to run step up deamon.
 */
  public void notifyWatchertoStart()
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

// reading the acknowledgement of starting the deamon
            int actualLength = in.read(data);



            String response = new String(data, 0, actualLength);
            in.close();
            conn.close();

     
           }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("Starting", "Unable to start the application",null,AlertType.WARNING);

          drvm.display.setCurrent(a,drvm.startlist);

          drvm.notifyDestroyed();
            }//catch



  }//requestcalllog
  /**
   * Notify Step Up watch to stop running Step up deamon
   */
  public void notifyWatchertoStepupDeamon()
  {


       try{

    StreamConnection conn = (StreamConnection)Connector.open(url);

            OutputStream out = conn.openOutputStream();

          // "20091128:160000.0"
          byte[] buf ="a".getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();

            int actualLength = in.read(data);



            String response = new String(data, 0, actualLength);
            in.close();
            conn.close();

     if(response.equals("a"))
    drvm.startdeamon();





           }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("Starting", "Unable to start the application",null,AlertType.WARNING);

          drvm.display.setCurrent(a,drvm.startlist);

          drvm.notifyDestroyed();
            }//catch


  }//requestcalllog

}//end of class
