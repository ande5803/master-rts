package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.Match;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

public class CollisionProcessor implements Processor {

    private Match match = PaxBritannicaGame.currentMatch;

    @Override
    public void process(float delta) {
        for (int i = 0; i < match.getBullets().size(); i++) {
            Bullet bullet = match.getBullets().get(i);
            for (Ship ship : match.getAllShips()) {
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
