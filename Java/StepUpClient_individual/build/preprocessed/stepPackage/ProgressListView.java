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
public class ProgressListView implements  CommandListener {

List progresslist;
private Command backCommand;
private AAAStepUp stepupMidlet;

ProgressListView(AAAStepUp su)
{
stepupMidlet=su;
progresslist=new List("Progress:", List.IMPLICIT);
            progresslist.append("My Daily Progress", null);
            progresslist.append("My Weekly Progress", null);
           //$$ progresslist.append("Team Today Progress", null);
backCommand = new Command("Back",Command.BACK, 1);
progresslist.addCommand(backCommand);
}//ProgressListView
void setProgressListView()
{
stepupMidlet.display.setCurrent(progresslist);
progresslist.setCommandListener(ProgressListView.this);
}
void mainlist()
{
    stepupMidlet.display.setCurrent(stepupMidlet.startlist);
    stepupMidlet.startlist.setCommandListener(stepupMidlet);
}
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
