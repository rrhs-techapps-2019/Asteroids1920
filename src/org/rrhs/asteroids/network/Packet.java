package org.rrhs.asteroids.network;

import org.rrhs.asteroids.actors.NetworkActor;

public class Packet {
    String actor;
    String action;
    String data;

    public Packet(String action, NetworkActor actor, String data) {
        this.action = action;
        this.actor = actor.toString();
        this.data = data;
    }
    public Packet(String action, String data) {
        this.action = action;
        this.actor = null;
        this.data = data;
    }
    public Packet(String action, NetworkActor actor) {
        this.action = action;
        this.actor = actor.toString();
        this.data = null;
    }

    public String setData(String data) {
        String ret = this.data;
        this.data = data;
        return ret;
    }

    public String setActor(NetworkActor actor) {
        String ret = this.actor;
        this.actor = actor.toString();
        return ret;
    }

    public String setAction(String action) {
        String ret = this.action;
        this.action = action;
        return ret;
    }
}
