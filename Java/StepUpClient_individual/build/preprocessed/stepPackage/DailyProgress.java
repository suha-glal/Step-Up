/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import java.util.Calendar;
import java.util.Date;
import javax.microedition.lcdui.*;
/**
 *
 * @author Suha
 */
public class DailyProgress implements  CommandListener{
    Form dailyProgform;
    private Command backCommand;
    private AAAStepUp stepupMidlet;


    DailyProgress(AAAStepUp su)
    {
        stepupMidlet=su;
        dailyProgform =new Form("My Daily Progress");

        backCommand= new Command("Back",Command.BACK, 0);
        dailyProgform.addCommand(backCommand);
    }//DailyProgress
void getDailyProgress()
{

  String userinfo="d"+stepupMidlet.username;
  handelSocketConnection(userinfo);


}//setProgress
void handelSocketConnection(final String groupinfo)
{/*
new Thread()
{
    public void run()
    {*/
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,dailyProgform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

       if (answer.charAt(0) =='N')
        {/*$$
            Alert a=  new Alert("Daily progress", "Unable to get daily progress!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.startlist.setCommandListener(stepupMidlet);
          stepupMidlet.display.setCurrent(a,stepupMidlet.startlist);
          $$*/
    dailyProgform.deleteAll();
    dailyProgform.append("Unable to get daily progress!");
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

void progrDisplay(String answer )
{
    StringOperation stropr= new StringOperation();
   String []progstr=stropr.Spliter(answer, "!");

  int [] dailysteps= new int[7];
  String []dayofmonth= new String[7];
  int daycounter=0;
  int dayofmonthcounter=0;
  String month= month=progstr[0].substring(5,7);
  int onetime=0;
//   for(int i=0;i<progstr.length-1;i++)
  for(int i=progstr.length-2;i>=0;i--)
   {

       if(i%2==0)
       {
       dayofmonth[dayofmonthcounter]=progstr[i].substring(8,progstr[i].length());
       if(month.equals(progstr[i].substring(5,7))==false&& onetime==0)
       {
           month+=("-"+progstr[i].substring(5,7));
           onetime=1;
       }
dayofmonthcounter++;
       }
       else if(i%2==1)
       {
       dailysteps[daycounter]= Integer.parseInt(progstr[i]);
               daycounter++;
       }
   }
 dailyProgform.deleteAll();

  lineGraph h= new lineGraph(month+"/"+progstr[0].substring(0, 4), dayofmonth,  dailysteps);

   dailyProgform.append(h);

}//progrDisplay
void setDailyProgressView()
{
stepupMidlet.display.setCurrent(dailyProgform);
dailyProgform.setCommandListener(DailyProgress.this);
}

public void commandAction(Command c, Displayable d) {

if(d==dailyProgform&&c==backCommand)
{
  stepupMidlet.display.setCurrent( stepupMidlet.proglistview.progresslist);
stepupMidlet.proglistview.progresslist.setCommandListener(stepupMidlet.proglistview);
}//if

}//commandAction
}//end class
