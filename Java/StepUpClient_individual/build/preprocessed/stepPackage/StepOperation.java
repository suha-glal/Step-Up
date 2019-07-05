/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
//import javax.microedition.io.file.*;
//import javax.microedition.io.*;
//import java.io.*;
import javax.microedition.lcdui.*;
//import java.util.Date;
/**
 *
 * @author Suha
 */
public class StepOperation {
     AAAStepUp stepupMidlet;
     int steps ;
     int serversteps;
      StepOperation(AAAStepUp d)
      {
       steps=0;
       serversteps=0;
       stepupMidlet=d;
      }

         
 
void recivedSteps(int noSteps)
{
   steps+=noSteps;
   serversteps+=noSteps;



new Thread()
{
    public void run()
    {
	stepupMidlet.stepview.DisblyStepsDistanceAndCalories(steps);
    }
}.start();

/*Date now= new Date();
long millsec2=now.getTime();
long millsec1=stepupMidlet.lastsenddate.getTime();
long result=millsec2-millsec1;
  result>=30/*000&&*/
if(serversteps>=50)//>=50 step
{
//stepupMidlet.lastsenddate.setTime(now.getTime());
handelSocketConnection();
}//if

   

}//RecivedSteps
void handelSocketConnection()
{
   
new Thread()
{
    public void run()
    {
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,stepupMidlet.stepview.stepsform);

        String steptosend="s"+stepupMidlet.username+","+stepupMidlet.userpassword+"?"+serversteps;
         serversteps=0;
        String answer=groupsocket.SendRecive(steptosend.toLowerCase());
        if(answer.charAt(0)=='S')
        {
           
          }
        else if(answer.charAt(0)=='F')
        {
             Alert a=  new Alert("Step Counter", "Unable to update database with your number of steps!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.stepview.stepsform);
        }
        else if(answer.charAt(0)=='P')
        {
             Alert a=  new Alert("Step Counter", "Unable to update database,wrong user password!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.stepview.stepsform);
        }
        else if (answer.charAt(0)=='N')
        {
             Alert a=  new Alert("Step Counter", "Unable to update database,wrong user name!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.stepview.stepsform);
        }//else if answer=N

    }//run

}.start();
}//handelSocketConnection

}
