package org.rrhs.asteroids.actors;
import mayflower.MayflowerImage;
import mayflower.*;


public class Ship extends NetworkActor
{
  
  private int accelerationUp;
  private int accelerationDown;
  private int speed;
  private int accelerationLeft;
  private int accelerationRight;
  private int rotationSpeed;
  private int energy;
  
  public Ship(int id)
  {
    super(id, "ship");
    MayflowerImage img = new MayflowerImage("img/Ship.png");
    img.scale(.2);
    setImage(img);
    speed = getSpeed();
    rotationSpeed = getRotationSpeed();
    energy =1;
    accelerationUp = 0;
    accelerationDown = 0;
    
  }
  
  public void rotate()
  {
     if(Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
     {
       this.turn(-getRotationSpeed());
     }
     else if(!Mayflower.isKeyDown(Keyboard.KEY_LEFT) && Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
     {
       
     }
     if(Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
     {
       this.turn(getRotationSpeed());
     }
     else if(!Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
     {
       
     }
  }
  
  public boolean movingUp()
  {
    if(getSpeed() > 0)
    {
        
      return true;
    }
    return false;
  }
  
  public void move()
   {
     move();
   }
  
  public void main(String[] args)
  {
    update();
  }
  
  public void update()
   {
     rotate();
     if(!Mayflower.wasKeyDown(Keyboard.KEY_LSHIFT) || !Mayflower.isKeyDown(Keyboard.KEY_LSHIFT))
     {
       accelerationUp = 0;
       accelerationDown = 0;
     }

     while((Mayflower.isKeyDown(Keyboard.KEY_UP)))
     {
       if((Mayflower.isKeyDown(Keyboard.KEY_LSHIFT)))
       {
         accelerationUp = accelerationUp + energy;
       }
       setSpeed(accelerationUp + speed);
       speed = speed + accelerationUp;
     }
     while((Mayflower.isKeyDown(Keyboard.KEY_DOWN)))
     {
       if((Mayflower.isKeyDown(Keyboard.KEY_LSHIFT)))
       {
         accelerationDown = accelerationDown - energy;
       }
       setSpeed(accelerationDown + speed);
       speed = speed + accelerationDown;
     }
   }
}
