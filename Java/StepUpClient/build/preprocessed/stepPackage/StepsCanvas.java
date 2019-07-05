package stepPackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.*;
import javax.microedition.lcdui.*;
/**
 * The class  is responisble of drawing the step view.
 * @author Suha
 */
public class StepsCanvas extends CustomItem{

/**
 * The background image of the view.
 */
  Image image;
/**
 * Number of steps walked so far.
 */
int steps;
/**
 * The values of distance traveled in KM
 */
double distance;
/**
 * The value of calories burned so far.
 */
double calories;
/**
 * initailize the varibles.
 */
  public StepsCanvas () {
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
  /**
 * return the minimum width of the content area, in pixels.
 * @return
 */
public int getMinContentWidth(){
    return 220;
  }
/**
 * return the minimum height of the content area, in pixels.
 * @return
 */
  public int getMinContentHeight(){
    return 220;
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
 */
  public void paint (Graphics g,int w, int h) {
      g.drawImage(image, 0, 0, g.LEFT | g.TOP);
      g.setColor(0x000000);
      g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));

   g.drawString(""+steps, 30,55, g.LEFT | g.TOP);
    g.drawString(""+distance, 30,130, g.LEFT | g.TOP);
    g.drawString(""+calories, 30,195,g.LEFT | g.TOP);
    
  }//paint
  /**
   * Update Steps,distance, and calories values and repaint the view to show the new updated values.
   * @param s
   * @param d
   * @param c
   */
void setSDC(int s,double d,double c)
{
steps=s;
distance=d;
calories=c;
repaint();
}//set



}
