package org.rrhs.asteroids;

import org.rrhs.asteroids.network.ServerWorld;

public class RunnerServer extends Runner
{
    public RunnerServer()
    {
        super(ServerWorld.class, true);
        super.initServer();
    }

    public static void main(String[] args)
    {
        new RunnerServer();
    }
}