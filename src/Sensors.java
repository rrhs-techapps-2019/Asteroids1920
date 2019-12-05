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
        ship=compareCords();
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
        ship = compareCords();
        asteroids = getAsteroids();
    }
    //public int Screen 
    public void createScreen(){
     for(NetworkActor asteroid : visibleAsteroids){
         addObject(asteroid,asteroid.getX(),asteroid.getY());
        }
     addObject(ship,ship.getX(),ship.getY());
    }
    //800 by 600 screen per person

    //gets ship coordinates, later will compare to other things
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
        }
        return ship;
    }
}
// Use some kind of time system to enlarge a circular object and then find out which objects 
// are touching it and blip them on the screen
// deal with zoom by having variables that change the size of the background