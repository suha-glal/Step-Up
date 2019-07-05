/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author Suha
 */
public class ProfileView implements  CommandListener{
    Form Profileform;

    private Command exitCommand;
    private Command JoinCommand;
// Profile items
 TextField FirstNamefield;
 TextField LastNamefield;
 TextField UserNamefield;
 TextField UpasswordNamefield;
 TextField GroupNamefield;
 TextField GPasswordNamefield;
 TextField agefield;
 TextField weightfield;
 TextField heightfield;
 ChoiceGroup genderChoiceGroup;

String fn="",ln="",un="",upw,gn="",gpw="",age="",weight="",height="",gender="";

 private AAAStepUp stepupMidlet;
 DBmanger profiledb;
 ProfileView(AAAStepUp su)
 {
     stepupMidlet=su;

  Profileform = new Form("Your Profile");

profiledb=new DBmanger("Profile");

FirstNamefield = new TextField("First Name", "", 20, TextField.INITIAL_CAPS_WORD);
LastNamefield= new TextField("Last Name", "", 20, TextField.INITIAL_CAPS_WORD);
UserNamefield= new TextField("User Name", "", 5, TextField.PLAIN);
UpasswordNamefield= new TextField("User Password Name", "", 20, TextField.PASSWORD);
GroupNamefield= new TextField("Group Name", "", 20, TextField.PLAIN);
GPasswordNamefield= new TextField("Group Password Name", "", 20, TextField.PASSWORD);
agefield= new TextField("Age", "", 3, TextField.NUMERIC);
weightfield= new TextField("Weight - Kg", "", 3, TextField.NUMERIC);
heightfield= new TextField("Height - cm", "", 3, TextField.NUMERIC);
genderChoiceGroup = new ChoiceGroup ("Gender",ChoiceGroup.EXCLUSIVE);

genderChoiceGroup.append("Female", null);
genderChoiceGroup.append("Male", null);

Profileform.append(GroupNamefield);
Profileform.append(GPasswordNamefield);
Profileform.append(FirstNamefield );
Profileform.append(LastNamefield);
Profileform.append(UserNamefield);
Profileform.append(UpasswordNamefield);
Profileform.append(agefield);
Profileform.append(weightfield);
Profileform.append(heightfield);
Profileform.append(genderChoiceGroup);

exitCommand= new Command("Exit",Command.EXIT, 0);
JoinCommand= new Command("Join Group",Command.OK, 1);
Profileform.addCommand(exitCommand);
Profileform.addCommand(JoinCommand);

 }
void setProfile()
{
profiledb.openRecStore();
stepupMidlet.display.setCurrent(Profileform);
Profileform.setCommandListener(ProfileView.this);
}//setProfile
void setProfileFromGroup()
{
profiledb.openRecStore();
Profileform.setCommandListener(ProfileView.this);
}
void addToDatabase(String fn,String ln,String un,String upw,String gn,String gpw,
        String age,String weight,String height,String gender)
{
      profiledb.writeRecord(fn);
      profiledb.writeRecord(ln);
      profiledb.writeRecord(un);
      profiledb.writeRecord(upw);
      profiledb.writeRecord(gn);
      profiledb.writeRecord(gpw);
      profiledb.writeRecord(age);
      profiledb.writeRecord(weight);
      profiledb.writeRecord(height);
      profiledb.writeRecord(gender);


}//addToDatabase
void UpdateDatabase(String fn,String ln,String un,String upw,String gn,String gpw,
        String age,String weight,String height,String gender)
{
      profiledb.UpdateRecord(1,fn);
      profiledb.UpdateRecord(2,ln);
      profiledb.UpdateRecord(3,un);
      profiledb.UpdateRecord(4,upw);
      profiledb.UpdateRecord(5,gn);
      profiledb.UpdateRecord(6,gpw);
      profiledb.UpdateRecord(7,age);
      profiledb.UpdateRecord(8,weight);
      profiledb.UpdateRecord(9,height);
      profiledb.UpdateRecord(10,gender);


}//addToDatabase
void readFromDatabase()
{
       
            if (profiledb.getNumOfRec() >= 1) {
                FirstNamefield.setString(profiledb.readRecord(1));
                LastNamefield.setString(profiledb.readRecord(2));
                UserNamefield.setString(profiledb.readRecord(3));
                UpasswordNamefield.setString(profiledb.readRecord(4));
                GroupNamefield.setString(profiledb.readRecord(5));
                GPasswordNamefield.setString(profiledb.readRecord(6));
                agefield.setString(profiledb.readRecord(7));
                weightfield.setString(profiledb.readRecord(8));
                heightfield.setString(profiledb.readRecord(9));
                int g;
                if (profiledb.readRecord(10).equals("Female")) {
                    g = 0;
                } else {
                    g = 1;
                }
                genderChoiceGroup.setSelectedIndex(g, true);
            }//if
       

}//readFromDatabase
void initalizefromProfile()
{
       
            if (profiledb.getNumOfRec() >= 1) {
                stepupMidlet.username = (profiledb.readRecord(3));
                stepupMidlet.userpassword = (profiledb.readRecord(4));
                stepupMidlet.groupname = (profiledb.readRecord(5));
                stepupMidlet.grouppassword = (profiledb.readRecord(6));
                stepupMidlet.weight = Integer.parseInt(profiledb.readRecord(8));
                stepupMidlet.height = Integer.parseInt(profiledb.readRecord(9));
                if (profiledb.readRecord(10).equals("Female")) {
                    stepupMidlet.gender = 0;
                } else {
                    stepupMidlet.gender = 1;
                }
            } //if
       


}//readFromDatabase
void handelSocketConnection(final String info)
{
new Thread()
{
    public void run()
    {
    Socketconnection groupsocket= new Socketconnection(stepupMidlet,Profileform);

        String answer=groupsocket.SendRecive(info.toLowerCase());
 // Alert a1=  new Alert("Answer", answer,null,AlertType.WARNING);
        //  a1.setTimeout(Alert.FOREVER);
        //  stepupMidlet.display.setCurrent(a1,Profileform);
        
        if (answer.charAt(0) =='D')
        {
             //stepupMidlet.display.setCurrent(Profileform);
              if(profiledb.getNumOfRec() >= 1)
         {
             UpdateDatabase(fn, ln, un,upw, gn, gpw,
         age, weight, height, gender);
         }//if
         else
         {
             addToDatabase( fn, ln, un, upw,gn,gpw,
         age,weight,height, gender);
         }//else
            //  stepupMidlet.startStepUpMenu();
          Alert a=  new Alert("Join Group", "You are member of "+GroupNamefield.getString().toUpperCase()+
                      " group now!",null,AlertType.CONFIRMATION);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.startStepCounting();
          stepupMidlet.display.setCurrent(a,stepupMidlet.startlist);
         
        }//if "D"
        else if (answer.charAt(0) =='N')
        {
            Alert a=  new Alert("Join Group", "The group name you entered does not exist,enter the correct name please!",null,AlertType.ERROR);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Profileform);
        }
        else if (answer.charAt(0) =='X')
        {
            Alert a=  new Alert("Join Group", "The group has six members, this is the maximum number of members in one group!",null,AlertType.ERROR);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Profileform);
        }
        else if (answer.charAt(0) =='P')
        {
            Alert a=  new Alert("Join Group", "The group password you entered is wrong,enter the correct password please!",null,AlertType.ERROR);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Profileform);
        }
        else if (answer.charAt(0) =='U')
        {
            Alert a=  new Alert("Join Group", "The user name you entered already exist,try different user name please!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Profileform);
        }
    }//run

}.start();
}//handelSocketConnection
public void commandAction(Command c, Displayable d) {
if(d==Profileform)
{
    
    if(c==exitCommand)
{
     profiledb.closeRecStore();
    stepupMidlet.destroyApp(true);
  stepupMidlet.notifyDestroyed();
}//if
  else if(c==JoinCommand)
   {
       
        fn=FirstNamefield.getString();
        ln=LastNamefield.getString();
        un=UserNamefield.getString();
        upw=UpasswordNamefield.getString();
        gn=GroupNamefield.getString();
        gpw=GPasswordNamefield.getString();
        age=agefield.getString();
        weight=weightfield.getString();
        height=heightfield.getString();
        gender=genderChoiceGroup.getString(genderChoiceGroup.getSelectedIndex());
  if(fn.length()<1||ln.length()<1
      ||un.length()<1||upw.length()<1
       ||gn.length()<1||gpw.length()<1
        ||age.length()<1||weight.length()<1
        ||height.length()<1||gender.length()<1)
  {
    Alert a=  new Alert(" Join Group", "Please fill all the information!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,Profileform);
  }
  else
  {
            String infor="u"+fn+"!"+ln+"@"+un+"#"+upw+"$"
              +gender+"%"+age+"^"+weight+"&"+height+"*"+gn+"("+gpw;

             stepupMidlet.display.setCurrent(stepupMidlet.formRunning);
               handelSocketConnection(infor);

   
      
       
      
  }//else if

   }//c==JoinCommand
}//d==Profileform
}//commandAction
}//end class
