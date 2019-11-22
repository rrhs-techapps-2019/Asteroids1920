public class Laser
{

    // seconds untill laer is destroyed
  int SecPassed = 0;
          Timer Timer = new Timer();
  TimerTask task new = new TimerTask ()
{
    public void run()
    {
        SecPassed++;

    }

}
public void start()
{
myTimer.scheduleAtFixedRate(task,1000,1000);
}
}