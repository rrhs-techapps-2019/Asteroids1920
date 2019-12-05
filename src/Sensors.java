import mayflower.*;
import java.util.*;

public class Sensors extends GameView
{ 
    private ArrayList<NetworkActor> asteroids;
    private NetworkActor ship;
    private ArrayList<NetworkActor> visibleAsteroids;
    //800 by 600 screen per person
    public Sensors(Client client, GameState state){
        super(client,state);
        asteroids = getAsteroids();
        compareCords();
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
  public void compareCords(){
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
  }
      
    public void createScreen(){
     for(NetworkActor asteroid : visibleAsteroids){
         addObject(asteroid,asteroid.getX(),asteroid.getY());
        }
     addObject(ship,ship.getX(),ship.getY());
    }
    
}