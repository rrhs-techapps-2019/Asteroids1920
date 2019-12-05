package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.Role;
import org.rrhs.asteroids.RunnerClient;
import org.rrhs.asteroids.actors.data.RoleData;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.logging.LogLevel;
import org.rrhs.asteroids.util.logging.LoggerConfigurator;
import org.rrhs.asteroids.util.logging.writers.ConsoleWriter;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


public class LobbyView extends World
{
    private static final String FONT_NAME_DEFAULT = "Segoe UI";
    private static final int FONT_SIZE_DEFAULT = 18;

    private final Map<Role, Point> labelPositions = new EnumMap<>(Role.class);
    private final Map<Integer, Integer> numberKeys = new HashMap<>();

    private Client client;
    private GameState state;
    private Role claimed = Role.NONE;

    public LobbyView(Client client, GameState state)
    {
        this.client = client;
        this.state = state;
        setBackground(MayflowerUtils.imageFromColor(getWidth(), getHeight(), Color.BLACK));
        setFont(FONT_NAME_DEFAULT, FONT_SIZE_DEFAULT);
        showText("CHOOSE YOUR POSITION", 50, 50);

        labelPositions.put(Role.PILOT, new Point(50, 100));
        labelPositions.put(Role.WEAPONS, new Point(50, 150));
        labelPositions.put(Role.SENSORS, new Point(50, 200));
        labelPositions.put(Role.ENGINEER, new Point(50, 250));
        IntStream.rangeClosed(1, 4).forEach(i -> numberKeys.put(i, i + 48));

        RoleData roleState = state.getRoleState();
        if (roleState.isAvailable(Role.PILOT)) super.showText("1: PILOT", 50, 100);
        if (roleState.isAvailable(Role.WEAPONS)) super.showText("2: WEAPONS", 50, 150);
        if (roleState.isAvailable(Role.SENSORS)) super.showText("3: SENSORS", 50, 200);
        if (roleState.isAvailable(Role.ENGINEER)) super.showText("4: ENGINEER", 50, 250);
    }

    @Override
    public void act()
    {
        state.act();

        RoleData roleState = state.getRoleState();
        roleState.getAll().forEach((role, id) -> {
            if (id == state.getClientId()) claimed = role;
            if (role == claimed && id != state.getClientId()) claimed = Role.NONE;
        });

        processInput();
        updateText();
    }

    private void updateText()
    {
        labelPositions.values().forEach(p -> removeText(p.x, p.y));
        Arrays.stream(Role.values()).forEach(role -> {
            if (role == Role.NONE) return;
            Point p = labelPositions.get(role);
            showText(makeLabelText(role), p.x, p.y);
        });
    }

    private String makeLabelText(Role role)
    {
        RoleData roleState = state.getRoleState();
        if (role == claimed) return "You are " + role + ". Press " + role.ordinal() + " to release.";
        else if (roleState.isAvailable(role)) return role.ordinal() + ": " + role.toString();
        else return role + " CLAIMED.";
    }

    private void processInput()
    {
        RoleData roleState = state.getRoleState();
        for (int number : numberKeys.keySet())
        {
            if (MayflowerUtils.wasKeyPressed(numberKeys.get(number)))
            {
                Role role = Role.values()[number];
                if (claimed == role)
                {
                    requestRelease(role);
                    return;
                } else if (roleState.isAvailable(role) && claimed == Role.NONE)
                {
                    requestClaim(role);
                    return;
                }
            }
        }
    }

    private void requestClaim(Role role)
    {
        Point labelPos = labelPositions.get(role);
        super.removeText(labelPos.x, labelPos.y);
        showText(role + " CLAIMED. Press " + role.ordinal() + " to release.", labelPos.x, labelPos.y);
        client.send(new Packet(PacketAction.CLAIM_ROLE, String.valueOf(role.ordinal())));
    }

    private void requestRelease(Role role)
    {
        Point labelPos = labelPositions.get(role);
        super.removeText(labelPos.x, labelPos.y);
        showText(role.ordinal() + ": " + role, labelPos.x, labelPos.y);
        client.send(new Packet(PacketAction.RELEASE_ROLE, String.valueOf(role.ordinal())));
    }

    public static void main(String[] args)
    {
        LoggerConfigurator.empty()
                .addWriter(new ConsoleWriter(System.out))
                .level(LogLevel.DEBUG)
                .activate();
        new RunnerClient(LobbyView.class);
    }
}