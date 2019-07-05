/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Suha
 */
public class HistoryView implements  CommandListener{

  private Form Historyform;
  private Form Historydata;
  private AAAStepUp stepupMidlet;

  private Command backCommand;
  private Command requestCommand;

  private DateField date1;
  private DateField date2;
  private DateField time1;
  private DateField time2;
    int hour=0;
    int minute=0;
    int year=0;
    int month=0;
    int dayofmonth=0;
    int hour2=0;
    int minute2=0;
    int year2=0;
    int month2=0;
    int dayofmonth2=0;
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
void setHistory()
{
 stepupMidlet.display.setCurrent(Historyform);
Historyform.setCommandListener(HistoryView.this);
}
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
     histogCanvas h= new histogCanvas(date1str+" To "+date2str,memberstep,membname);
         
          Historydata.deleteAll();
          Historydata.append(h);
          stepupMidlet.display.setCurrent(Historydata);
           Historydata.setCommandListener(HistoryView.this);

}//progrDisplay

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

Date t1= time1.getDate();
Date d1=date1.getDate();
Date t2=time2.getDate();
Date d2=date2.getDate();
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
