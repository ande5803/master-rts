package com.sdu.abund14.master.paxbrit.util;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;

import java.awt.geom.Point2D;
import java.util.List;

public class ShipsUtil {

    public static Ship getNearestEnemyShipWithTypePriorities(Ship source, ShipType... priorities) {
        for (ShipType type : priorities) {
            Ship ship = getNearestEnemyShipOfType(source, type);
            if (ship != null) {
                return ship;
            }
        }
        return null;
    }

    private static Ship getNearestEnemyShipOfType(Ship source, ShipType type) {
        List<? extends Ship> possibleTargets = getShipsOfType(type);
        if (possibleTargets == null || possibleTargets.size() == 0) {
            return null;
        }
        Ship closestShip = null;
        double closestDistance = Double.MAX_VALUE;
        for (Ship ship : possibleTargets) {
            if (source.getPlayerNumber() == ship.getPlayerNumber()) {
                continue;
            }
            double distance = Point2D.distance(source.getX(), source.getY(), ship.getX(), ship.getY());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestShip = ship;
            }
        }
        return closestShip;
    }

    private static List<? extends Ship> getShipsOfType(ShipType type) {
        switch (type) {
            case FIGHTER:
                return PaxBritannicaGame.currentMatch.getFighters();
            case BOMBER:
                return PaxBritannicaGame.currentMatch.getBombers();
            case FRIGATE:
                return PaxBritannicaGame.currentMatch.getFrigates();
            case FACTORY:
                return PaxBritannicaGame.currentMatch.getFactories();
            default:
                System.out.println("Error: Unknown ShipType");
                return null;
        }
    }
}
