//import java.awt.Frame;
import java.awt.*;
import java.util.*;
//import java.awt.Color;
//import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;


public class Game extends Frame {

// instance variables: list of all Asteroids, list of all Missiles, gun, etc.
    // ... instance variable declarations will go here
Gun gun;
//instance variables for double buffering
    private Image buffer;
    private Graphics bufferGC;

// constant values that govern game appearance & behavior
    private final int GAME_X_POSITION = 100;                    // screen position of ...
    private final int GAME_Y_POSITION = 100;                    //    upper left corner of game window
    private final String GAME_TITLE = "Asteroids Video Game";   // title of game window
    private final int GAME_WIDTH = 960;                         // game width in pixels
    private final int GAME_HEIGHT = 640;                        // game height in pixels
    private final Color COLOR_BACKGROUND = Color.GRAY;         // color of the sky
    private final Color COLOR_ASTEROID = Color.BLACK;           // color of Asteriod;
    private final int MIN_ASTEROID_ROT_SPD = 180;
    private final int MAX_ASTEROID_ROT_SPD = 240;
    private final int MIN_ASTEROID_DIAMETER = 50;
    private final int MAX_ASTEROID_DIAMETER = 100;
    private final double ASTEROID_Y_START = 0.0;
    private int SCORE=0;
    private final int PAUSE_TIME = 50;                          // milliseconds between re-paints
    private final Image image = Toolkit.getDefaultToolkit().getImage("asteroid.png");  
    ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    ArrayList<Missile> missiles = new ArrayList<Missile>();
    
    public static void main(String[] args) {
        Game gameObject = new Game();
        gameObject.runGame();
    }

    
    public Game() {
        this.setLocation(GAME_X_POSITION, GAME_Y_POSITION);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle(GAME_TITLE);
        this.addWindowListener(new HandleEvents());
        this.addKeyListener(new KeyPress());
    }

    
    public void runGame() {
        createSky();
        this.setVisible(true);
        //for double buffering
        buffer = createImage(this.getSize().width, this.getSize().height);
        bufferGC = buffer.getGraphics();
        gun = new Gun(GAME_WIDTH/2,GAME_HEIGHT+10,20,50/2,90,1,1);
        while (true) {
            createNewAsteroid();
            move();
            repaint();//repaint calls to the JRE's repaint method, which calls back to the paint method.
            pause(PAUSE_TIME);
            interact();
        }
    }

    
    private void createSky() {
        this.setBackground(COLOR_BACKGROUND);
    }
    private int asteroidDiameter(){

      
      return (int) ((MAX_ASTEROID_DIAMETER - MIN_ASTEROID_DIAMETER) * Math.random() + MIN_ASTEROID_DIAMETER);
    }
    private int asteroidSpin(){

      
      return (int) ((MIN_ASTEROID_ROT_SPD - MAX_ASTEROID_ROT_SPD) * Math.random() + MIN_ASTEROID_ROT_SPD);
    }
    private int asteroidXStart(){
      
      return (int) (GAME_WIDTH * Math.random());
    }
    private int asteroidDeltaX(){
      
      return (int) (6 * Math.random())-3;
    }
    private int asteroidDeltaY(){
      
      return (int) (3 * Math.random()+1);
    }
    private void createNewAsteroid() {
        // body to be written ... with some probability, will create a new Asteroid 
        if(Math.random() <.1){
            int di = asteroidDiameter();
            asteroids.add(new Asteroid(asteroidXStart(), ASTEROID_Y_START, asteroidDeltaX(), asteroidDeltaY(), di, di/100.0,asteroidSpin(), COLOR_ASTEROID, GAME_HEIGHT));
        }
    }
    private void createNewMissile() {
        // body to be written ... with some probability, will create a new Asteroid

            int di = asteroidDiameter();
            //Math.cos(Gun.z), Math.sin(Gun.z)
            //System.out.println(gun.x+":"+gun.y);
            missiles.add(new Missile(gun.x-84*Math.cos(gun.z+Math.PI/2), gun.y-84*Math.sin(gun.z+Math.PI/2), -Math.cos(gun.z+Math.PI/2)*10, -Math.sin(gun.z+Math.PI/2)*10, 25, 25/25.0,50, COLOR_ASTEROID, GAME_HEIGHT));

    }
    
    private void move() {
        // body to be written ... will move all objects in the game each frame
        for(int i = asteroids.size()-1; i > -1; i--){
            asteroids.get(i).move();
            if(asteroids.get(i).y>GAME_HEIGHT){
                asteroids.remove(i);
                SCORE-=2;
            }else if(asteroids.get(i).y<0 || asteroids.get(i).x>GAME_WIDTH || asteroids.get(i).x<0){
                 asteroids.remove(i);
            }
        }
        for(int i = missiles.size()-1; i > -1; i--){
            missiles.get(i).move();
            if(missiles.get(i).y<0 || missiles.get(i).y>GAME_HEIGHT || missiles.get(i).x>GAME_WIDTH || missiles.get(i).x<0){
                missiles.remove(i);
            }
        }

    }
    
    public void update(Graphics g){
        bufferGC.setColor(COLOR_BACKGROUND);
        bufferGC.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        paint(bufferGC);
        //buffer to screen
        g.drawImage(buffer,0,0,this);
    }
    public void paint(Graphics g) {
        // body to be written ... will "paint" all objects in the game each frame
        for(int i = 0; i < asteroids.size(); i++){
            asteroids.get(i).paint(g);
        }
        gun.paint(g);
        for(int i = 0; i < missiles.size(); i++){
            missiles.get(i).paint(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("SCORE:"+SCORE,25,50);
      //  g.drawImage(image, 100, 100, 500, 500, null);  
      //g.fillRect(0,0,100,100);
    }
    
    
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }
    
    
    private void interact() {
        /*
         * 
        
         * 
         */
        Iterator aIter = asteroids.iterator();
        while(aIter.hasNext()){
         Asteroid a = (Asteroid) aIter.next();
         Iterator bIter = missiles.iterator();
         int rA = (int)(a.scale*a.diameter)/2;
        while(bIter.hasNext()){
            Missile b = (Missile) bIter.next();
            int rB = (int)(b.scale*b.diameter)/2;
            int length = (int) Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
            if(rA+rB>length){
                aIter.remove();
                bIter.remove();
                SCORE++;
                break;
            }
        }
        }
    /*    for(Asteroid a:asteroids){

            for(Missile b:missiles){
               
            }
        }*/
        
    }

/*
 *   This class lies INSIDE the Game class.
 *
 *   Its purpose is to translate key presses
 *   into game events:
 *   - pressing comma moves the gun left.
 *   - pressing period moves the gun right.
 *   - pressing space fires a missile.
 */
    private class KeyPress extends KeyAdapter {
        private final char COMMA = ',';
        private final char PERIOD = '.';
        private final char SPACE = ' ';

        public void keyPressed (KeyEvent e) {
            char key = e.getKeyChar();
            //System.out.println(key);
            switch (key) {
            case COMMA:
                gun.moveLeft();
                break;
            case PERIOD:
                gun.moveRight();
                break;
            case SPACE:
            createNewMissile();
                break;
            case 'q':
                System.exit(0);
                break;
            }
        }
    }  // end of KeyPress class
}  // end of Game class


/*
 *   This class lies OUTSIDE the Game class.
 *
 *   Its purpose is to shut down the game when
 *   the user clicks the X button in the upper right
 */
class HandleEvents extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
}  // end of HandleEvents class
