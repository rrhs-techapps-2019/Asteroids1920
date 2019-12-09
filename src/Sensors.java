import mayflower.*;
import java.util.*;

public class Sensors extends GameView
{ 
    private ArrayList<NetworkActor> asteroids;
    private NetworkActor ship;
    private ArrayList<NetworkActor> visibleAsteroids;
    private mayflower.Timer time ;
    //800 by 600 screen per person
    public Sensors(Client client, GameState state){
        super(client,state);
        asteroids = getAsteroids(); 
        ship=compareCords();
        time = new mayflower.Timer(1000);
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
    public void moveCircle(int power){
         time.set(((100-power)*75)+250);  
         if(time.isDone()){
             // increase the image size of the circle 
             time.reset();
            }
    }
    public void act()
    {
        
        super.act(); 
        ship = compareCords();
        asteroids = getAsteroids();
        
    }
    //public int Screen 
   
  //800 by 600 screen per person
  
  //gets ship coordinates, then compares those to all asteroids
  //if an asteroid would display on the screen, it is added to a list
  public NetworkActor compareCords(){
    NetworkActor[] actors =getState().getActors();
    NetworkActor ship = null;
    for (NetworkActor a:actors){
      if(a.getType().equals("ship")){
        ship = a;
        break;
      }
    }
    if(ship!=null){
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
    //800 by 600 screen per person

    //gets ship coordinates, later will compare to other things
  
}
// Use some kind of time system to enlarge a circular object and then find out which objects 
// are touching it and blip them on the screen
// deal with zoom by having variables that change the size of the background
