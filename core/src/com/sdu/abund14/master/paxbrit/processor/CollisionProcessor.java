package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

public class CollisionProcessor implements Processor {

    @Override
    public void process(float delta) {
        for (Bullet bullet : PaxBritannicaGame.currentMatch.getBullets()) {
            for (Ship ship : PaxBritannicaGame.currentMatch.getAllShips()) {
                if (bullet.getPlayerNumber() == ship.getPlayerNumber()) {
                    continue; //Skip friendly fire collisions
                }
                //TODO Use something more precise than bounding rectangles
                if (bullet.getBoundingRectangle().overlaps(ship.getBoundingRectangle())) {
                    ship.takeDamage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
    }
}
