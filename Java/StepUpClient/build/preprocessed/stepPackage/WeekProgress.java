/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
/**
 *The class is responsibel of managing the weekly progress view.
 * @author Suha
 */
public class WeekProgress implements  CommandListener{
    /**
     * A form to view the user weekly progress
     */
     Form WeeklyProgressform;
     /**
      *  pressing this command take the user to the previous screen.
      */
    private Command backCommand;
    /**
 * Reference to the main class midlet.
*/
    private AAAStepUp stepupMidlet;
/**
 * The constructor saves reference to the main class midlet, initalize the form,and add commands to the form.
 * @param su reference to the main class midlet.
 */

    WeekProgress(AAAStepUp su)
    {
        stepupMidlet=su;
WeeklyProgressform =new Form("Team Progress");

backCommand= new Command("Back",Command.BACK, 0);
WeeklyProgressform.addCommand(backCommand);
    }//TeamProgress
    /**
     * Gets the user weekly progress from Step up server by sending the usrname.
     */
void getWeeklyProgress()
{
// stepupMidlet.display.setCurrent(stepupMidlet.formRunning);
  String userinfo="w"+stepupMidlet.username;
 handelSocketConnection(userinfo);



}//setProgress
/**
 * Starts a socket connection with step up server to send string of data, and then handles the server response.
 * @param info the data request which will be sent to the server.
 */
void handelSocketConnection(final String groupinfo)
{
/*new Thread()
{
    public void run()
    {*/
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,WeeklyProgressform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

       if (answer.charAt(0) =='N')
        {
           WeeklyProgressform.deleteAll();
           WeeklyProgressform.append("Unable to get weekly progress!");
        }
        
        else
        {
            progrDisplay(answer);

        }//if

/*
    }//run

}.start();
 */
}//handelSocketConnection
/**
 * Display the user weekly progress in a bar graph.
 * @param answer
 */
void progrDisplay(String answer)
{
    StringOperation stropr= new StringOperation();
   String []progstr=stropr.Spliter(answer, "!");
int [] wksteps= new int[4];
   for(int i=0;i<progstr.length-1;i++)
   {
       wksteps[i]= Integer.parseInt(progstr[i]);
   }

String wkNo[]= {"0wkAgo","1wkAgo","2wkAgo","3wkAgo"};

    WeeklyProgressform.deleteAll();

HistogCanvas h= new HistogCanvas("Weekly Progress",wksteps,wkNo);
WeeklyProgressform.append(h);

}//progrDisplay
/**
 * Set weekly progress as the current view.
 */
void setWeeklyProgressView()
{
stepupMidlet.display.setCurrent(WeeklyProgressform);
WeeklyProgressform.setCommandListener(WeekProgress.this);
}
/**
 * handle commands.
 * @param c command
 * @param d display
 */
public void commandAction(Command c, Displayable d) {
//go back to the progress list view
if(d==WeeklyProgressform&&c==backCommand)
{
    stepupMidlet.display.setCurrent( stepupMidlet.proglistview.progresslist);
    stepupMidlet.proglistview.progresslist.setCommandListener(stepupMidlet.proglistview);
}//if

}//commandAction

}
