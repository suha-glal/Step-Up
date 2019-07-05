/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Date;
/**
 * This is the main class (MIDlet) were the j2me application starts executing
 * @author Suha
 */

public class AAAStepUp extends MIDlet implements  CommandListener{
/**
 * reference to the screen display
 */
Display display;
/**
 * help information will be displayed in this form.
 */
Form helpform;

/**
 * used to show the user that the application is trying to connect to the server.
 */
Gauge gauge;
/**
 * the gauge will be added to this form.
 * @see #gauge.
 */
Form formRunning;
/**
 * the main menu list it includes:
 * <p>Start: it includes number of steps walked, number of meters walked, and number of calories burned.
 * <p>Progress: it shows the progress of the team members, the progress include number of steps every members walked during the day.
 * <p>History: the user can choose the a specific period and check the progress of his grop
 */
List startlist;
/**
 * the list that will be displayed when you open the application for the first time. If you did not register the list will appear untill you register and fill your profile information.
 *<p> It consist of :
 * <p> create new group
 * <p> Join group
 */
List joinlist;
/**
 * use the object to display No. of steps walked, distance walked in km and number of calories burned in Kcal.
 */
StepsView stepview;
/**
 * use this view to display: daily progress, weekly progress, and team progress list.
 */
ProgressListView proglistview;
/**
 * use this view to allow the user to specifiy a spesific dates and aquire the team progress for these dates.
 */
HistoryView historyview;
/**
 * used to request profile information(group name,group password,first name,last name,user name, user password,age,weight,gender) from the user.
 */
ProfileView profileview;
/**
 * used to fill the new group information.
 */
NewGroupView newgroupview;
/**
 * used to display the daily progress to the user.
 */
DailyProgress dailyprogress;
/**
 * used to display the weekly progress to the user.
 */
WeekProgress weeklyprogress;
/**
 * used to display the team progress.
 */
TeamProgress teamprogress;
/**
 * pressing this command exit the program.
 */
private Command exitCommand;
/**
 * pressing this command gets the help form.
 */
private Command helpCommand;
/**
 *  pressing this command take the user to the previous screen.
 */
private Command backCommand;
/**
 * save the user name.
 */
String username;
/**
 * save the user password.
 */
String userpassword;
/**
 * save the group name.
 */
String groupname;
/**
 * save the group password
 */
String grouppassword;
/**
 * save the gender. If it equal 0 it means female and if it equal 1 it means male.
 */
int gender;
/**
 * save the user weight.
 */
int weight;
/**
 * save the user height.
 */
int height;
/**
 * specifiy the the frequency of updating the progress of the user and his/her team members.
 */
int updatetime=120000;//sleep(1000)sleeps for 1 second --> 120000 ms = 2 min
/**
 * handles symbian c++ step counter deamon. It connects to the deamon,starts the communication and ended it.
 * @see DeamonClient
 */
DeamonClient deamonClient;
/**
 * it receives number of steps from symbian c++ motion classifier deamon.
 */
StepReciver stepReciver;
/**
 * it communicate with symbian c++ watcher deamon that runs all the time in the background.
 * @see Watcher
 */
Watcher watch;
/**
 * the constructor :
 * 1.initialize the application main variables.
 * 2. create the main list
 */
   public  AAAStepUp()
    {
          display = Display.getDisplay(this);
          stepReciver=new StepReciver(this);
          deamonClient= new DeamonClient(this);
          watch=new Watcher(this);
          watch.notifyWatchertoStart();
          watch.notifyWatchertoStepupDeamon();
         


            startlist=new List("StepUp:", List.IMPLICIT);
            startlist.append("Start", null);
            startlist.append("Progress", null);
            startlist.append("History", null);
            



            stepview= new StepsView(this);
            proglistview= new ProgressListView(this);
            historyview=new HistoryView(this);
            profileview=new ProfileView(this);
            newgroupview= new NewGroupView(this);
           dailyprogress= new  DailyProgress (this);
           weeklyprogress= new WeekProgress(this);
           teamprogress= new TeamProgress(this);



           formRunning = new Form("Connecting to StepUp Database");
           formRunning.append(new Gauge("Connecting",false,Gauge.INDEFINITE,Gauge.CONTINUOUS_RUNNING));

createCommands();
addCommands();
           
    }//AAAstepUp
   /**
    * create the application commands and give them priority which include:
    * Exit command to exit the application.
    * Back command to go back  from the application to the main screen.
    * Help command to take the user to the help instructions.
    */
   private void createCommands()
{
       exitCommand = new Command("Exit", Command.EXIT, 0);
       helpCommand=new Command("Help",Command.HELP, 1);
      backCommand = new Command("Back",Command.BACK, 1);
}
   /**
    * Add the commands back and exit tot he main list.
    */
   private void addCommands()
{
 startlist.addCommand(backCommand);
  startlist.addCommand(exitCommand);
}
/**
 * Alert the watcher deamon that the application started.
 */
   void startdeamon()
{
    new Thread()
    {
        public void run()
        {
            deamonClient.startdeamon();
        }
    }.start();

}
   /**
    * Alert the watcher deamon that the application closed.
    */
void enddeamon()
{
    new Thread()
    {
        public void run()
        {
            deamonClient.enddeamon();
        }
    }.start();

}
/**
 * This function get called if this is the first time the user open the application or he/she have not finish registration process.
 * The function do the following:
 * <p> 1- get the user information from the mobile database this information.
 * <P> 2- start recive number of walked steps from the step counter deamon.
 * <p> 3- connect to step up database on the server to get the daily, weekly and team progress.
 * <p> 4- set the application to get the progress every specific time interval.
 */
    void startStepCounting()
    {
profileview.initalizefromProfile();//get the user information from the mobile database.
stepReciver.start();//start recive number of walked steps from the step counter deamon.
startlist.setCommandListener(this);

 //3- connect to step up database on the server to get the daily, weekly and team progress.

dailyprogress.getDailyProgress();
weeklyprogress.getWeeklyProgress();
teamprogress.getTeamProgress();

getProgress();//4- set the application to get the progress every specific time interval.

    }
    /**
     * This function is called when the system activates the MIDlet.
     * <p> IT check if this is the first time the user opens the application it display "Create/join" list and if the user have
     * created group then it only display "Join Group" list's item and if the user already registed it diplay the main menu.
     *
     */
    public void startApp() {
        
 if(profileview.profiledb.getNumOfRec()<1)
            {
joinlist= new List("Create/Join Group:", List.IMPLICIT);

     if(newgroupview.groupdb.getNumOfRec()<1)
      joinlist.append("Create New Group", null);

      joinlist.append("Join Group", null);
      joinlist.addCommand(exitCommand);
     // joinlist.addCommand(helpCommand);

   /*   helpform=new Form("Welcome to StepUp Appication");
      helpform.append("If you want to join an existing group you should have the group " +
                    "name and password and then select 'Join Group '" +
                    " if you want to create a new group then select 'Create New Group' from the list options");
      helpform.addCommand(backCommand);
    */

              display.setCurrent(joinlist);
              joinlist.setCommandListener(this);
            }
    else{

startStepUpMenu();
        }//else
    }
 /**
 * This function get called if the user have registed before which mean his information is saved in the mobile.
 * The function do the following:
 * <p> 1- get the user information from the mobile database.
 * <P> 2- start recive number of walked steps from the step counter deamon.
 * <p> 3- connect to step up database on the server to get the daily, weekly and team progress.
 * <p> 4- set the application to get the progress every specific time interval.
 */
void startStepUpMenu()
{
 profileview.initalizefromProfile();//1- get the user information from the mobile database.
        stepReciver.start();//2- start recive number of walked steps from the step counter deamon.
        display.setCurrent(startlist);//set the main menu as the main view.
        startlist.setCommandListener(this);
        //get Progress
       dailyprogress.getDailyProgress();
       weeklyprogress.getWeeklyProgress();
        teamprogress.getTeamProgress();
      getProgress();//get progress every 2 hours
      
 
}//
/**
 * handle every command depending on the display that fired the command.
 * @param c the command for example (exitCommand,AboutCommand,DataCommand)
 * @param d the diaplay for example (form, list)
 * @see #startlist
 * @see #joinlist
 * @see #exitCommand
 * @see #helpCommand
 * @see #backCommand
 * @see #helpform
 */
public void commandAction(Command c, Displayable d) {
    //destory the application
if (c == exitCommand) {

           destroyApp(true);
           
        }
else if (d==startlist&& c==backCommand)
{
    //leave the application without closing it.
    display.setCurrent(null);
   
}//d==helpform&& c==backCommand
//display the start menu.
else if(d==startlist)
   {
    setDisplay(startlist.getSelectedIndex()) ;
  }

else if(d==joinlist)
{
    //display the help form
    if(c==helpCommand)
    {
        display.setCurrent(helpform);
        helpform.setCommandListener(this);
    }

    else if(joinlist.getString(joinlist.getSelectedIndex()).equals("Create New Group"))
    {    //display create new group form
        setDisplay(4) ;
    }
    else if(joinlist.getString(joinlist.getSelectedIndex()).equals("Join Group"))
    {
        //display profile form
        setDisplay(3) ;
    }//else if

   
}//d==joinlist
//display the joinlist
else if (d==helpform&& c==backCommand)
{
    display.setCurrent(joinlist);
    joinlist.setCommandListener(this);
}//d==helpform&& c==backCommand


}//commandAction
/**
 * gets the progress every specific time interval.
 */
void getProgress()
{
 new Thread()
{
    public void run()
    {

 
      while(true) {
                    try {

                        sleep(updatetime);
                        dailyprogress.getDailyProgress();
                       weeklyprogress.getWeeklyProgress();
                        teamprogress.getTeamProgress();
                      
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
          
         
 }//while true
}//run

}.start();
}//getProgress


/**
 * The function calls anther functions based on the number that is sent to it.
 * @param dis the number.
 * @see StepsView#setSteps()
 * @see ProgressListView#setProgressListView()
 * @see HistoryView#setHistory()
 * @see ProfileView#setProfile()
 * @see NewGroupView#setNewGroup()
 */
void setDisplay(int dis)
{
    if(dis==0)
    {
    stepview.setSteps();
    }
    else if(dis==1)
    {
proglistview.setProgressListView();
    }
    else if(dis==2)
    {
historyview.setHistory();
    }
    else if(dis==3)
    {
profileview.setProfile();
    }
    else if(dis==4)
    {
newgroupview.setNewGroup();
    }
}//setDisplay
/**
 * called when the application pause for any reson for example phone call.
 */
    public void pauseApp() 
    {

    }
/**
 * Before the application exit it make sure that the gps is not runing and if it was, it stops the gps and save the journy data.
 * Also, it check if splashScreen= false. This means the application started and the accelometer client,call log client and key capture client is running so it closes them.
 * Finally, call notifyDestroyed().
 * @param unconditional if true it destroy the application if it is false it will not destroy the application.
 */
    public void destroyApp(boolean unconditional) {
stepReciver.stop();
enddeamon();
Socketconnection groupsocket= new Socketconnection(this,this.stepview.stepsform);

        String steptosend="s"+username+","+userpassword+"?"+stepReciver.stepOpr.serversteps;

        String answer=groupsocket.SendRecive(steptosend.toLowerCase());
notifyDestroyed();
    }
}
