/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
/**
 *
 * @author Suha
 */
public class StepsView implements  CommandListener{

Form stepsform;
private Command backCommand;
//private Command startCommand;

//steps lab
StringItem stepslab;
StringItem distancelab;
StringItem calorieslab;
private AAAStepUp stepupMidlet;
 stepsCanvas Scanvas;
StepsView(AAAStepUp su)
{
    stepupMidlet=su;

    stepsform = new Form("Now");
   Scanvas= new stepsCanvas();
   stepsform.append(Scanvas);
/*
    stepslab=new StringItem ("Steps","");
    distancelab=new StringItem ("Distance","");
    calorieslab=new StringItem ("Calories","");

            stepsform.append(stepslab);
            stepsform.append(distancelab);
            stepsform.append(calorieslab);
*/
/*startCommand= new Command("Start",Command.OK, 1);
stepsform.addCommand(startCommand);*/
  //   stepsform.append(Scanvas);
backCommand= new Command("Back",Command.BACK, 0);
stepsform.addCommand(backCommand);
}//StepsView
void setSteps()
{
   
  stepupMidlet.display.setCurrent(stepsform);
  stepsform.setCommandListener(StepsView .this);

}//setSteps
void DisblyStepsDistanceAndCalories(int Steps )
{
    
     /*
     * Females: Your height x .413 equals your stride length
     * Males: Your height x .415 equals your stride length
     * distance = number of steps × step length
     * calories = kilometers x kilograms x 1.036
      */



	double steplength=0,distance=0,calories=0;

	if(stepupMidlet.gender==0)
	{
        //step length cm if height is cm and meter if height is meter
	steplength=(double)stepupMidlet.height* 0.413;
	}//if
	else if(stepupMidlet.gender==1)
	{
        //step length cm if height is cm and meter if height is meter
	steplength=(double)stepupMidlet.height * 0.415;
	}//else
	distance=Steps*steplength*0.00001;//km
	calories=distance * stepupMidlet.weight * 1.036;

        Scanvas.setSDC(Steps,Math.floor(distance), Math.floor(calories));
        /*
        stepslab.setText(""+Steps);
        distancelab.setText(""+Math.floor(distance));
        calorieslab.setText(""+Math.floor(calories));
         */

}
public void commandAction(Command c, Displayable d) {
if(d==stepsform &&c==backCommand)
{
    stepupMidlet.display.setCurrent(stepupMidlet.startlist);
    stepupMidlet.startlist.setCommandListener(stepupMidlet);
}//if


}//commandAction
}//end class
