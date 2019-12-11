package org.rrhs.asteroids.actors.objects;
import mayflower.Actor;
import mayflower.MayflowerImage;

public class Selector extends Actor{
    public Selector(){
       MayflowerImage img = new MayflowerImage("C:\\Users\\s632604\\Documents\\GitHub\\Asteroids1920\\img\\rectangle.png");
       img.scale(320,425);
       this.setImage(img);

    }
    public void act(){


    }
}
