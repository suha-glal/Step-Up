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
public class TeamProgress implements  CommandListener{
    Form Progressform;
    private Command backCommand;
    private AAAStepUp stepupMidlet;
    
   
    TeamProgress(AAAStepUp su)
    {
        stepupMidlet=su;
Progressform =new Form("Team Progress");

backCommand= new Command("Back",Command.BACK, 0);
Progressform.addCommand(backCommand);
    }//TeamProgress
void getTeamProgress()
{
// stepupMidlet.display.setCurrent(stepupMidlet.formRunning);
  String groupinfo="p"+stepupMidlet.groupname+","+stepupMidlet.grouppassword;
 handelSocketConnection(groupinfo);

/* DBmanger profiledb=new DBmanger("Profile");
 //if(profiledb.getNumOfRec()>=1){
 String GroupName=profiledb.readRecords(5);
 String GPassword=profiledb.readRecords(6);
 if(GroupName!=null&&GPassword!=null)
 {
  String groupinfo="p"+GroupName+","+GPassword;
 handelSocketConnection(groupinfo);
 }//if
 }//if
 else{
     Alert a=  new Alert("Group progress", "You have not joined a group yet.Please fill in your profile information!",null,AlertType.ERROR);
  a.setTimeout(Alert.FOREVER);
  stepupMidlet.display.setCurrent(a,stepupMidlet.profileview.Profileform);
  stepupMidlet.profileview.Profileform.setCommandListener(stepupMidlet.profileview);
 }//else

*/

 
}//setProgress
void handelSocketConnection(final String groupinfo)
{/*
new Thread()
{
    public void run()
    {*/
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,Progressform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

       if (answer.charAt(0) =='N')
        {/*$$
            Alert a=  new Alert("Group progress", "There is no members in this group!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.startlist.setCommandListener(stepupMidlet);
          stepupMidlet.display.setCurrent(a,stepupMidlet.startlist);
          * $$*/
             Progressform.deleteAll();
             Progressform.append("There is no members in this group!");
        }
        else if (answer.charAt(0) =='W')
        {/*$$
            Alert a=  new Alert("Group progress", "No one have walked yet!",null,AlertType.INFO);
          a.setTimeout(Alert.FOREVER);
           stepupMidlet.startlist.setCommandListener(stepupMidlet);
          stepupMidlet.display.setCurrent(a,stepupMidlet.startlist);
          $$**/
             Progressform.deleteAll();
             Progressform.append("No one have walked yet!");
        }
         else if (answer.charAt(0) =='i')
        {
             /* $$
            Alert a=  new Alert("Group progress", "Unable to retrive info",null,AlertType.INFO);
          a.setTimeout(Alert.FOREVER);
           stepupMidlet.startlist.setCommandListener(stepupMidlet);
          stepupMidlet.display.setCurrent(a,stepupMidlet.startlist);
             $$**/
           Progressform.deleteAll();
             Progressform.append("Unable to retrive info!");
        }
        else
        {
            progrDisplay(answer);
            /*
           for(int i=0;i<progstr.length;i++)
            Progressform.append(progstr[i]+"\n");

          stepupMidlet.display.setCurrent(Progressform);
           Progressform.setCommandListener(TeamProgress.this);*/
        }//if 


  /*  }//run

}.start();
 */
}//handelSocketConnection

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
    histogCanvas h= new histogCanvas("Today Progress",memberstep,membname);
         Progressform.append(h);
        
          
}//progrDisplay
void setProgressView()
{
stepupMidlet.display.setCurrent(Progressform);
Progressform.setCommandListener(TeamProgress.this);
}

public void commandAction(Command c, Displayable d) {

if(d==Progressform&&c==backCommand)
{
stepupMidlet.display.setCurrent( stepupMidlet.proglistview.progresslist);
stepupMidlet.proglistview.progresslist.setCommandListener(stepupMidlet.proglistview);
}//if

}//commandAction
}//end class
