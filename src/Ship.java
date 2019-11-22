import mayflower.*;

public class Ship extends NetworkActor
{
    public Ship(int id)
    {
        super(id, "ship");
        MayflowerImage img = new MayflowerImage("img/Ship.png");
        img.scale(.2);
        setImage(img);
    }
}