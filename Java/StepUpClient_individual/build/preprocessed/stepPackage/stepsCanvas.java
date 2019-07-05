package stepPackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.*;
import javax.microedition.lcdui.*;

public class stepsCanvas extends CustomItem{



  Image image;
Graphics graph;
int steps;
double distance;
double calories;
  public stepsCanvas () {
        super(null);
steps=0;
distance=0;
calories=0;
    
    try {
      image = Image.createImage ("/img/stepsview.png");
    }
    catch (IOException e) {
      throw new RuntimeException ("Unable to load Image: "+e);
    }

  }
public int getMinContentWidth(){
    return 220;
  }

  public int getMinContentHeight(){
    return 220;
  }

  public int getPrefContentWidth(int width){
    return getMinContentWidth();
  }

  public int getPrefContentHeight(int height){
    return getMinContentHeight();
  }
  public void paint (Graphics g,int w, int h) {
      g.drawImage(image, 0, 0, g.LEFT | g.TOP);
      g.setColor(0x000000);
      g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));

   g.drawString(""+steps, 30,55, g.LEFT | g.TOP);
    g.drawString(""+distance, 30,130, g.LEFT | g.TOP);
    g.drawString(""+calories, 30,195,g.LEFT | g.TOP);
    
  }//paint
void setSDC(int s,double d,double c)
{
steps=s;
distance=d;
calories=c;
repaint();
}//set



}
