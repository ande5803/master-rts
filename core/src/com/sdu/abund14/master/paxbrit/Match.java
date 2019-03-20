package com.sdu.abund14.master.paxbrit;

import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Match {
    private Queue<Fighter> fighters;
    private Queue<Bomber> bombers;
    private Queue<Frigate> frigates;
    private Queue<FactoryShip> factories;
    private Queue<Bullet> bullets;
    private GameScreen screen;

    Match(GameScreen screen) {
        this.screen = screen;
        fighters = new ConcurrentLinkedQueue<Fighter>();
        bombers = new ConcurrentLinkedQueue<Bomber>();
        frigates = new ConcurrentLinkedQueue<Frigate>();
        factories = new ConcurrentLinkedQueue<FactoryShip>();
        bullets = new ConcurrentLinkedQueue<Bullet>();
    }

    public GameScreen getScreen() {
        return screen;
    }

    public Queue<Fighter> getFighters() {
        return fighters;
    }

    public Queue<Bomber> getBombers() {
        return bombers;
    }

    public Queue<Frigate> getFrigates() {
        return frigates;
    }

    public Queue<FactoryShip> getFactories() {
        return factories;
    }

    public Queue<Bullet> getBullets() { return bullets; }

    public Queue<Ship> getAllShips() {
        Queue<Ship> ships = new ConcurrentLinkedQueue<Ship>();
        ships.addAll(getCombatShips());
        ships.addAll(factories);
        return ships;
    }

    public Queue<CombatShip> getCombatShips() {
        Queue<CombatShip> ships = new ConcurrentLinkedQueue<CombatShip>();
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
