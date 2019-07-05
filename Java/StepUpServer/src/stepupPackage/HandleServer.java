/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepupPackage;


import java.util.Date;
import java.text.SimpleDateFormat;

import java.awt.Color;
import java.io.*;
import java.sql.*;
import java.net.*;

/**
 * Handle communication with client and mysql database.
 * @author Suha
 */
/******************************** HandleServer  ***********/

class HandleServer extends Thread  {
    /**
      * used to create a socket connection with the step up  database to send and receive data.
      */

    Connection conn = null;
    /**
     * the url to mysql database
     */
    String url = "jdbc:mysql://localhost:3306/";
    /**
     * mysql database name
     */
    String dbName = "stepup";
    /**
     * Driver which will handle mysql querys.
     */
    String driver = "com.mysql.jdbc.Driver";
    /**
     * mysql database user name.
     */
    String userNamedb = "root";
    /**
     * mysql databasse user password
     */
    String password = "";


    /////////////
StepUp source;
/**
 * used to create a socket connection with step up  clients to send and receive data.
  */
Socket connection;
/**
 * used for reading data received from clients.
 */
InputStream inStream;
/**
 * used for reading data received from clients.
 */
DataInputStream inDataStream;
/**
 * used for writing data to the client.
 */
OutputStream outStream;
/**
 * used for writing data to the client.
 */
DataOutputStream outDataStream;
/**
 * message recived from the client.
 */
String message;

/**
 * Create the byte array to hold the data
 */
        byte[] b = new byte[(int)100];

// **************  HandleServer constructor
/**
 * Handle connection with mysql database.
 * @param socket
 * @param s
 */
    HandleServer ( Socket socket, StepUp s)  {

        super ();
        connection =  socket;
        source =  s;
 //connect to stepup database
        try {
      Class.forName(driver).newInstance();
      conn = DriverManager.getConnection(url+dbName,userNamedb,password);
      source.logDisplay.append("Connected to the database\n");

      } catch (Exception e) {
     // e.printStackTrace();
      source.logDisplay.append(e.getMessage());
    }
    }  // end constructor


// **************  run
/**
 * Gets connections from clients and handle their requests.
 */
    public void run  ()  {


        // write
       source.msgDisplay.setForeground ( Color.red );
        InetAddress inet = connection.getInetAddress ();
        String origin = inet.getHostName ();
        int originport = connection.getPort ();

        source.logDisplay.append ( "Adding Client: \n  "+origin+":"+originport+"\n\n" );
        

        try  {

            outStream = connection.getOutputStream ();
            outDataStream = new DataOutputStream ( outStream );
             //BufferedWriter out
            inStream = connection.getInputStream ();
            //inDataStream = new DataInputStream ( inStream );
BufferedReader d= new BufferedReader(new InputStreamReader(inStream));
            while ( true )  {

            
              message=d.readLine();
              source.logDisplay.append( "Message, below, received\n" );

              source.msgDisplay.setText (message);
             
               if(message.charAt(0)=='g')
               {
                 source.logDisplay.append( "Add new Group\n" );
                  addnewGroup();
                  break;
               }//'g'
               else if(message.charAt(0)=='u')
               {
                   source.logDisplay.append( "Add new User\n" );
                    addnewUser();
                    break;
               }//'u'
              //
                else if(message.charAt(0)=='s')
                {
                    source.logDisplay.append("Add Steps\n");
                    addSteps();
                    break;
                }//'s'
              //progress request
              else if(message.charAt(0)=='p')
                {
              source.logDisplay.append("get Team Progress\n");
              getTeamProgress();
              break;
                }//'p'
                   //history Request
              else if(message.charAt(0)=='h')
                {
                  source.logDisplay.append("get History\n");
                  getHistory();
                   break;
                }//'h'
              else if(message.charAt(0)=='d')
              {
                  source.logDisplay.append("get Daily Progress\n");
                  getDailyProgress();
                 break;
              }//'d'
              else if(message.charAt(0)=='w')
              {
                  source.logDisplay.append("get Weekly Progress\n");
                  getWeeklyProgress();
                 break;
              }//'w'

            }  // end while

        }  // end try

        catch ( EOFException except ) {
            source.logDisplay.append( "Connection closed by Client\n\n" );
            try  {
                connection.close ();
                return;
            }
            catch ( IOException e )  {
                source.logDisplay.append( e.getMessage()+"\n");
                return;
            }  // end IOException

         }  // end catch EOFException
         catch ( IOException e )  {
            source.logDisplay.append( "Connection closed abormally\n" );
            e.printStackTrace ();
            return;
         }  // end catch IOException


    }  // end run
/**
 * Add new group to step up database.
 */
void addnewGroup()
{ 
String newgroupName,newgroupPassword;
                  int i1;
                  i1=message.indexOf('#');

                  newgroupName=message.substring(1, i1);

                  newgroupPassword=message.substring(i1+1, message.length());

                  String todo = ("INSERT into groups(group_name,password,no_of_users)values ('"
                          +newgroupName+"', '"+
                 newgroupPassword+"','0')") ;
                   try {
                Statement s = conn.createStatement();
                int r = s.executeUpdate (todo);

                writetoStream("D","no");

                   }//try
          catch (Exception e) {

                            writetoStream("E",e.getMessage());

                }//catch

}//addnewGroup
/**
 * Add new user to a specific group.
 */
void addnewUser()
{

               String firstName,lastName,userName,passWord,gender,age,weight,group,height,gpassword;
               String dbpassword=null;
               int i1,i2,i3,i4,i5,i6,i7,i8,i9;
               i1=message.indexOf('!');
               i2=message.indexOf('@');
               i3=message.indexOf('#');
               i4=message.indexOf('$');
               i5=message.indexOf('%');
               i6=message.indexOf('^');
               i7=message.indexOf('&');
               i8=message.indexOf('*');
               i9=message.indexOf('(');

            firstName=message.substring(1, i1);
            lastName=message.substring(i1+1, i2);
            userName=message.substring(i2+1, i3);
            passWord=message.substring(i3+1, i4);
            gender=message.substring(i4+1, i5);
            age=message.substring(i5+1, i6);
            weight=message.substring(i6+1, i7);
            height=message.substring(i7+1, i8);
            group=message.substring(i8+1, i9);
            gpassword=message.substring(i9+1,message.length());

 //see if there are less than six members in the team
 String select1=("SELECT no_of_users FROM groups WHERE group_name ='"+group+"'" );
             ResultSet rs1;
             int t1=0;
             int no_of_users=0;
              try {
                Statement stmt1 = conn.createStatement();
                rs1 = stmt1.executeQuery(select1);
                 while ( rs1.next() ) {
                 no_of_users = rs1.getInt("no_of_users");
                 t1++;

            }//while
              }//try
                 catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            source.msgDisplay.append("Oh oops\n#1,"+e);
        }//catch count number of users
  if(no_of_users<6){

              //mysql database
             String select=("SELECT password FROM groups WHERE group_name ='"+group+"'" );
             ResultSet rs;
             int t=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(select);
                 while ( rs.next() ) {
                 dbpassword = rs.getString("password");
                 t++;

            }
        }
        catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            source.msgDisplay.append("Oh oops\n#1,"+e);

        }

