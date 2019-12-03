import mayflower.*;
import java.util.*;

public class Sensors extends GameView
{
  //800 by 600 screen per person
  public Sensors(Client client, GameState state){
    super(client,state);
  }
  
  //gets ship coordinates, later will compare to other things
  public void compareCords(){
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
  }
}