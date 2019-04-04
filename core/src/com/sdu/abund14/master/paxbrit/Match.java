package com.sdu.abund14.master.paxbrit;

import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.bullet.BulletPool;
import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.LinkedList;
import java.util.List;

public class Match {
    private static final int BULLET_POOL_SIZE = 200;

    private List<Fighter> fighters;
    private List<Bomber> bombers;
    private List<Frigate> frigates;
    private List<FactoryShip> factories;
    private List<Bullet> bullets;
    private BulletPool bulletPool;
    private GameScreen screen;

    Match(GameScreen screen) {
        this.screen = screen;
        fighters = new LinkedList<Fighter>();
        bombers = new LinkedList<Bomber>();
        frigates = new LinkedList<Frigate>();
        factories = new LinkedList<FactoryShip>();
        bullets = new LinkedList<Bullet>();
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

    public List<Bullet> getBullets() { return bullets; }

    public List<Ship> getAllShips() {
        List<Ship> ships = new LinkedList<Ship>();
        ships.addAll(getCombatShips());
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

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public BulletPool getBulletPool() {
        if (bulletPool == null) { //Lazy init
            bulletPool = new BulletPool(BULLET_POOL_SIZE);
        }
        return bulletPool;
    }

    public void checkGameEndConditions() {
        int playerShips = 0;
        int opponentShips = 0;
        for (FactoryShip ship : factories) {
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
}
