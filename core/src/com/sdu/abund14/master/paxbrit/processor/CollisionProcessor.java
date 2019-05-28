package com.sdu.abund14.master.paxbrit.processor;

import com.badlogic.gdx.math.Intersector;
import com.sdu.abund14.master.paxbrit.GameEntity;
import com.sdu.abund14.master.paxbrit.Grid;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.LinkedList;
import java.util.List;

public class CollisionProcessor implements Processor {

    private Grid grid = NautilusGame.currentMatch.getGrid();

    @Override
    public void process(float delta) {
        //Keep a list of bullets to remove after iterating
        List<Bullet> bulletsToRemove = new LinkedList<Bullet>();

        //For each cell in the grid
        for (int x = 0; x < Grid.NUM_CELLS; x++) {
            for (int y = 0; y < Grid.NUM_CELLS; y++) {
                //Skip empty cells
                if (grid.getCells()[x][y].isEmpty()) continue;

                //For each entity in cell, compare to every entity after it in the list
                for (int i = 0; i < grid.getCells()[x][y].size(); i++) {
                    if (i >= grid.getCells()[x][y].size()) return;
                    GameEntity entity = grid.getCells()[x][y].get(i);

                    for (int j = i + 1; j < grid.getCells()[x][y].size(); j++) {
                        GameEntity next = grid.getCells()[x][y].get(j);

                        //Skip entities belonging to the same player
                        if (entity.getPlayerNumber() == next.getPlayerNumber()) continue;

                        //Check if comparing a bullet and a ship, then cast
                        Bullet bullet = null;
                        Ship ship = null;
                        if (entity instanceof Bullet && next instanceof Ship) {
                            bullet = (Bullet) NautilusGame.currentMatch.getEntityById(entity.getId());
                            ship = (Ship) NautilusGame.currentMatch.getEntityById(next.getId());
                            //If bullet cannot be found or is dead, it can be removed from the cell
                            if (bullet == null || bullet.isDead()) {
                                bulletsToRemove.add((Bullet) entity);
                                continue;
                            }
                        } else if (entity instanceof Ship && next instanceof Bullet) {
                            bullet = (Bullet) NautilusGame.currentMatch.getEntityById(next.getId());
                            ship = (Ship) NautilusGame.currentMatch.getEntityById(entity.getId());
                            if (bullet == null || bullet.isDead()) {
                                bulletsToRemove.add((Bullet) next);
                                continue;
                            }
                        }
                        if (bullet == null || ship == null) continue;

                        //Check collision between the collision polygons (hitboxes) of each entity
                        if (Intersector.overlapConvexPolygons(
                                        bullet.getCollisionPolygon().getTransformedVertices(),
                                        ship.getCollisionPolygon().getTransformedVertices(), null)
                        ) {
                            ship.takeDamage(bullet.getDamage());
                            bulletsToRemove.add(bullet);
                        }
                    }
                }
                //Remove bullets that have collided with an enemy ship or have otherwise died
                for (Bullet bullet : bulletsToRemove) {
                    bullet.destroy();
                    grid.getCells()[x][y].remove(bullet);
                }
            }
        }
    }
}
