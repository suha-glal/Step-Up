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
public class WeekProgress implements  CommandListener{
     Form WeeklyProgressform;
    private Command backCommand;
    private AAAStepUp stepupMidlet;


    WeekProgress(AAAStepUp su)
    {
        stepupMidlet=su;
WeeklyProgressform =new Form("Team Progress");

backCommand= new Command("Back",Command.BACK, 0);
WeeklyProgressform.addCommand(backCommand);
    }//TeamProgress
void getWeeklyProgress()
{
// stepupMidlet.display.setCurrent(stepupMidlet.formRunning);
  String userinfo="w"+stepupMidlet.username;
 handelSocketConnection(userinfo);



}//setProgress
void handelSocketConnection(final String groupinfo)
{
/*new Thread()
{
    public void run()
    {*/
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,WeeklyProgressform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

       if (answer.charAt(0) =='N')
        {/*$$
            Alert a=  new Alert("Group progress", "Unable to get weekly progress!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.startlist.setCommandListener(stepupMidlet);
          stepupMidlet.display.setCurrent(a,stepupMidlet.startlist);
          $$*/
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

void progrDisplay(String answer)
{
    StringOperation stropr= new StringOperation();
   String []progstr=stropr.Spliter(answer, "!");
int [] wksteps= new int[4];
   for(int i=0;i<progstr.length-1;i++)
   {
       wksteps[i]= Integer.parseInt(progstr[i]);
   }

String wkNo[]= {"Thiswk","1wkAgo","2wkAgo","3wkAgo"};

    WeeklyProgressform.deleteAll();

histogCanvas h= new histogCanvas("Weekly Progress",wksteps,wkNo);
WeeklyProgressform.append(h);

}//progrDisplay
void setWeeklyProgressView()
{
stepupMidlet.display.setCurrent(WeeklyProgressform);
WeeklyProgressform.setCommandListener(WeekProgress.this);
}

public void commandAction(Command c, Displayable d) {

if(d==WeeklyProgressform&&c==backCommand)
{
    stepupMidlet.display.setCurrent( stepupMidlet.proglistview.progresslist);
    stepupMidlet.proglistview.progresslist.setCommandListener(stepupMidlet.proglistview);
}//if

}//commandAction

}
