import java.awt.*;
import java.awt.geom.*;
//import java.awt.geom.AffineTransformOp;

public class Missile{
  //instance variables
  //private to restrict access only to this class
  public double x;  // x coordinate in pixels double referrs to decimal point numbers vs int's whole numbers
  public double y;  // y coordinate in pixels
  public double z;
  public double scale;
  private double dZ; //delta x (per frame)
  private double dX; //delta x (per frame)
  private double dY; //delta x (per frame)
  public int diameter; //diameter in pixels of circle
  private Color color;  //Color vs color Class vs variable name
  private final Image image = Toolkit.getDefaultToolkit().getImage("bomb.png");  
  private int GAME_HEIGHT;
  
  public Missile (double xpos, double ypos, double delX, double delY, int width, double scl, double rot, Color c, int GMH)
  {// assigns correct values to instance variables
    x = xpos;
    y = ypos;
    dX = delX;
    dY = delY;
    diameter = width;
    z=0;
    dZ=Math.PI/rot;
    scale=scl;
    //System.out.println(dX);
    color = c;
    GAME_HEIGHT = GMH;

  }
  public void move (){
      y+=dY;
      x+=dX;
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
              at.scale(scale, scale);

              // 1. translate the object so that you rotate it around the 
              //    center (easier :))
              at.translate(-diameter/2, -diameter/2);

              // draw the image
              Graphics2D g2d = (Graphics2D) g;
              g2d.drawImage(image, at, null);

    //   
    //g.drawImage(image,(int)x, (int)y, diameter, diameter, null);  
    //g.setColor(savedColor);
  }
}