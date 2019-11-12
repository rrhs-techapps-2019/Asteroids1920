import java.util.*;

public class GameState
{
  private Queue<String> updates;
  private Map<Integer, NetworkActor> actors;
  
  public GameState()
  {
    updates = new LinkedList<>();
    actors = new HashMap<>();
  }
  
  
  public NetworkActor[] getActors()
  {
    return actors.values().toArray(new NetworkActor[] {});
  }
  
  public void act()
  {
    processUpdates();
    processActors();
  }
  
  public void processActors()
  {
    for(NetworkActor actor : actors.values())
    {
      actor.act();
    }
  }
  
  private void processUpdates()
  {
    for(int i=0; i<numUpdates(); i++)
    {
      String update = getNextUpdate();
      
      String[] parts = update.split(" ");
      String action = parts[0];
      
      String type = parts[1];
      int id = Integer.parseInt(parts[2]);
      int x = Integer.parseInt(parts[3]);
      int y = Integer.parseInt(parts[4]);
      int rot = Integer.parseInt(parts[5]);
      int speed = Integer.parseInt(parts[6]);
      int rotationSpeed = Integer.parseInt(parts[7]);
      
      NetworkActor actor = actors.get(id);
      
      if(null == actor)
      {
        if("ship".equals(type))
        {
          actor = new Ship(id);
        }
        else
        {
          actor = new Asteroid(id);
        }
 
        actors.put(id, actor);
      }
      actor.setLocation(x, y);
      actor.setRotation(rot);
      actor.setSpeed(speed);
      actor.setRotationSpeed(rotationSpeed);
    }
  }
  
  public String getNextUpdate()
  {
    return updates.remove();
  }
  
  public void addUpdate(String update)
  {
    updates.add(update);
  }
  
  public int numUpdates()
  {
    return updates.size();
  }
}