if(t!=0)
             {
if(dbpassword.equals(gpassword)){
   
String todo = ("INSERT into users " +
                "(first_name,last_name,user_name,user_password,gender,age,weight,group_name) "+
                "values ('"+firstName+"', '"+lastName+"', '"+ userName+
                "','"+passWord+"','"+gender+"','"+age+"','"+weight+"','"+group+"')") ;

        try {
                Statement s = conn.createStatement();
                int r = s.executeUpdate (todo);
                //#####################################
                 writetoStream("D","no");//done the user was added successfully

     String todo2 = ("SELECT COUNT( * ) AS members FROM users WHERE group_name ='"+group+"'") ;

        ResultSet rs2;
          int tt=0,g=0;
              try {
                Statement stmt2 = conn.createStatement();
                rs2 = stmt2.executeQuery(todo2);
                 while ( rs2.next() )
                 {
                 g = rs2.getInt("members");
                 tt++;
                  }//while
                  }//try
        catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            source.msgDisplay.append("Oh oops\n#2,"+e);
            }//catch
          String todo3 = ("UPDATE groups SET no_of_users='"+g+"' WHERE group_name ='"+group+"'") ;
        try {
                 s = conn.createStatement();
                 r = s.executeUpdate (todo3);
           }
        catch (Exception e) {
             source.msgDisplay.append("Oh oops\n#3,"+e);
                }
          String todo4 = ("INSERT INTO steps (user_name,no_of_steps) VALUES ('"+userName+"', '0')") ;
        try {
                 s = conn.createStatement();
                 r = s.executeUpdate (todo4);
           }
        catch (Exception e) {
             source.msgDisplay.append("Oh oops \n#4"+e);
                }
                //######################################3
        }
        catch (Exception e) {

                     writetoStream("U",e.getMessage());//The username already exist

                }//catch

       

}//if dbpasword==gpassword
else
{

               writetoStream("P","no");//wrong group password\n

}//else
}//if t
else
{ writetoStream("N","no");//wrong group name\n

}//else

   }//no_of_users<6
  else
  {
   writetoStream("X","no");//wrong group password\n
  }//no_of_users=6
}//addnewUser
/**
 * Adds number of new walked steps to the database for every user individualy.
 */
