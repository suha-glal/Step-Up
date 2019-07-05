/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

import javax.microedition.lcdui.*;
/**
 * This class is responsible of getting and setting the user daily progress.
 * @author Suha
 */
public class DailyProgress implements  CommandListener{
    /**
     * The form display the daily progress information.
     */
    Form dailyProgform;
    /**
     *  pressing this command take the user to the previous screen.
     */
    private Command backCommand;
    /**
     * Reference to the main class midlet.
     */
    private AAAStepUp stepupMidlet;

/**
 * The constructor create new form and add commands to it.
 * @param su Reference the main midlet class.
 */
    DailyProgress(AAAStepUp su)
    {
        stepupMidlet=su;
        dailyProgform =new Form("My Daily Progress");

        backCommand= new Command("Back",Command.BACK, 0);
        dailyProgform.addCommand(backCommand);
    }//DailyProgress
    /**
     * Request the daily progress form step up sever (it sends the letter 'd' followed by the user name.
     * @see DailyProgress#handelSocketConnection(java.lang.String)
     */
void getDailyProgress()
{

  String userinfo="d"+stepupMidlet.username;
  handelSocketConnection(userinfo);

}//setProgress
/**
 * Starts a socket connection with step up server to send string of data, and then handles the server response.
 * @param info the data request which will be sent to the server.
 */
void handelSocketConnection(final String info)
{/*
new Thread()
{
    public void run()
    {*/
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,dailyProgform);

        String answer=groupsocket.SendRecive(info.toLowerCase());

       if (answer.charAt(0) =='N')
        {
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
/**
 * display the daily progress to the user in the screen.
 *
 * @param answer the user daily progress sent by the server.
 *
 *  */
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

  LineGraph h= new LineGraph(month+"/"+progstr[0].substring(0, 4), dayofmonth,  dailysteps);

   dailyProgform.append(h);

}//progrDisplay
/**
 * set the daily progress view.
 */
void setDailyProgressView()
{
stepupMidlet.display.setCurrent(dailyProgform);
dailyProgform.setCommandListener(DailyProgress.this);
}
/**
 * handle commands.
 * @param c command
 * @param d display
 */
public void commandAction(Command c, Displayable d) {

if(d==dailyProgform&&c==backCommand)
{
  stepupMidlet.display.setCurrent( stepupMidlet.proglistview.progresslist);
stepupMidlet.proglistview.progresslist.setCommandListener(stepupMidlet.proglistview);
}//if

}//commandAction
}//end class
