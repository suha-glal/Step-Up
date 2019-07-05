package stepPackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 *
 * @author Rayan
 */
public class lineGraph extends CustomItem {
String[] day;
int[] steps;
int[] xVals;
int[] yVals;
String date;

  public lineGraph(String dt, String[] d, int[] s) {
      super(null);
  day=d;
  steps=s;
  date=dt;
  xVals = new int[day.length];
  yVals = new int[day.length];
  }
public int getMinContentWidth(){
    return 230;
  }

  public int getMinContentHeight(){
    return 250;
  }

  public int getPrefContentWidth(int width){
    return getMinContentWidth();
  }

  public int getPrefContentHeight(int height){
    return getMinContentHeight();
  }
  public void paint (Graphics g,int w, int h) {
g.setColor(0xFFFFFF);
 g.fillRect (0, 0, w, h);
 g.setColor(0x993300);
 g.drawLine(42, 5, 42, 215);
 g.setColor(0x000000);
int j=0;
  
     for (int i=10000 ; i>=0; i-=2000){
  g.drawString("" + i, 0, j, g.LEFT | g.TOP);
  j+=40;
 }

 g.drawLine(42, 215, 220, 215);

 int q = 44;

 for (int z=0; z < day.length ; z++){
    g.drawString(day[z] , q-5 , 215, g.LEFT | g.TOP);

    xVals[z]=q;
    q+=26;//28
   
 }//
      double temp;
   for (int z=0; z < day.length ; z++){
       temp=((double)steps[z])/10000;
       temp*=200;
    yVals[z]= 200- (int)temp;
    }//z
  int z;
 for (z=0; z < day.length -1; z++)
 {
      g.setColor(0x177526);
 g.drawLine(xVals[z], yVals[z], xVals[z+1], yVals[z+1]);
 g.setColor(0xff0000);
   g.fillArc(xVals[z], yVals[z], 4, 4, 0, 360);
 System.out.println(""+xVals[z]+","+yVals[z]);
 }
      g.fillArc(xVals[z], yVals[z], 4, 4, 0, 360);
      g.setColor(0x94d49e);
      g.drawRect(0,0, w-1, h-1);
  //g.drawLine(xVals[z], yVals[z], xVals[z+1], yVals[z+1]);
      g.setColor(0x58713f);
                          //245
  g.drawString(date, 112, 250, g.HCENTER | g.BOTTOM );
}
 
 



}

