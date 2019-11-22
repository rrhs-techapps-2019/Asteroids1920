package org.rrhs.asteroids.views;

import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.GameState;

public class WeaponsView extends GameView
{
  //To DO:
  //Make A Small White Line That Will Delete itself after 10 seconds
  //Move turret
  //detect direction of turret
  //Reload meter (talk with Eng. group to find out how long to reload)
  //Aiming Retical (Different Color? is it different because of energy?)
  //Talk With Ast. group about how astroids will ushinteract with our bullets and how energy can change that
  public WeaponsView(Client client, GameState state)
  {
    super(client,state);
  }
}