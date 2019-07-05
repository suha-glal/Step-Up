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
public class newGroupView implements  CommandListener{
    Form Groupform;
    private Command backCommand;
    private Command createCommand;

    private AAAStepUp stepupMidlet;
    private TextField GroupNamefield;
    private TextField GPasswordfield;

    DBmanger groupdb;
      
    String gn="";
    String gpw="";

    newGroupView(AAAStepUp su)
    {
    stepupMidlet=su;
    groupdb=new DBmanger("Group");

          Groupform = new Form("Create new Group");

          GroupNamefield= new TextField("Group Name", "", 20, TextField.PLAIN);
          GPasswordfield= new TextField("Group Password Name", "", 20, TextField.PASSWORD);

          Groupform.append(GroupNamefield);
           Groupform.append(GPasswordfield);
           createCommands();
          
    }//newGroupView
    void createCommands()
    {
    backCommand= new Command("Back",Command.BACK, 0);
    createCommand= new Command("Create",Command.SCREEN, 1);
          Groupform.addCommand(createCommand);
          Groupform.addCommand(backCommand);
    }//createCommands
void setNewGroup()
{    groupdb.openRecStore();
    //readFromDatabase();
    stepupMidlet.display.setCurrent(Groupform);
    Groupform.setCommandListener(newGroupView.this);
}
void addToDatabase(String gn1,String gpw1)
{
      
      groupdb.writeRecord(gn1);
     groupdb.writeRecord(gpw1);
  


}//addToDatabase
void UpdateDatabase(String gn1,String gpw1)
{
     
      groupdb.UpdateRecord(1,gn1);
      groupdb.UpdateRecord(2,gpw1);
     
}//addToDatabase
void readFromDatabase()
{
        
            if (groupdb.getNumOfRec() >= 1) {
                GroupNamefield.setString(groupdb.readRecord(1));
                GPasswordfield.setString(groupdb.readRecord(2));
                
                
            }//if
        

}//readFromDatabase
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
public void commandAction(Command c, Displayable d) {

if(d==Groupform ){
   if(c==backCommand)
   {
      // groupdb.closeRecStore();
    stepupMidlet.display.setCurrent(stepupMidlet.joinlist);
    stepupMidlet.joinlist.setCommandListener(stepupMidlet);
    
   }//c==back
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