void addSteps()
{
    String username,userpassword,dbupassword="",no_of_steps;
               int i1,i2;
               i1=message.indexOf(',');
               i2=message.indexOf('?');
               username=message.substring(1, i1);
              userpassword=message.substring(i1+1, i2);
              no_of_steps=message.substring(i2+1, message.length());
               String select=("SELECT user_password FROM users WHERE user_name ='"+username+"'" );
             ResultSet rs;
             int t=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(select);
                 while ( rs.next() )
                 {
                 dbupassword = rs.getString("user_password");
                 t++;

                  }//while
        }//try
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            source.msgDisplay.append("Oh oops\n"+e);
        }//catch
             if(t!=0)
             {
                 if(dbupassword.equals(userpassword))
                 {
                   String todo = ("INSERT into steps " +
                "(user_name,no_of_steps) "+
                "values ('"+username+"', '"+no_of_steps+"')") ;

        try {
                Statement s = conn.createStatement();
                int r = s.executeUpdate (todo);

                writetoStream("S","no");//"Updated the database\n"
        }//try
         catch (Exception e) {

             writetoStream("F",e.getMessage());
                }//catch
                 }//end if
                 else
                 {

                 writetoStream("P","no");
             }//elsedatabase userpassword != userpassword
             }//t!=0
             else
             {
                writetoStream("N","no");
             }//t==0

}//addSteps

/**
 * get the team progress.
 */
void getTeamProgress()
{
 String groupname,groupPassword,progresstxt="";
                 int i1,i2;
               i1=message.indexOf(',');
               groupname=message.substring(1, i1);
              groupPassword=message.substring(i1+1, message.length());

String date1="",date2="";
 String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

 Date nowdate = new Date();
  SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_NOW);
  date2=""+dateFormat.format(nowdate);
 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
Date now = new Date();
date1 = sdfDate.format(now);


     date1+=" "+0+":"+0+":"+0;
       source.msgDisplay.append("\n"+date1+"\n"+date2);

       String mem=("SELECT no_of_users FROM groups " +
               "WHERE group_name ='"+groupname+"' " +
               "AND PASSWORD ='"+groupPassword+"'" );
String sumstr=("SELECT steps.user_name, SUM( `no_of_steps` ) AS Total " +
        "FROM steps, groups, users " +
        "WHERE (users.user_name = steps.user_name " +
        "AND users.group_name = '"+groupname+"' " +
        "AND groups.password = '"+groupPassword+"' " +
        "AND time_date >='"+date1+"' AND time_date <='"+date2+"') "+
        "GROUP BY steps.user_name "+
        "ORDER BY Total DESC");
String topMember="SELECT user_name, SUM( no_of_steps ) AS Total FROM steps GROUP BY user_name ORDER BY Total DESC";
String topGroup="SELECT groups.group_name, SUM( no_of_steps ) AS Total FROM steps, groups, users " +
        "WHERE steps.user_name = users.user_name AND groups.group_name = users.group_name " +
        "GROUP BY group_name ORDER BY Total DESC";
