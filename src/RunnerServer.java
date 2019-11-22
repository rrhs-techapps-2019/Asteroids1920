import mayflower.*;

//This class is the starting point of your program.
//It contains the main method which will execute when you run the project.
public class RunnerServer extends MayflowerHeadless
{
    //Construct a Mayflower Window.
    public RunnerServer()
    {
        //Open a GUI Window with the specified title, width, and height
        //DO NOT WRITE ANY ADDITIONAL CODE IN THIS CONSTRUCTOR!
        //You should only change the arguments to the super method.
        super("Asteroids", 800, 600);
    }

    //The init method is called as soon as the Mayflower window is opened.
    //This is where you will setup the initial state of your game
    //ie, create the first world of your game
    public void init()
    {
        //Show a black border around the Actors
        //Mayflower.showBounds(true);


        //Create a new instance of a BlockWorld
        World startingWorld = new ServerWorld();

        Server server = new Server(startingWorld);

        //Load the startingWorld into the Mayflower Window
        Mayflower.setWorld(startingWorld);
    }

    //This is the first method that is called when you run your program
    //It is the starting point of your program.
    public static void main(String[] args)
    {
        //Instantiate an instance of this Runner class.
        // The constructor will be called
        // Then the init() method will be called
        //DO NOT WRITE ANY ADDITIONAL CODE IN THIS METHOD!
        new RunnerServer();
    }
}