package com.sdu.abund14.master.paxbrit;

import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.LinkedList;
import java.util.List;

public class Match {
    private List<Fighter> fighters;
    private List<Bomber> bombers;
    private List<Frigate> frigates;
    private List<FactoryShip> factories;
    private GameScreen screen;

    public Match(GameScreen screen) {
        this.screen = screen;
        fighters = new LinkedList<Fighter>();
        bombers = new LinkedList<Bomber>();
        frigates = new LinkedList<Frigate>();
        factories = new LinkedList<FactoryShip>();
    }

    public GameScreen getScreen() {
        return screen;
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public List<Bomber> getBombers() {
        return bombers;
    }

    public List<Frigate> getFrigates() {
        return frigates;
    }

    public List<FactoryShip> getFactories() {
        return factories;
    }

    public List<Ship> getAllShips() {
        List<Ship> ships = new LinkedList<Ship>();
        ships.addAll(fighters);
        ships.addAll(bombers);
        ships.addAll(frigates);
        ships.addAll(factories);
        return ships;
    }

    public List<CombatShip> getCombatShips() {
        List<CombatShip> ships = new LinkedList<CombatShip>();
        ships.addAll(fighters);
        ships.addAll(bombers);
        ships.addAll(frigates);
        return ships;
    }

    public void addShip(Ship ship) {
        if (ship instanceof Fighter) {
            fighters.add((Fighter) ship);
        } else if (ship instanceof Bomber) {
            bombers.add((Bomber) ship);
        } else if (ship instanceof Frigate) {
            frigates.add((Frigate) ship);
        } else if (ship instanceof FactoryShip) {
            factories.add((FactoryShip) ship);
        }
    }

    public void removeShip(Ship ship) {
        if (ship instanceof Fighter) {
            fighters.remove(ship);
        } else if (ship instanceof Bomber) {
            bombers.remove(ship);
        } else if (ship instanceof Frigate) {
            frigates.remove(ship);
        } else if (ship instanceof FactoryShip) {
            factories.remove(ship);
        }
    }
}
