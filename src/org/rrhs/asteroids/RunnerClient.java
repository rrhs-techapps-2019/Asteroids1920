package org.rrhs.asteroids;


import mayflower.Mayflower;
import mayflower.World;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.views.GameView;


//This class is the starting point of your program.
//It contains the main method which will execute when you run the project.
public class RunnerClient extends Mayflower
{
    //Construct a Mayflower Window.
    public RunnerClient()
    {
        //Open a GUI Window with the specified title, width, and height
        //DO NOT WRITE ANY ADDITIONAL CODE IN THIS CONSTRUCTOR!
        //You should only change the arguments to the super method.
        super("Asteroids", 800, 600);
    }


    //This is the first method that is called when you run your program
    //It is the starting point of your program.
    public static void main(String[] args)
    {
        //Instantiate an instance of this Runner class.
        // The constructor will be called
        // Then the init() method will be called
        //DO NOT WRITE ANY ADDITIONAL CODE IN THIS METHOD!
        new RunnerClient();
    }


    //The init method is called as soon as the Mayflower window is opened.
    //This is where you will setup the initial state of your game
    //ie, create the first world of your game
    public void init()
    {
        //Show a black border around the Actors
        //Mayflower.showBounds(true);

        GameState state = new GameState();
        Client client = new Client(state);

        //Create a new instance of a BlockWorld
        World startingWorld = new GameView(client, state);

        //Load the startingWorld into the Mayflower Window
        Mayflower.setWorld(startingWorld);
    }

}