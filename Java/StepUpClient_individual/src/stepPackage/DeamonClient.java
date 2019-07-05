package stepPackage;



import stepPackage.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
class DeamonClient {
  
String calls;
StreamConnection conn;
 private static String url = "socket://127.0.0.1:8567";
   
  boolean finish;
AAAStepUp stepmidlet;

  DeamonClient(AAAStepUp sm) {
    
     stepmidlet=sm;
    
  }
 
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

           
            //drvm.s.setText(response);
             
      }//try
           catch(IOException ioe)
           {
            ioe.printStackTrace();
          Alert a=  new Alert("", ioe.getMessage(),null,AlertType.WARNING);
           a.setTimeout(Alert.FOREVER);
           stepmidlet.display.setCurrent(a, stepmidlet.startlist);
          
            }//catch

       
  }//requestcalllog
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
          Alert a=  new Alert("Call log server", "Unable to connect to Call log server",null,AlertType.WARNING);

           stepmidlet.display.setCurrent(a, stepmidlet.startlist);
          
            }//catch


  }//endcalllog
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
          Alert a=  new Alert("Call log server", "Unable to connect to Call log server",null,AlertType.WARNING);

           stepmidlet.display.setCurrent(a, stepmidlet.startlist);

            }//catch

      }
        
   
}//end of class
