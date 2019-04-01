package com.sdu.abund14.master.paxbrit;

import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.LinkedList;
import java.util.List;

public class Match {
    private List<Ship> ships;
    private List<Bullet> bullets;
    private GameScreen screen;

    //For data locality of bullets and ships
    private int numPlayer1Bullets = 0;
    private int numPlayer2Bullets = 0;
    private int numPlayer3Bullets = 0;
    private int numPlayer1Ships = 0;
    private int numPlayer2Ships = 0;
    private int numPlayer3Ships = 0;

    Match(GameScreen screen) {
        this.screen = screen;
        ships = new LinkedList<Ship>();
        bullets = new LinkedList<Bullet>();
    }

    public GameScreen getScreen() {
        return screen;
    }

    public List<Fighter> getFighters() {
        List<Fighter> fighters = new LinkedList<Fighter>();
        for (Ship ship : ships) {
            if (ship instanceof Fighter) {
                fighters.add((Fighter) ship);
            }
        }
        return fighters;
    }

    public List<Bomber> getBombers() {
        List<Bomber> bombers = new LinkedList<Bomber>();
        for (Ship ship : ships) {
            if (ship instanceof Bomber) {
                bombers.add((Bomber) ship);
            }
        }
        return bombers;
    }

    public List<Frigate> getFrigates() {
        List<Frigate> frigates = new LinkedList<Frigate>();
        for (Ship ship : ships) {
            if (ship instanceof Frigate) {
                frigates.add((Frigate) ship);
            }
        }
        return frigates;
    }

    public List<FactoryShip> getFactories() {
        List<FactoryShip> factoryShips = new LinkedList<FactoryShip>();
        for (Ship ship : ships) {
            if (ship instanceof FactoryShip) {
                factoryShips.add((FactoryShip) ship);
            }
        }
        return factoryShips;
    }

    public List<Bullet> getBullets() { return bullets; }

    public List<Ship> getShips() {
        return ships;
    }

    public List<CombatShip> getCombatShips() {
        List<CombatShip> combatShips = new LinkedList<CombatShip>();
        for (Ship ship : ships) {
            if (ship instanceof Fighter) { combatShips.add((Fighter) ship); }
            else if (ship instanceof Bomber) { combatShips.add((Bomber) ship); }
            else if (ship instanceof Frigate) { combatShips.add((Frigate) ship); }
        }
        return combatShips;
    }

    public void addShip(Ship ship) {
        switch (ship.getPlayerNumber()) {
            case 1:
                ships.add(0, ship);
                numPlayer1Ships++;
                break;
            case 2:
                ships.add(numPlayer1Ships, ship);
                numPlayer2Ships++;
                break;
            case 3:
                ships.add(numPlayer1Ships + numPlayer2Ships, ship);
                numPlayer3Ships++;
                break;
            case 4:
                ships.add(ships.size(), ship);
                break;
        }
    }

    public void removeShip(Ship ship) {
        switch (ship.getPlayerNumber()) {
            case 1:
                numPlayer1Ships--;
                break;
            case 2:
                numPlayer2Ships--;
                break;
            case 3:
                numPlayer3Ships--;
                break;
        }
    }

    public void addBullet(Bullet bullet) {
        switch (bullet.getPlayerNumber()) {
            case 1:
                bullets.add(0, bullet);
                numPlayer1Bullets++;
                break;
            case 2:
                bullets.add(numPlayer1Bullets, bullet);
                numPlayer2Bullets++;
                break;
            case 3:
                bullets.add(numPlayer1Bullets + numPlayer2Bullets, bullet);
                numPlayer3Bullets++;
                break;
            case 4:
                bullets.add(bullets.size(), bullet);
                break;
        }
    }

    public void removeBullet(Bullet bullet) {
        bullet.destroy();
        switch (bullet.getPlayerNumber()) {
            case 1:
                numPlayer1Bullets--;
                break;
            case 2:
                numPlayer2Bullets--;
                break;
            case 3:
                numPlayer3Bullets--;
                break;
        }
        bullets.remove(bullet);
    }

    public void checkGameEndConditions() {
        int playerShips = 0;
        int opponentShips = 0;
        for (FactoryShip ship : getFactories()) {
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

    public int getNumPlayer1Bullets() {
        return numPlayer1Bullets;
    }

    public int getNumPlayer2Bullets() {
        return numPlayer2Bullets;
    }

    public int getNumPlayer3Bullets() {
        return numPlayer3Bullets;
    }

    public int getNumPlayer1Ships() {
        return numPlayer1Ships;
    }

    public int getNumPlayer2Ships() {
        return numPlayer2Ships;
    }

    public int getNumPlayer3Ships() {
        return numPlayer3Ships;
    }
}
