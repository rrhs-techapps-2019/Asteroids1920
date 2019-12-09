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
    //800 by 600 screen per person
    public Sensors(Client client, GameState state){
        super(client,state);
        asteroids = getAsteroids();
        compareCords();
        xBase = 800;
        yBase = 600;
        scaleFactor = 1.0;
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

    public void act()
    {
        super.act();
        asteroids = getAsteroids();
    }
    //public int Screen 
   
  //800 by 600 screen per person
  
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
     for(NetworkActor asteroid : visibleAsteroids){
         addObject(asteroid,asteroid.getX(),asteroid.getY());
        }
     addObject(ship,ship.getX(),ship.getY());
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
        xCur = x;
        yCur = y;
      }
      if(change<0&&scaleFactor<2){
        scaleFactor+=.2;
        double x = xBase*scaleFactor;
        double y = yBase*scaleFactor;
        xCur = x;
        yCur = y;
      }
    }
    //800 by 600 screen per person

    //gets ship coordinates, later will compare to other things
 
}
// Use some kind of time system to enlarge a circular object and then find out which objects 
// are touching it and blip them on the screen
// deal with zoom by having variables that change the size of the background