ResultSet rs;
             int num=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(mem);
                 while ( rs.next() )
                 {
                 num = rs.getInt("no_of_users");

                  }//while
                  }//try
        catch (Exception e) {

               writetoStream("N",e.getMessage());
            }//catch
                  if(num!=0){

             String[] member = new String[num];
             int [] steps=new int[num];

             int t=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sumstr);
                while ( rs.next() )
                {
                member[t] = rs.getString("user_name");
                steps[t]=rs.getInt("Total");
                progresstxt+=member[t]+"@"+steps[t]+"@";
                 t++;
                }//while
               // history+="\n";
                if(t>0)
                {
                    String memb;
                    int ste;
                    try {
                Statement stmt1 = conn.createStatement();
                rs = stmt1.executeQuery(topMember);
                while ( rs.next() )
                {
                memb= rs.getString("user_name");
                ste=rs.getInt("Total");
               // progresstxt+="#";// Best member in the program
                progresstxt+=memb+"$"+ste+"$";

                 break;
                 }//while


                try {
                 stmt1 = conn.createStatement();
                rs = stmt1.executeQuery(topGroup);
                while ( rs.next() )
                {
                memb= rs.getString("group_name");
                ste=rs.getInt("Total");
               // progresstxt+="^";//Best group in the program
                progresstxt+=memb+"#"+ste+"#";

                 break;
                 }//while

                writetoStream(progresstxt,"no");
                    }//try
                 catch (Exception e) {

                              writetoStream(progresstxt,e.getMessage());
               }//catch
                    }//try

           catch (Exception e) {

              writetoStream(progresstxt,e.getMessage());

               }//catch

               }//if t>0
                else
                {

               writetoStream("W","no");
                }//else
        }//try
        catch (Exception e)
        {


              writetoStream("i",e.getMessage());

        }

       }//if num!=0



}//getTeamProgress
/**
 * Get the history of team progress.
 */
