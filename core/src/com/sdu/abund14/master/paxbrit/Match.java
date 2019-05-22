package com.sdu.abund14.master.paxbrit;

import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.ship.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Match {
    private ConcurrentLinkedQueue<Fighter> fighters;
    private ConcurrentLinkedQueue<Bomber> bombers;
    private ConcurrentLinkedQueue<Frigate> frigates;
    private ConcurrentLinkedQueue<FactoryShip> factories;
    private ConcurrentLinkedQueue<Bullet> bullets;
    private GameScreen screen;

    Match(GameScreen screen) {
        this.screen = screen;
        fighters = new ConcurrentLinkedQueue<>();
        bombers = new ConcurrentLinkedQueue<>();
        frigates = new ConcurrentLinkedQueue<>();
        factories = new ConcurrentLinkedQueue<>();
        bullets = new ConcurrentLinkedQueue<>();
    }

    public GameScreen getScreen() {
        return screen;
    }

    public ConcurrentLinkedQueue<Fighter> getFighters() {
        return fighters;
    }

    public ConcurrentLinkedQueue<Bomber> getBombers() {
        return bombers;
    }

    public ConcurrentLinkedQueue<Frigate> getFrigates() {
        return frigates;
    }

    public ConcurrentLinkedQueue<FactoryShip> getFactories() {
        return factories;
    }

    public ConcurrentLinkedQueue<Bullet> getBullets() { return bullets; }

    public ConcurrentLinkedQueue<Ship> getAllShips() {
        ConcurrentLinkedQueue<Ship> ships = new ConcurrentLinkedQueue<>();
        ships.addAll(getCombatShips());
        ships.addAll(factories);
        return ships;
    }

    public ConcurrentLinkedQueue<CombatShip> getCombatShips() {
        ConcurrentLinkedQueue<CombatShip> ships = new ConcurrentLinkedQueue<>();
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
