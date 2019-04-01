package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CollisionProcessor implements Processor {

    @Override
    public void process(float delta) {
        List<Bullet> bulletsToRemove = new LinkedList<Bullet>();
        List<Ship> ships = PaxBritannicaGame.currentMatch.getShips();
        List<Bullet> bullets = PaxBritannicaGame.currentMatch.getBullets();
        int p1Ships = PaxBritannicaGame.currentMatch.getNumPlayer1Ships();
        int p2Ships = PaxBritannicaGame.currentMatch.getNumPlayer2Ships();
        int p3Ships = PaxBritannicaGame.currentMatch.getNumPlayer3Ships();
        int p1Bullets = PaxBritannicaGame.currentMatch.getNumPlayer1Bullets();
        int p2Bullets = PaxBritannicaGame.currentMatch.getNumPlayer2Bullets();
        int p3Bullets = PaxBritannicaGame.currentMatch.getNumPlayer3Bullets();

        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            for (int j = 0; j < bullets.size(); j++) {
                if (i < p1Ships) {
                    j = p1Bullets;
                } else if (i < p1Ships + p2Ships && j >= p1Bullets) {
                    j = p1Bullets + p2Bullets;
                } else if (i < p1Ships + p2Ships + p3Ships && j >= p1Bullets + p2Bullets) {
                    j = p1Bullets + p2Bullets + p3Bullets;
                } else if (j >= p1Bullets + p2Bullets + p3Bullets) {
                    break;
                }

                Bullet bullet = bullets.get(j);
                if (bullet.getBoundingRectangle().overlaps(ship.getBoundingRectangle())) {
                    ship.takeDamage(bullet.getDamage());
                    bulletsToRemove.add(bullet);
                    break;
                }
            }
        }
        for (Bullet bullet : bulletsToRemove) {
            PaxBritannicaGame.currentMatch.removeBullet(bullet);
        }
    }
}
