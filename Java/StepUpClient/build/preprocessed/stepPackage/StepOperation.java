/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

import javax.microedition.lcdui.*;

/**
 * The class is responsible of managing steps operations.
 * @author Suha
 */
public class StepOperation {
/**
 * Reference to the main class midlet.
 */
     AAAStepUp stepupMidlet;
     /**
      * Number of steps walked since the user started the application.
      */
     int steps ;
     /**
      * Number of steps sent to the server.
      */
     int serversteps;
      StepOperation(AAAStepUp d)
      {
       steps=0;
       serversteps=0;
       stepupMidlet=d;
      }

         
 /**
  * The Function called when the application receive new steps from the step counter deamon.
  * 1.It increment steps vairble and serversteps
  * 2. Update Steps view with the new steps
  * 3.when serversteps become more that 50 it call handelSocketConnection function.
  * @param noSteps new steps received from the step counter deamon.
  */
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

if(serversteps>=50)//>=50 step
{
//stepupMidlet.lastsenddate.setTime(now.getTime());
handelSocketConnection();
}//if

   

}//RecivedSteps
/**
 * The function send number of serversteps to step up server and set serversteps to zero
 */
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
