/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
/**
 *This class is responsible of setting the progress list view.
 * @author Suha
 */
public class ProgressListView implements  CommandListener {
/**
 * Progress list.
 */
List progresslist;
/**
 *  pressing this command take the user to the previous screen.
 */
private Command backCommand;
/**
 * Reference to the main class midlet.
*/
private AAAStepUp stepupMidlet;
/**
 * Create a list and add commands to the list.
 * @param su Reference to the main class midlet.
 */
ProgressListView(AAAStepUp su)
{
stepupMidlet=su;
progresslist=new List("Progress:", List.IMPLICIT);
            progresslist.append("My Daily Progress", null);
            progresslist.append("My Weekly Progress", null);
            progresslist.append("Team Today Progress", null);
backCommand = new Command("Back",Command.BACK, 1);
progresslist.addCommand(backCommand);
}//ProgressListView
/**
 * Set progress list as the current view.
 */
void setProgressListView()
{
stepupMidlet.display.setCurrent(progresslist);
progresslist.setCommandListener(ProgressListView.this);
}
/**
 * Set the main list as the current view.
 */
void mainlist()
{
    stepupMidlet.display.setCurrent(stepupMidlet.startlist);
    stepupMidlet.startlist.setCommandListener(stepupMidlet);
}
/**
 * handle commands
 * @param c commands
 * @param d display
 */
public void commandAction(Command c, Displayable d)
{
        if(c==backCommand)
        {
       mainlist();
        }
        else if (d==progresslist)
        {
         setProgressType(progresslist.getSelectedIndex()) ;
        }//
}
/**
 * Set progress type to be diplayed:
 * 1. Daily Progress
 * 2.Weekly Progress
 * 3.Team Progress
 * @param x number specifiy the type of progress to be set.
 */
 void setProgressType(int x)
 {
 if(x==0)
 {

stepupMidlet.dailyprogress.setDailyProgressView();
 }
 else if (x==1)
 {

stepupMidlet.weeklyprogress.setWeeklyProgressView();
 }
 else if(x==2)
 {
     stepupMidlet.teamprogress.setProgressView();
 }//else if

 }//setProgressType
}//end class
