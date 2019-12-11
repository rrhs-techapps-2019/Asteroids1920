package org.rrhs.asteroids.actors.data;

public class ShipData
{
    public final int pilotPower;
    public final int weaponsPower;
    public final int sensorsPower;


    public ShipData(int pilotPower, int weaponsPower, int sensorsPower)
    {
        this.pilotPower = pilotPower;
        this.weaponsPower = weaponsPower;
        this.sensorsPower = sensorsPower;
    }
}