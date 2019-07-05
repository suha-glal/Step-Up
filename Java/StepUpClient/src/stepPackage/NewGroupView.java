/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;

/**
 *The class is responsibel of managing create or join a group view.
 * @author Suha
 */
public class NewGroupView implements  CommandListener{
    /**
     * The form to view group information.
     */
    Form Groupform;
    /**
     *  pressing this command take the user to the previous screen.
     */
    private Command backCommand;
    /**
     * pressing this command start the communication with step up server to create new group
     */
    private Command createCommand;
/**
 * Reference to the main class midlet.
*/
    private AAAStepUp stepupMidlet;
    /**
     * The group name text field.
     */
    private TextField GroupNamefield;
    /**
     * The group password text field.
     */
    private TextField GPasswordfield;
/**
 * DBmanger object to save the group information.
 */
    DBmanger groupdb;
      /**
       * group name string.
       */
    String gn="";
    /**
     * group password string.
     */
    String gpw="";
/**
 * initialize the varibles.
 * @param su reference to the main class midlet.
 */
    NewGroupView(AAAStepUp su)
    {
    stepupMidlet=su;
    groupdb=new DBmanger("Group");

          Groupform = new Form("Create new Group");

          GroupNamefield= new TextField("Group Name", "", 20, TextField.PLAIN);
          GPasswordfield= new TextField("Group Password Name", "", 20, TextField.PASSWORD);

          Groupform.append(GroupNamefield);
           Groupform.append(GPasswordfield);
           createCommands();
          
    }//NewGroupView
    /**
     * initialize and create commands to group view.
     */
    void createCommands()
    {
    backCommand= new Command("Back",Command.BACK, 0);
    createCommand= new Command("Create",Command.SCREEN, 1);
          Groupform.addCommand(createCommand);
          Groupform.addCommand(backCommand);
    }//createCommands
    /**
     * set new Group view as the current view.
     */
void setNewGroup()
{    groupdb.openRecStore();
    //readFromDatabase();
    stepupMidlet.display.setCurrent(Groupform);
    Groupform.setCommandListener(NewGroupView.this);
}
/**
 * Add the group name and password to the database.
 * @param gn1 group name
 * @param gpw1 group password
 */
void addToDatabase(String gn1,String gpw1)
{
      
      groupdb.writeRecord(gn1);
     groupdb.writeRecord(gpw1);
  


}//addToDatabase
/**
 * update the group name and password in the database.
 * @param gn1
 * @param gpw1
 */
void UpdateDatabase(String gn1,String gpw1)
{
     
      groupdb.UpdateRecord(1,gn1);
      groupdb.UpdateRecord(2,gpw1);
     
}//addToDatabase
/**
 * Get the group name and password from the data base to use them when communicating with step up server.
 */
void readFromDatabase()
{
        
            if (groupdb.getNumOfRec() >= 1) {
                GroupNamefield.setString(groupdb.readRecord(1));
                GPasswordfield.setString(groupdb.readRecord(2));
                
                
            }//if
        

}//readFromDatabase
/**
 * Starts a socket connection with step up server to send string of data, and then handles the server response.
 * @param groupinfo group name and password
 */
void handelSocketConnection(final String groupinfo)
{
new Thread()
{
    public void run()
    {
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,Groupform);

        String answer=groupsocket.SendRecive(groupinfo.toLowerCase());

        if (answer.charAt(0) =='D')
        {
            //should set empty form

             if(groupdb.getNumOfRec() >= 1)
         {
             UpdateDatabase( gn, gpw);
         }//if
         else
         {
             addToDatabase(gn,gpw);
         }//else
       
             stepupMidlet.profileview.setProfileFromGroup();

          Alert a=  new Alert("Create New Group", "The new group was created successfully!",null,AlertType.CONFIRMATION);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.profileview.Profileform);

        }//if "T"
        else if (answer.charAt(0) =='E')
        {
            Alert a=  new Alert("Create New Group", "The group name you entered already exist,try different name please!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Groupform);
        }
    }//run

}.start();
}//handelSocketConnection
/**
 * Handle  the commands
 * @param c command
 * @param d display that fire the command.
 */
public void commandAction(Command c, Displayable d) {

if(d==Groupform ){
   if(c==backCommand)
   {
      // go the the join list view
    stepupMidlet.display.setCurrent(stepupMidlet.joinlist);
    stepupMidlet.joinlist.setCommandListener(stepupMidlet);
    
   }//c==back
   //send the group name and password to the database to create a new group.
   else if(c==createCommand)
   {
       
       gn=GroupNamefield.getString();
       gpw=GPasswordfield.getString();
  if(gn.length()<1)
  {
    Alert a=  new Alert("Create New Group", "Please enter the new group name",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Groupform);
  }
  else if (gpw.length()<1)
  {
    Alert a=  new Alert("Create New Group", "Please enter the new group password",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Groupform);
  }
  else if(gn.length()>=1 && gpw.length()>=1)
  {
      

stepupMidlet.display.setCurrent(stepupMidlet.formRunning);
handelSocketConnection("g"+gn+"#"+gpw);

     
  }//else if

   }//c==create
}//if Groupform

}//commandAction
}//end class
