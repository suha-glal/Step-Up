/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
import java.util.Calendar;
import java.util.Date;
/**
 * This class is responsible of getting and setting the user History view.
 * @author Suha
 */
public class HistoryView implements  CommandListener{

    /**
     * The form help the user define the times and dates for a period he/she  to know his team progress during it.
     */
  private Form Historyform;
  /**
   * Display the history data for the team.
   */
  private Form Historydata;
   /**
   * Reference to the midlet class.
   */
  private AAAStepUp stepupMidlet;
/**
 * pressing this command take the user to the previous screen.
 */
  private Command backCommand;
  /**
   * pressing this command sends request to the step up server to get history data.
   */
  private Command requestCommand;
/**
 * a Text field that allow the user to choose the start date.
 */
  private DateField date1;
 /**
 * a Text field that allow the user to choose the end date.
 */
  private DateField date2;
/**
 * a Text field that allow the user to choose the start time.
 */
  private DateField time1;
 /**
 * a Text field that allow the user to choose the end time.
 */
  private DateField time2;
  /**
   * Save the start hour
   */
    int hour=0;
   /**
   * Save the start minute
   */
    int minute=0;
   /**
   * Save the start year
   */
    int year=0;
   /**
   * Save the start month
   */
    int month=0;
   /**
   * Save the start day of month
   */
    int dayofmonth=0;
   /**
   * Save the end hour
   */
    int hour2=0;
    /**
   * Save the end minute
   */
    int minute2=0;
    /**
   * Save the end year
   */
    int year2=0;
   /**
   * Save the end month
   */
    int month2=0;
    /**
   * Save the end hour
   */
    int dayofmonth2=0;
    /**
     * The constructor save reference to the main class midlet,create the form, and add Date Fields to the the Historydata form.
     * @param su
     */
    HistoryView(AAAStepUp su)
    {
stepupMidlet=su;

Historyform = new Form("History");
Historydata=new Form("History Data");

 time1=new DateField("From time:", DateField.TIME);
  date1= new DateField("From date: ", DateField.DATE);
 time2=  new DateField("To time: ", DateField.TIME);
  date2= new DateField("To date: ", DateField.DATE);

 Historyform.append(time1);
 Historyform.append(date1);
 Historyform.append(time2);
 Historyform.append(date2);

backCommand= new Command("Back",Command.BACK, 0);
requestCommand=new Command("Get history",Command.OK, 1);

Historydata.addCommand(backCommand);
Historyform.addCommand(backCommand);
Historyform.addCommand(requestCommand);
    }

    /**
     * Set the History view to the user.
     */
void setHistory()
{
 stepupMidlet.display.setCurrent(Historyform);
Historyform.setCommandListener(HistoryView.this);
}

/**
 * Starts a socket connection with step up server to send string of data, and then handles the server response.
 * @param groupinfo the data request which will be sent to the server.
 */
void handelSocketConnection(final String groupinfo)
{
new Thread()
{
    public void run()
    {
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,Historyform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

       if (answer.charAt(0)=='N')
        {

          Alert a=  new Alert("Group History", "There is no members in this group!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Historyform);
          
        }
        else if (answer.charAt(0)=='W'||answer.charAt(0)=='T')
        {
          Alert a=  new Alert("Group History", "No one of your group walked in the specified period of time!",null,AlertType.INFO);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Historyform);
            
            }
         else if (answer.charAt(0)=='i')
        {
          Alert a=  new Alert("Group History", "Unable to retrive info!",null,AlertType.INFO);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Historyform);
           

        }
         else if (answer.charAt(0)=='G')
        {
             
          Alert a=  new Alert("Group History", "There is no members in this group!",null,AlertType.INFO);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Historyform);
              
          
        }
        else
        {
             progrDisplay(answer);
           
        }//if "T"


    }//run

}.start();
}//handelSocketConnection
/**
 * Display the team progress during the period the user specified in the screen.
 *
 * @param answer the team history progress sent by the server.
 *
 */
void progrDisplay(String answer )
{
    StringOperation stropr= new StringOperation();
   String []progstr=stropr.Spliter(answer,"@");
    int len=progstr.length-1;
    int[] memberstep= new int[(len)/2];
    String[] membname= new String[(len)/2];
   
   int ind=0;
for(int i=0;i<len;i++){
   if(i%2==0){
    membname[ind]=progstr[i];
   }
   else{
    memberstep[ind]=Integer.parseInt(progstr[i]);
    ind++;
   }
}//for

     
     String date1str=""+dayofmonth+"/"+month+"/"+year;
     String date2str=""+dayofmonth2+"/"+month2+"/"+year2;
     HistogCanvas h= new HistogCanvas(date1str+" To "+date2str,memberstep,membname);
         
          Historydata.deleteAll();
          Historydata.append(h);
          stepupMidlet.display.setCurrent(Historydata);
           Historydata.setCommandListener(HistoryView.this);

}//progrDisplay
/**
 * Handle commands.
 *
 * @param c commands
 * @param d display
 */
public void commandAction(Command c, Displayable d) {
if(d==Historyform){
    if(c==backCommand)
{
    stepupMidlet.display.setCurrent(stepupMidlet.startlist);
    stepupMidlet.startlist.setCommandListener(stepupMidlet);
}//if
    else if (c==requestCommand)
    {
Calendar cc = Calendar.getInstance();
//get the date and time form the date fields.
Date t1= time1.getDate();
Date d1=date1.getDate();
Date t2=time2.getDate();
Date d2=date2.getDate();
//if all the date fields is filled send
if(t1!=null && d1!=null && t2!=null&& d2!=null)
{
cc.setTime(t1);
 hour=cc.get(Calendar.HOUR_OF_DAY);
 minute=cc.get(Calendar.MINUTE);
cc.setTime(d1);
  year=cc.get(Calendar.YEAR);
  month=cc.get(Calendar.MONTH)+1;
  dayofmonth=cc.get(Calendar.DAY_OF_MONTH);
cc.setTime(t2);
 hour2=cc.get(Calendar.HOUR_OF_DAY);
 minute2=cc.get(Calendar.MINUTE);
cc.setTime(d2);
  year2=cc.get(Calendar.YEAR);
  month2=cc.get(Calendar.MONTH)+1;
  dayofmonth2=cc.get(Calendar.DAY_OF_MONTH);

String h="h"+stepupMidlet.groupname+","+stepupMidlet.grouppassword+"?"+minute+"@"+hour+"#"+dayofmonth+"$"+month+"%"+year+"^"+
         minute2+"*"+hour2+"("+dayofmonth2+")"+month2+">"+year2;
handelSocketConnection(h);
stepupMidlet.display.setCurrent(stepupMidlet.formRunning);

}//specified time and date
else
{
  Alert a=  new Alert("Group History", "Please fill the date and time information correctly!",null,AlertType.ERROR);
  a.setTimeout(Alert.FOREVER);
  stepupMidlet.display.setCurrent(a,Historyform);
}
    }//c==requestcommand


}//if d=history
else if(d==Historydata)
{
    if(c==backCommand)
{
    stepupMidlet.display.setCurrent(Historyform);
   Historyform.setCommandListener(this);
}//if
}//d==Historydata
}//commandAction

}//end class
