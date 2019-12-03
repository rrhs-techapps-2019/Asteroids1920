import mayflower.*;
import java.util.*;

public class Sensors extends GameView
{ 
    private ArrayList<NetworkActor> asteroids;
    //800 by 600 screen per person
    public Sensors(Client client, GameState state){
        super(client,state);
        asteroids = getAsteroids(); 
    }

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
        asteroids = getAsteroids();
        super.act(); 
    }
    //public int Screen 
}