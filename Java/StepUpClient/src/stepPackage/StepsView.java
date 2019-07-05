/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
/**
 * Manage steps view which display number of steps , distance walked km,and calories burned.
 * @author Suha
 */
public class StepsView implements  CommandListener{
/**
 * The form displays number of steps , distance walked km,and calories burned.
 */
Form stepsform;
/**
 *pressing this command take the user to the previous screen.
 */
private Command backCommand;

/**
 * Reference to the main class midlet.
*/
private AAAStepUp stepupMidlet;
/**
 * Is object of type StepsCanvas
 * @see StepsCanvas
 */
 StepsCanvas Scanvas;
 /**
  * The constructor create form,add commands to the form ,and intialize StepsCanvas object.
  * @param su Reference to the main class midlet.
  */
StepsView(AAAStepUp su)
{
    stepupMidlet=su;

    stepsform = new Form("Now");
   Scanvas= new StepsCanvas();
   stepsform.append(Scanvas);

backCommand= new Command("Back",Command.BACK, 0);
stepsform.addCommand(backCommand);
}//StepsView
/**
 * Set step view as the current view.
 */
void setSteps()
{
   
  stepupMidlet.display.setCurrent(stepsform);
  stepsform.setCommandListener(StepsView .this);

}//setSteps
/**
 * The function displays number of steps , distance walked km,and calories burned in StepsCanvas object.
 * @param Steps number of steps received from the server
 */
void DisblyStepsDistanceAndCalories(int Steps )
{   /*
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
        

}
/**
 * handle commands
 * @param c command
 * @param d display
 */
public void commandAction(Command c, Displayable d) {
if(d==stepsform &&c==backCommand)
{
    stepupMidlet.display.setCurrent(stepupMidlet.startlist);
    stepupMidlet.startlist.setCommandListener(stepupMidlet);
}//if


}//commandAction
}//end class
