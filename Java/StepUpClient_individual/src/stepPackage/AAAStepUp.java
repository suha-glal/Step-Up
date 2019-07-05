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
newGroupView newgroupview;
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
 * pressing this command take the user back.
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
           //$$ startlist.append("History", null);
            



            stepview= new StepsView(this);
            proglistview= new ProgressListView(this);
            historyview=new HistoryView(this);
            profileview=new ProfileView(this);
            newgroupview= new newGroupView(this);
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
    * Add the 
    */
   private void addCommands()
{
 startlist.addCommand(backCommand);
  startlist.addCommand(exitCommand);
}

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

    void startStepCounting()
    {
profileview.initalizefromProfile();
stepReciver.start();
startlist.setCommandListener(this);

 //get Progresses
dailyprogress.getDailyProgress();
weeklyprogress.getWeeklyProgress();
teamprogress.getTeamProgress();
getProgress();//get progress every 2 hours

    }
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
void startStepUpMenu()
{
 profileview.initalizefromProfile();
       stepReciver.start();
        display.setCurrent(startlist);
        startlist.setCommandListener(this);
        //get Progress
       dailyprogress.getDailyProgress();
       weeklyprogress.getWeeklyProgress();
        teamprogress.getTeamProgress();
      getProgress();//get progress every 2 hours
      
 
}//
public void commandAction(Command c, Displayable d) {
if (c == exitCommand) {

           destroyApp(true);
           
        }
else if (d==startlist&& c==backCommand)
{
    display.setCurrent(null);
   
}//d==helpform&& c==backCommand
else if(d==startlist)
   {
    setDisplay(startlist.getSelectedIndex()) ;
  }
else if(d==joinlist)
{
    if(c==helpCommand)
    {
        display.setCurrent(helpform);
        helpform.setCommandListener(this);
    }
    else if(joinlist.getString(joinlist.getSelectedIndex()).equals("Create New Group"))
    {    //create new group
        setDisplay(4) ;
    }
    else if(joinlist.getString(joinlist.getSelectedIndex()).equals("Join Group"))
    {
        //join existing group
        setDisplay(3) ;
    }//else if

   
}//d==joinlist
else if (d==helpform&& c==backCommand)
{
    display.setCurrent(joinlist);
    joinlist.setCommandListener(this);
}//d==helpform&& c==backCommand


}//commandAction
void getProgress()
{
 new Thread()
{
    public void run()
    {

 Date before=null;
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
    public void pauseApp() 
    {

    }

    public void destroyApp(boolean unconditional) {
stepReciver.stop();
enddeamon();
Socketconnection groupsocket= new Socketconnection(this,this.stepview.stepsform);

        String steptosend="s"+username+","+userpassword+"?"+stepReciver.stepOpr.serversteps;

        String answer=groupsocket.SendRecive(steptosend.toLowerCase());
notifyDestroyed();
    }
}
