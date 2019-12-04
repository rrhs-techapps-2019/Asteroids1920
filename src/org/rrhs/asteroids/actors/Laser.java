package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

public class Laser extends NetworkActor
{
    public Laser(int id) {
        super(id, "laser");
        MayflowerImage img = new MayflowerImage("img/Laser.png");
        img.scale(.2);
        setImage(img);
    }
}
// Just read some of the methods here and I just realized that objects can only move by sending it to the server first
// so everything commnented here wouldn't really work or would make things harder.
// Anyway I've figured out how it works, but just in case I'm leaving it here.
// Gianni

//    public Laser( Ship from, String direction)
//    {
//        this.direction = direction;
//        this.from = from;
//        Picture pic = new Picture("");
//        pic.resize(NA, NA);
//        pic.setBounds(NA, NA, NA, NA);
//        setPicture(pic);
//    }
//
//    public void update() {
//        super.update();
//
//        Actor[] touching = getTouching();
//
//        for (Actor lazer : touching) {
//            if (lazer instanceof AsteroidBig) {
//                Stage s = getStage();
//                s.removeActor(AstroidBig);
//                s.add(AstroidSmall);
//                s.add(AstroidSmall);
//                s.removeActor(this);
//            }
//            if (lazer instanceof AsteroidSmall) {
//                Stage s = getStage();
//                s.removeActor(AstroidSmall);
//                s.removeActor(this);
//            }
//        }
//    }

}