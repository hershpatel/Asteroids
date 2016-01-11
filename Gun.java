import java.awt.*;
import java.awt.geom.*;
//import java.awt.geom.AffineTransformOp;

public class Gun{
  //instance variables
  //private to restrict access only to this class
  public double x;  // x coordinate in pixels double referrs to decimal point numbers vs int's whole numbers
  public double y;  // y coordinate in pixels
  public double z;
  private double dZ; //delta x (per frame)
  private int scaleW; //diameter in pixels of circle
  private int scaleH; //diameter in pixels of circle
  private int width;
  private int height;
  private final Image image = Toolkit.getDefaultToolkit().getImage("missileTube.png");  
  //gun tip baseX+ length*Math.cos(angle);
  //BaseY - length*Math.sin(z)
  public Gun (double xpos, double ypos, double delZ, int w, int h, int dW, int dH)
  {// assigns correct values to instance variables
    x = xpos;
    y = ypos;
    z=0;
    dZ=Math.PI/delZ;
    scaleW=dW;
    scaleH=dH;
    width=w;
    height=h;
    
  }
  public void moveLeft (){
      z-=dZ;
  }
  public void moveRight (){
      z+=dZ;
  }
  public void paint (Graphics g){//graphics are usually done with Graphics g. g is optional, Graphics is not, as a Class
    Color savedColor = g.getColor();//saves the current color
    //g.setColor(color);//loads the color that 
    //g.fillOval((int)x,(int)y,diameter,diameter);
    // create the transform, note that the transformations happen
              // in reversed order (so check them backwards)
              AffineTransform at = new AffineTransform();

              // 4. translate it to the center of the component
              at.translate(x,y);

              // 3. do the actual rotation
              at.rotate(z);

              // 2. just a scale because this image is big
              at.scale(scaleW, scaleH);

              // 1. translate the object so that you rotate it around the 
              //    center (easier :))
              at.translate(-width, -height);

              // draw the image
              Graphics2D g2d = (Graphics2D) g;
              g2d.drawImage(image, at, null);
              
    //   
    //g.drawImage(image,(int)x, (int)y, diameter, diameter, null);  
    //g.setColor(savedColor);
  }
}