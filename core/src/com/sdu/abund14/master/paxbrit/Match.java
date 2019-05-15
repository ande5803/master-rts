package com.sdu.abund14.master.paxbrit;

import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.*;

public class Match {
    private Map<UUID, Fighter> fighters;
    private Map<UUID, Bomber> bombers;
    private Map<UUID, Frigate> frigates;
    private Map<UUID, FactoryShip> factories;
    private Map<UUID, CombatShip> combatShips;
    private Map<UUID, Ship> allShips;
    private Map<UUID, Bullet> bullets;
    private GameScreen screen;
    private Grid grid;

    Match(GameScreen screen) {
        this.screen = screen;
        fighters = new HashMap<UUID, Fighter>();
        bombers = new HashMap<UUID, Bomber>();
        frigates = new HashMap<UUID, Frigate>();
        factories = new HashMap<UUID, FactoryShip>();
        combatShips = new HashMap<UUID, CombatShip>();
        allShips = new HashMap<UUID, Ship>();
        bullets = new HashMap<UUID, Bullet>();
        grid = new Grid();
    }

    public GameScreen getScreen() {
        return screen;
    }

    public Map<UUID, Fighter> getFighters() {
        return fighters;
    }

    public Map<UUID, Bomber> getBombers() {
        return bombers;
    }

    public Map<UUID, Frigate> getFrigates() {
        return frigates;
    }

    public Map<UUID, FactoryShip> getFactories() {
        return factories;
    }

    public Map<UUID, Bullet> getBullets() { return bullets; }

    public Grid getGrid() {
        return grid;
    }

    public Map<UUID, Ship> getAllShips() {
        return allShips;
    }

    public Map<UUID, CombatShip> getCombatShips() {
        return combatShips;
    }

    public void addShip(Ship ship) {
        if (ship instanceof Fighter) {
            fighters.put(ship.getId(), (Fighter) ship);
        } else if (ship instanceof Bomber) {
            bombers.put(ship.getId(), (Bomber) ship);
        } else if (ship instanceof Frigate) {
            frigates.put(ship.getId(), (Frigate) ship);
        } else if (ship instanceof FactoryShip) {
            factories.put(ship.getId(), (FactoryShip) ship);
        }
        allShips.put(ship.getId(), ship);
        if (ship instanceof CombatShip) combatShips.put(ship.getId(), (CombatShip) ship);
    }

    public void addBullet(Bullet bullet) {
        bullets.put(bullet.getId(), bullet);
    }

    public void checkGameEndConditions() {
        int playerShips = 0;
        int opponentShips = 0;
        for (FactoryShip ship : factories.values()) {
            if (ship.isDead()) continue;
            if (ship.isPlayerControlled()) {
                playerShips++;
            } else {
                opponentShips++;
            }
        }
        if (playerShips == 0) {
            endGame(false);
        } else if (playerShips > 0 && opponentShips == 0) {
            endGame(true);
        }
    }

    private void endGame(boolean victory) {
        System.out.println(victory ? "Victory!" : "Defeat!");
        screen.endGame(victory);
    }

    public GameEntity getEntityById(UUID id) {
        if (allShips.get(id) != null) return allShips.get(id);
        if (bullets.get(id) != null) return bullets.get(id);
        return null;
    }
}
