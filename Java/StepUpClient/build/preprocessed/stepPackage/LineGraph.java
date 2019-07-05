package stepPackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.*;
/**
 * The class is responsible of drawing a line graph.
 * @author Rayan
 */
public class LineGraph extends CustomItem {
/**
 * The x value of the line graph which represnts day of month.
 */
String[] day;
/**
 * The y value of the line graph which represents number of steps walked.
 */
int[] steps;
/**
 * The x value of every point in the line graph in pixels.
 */
int[] xVals;
/**
 * The y value of every point in the line graph in pixels.
 */
int[] yVals;
/**
 * The graph title which includes the date.
 */
String date;
/**
 * The constructor initialize the varibles.
 * @param dt The graph title which includes the date.
 * @param d The x value of the line graph which represnts day of month.
 * @param s The y value of the line graph which represents number of steps walked.
 */
  public LineGraph(String dt, String[] d, int[] s) {
      super(null);
  day=d;
  steps=s;
  date=dt;
  xVals = new int[day.length];
  yVals = new int[day.length];
  }
  /**
 * return the minimum width of the content area, in pixels.
 * @return
 */
public int getMinContentWidth(){
    return 230;
  }
/**
 * return the minimum height of the content area, in pixels.
 * @return
 */
  public int getMinContentHeight(){
    return 250;
  }

  /**
 *return the preferred width of the content area, in pixels.
 * @return
 */
  public int getPrefContentWidth(int width){
    return getMinContentWidth();
  }
/**
 *return the preferred height of the content area, in pixels.
 * @return
 */
  public int getPrefContentHeight(int height){
    return getMinContentHeight();
  }

/**
 * Draw the line graph after calculting the x values from array day and the y values from array steps.
 * @param g the Graphics object to be used for rendering the item
 * @param w current width of the item in pixels
 * @param h current height of the item in pixels
 */
  public void paint (Graphics g,int w, int h) {
g.setColor(0xFFFFFF);
 g.fillRect (0, 0, w, h);
 g.setColor(0x993300);
 //draw vertical line
 g.drawLine(42, 5, 42, 215);
 g.setColor(0x000000);
int j=0;
  // write number of steps walked in thousands  in the vertical line
     for (int i=10000 ; i>=0; i-=2000){
  g.drawString("" + i, 0, j, g.LEFT | g.TOP);
  j+=40;
 }
//draw horizontal line
 g.drawLine(42, 215, 220, 215);

 int q = 44;

 for (int z=0; z < day.length ; z++){
     //write the day in the horizontal line
    g.drawString(day[z] , q-5 , 215, g.LEFT | g.TOP);
//calculating the x values of the line graph
    xVals[z]=q;
    q+=26;//28
   
 }//
      double temp;
      // claculating the y values of the line graph
   for (int z=0; z < day.length ; z++){
       temp=((double)steps[z])/10000;
       temp*=200;
    yVals[z]= 200- (int)temp;
    }//z
  int z;
  //draw the line graph after having both the x and the y vlaues.
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
  
      g.setColor(0x58713f);
  //draw the graph title
  g.drawString(date, 112, 250, g.HCENTER | g.BOTTOM );
}
 
 



}

