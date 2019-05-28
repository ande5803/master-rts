package com.sdu.abund14.master.paxbrit.util;

import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;

import java.awt.geom.Point2D;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShipsUtil {

    public static Ship getNearestEnemyShipWithTypePriorities(Ship source, ShipType... priorities) {
        if (priorities == null || priorities.length <= 0) return null;
        for (ShipType type : priorities) {
            Ship ship = getNearestEnemyShipOfType(source, type);
            if (ship != null) {
                return ship;
            }
        }
        return null;
    }

    private static Ship getNearestEnemyShipOfType(Ship source, ShipType type) {
        ConcurrentLinkedQueue<? extends Ship> possibleTargets = getShipsOfType(type);
        if (possibleTargets == null || possibleTargets.size() == 0) {
            return null;
        }
        Ship closestShip = null;
        double closestDistance = Double.MAX_VALUE;
        for (Ship ship : possibleTargets) {
            if (source.getPlayerNumber() == ship.getPlayerNumber()
                || ship.isOffScreen()) {
                continue; //Skip allies and off-screen ships
            }
            double distance = Point2D.distance(source.getX(), source.getY(), ship.getX(), ship.getY());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestShip = ship;
            }
        }
        return closestShip;
    }

    private static ConcurrentLinkedQueue<? extends Ship> getShipsOfType(ShipType type) {
        switch (type) {
            case FIGHTER:
                return NautilusGame.currentMatch.getFighters();
            case BOMBER:
                return NautilusGame.currentMatch.getBombers();
            case FRIGATE:
                return NautilusGame.currentMatch.getFrigates();
            case FACTORY:
                return NautilusGame.currentMatch.getFactories();
            default:
                System.out.println("Error: Unknown ShipType");
                return null;
        }
    }

    public static int getEnemyShipCount(ShipType type, int playerNumber) {
        int result = 0;
        for (Ship ship : getShipsOfType(type)) {
            if (ship.getPlayerNumber() != playerNumber) {
                result++;
            }
        }
        return result;
    }
}
