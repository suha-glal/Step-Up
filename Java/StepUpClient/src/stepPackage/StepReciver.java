package stepPackage;


import javax.microedition.io.*;
import java.io.*;
/**
 * Handle communication with motionClassifier deamon.
 * @author Suha
 */

class StepReciver implements Runnable {
    /**
     * The thread that function will run on.
     * we need this class to be running all the time thats why we run it in a seprate thread.
     */
  Thread thrd;
  /**
   * Used to suspend the function running.
   */
  boolean suspended;
  /**
   * Used to stop running the function.
   */
  boolean stopped;
  /**
   * steps received from the server
   */
 int steps;
 /**
   * used to create a socket connection with the motionClassifier deamon to send and receive data.
   */
StreamConnection conn;
/**
  * Represent the socket connection address.
  * <p> for example url="socket://127.0.0.1:8101"
  * <p> socket: is the protocol used.
  * <p> 127.0.0.1: is the standard IP address used for a loopback network connection. In other words, the connection is made to the same device.
  * <p> 8101: is the port number where the c++ daemon is listening for connection.
 */
private static String url = "socket://127.0.0.1:8444";
/**
 * StepOperation object
 * @see StepOperation
 */
 StepOperation stepOpr;
 /**
 * Reference to the main class midlet.
*/
 AAAStepUp stepMidlet;
 /**
  * Create the thread and initialize the other varibles.
  * @param d Reference to the main class midlet.
  */
  StepReciver(AAAStepUp d)
  {

    thrd = new Thread(this,"StepReciver");
    suspended = false;
    stopped = false;
    stepMidlet=d;
    stepOpr=new StepOperation(stepMidlet);
    steps=0;

  }
  /**
   * Starts running the thread.
   */
  public void start()
  {
 thrd.start();
  }
  /**
   * The function keep requsting number of steps and receiving them form the motion classifier deamon through socket connection.
   */
  public void run() {


    try {

            while(!stopped||!suspended){
            try{
    StreamConnection conn = (StreamConnection)Connector.open(url);

          OutputStream out = conn.openOutputStream();
          byte[] buf = "g".getBytes();// send it to motionClassifier deamon
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();



            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data);
            String response = new String(data, 0, actualLength);//number of steps
            steps= Integer.parseInt(response);
            in.close();
            conn.close();
            stepOpr.recivedSteps(steps);
             }//try
           catch(IOException ioe)
           {
               stepMidlet.watch.notifyWatchertoStepupDeamon();
            /*
             ioe.printStackTrace();
              Alert a=  new Alert("Acc server", "Unable to connect to acc server.",null,AlertType.WARNING);
              stepMidlet.display.setCurrent(a,stepMidlet.startlist);
           */

            }//catch

      //}
        synchronized (this) {
          while (suspended)
            wait();
          if (stopped)
            break;
        }
      }//while
    } catch (InterruptedException exc) {
      System.out.println(thrd.getName() + " interrupted.");
    }
    System.out.println("\n" + thrd.getName() + " exiting.");

  }//run
/**
 * Stop running the thread.
 */
  synchronized void stop()
  {
    stopped = true;
    suspended = false;
   
    notify();
  }
/**
 * Suspend running the thread.
 */
  synchronized void suspend()
  {
    suspended = true;
  }
/**
 * Resume running the thread.
 */
  synchronized void resume()
  {
    suspended = false;
    notify();
  }

}//end of class
