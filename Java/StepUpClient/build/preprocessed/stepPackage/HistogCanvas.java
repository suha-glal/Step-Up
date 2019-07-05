package stepPackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.microedition.lcdui.*;
/**
 * The class is reponisble of drawing histograph.
 * @author Suha
 */
public class HistogCanvas extends CustomItem{

/**
 * The array contains the int values of every bar
 */
int[] bar;
/**
 * The array contains every bar title.
 */
String[] names;
/**
 * Contians the graph title which represent the time and date the graph represents.
 */
String Time;
/**
 * initialize the varibles.
 * @param time the graph title which represent the time and date the graph represents
 * @param b contains the int values of every bar
 * @param n contains every bar title.
 */
  public HistogCanvas(String time,int[] b,String[] n) {
        super(null);
        bar=b;
        names=n;
        Time=time;
  }
  /**
 * return the minimum width of the content area, in pixels.
 * @return
 */
public int getMinContentWidth(){
    return 225;
  }
/**
 * return the minimum height of the content area, in pixels.
 * @return
 */
  public int getMinContentHeight(){
    return 240;
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
 *  paints every pixel within the given clip area.
 * @param g the Graphics object to be used for rendering the item
 * @param w current width of the item in pixels
 * @param h current height of the item in pixels
 * @see HistogCanvas#drawHistogram(javax.microedition.lcdui.Graphics)
 */
  public void paint (Graphics g,int w, int h) {
 
 g.setColor(0xFFFFFF);
 g.fillRect (0, 0, w, h);
 g.setColor(0x94D49E);
 g.drawRect(0, 0, w - 1, h - 1);
 g.setColor(0x58713F);
 g.drawString(Time, 10,0, g.LEFT | g.TOP);
 drawHistogram(g);
 g.setColor(0x000000);
  }
/**
 * The function draw histograph depending on the number of bars in the array bar. Then it diving the drawing array between number of bar.
 *
 * @param g reference to the graphic area.
 */
void drawHistogram(Graphics g)
{
    //start drawing from pixel 20
    int startdrw=20;
    int startRec=60;
// the bars colors
    int color[]={0xFFFF33,0xFF3333,0x3333FF,0x996600,0x99FF33,0xCC33FF};
  //maximum bar lenght
 int maxbarL=98;
 //maximum bar width
 int maxbarW=190;
 //calculating the space we will use to draw the bars
 int space= maxbarW - (2*bar.length);
 //bar width is relative to the space and number of bars
 int barW=space/bar.length;
 //Next Height to start drawing from
 int nextH=20;
 //the percentage we will use to draw the bars
double persent;
//bar length
int barL;
//devider, to give percentage. first is initailized to zero
int div=1;
//if the maximum bar value which is saved in the first array element is greater than 10000 then the divider is 10000 else the divider = the first array element.
if(10000>bar[0])
    div=10000;
else
    div=bar[0];

 for(int i=0;i<bar.length;i++){
     //calculate the bar percentage form the drawing space
     persent=(double) bar[i]/div;
     //get the value of the bar length
     barL=(int)(persent*maxbarL);
     //set color to black to write the bar title
     g.setColor(0x000000);
// write the bar title
     g.drawString(names[i], 1,nextH+(barW/3), g.LEFT | g.TOP);
     //write the bar absolute value
     g.drawString(""+bar[i],(barL+startRec+1),nextH+(barW/3), g.LEFT | g.TOP);
     //set the bar color
     g.setColor(color[i]);
     //draw the bar
     g.fillRect(startRec, nextH,barL , barW);
     //set the next height to draw the next bar
      nextH+=(2+barW);
 }//for
nextH-=2;
g.setColor(0x000000);
//draw horizontal line
g.drawLine(startRec, startdrw,startRec ,nextH);
//draw vertical line
g.drawLine(startRec, nextH,219 ,nextH);
// write the word steps in the horizontal line
g.drawString("Steps",180,nextH+11, g.LEFT | g.TOP);

g.setColor(0x003300);
//set the drawing brush to style dotted like this "------"
g.setStrokeStyle(g.DOTTED);
    persent=(double) 10000/div;
    barL=(int)(persent*maxbarL);
    //draw vertical line
    g.drawLine(barL+startRec,startdrw,barL+startRec,nextH+2);
    //write the word "10000" at the end of the line
    g.drawString("10000",barL+(startRec/2),nextH, g.LEFT | g.TOP);
      
}//drawHistogram

}
