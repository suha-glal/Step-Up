package stepPackage;


import javax.microedition.io.*;
import java.io.*;


class StepReciver implements Runnable {
  Thread thrd;
  boolean suspended;
  boolean stopped;
 int steps;
  StreamConnection conn;

  private static String url = "socket://127.0.0.1:8444";

 StepOperation stepOpr;
 AAAStepUp stepMidlet;
  StepReciver(AAAStepUp d)
  {

    thrd = new Thread(this,"StepReciver");
    suspended = false;
    stopped = false;
    stepMidlet=d;
    stepOpr=new StepOperation(stepMidlet);
    steps=0;

  }
  public void start()
  {
 thrd.start();
  }
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

  synchronized void stop()
  {
    stopped = true;
    suspended = false;
   
    notify();
  }

  synchronized void suspend()
  {
    suspended = true;
  }

  synchronized void resume()
  {
    suspended = false;
    notify();
  }

}//end of class
