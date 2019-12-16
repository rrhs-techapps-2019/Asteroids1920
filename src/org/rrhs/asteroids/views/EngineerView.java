package org.rrhs.asteroids.views;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerClient;
import org.rrhs.asteroids.RunnerServer;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.util.logging.LogLevel;
import org.rrhs.asteroids.util.logging.LoggerConfigurator;
import org.rrhs.asteroids.util.logging.writers.ConsoleWriter;

public class EngineerView extends GameView
{
    //private int energyPilot = 50;
    public EngineerView(Client client, GameState state)
    {
        super(client, state);
    }

    public static void main(String[] args)
    {
        LoggerConfigurator.empty()
                .addWriter(new ConsoleWriter())
                .level(LogLevel.DEBUG)
                .activate();
        new RunnerServer();
        new RunnerClient(EngineerView.class);
    }
}