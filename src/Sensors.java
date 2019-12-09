import mayflower.*;
import java.util.*;

public class Sensors extends GameView
{ 
    private ArrayList<NetworkActor> asteroids;
    private NetworkActor ship;
    private ArrayList<NetworkActor> visibleAsteroids;
    private int xBase;
    private int yBase;
    private int xCur;
    private int yCur;
    private double scaleFactor;
    private mayflower.Timer time ;
    private int power;
    private Circle circle;
    //800 by 600 screen per person
    public Sensors(Client client, GameState state){
        super(client,state);
        asteroids = getAsteroids();
        compareCords();
        xBase = 800;
        yBase = 600;
        xCur = 800;
        yCur = 600;
        scaleFactor = 1.0;
        time = new mayflower.Timer(1000);
        power = 0;
        circle= new Circle();
    }
    //gets list of asteroids to use later
    public ArrayList<NetworkActor> getAsteroids(){ 
        ArrayList<NetworkActor> ret = new ArrayList<NetworkActor>();
        NetworkActor[] actors = super.getState().getActors();
        for(int i = actors.length -1; i>=0;i--){
            NetworkActor n=actors[i];
            if((n.getType().equals("asteroid"))){
                ret.add(n);
            }
        }
        return ret;
    }

    public void moveCircle(){
        time.set(((100-power)*75)+250);  
        if(time.isDone()){
            // increase the image size of the circle 
            
            MayflowerImage img = circle.getImage();
            if(img.getHeight()>=1.2*yCur){
            img.scale(40,40);
        }
            img.scale(1.2);
            circle.setImage(img);
            mayflower.Timer t = new mayflower.Timer(250);
            ArrayList<NetworkActor> added = new ArrayList<NetworkActor>();
            for(NetworkActor asteroid : visibleAsteroids){
                if(asteroid.touchingCircle()){
                   addObject(asteroid,asteroid.getX(),asteroid.getY());
                   added.add(asteroid);
                }
            }
            if(t.isDone()){
               for (NetworkActor actor: added){
                   removeObject(actor);
                }
            }
            time.reset();
        }
    }

    public void act()
    {
        super.act(); 
        ship = compareCords();
        asteroids = getAsteroids();
        moveCircle();
    }
    //public int Screen 
    //gets ship coordinates, then compares those to all asteroids
    //if an asteroid would display on the screen, it is added to a list
    public NetworkActor compareCords(){
        NetworkActor[] actors =getState().getActors();
        NetworkActor s = null;
        for (NetworkActor a:actors){
            if(a.getType().equals("ship")){
                s = a;
                break;
            }
        }
        if(ship!=null){
            ship = s;
            int shipx = ship.getX();
            int shipy = ship.getY();
            visibleAsteroids = new ArrayList<NetworkActor>();
            for (NetworkActor a: asteroids){
                int xdiff = Math.abs(shipx-a.getX());
                int ydiff = Math.abs(shipy-a.getY());
                if(xdiff<=400&&ydiff<=300){visibleAsteroids.add(a);}
            }
        }
        return ship;
    }

    public void createScreen(){
        // for(NetworkActor asteroid : visibleAsteroids){
        // addObject(asteroid,asteroid.getX(),asteroid.getY());
        // }
        addObject(ship,ship.getX(),ship.getY());
        addObject(circle, ship.getX(),ship.getY());
    }
    
    //takes some arguememt which tells it how to alter its magnification
    //positive number moves zooms in, negative zooms out
    //zooms in and out in descrete increments, not proportional to the number input
    public void changeMagnification(int change)
    {
      if(change>0&&scaleFactor>.2){
        scaleFactor-=.2;
        double x = xBase*scaleFactor;
        double y = yBase*scaleFactor;
        xCur = (int)x;
        yCur = (int)y;
      }
      if(change<0&&scaleFactor<2){
        scaleFactor+=.2;
        double x = xBase*scaleFactor;
        double y = yBase*scaleFactor;
        xCur = (int)x;
        yCur = (int)y;
      }
    }
    //800 by 600 screen per person

    //gets ship coordinates, later will compare to other things

}
// Use some kind of time system to enlarge a circular object and then find out which objects 
// are touching it and blip them on the screen
// deal with zoom by having variables that change the size of the background
