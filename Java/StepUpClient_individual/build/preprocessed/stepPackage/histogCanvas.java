package stepPackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.microedition.lcdui.*;

public class histogCanvas extends CustomItem{

  Image image;
int[] bar;
String[] names;
String Time;
  public histogCanvas(String time,int[] b,String[] n) {
        super(null);
        bar=b;
        names=n;
        Time=time;
  }
public int getMinContentWidth(){
    return 225;
  }

  public int getMinContentHeight(){
    return 240;
  }

  public int getPrefContentWidth(int width){
    return getMinContentWidth();
  }

  public int getPrefContentHeight(int height){
    return getMinContentHeight();
  }
  public void paint (Graphics g,int w, int h) {
  /*  try {
     image = Image.createImage("/img/arrdown.jpg");
    }
    catch( IOException e ){
    }*/
 g.setColor(0xFFFFFF);
 g.fillRect (0, 0, w, h);
 g.setColor(0x94D49E);
 g.drawRect(0, 0, w - 1, h - 1);
 g.setColor(0x58713F);
 g.drawString(Time, 10,0, g.LEFT | g.TOP);
 drawHistogram(g);
 g.setColor(0x000000);
  }

void drawHistogram(Graphics g)
{
    int startdrw=20;
    int startRec=60;

    int color[]={0xFFFF33,0xFF3333,0x3333FF,0x996600,0x99FF33,0xCC33FF};
 int maxbarL=98;
 int maxbarW=190;
 int space= maxbarW - (2*bar.length);
 int barW=space/bar.length;
 int nextH=20;
double persent;
int barL;
int div=1;//devider, to give percentage

if(10000>bar[0])
    div=10000;
else
    div=bar[0];

 for(int i=0;i<bar.length;i++){
     persent=(double) bar[i]/div;
     barL=(int)(persent*maxbarL);
     g.setColor(0x000000);
if(names[i].endsWith("Ago")==true){
     Font f= Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_SMALL);
     g.setFont(f);
}

     g.drawString(names[i], 1,nextH+(barW/3), g.LEFT | g.TOP);
     Font f= Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_MEDIUM);
     g.setFont(f);
     g.drawString(""+bar[i],(barL+startRec+1),nextH+(barW/3), g.LEFT | g.TOP);
     g.setColor(color[i]);
     g.fillRect(startRec, nextH,barL , barW);
      nextH+=(2+barW);
 }//for
nextH-=2;
g.setColor(0x000000);
g.drawLine(startRec, startdrw,startRec ,nextH);
g.drawLine(startRec, nextH,219 ,nextH);
g.drawString("Steps",180,nextH+11, g.LEFT | g.TOP);
/* //to draw the arrow in the case of team progress.
if(Time.charAt(0)=='T')
g.drawImage(image,100,nextH+11,g.LEFT | g.TOP);
*/
g.setColor(0x003300);
g.setStrokeStyle(g.DOTTED);
    persent=(double) 10000/div;
    barL=(int)(persent*maxbarL);
    g.drawLine(barL+startRec,startdrw,barL+startRec,nextH+2);
    g.drawString("10000",barL+(startRec/2),nextH, g.LEFT | g.TOP);
      
}//drawHistogram

}
