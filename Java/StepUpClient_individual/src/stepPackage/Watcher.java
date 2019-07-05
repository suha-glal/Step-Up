package stepPackage;


import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
class Watcher {


StreamConnection conn;
 private static String url = "socket://127.0.0.1:8555";

AAAStepUp drvm;
  Watcher(AAAStepUp dm) {

    drvm= dm;

  }

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
