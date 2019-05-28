package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.LinkedList;
import java.util.List;

public class CollisionProcessor implements Processor {

    @Override
    public void process(float delta) {
        List<Ship> ships = NautilusGame.currentMatch.getShips();
        List<Bullet> bullets = NautilusGame.currentMatch.getBullets();
        int p1Ships = NautilusGame.currentMatch.getNumPlayer1Ships();
        int p2Ships = NautilusGame.currentMatch.getNumPlayer2Ships();
        int p3Ships = NautilusGame.currentMatch.getNumPlayer3Ships();
        int p1Bullets = NautilusGame.currentMatch.getNumPlayer1Bullets();
        int p2Bullets = NautilusGame.currentMatch.getNumPlayer2Bullets();
        int p3Bullets = NautilusGame.currentMatch.getNumPlayer3Bullets();

        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            List<Bullet> bulletsToRemove = new LinkedList<Bullet>();
            for (int j = 0; j < bullets.size(); j++) {
                if (i >= p1Ships + p2Ships + p3Ships && j == p1Bullets + p2Bullets + p3Bullets) {
                    break;
                } else if (i >= p1Ships + p2Ships && j == p1Bullets + p2Bullets) {
                    j += p3Bullets;
                } else if (i >= p1Ships && j == p1Bullets) {
                    j += p2Bullets;
                } else if (i < p1Ships && j == 0) {
                    j += p1Bullets;
                }

                if (j >= bullets.size()) break;

                Bullet bullet = bullets.get(j);
                if (bullet.getBoundingRectangle().overlaps(ship.getBoundingRectangle())) {
                    ship.takeDamage(bullet.getDamage());
                    bulletsToRemove.add(bullet);
                    break;
                }
            }
            for (Bullet bullet : bulletsToRemove) {
                NautilusGame.currentMatch.removeBullet(bullet);
            }
        }
    }
}
