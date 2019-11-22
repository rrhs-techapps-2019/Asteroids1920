import mayflower.*;

import java.util.*;

public class GameView extends World
{
    private Client client;
    private GameState state;
    private Map<Integer, NetworkActor> actors;
    private int resyncCounter;

    public GameView(Client client, GameState state)
    {
        this.client = client;
        this.state = state;
        resyncCounter = 0;

        actors = new HashMap<>();

        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.BLACK));
    }


    public void act()
    {
        resync();
        state.act();
        processInput();
        processActors();
    }

    public void resync()
    {
        resyncCounter++;
        if (resyncCounter > 30)
        {
            client.send("update");
            resyncCounter = 0;
        }
    }

    public void processActors()
    {
        for (NetworkActor nActor : state.getActors())
        {
            int id = nActor.getId();
            NetworkActor actor = actors.get(id);
            if (null == actor)
            {
                if ("ship".equals(nActor.getType()))
                {
                    actor = new Ship(id);
                } else
                {
                    actor = new Asteroid(id);
                }
                addObject(actor, nActor.getX(), nActor.getY());
                actors.put(id, actor);
            }

            actor.setLocation(nActor.getX(), nActor.getY());
            actor.setRotation(nActor.getRotation());
            actor.setSpeed(nActor.getSpeed());
            actor.setRotationSpeed(nActor.getRotationSpeed());
        }
    }

    private void processInput()
    {
        // force server update
        if (Mayflower.isKeyDown(Keyboard.KEY_SPACE) && !Mayflower.wasKeyDown(Keyboard.KEY_SPACE))
        {
            client.send("update");
        }

        // move forward
        if (Mayflower.isKeyDown(Keyboard.KEY_UP) && !Mayflower.wasKeyDown(Keyboard.KEY_UP))
        {
            client.send("move");
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_UP) && Mayflower.wasKeyDown(Keyboard.KEY_UP))
        {
            client.send("stop move");
        }

        // turn left
        if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            client.send("turn left");
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_LEFT) && Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            client.send("stop turn");
        }

        // turn right
        if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            client.send("turn right");
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            client.send("stop turn");
        }

    }
}
  
  
    
  