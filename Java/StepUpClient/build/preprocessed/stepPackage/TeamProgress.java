/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;


/**
 *The class is reponsible of managing team progress view
 * @author Suha
 */
public class TeamProgress implements  CommandListener{
    /**
     * The form will display the team progress
     */
    Form Progressform;
    /**
     *  pressing this command take the user to the previous screen.
     */
    private Command backCommand;
/**
 * Reference to the main class midlet.
 */
    private AAAStepUp stepupMidlet;
    
   /**
    *The constructor create the form and commands to it.
    * @param su reference to the main class midlet.
    */
    TeamProgress(AAAStepUp su)
    {
        stepupMidlet=su;
        Progressform =new Form("Team Progress");

        backCommand= new Command("Back",Command.BACK, 0);
        Progressform.addCommand(backCommand);
    }//TeamProgress
    /**
     * get Team progress by sending request to Step Up database.
     */
void getTeamProgress()
{

  String groupinfo="p"+stepupMidlet.groupname+","+stepupMidlet.grouppassword;
 handelSocketConnection(groupinfo);

 }//setProgress
/**
 * Starts a socket connection with step up server to send string of data, and then handles the server response.
 * @param groupinfo group name and password
 */
void handelSocketConnection(final String groupinfo)
{/*
new Thread()
{
    public void run()
    {*/
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,Progressform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

       if (answer.charAt(0) =='N')
        {
             Progressform.deleteAll();
             Progressform.append("There is no members in this group!");
        }
        else if (answer.charAt(0) =='W')
        {
             Progressform.deleteAll();
             Progressform.append("No one have walked yet!");
        }
         else if (answer.charAt(0) =='i')
        {
            
           Progressform.deleteAll();
             Progressform.append("Unable to retrive info!");
        }
        else
        {
            progrDisplay(answer);
            
        }//if 


  /*  }//run

}.start();
 */
}//handelSocketConnection
/**
 * display the team progress using HistogCanvas object.
 *
 * @param answer the string contains team members names and number of steps they walked.
 */
void progrDisplay(String answer )
{
    StringOperation stropr= new StringOperation();
   String []progstr=stropr.ProgressStr(answer);
    int len=progstr.length;
    int[] memberstep= new int[(len-4)/2];
    String[] membname= new String[(len-4)/2];
    String bestMemb[]= new String[2];
    String bestGrp[]= new String[2];
  
  
   int ind=0;
for(int i=0;i<len-4;i++){
   if(i%2==0){
    membname[ind]=progstr[i];
   }
   else{
    memberstep[ind]=Integer.parseInt(progstr[i]);
    ind++;
   }
}//for

    bestMemb[0]=progstr[len-4];
    bestMemb[1]=progstr[len-3];

    bestGrp[0]=progstr[len-2];
    bestGrp[1]=progstr[len-1];

    Progressform.deleteAll();
    HistogCanvas h= new HistogCanvas("Today Progress",memberstep,membname);
         Progressform.append(h);
        
          
}//progrDisplay
/**
 * Set team progress as the current view.
 */
void setProgressView()
{
stepupMidlet.display.setCurrent(Progressform);
Progressform.setCommandListener(TeamProgress.this);
}
/**
 * Handle commands
 * @param c commands
 * @param d display
 */
public void commandAction(Command c, Displayable d) {

if(d==Progressform&&c==backCommand)
{
stepupMidlet.display.setCurrent( stepupMidlet.proglistview.progresslist);
stepupMidlet.proglistview.progresslist.setCommandListener(stepupMidlet.proglistview);
}//if

}//commandAction
}//end class
