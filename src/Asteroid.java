import mayflower.*;

public class Asteroid extends NetworkActor
{
    public Asteroid(int id)
    {
        super(id, "asteroid");
        MayflowerImage img = new MayflowerImage("img/Asteroid.png");
        img.scale(.2);
        setImage(img);
    }

    public void act()
    {
        super.act();

        while (isAtEdge())
        {
            turn(10);
            move(1);
        }
    }
}