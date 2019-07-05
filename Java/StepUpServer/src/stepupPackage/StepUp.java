/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepupPackage;



import java.awt.*;
import java.awt.event.*;
import java.net.*;


// ********************  ListenServer  ***********

public class StepUp extends Frame implements ActionListener, WindowListener {

//*****  Mulit-threaded server, accepts multiple messages
//       expected to be run on hawk-cs.cs.unc.edu:8901


protected String DEFAULT_HOST = "localhost";
protected int DEFAULT_PORT = 1111;
String host;
int port;

ListenServer listen;

TextField hostDisplay, portDisplay;
TextArea logDisplay, msgDisplay;
Panel topPanel;
Panel middlePanel;
Panel buttonPanel;
Button listenButton, quitButton;


// **************  StepUp

    public StepUp ()  {

        super ( "StepUp Server " );


        buildUI ();

    }  // end constructor


// **************  main

    public static void main ( String [ ] args )  {

        StepUp server = new StepUp ();

    }  // end main


//***********  Interface Methods   ***********


//****  ActionListener methods

  public void actionPerformed ( ActionEvent e )  {

    Object s = e.getSource();

    // *** process Button actions

    if ( s instanceof Button )  {

        if ( s == listenButton )  {
            listen = new ListenServer ( this );
            listen.start ();
            listenButton.disable();
        }  // end listenButton

        else if ( s == quitButton )  {
               

                    // hide();
                    dispose();
                    System.exit (0);
            }  // end quitButton

    }  // end process Button actions

  }  // end actionPerformed


//****  WindowListener methods

  public void windowActivated ( WindowEvent e )  {
  }

  public void windowDeactivated ( WindowEvent e )  {
  }

  public void windowOpened ( WindowEvent e )  {
  }

   public void windowClosed ( WindowEvent e )  {
         dispose ();
      System.exit(0);
  }

  public void windowClosing ( WindowEvent e )  {

   
 
  }

  public void windowIconified ( WindowEvent e )  {
  }

  public void windowDeiconified ( WindowEvent e )  {
  }


//***********  Utility Methods   ***********

// **************  buildUI

  private void buildUI ()  {

    try {
        InetAddress here = InetAddress.getLocalHost ();
        host = here.getHostName ();
    }
    catch (UnknownHostException e) { ;}

    hostDisplay = new TextField ( host, 30 );
    portDisplay = new TextField ( Integer.toString ( DEFAULT_PORT ), 4 );
    topPanel = new Panel ();
    topPanel.setLayout ( new GridLayout ( 2, 1 ) );
    topPanel.add ( hostDisplay );
    topPanel.add ( portDisplay );

    logDisplay = new TextArea ( 40, 10 );
    msgDisplay = new TextArea ( 40, 10 );
    middlePanel = new Panel ();
    middlePanel.setLayout ( new GridLayout ( 2, 1 ) );
    middlePanel.add ( logDisplay );
    middlePanel.add ( msgDisplay );

    listenButton = new Button ( "Listen" );
    quitButton = new Button ( "Quit" );
    listenButton.addActionListener ( this );
    quitButton.addActionListener ( this );
    buttonPanel = new Panel ( );
    buttonPanel.add ( listenButton );
    buttonPanel.add ( quitButton );

    this.add ( "North", topPanel );
    add ( "Center", middlePanel );
    add ( "South", buttonPanel );

    resize ( 400, 450 );
    show ();

    }  // end buildUI


}  // end StepUp