void getHistory()
{
    String groupname,groupPassword,mi,mi2,h,da,m,y,h2,da2,m2,y2,history="";
                 int i1,i2;
               i1=message.indexOf(',');
               i2=message.indexOf('?');
              groupname=message.substring(1, i1);
              groupPassword=message.substring(i1+1, i2);
              i1=message.indexOf('?');
              i2=message.indexOf('@');
              mi=message.substring(i1+1, i2);
              i1=message.indexOf('#');
             h=message.substring(i2+1, i1);
             i2=message.indexOf('$');
             da=message.substring(i1+1, i2);
             i1=message.indexOf('%');
             m=message.substring(i2+1, i1);
             i2=message.indexOf('^');
             y=message.substring(i1+1, i2);
              i1=message.indexOf('^');
              i2=message.indexOf('*');
              mi2=message.substring(i1+1, i2);
             i1=message.indexOf('(');
             h2=message.substring(i2+1, i1);
             i2=message.indexOf(')');
             da2=message.substring(i1+1, i2);
             i1=message.indexOf('>');
             m2=message.substring(i2+1, i1);
             y2=message.substring(i1+1, message.length());

            String date1="",date2="";
            date1=""+y+"-"+m+"-"+da+" "+h+":"+mi+":"+0+"."+0;
            date2=""+y2+"-"+m2+"-"+da2+" "+h2+":"+mi2+":"+0+"."+0;
       String mem=("SELECT no_of_users FROM groups " +
               "WHERE group_name ='"+groupname+"' " +
               "AND PASSWORD ='"+groupPassword+"'" );
        String sumstr=("SELECT steps.user_name, SUM( `no_of_steps` ) AS Total " +
                "FROM steps, groups, users " +
                "WHERE (users.user_name = steps.user_name " +
                "AND users.group_name = '"+groupname+"' " +
                "AND groups.password = '"+groupPassword+"' " +
                "AND time_date >='"+date1+"' AND time_date <='"+date2+"') "+
                "GROUP BY steps.user_name "+
                "ORDER BY Total DESC");

ResultSet rs;
             int num=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(mem);
                 while ( rs.next() )
                 {
                 num = rs.getInt("no_of_users");

                  }//while
                  }//try
        catch (Exception e) {
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                    source.msgDisplay.append("Oh oops\n"+e);
            }//catch
                  if(num!=0){

             String[] member = new String[num];
             int [] steps=new int[num];

             int t=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sumstr);
                while ( rs.next() )
                {
                member[t] = rs.getString("user_name");
                steps[t]=rs.getInt("Total");
                history+=member[t]+"@"+steps[t]+"@";
                 t++;
                }//while

                if(t>0)
                {

                    writetoStream(history,"no");
                }//if t>0
                else
                {

              writetoStream("T","no");
                }//else
        }//try
        catch (Exception ei) {

               writetoStream("i",ei.getMessage());

        }//catch

                  }//if num!=0
                  else{
                   writetoStream("G","no");

                  }//num==0



}//getHistory
/**
 * Gets the user daily progress.
 */
 void getDailyProgress()
 {
   String usename,result="";
   boolean canSend=false;

              usename=message.substring(1,  message.length());



String date1="";
 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
Date now = new Date();
date1 = sdfDate.format(now);


for(int i=0;i<7;i++){

    // to get date -1 i value
             ResultSet rsday;
             String day_date="select DATE_SUB('"+date1+"', INTERVAL "+ i +" DAY ) as date;";
             String day="";
              try {
                Statement stmt = conn.createStatement();
                rsday = stmt.executeQuery(day_date);
                 while ( rsday.next() )
                 {
                 day= rsday.getString("date");
                 System.out.println(day);

                  }//while
                result+=day+"!";
                canSend=true;
                  }//try
        catch (Exception e) {

               writetoStream("N",e.getMessage());
            }//catch
    ////////////////////////////////////////////////////////
String dailyprog="SELECT SUM( no_of_steps ) as total FROM (" +
        "SELECT user_name, no_of_steps, date( time_date ) AS date1 FROM steps WHERE 1 ) AS steps2" +
        " WHERE date1= DATE_SUB( '"+date1+"', INTERVAL "+ i +" DAY ) " +
       " AND user_name = '"+usename+"'";

ResultSet rs;
             int num=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(dailyprog);
                 while ( rs.next() )
                 {
                 num = rs.getInt("total");
                 System.out.println(num);

                  }//while
                result+=num+"!";
                canSend=true;
                  }//try
        catch (Exception e) {

               writetoStream("N",e.getMessage());
            }//catch



}//for i
if(canSend==true)
{
  writetoStream(result,"no");
  System.out.println(result);
}

 }//getDailyProgress
 /**
  * Gets the user weekly progress.
  */
 void  getWeeklyProgress()
 {
   String usename,result="";
   boolean canSend=false;

              usename=message.substring(1,  message.length());
String date1="";
 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
Date now = new Date();
date1 = sdfDate.format(now);

int j=6;
for( int i=0;i<=21;i+=7){
  
String dailyprog="SELECT SUM( no_of_steps ) as total FROM (" +
        "SELECT user_name, no_of_steps, date( time_date ) AS date1 FROM steps WHERE 1 ) AS steps2" +
        " WHERE date1<= DATE_SUB( '"+date1+"', INTERVAL "+ i +" DAY ) AND " +
        "date1 >= DATE_SUB( '"+date1+"', INTERVAL "+ j +" DAY ) AND user_name = '"+usename+"'";

ResultSet rs;
             int num=0;
              try {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(dailyprog);
                 while ( rs.next() )
                 {
                 num = rs.getInt("total");
                 System.out.println(num);

                  }//while
                result+=num+"!";
                canSend=true;
                  }//try
        catch (Exception e) {

               writetoStream("N",e.getMessage());
            }//catch
j+=7;
}//for i
if(canSend==true)
{
  writetoStream(result,"no");
  System.out.println(result);
}

 }//getWeeklyProgress
 /**
  * Print message to the screen
  * @param ans the server response that will be sent to the client
  * @param err The error message
  */
void writetoStream(String ans,String err)
{
            try{
               outDataStream.writeBytes(ans) ;
               outDataStream.write("\r\n".getBytes());
               outDataStream.flush();
               outDataStream.close();
               source.logDisplay.append ( "Message returned to client \n\n" );
               source.msgDisplay.append("\nerr:"+err);
             }catch (IOException ex)
             {
               source.msgDisplay.append("\nStreamWriteErr:" + ex);
             }//catch
}//writetoStream

}  // end HandleServer
