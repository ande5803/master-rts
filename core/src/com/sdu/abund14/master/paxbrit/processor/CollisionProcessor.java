package com.sdu.abund14.master.paxbrit.processor;

import com.badlogic.gdx.math.Intersector;
import com.sdu.abund14.master.paxbrit.GameEntity;
import com.sdu.abund14.master.paxbrit.Grid;
import com.sdu.abund14.master.paxbrit.Match;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

public class CollisionProcessor implements Processor {

    private Grid grid = PaxBritannicaGame.currentMatch.getGrid();

    @Override
    public void process(float delta) {
        for (int x = 0; x < Grid.NUM_CELLS; x++) {
            for (int y = 0; y < Grid.NUM_CELLS; y++) {
                GameEntity entity = grid.getCells()[x][y];
                while (entity != null) {
                    if (entity instanceof Ship) {
                        GameEntity next = entity.getNext();
                        while (next != null) {
                            if (next instanceof Bullet && Intersector.overlapConvexPolygons(entity.getCollisionPolygon(), next.getCollisionPolygon())) {
                                Ship ship = (Ship) entity;
                                Bullet bullet = (Bullet) next;
                                ship.takeDamage(bullet.getDamage());
                                bullet.destroy();
                            }
                            next = next.getNext();
                        }
                    }
                    entity = entity.getNext();
                }
            }
        }

//        for (int i = 0; i < PaxBritannicaGame.currentMatch.getBullets().size(); i++) {
//            Bullet bullet = PaxBritannicaGame.currentMatch.getBullets().get(i);
//            for (Ship ship : PaxBritannicaGame.currentMatch.getAllShips()) {
//                if (bullet.getPlayerNumber() == ship.getPlayerNumber()) {
//                    continue; //Skip friendly fire collisions
//                }
//                if (Intersector.overlapConvexPolygons(bullet.getCollisionPolygon(), ship.getCollisionPolygon())) {
//                    ship.takeDamage(bullet.getDamage());
//                    bullet.destroy();
//                }
//            }
//        }
    }
}
