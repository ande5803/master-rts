package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.Match;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CollisionProcessor implements Processor {

    private Match match = PaxBritannicaGame.currentMatch;

    @Override
    public void process(float delta) {
        PaxBritannicaGame.forkJoinPool.submit(new CheckCollisionAction(match.getAllShips()));
    }

    class CheckCollisionAction extends RecursiveAction {

        ConcurrentLinkedQueue<Ship> ships;
        int shipThreshold = 20;

        CheckCollisionAction(ConcurrentLinkedQueue<Ship> ships) {
            this.ships = ships;
        }

        @Override
        protected void compute() {
            if (ships.size() > shipThreshold) {
                ForkJoinTask.invokeAll(createSubTasks());
            } else {
                processSubTask(ships);
            }
        }

        List<CheckCollisionAction> createSubTasks() {
            List<CheckCollisionAction> subTasks = new ArrayList<>();
            ConcurrentLinkedQueue<Ship> subQueueOne = ships;
            ConcurrentLinkedQueue<Ship> subQueueTwo = new ConcurrentLinkedQueue<>();
            for (int i = 0; i < ships.size() / 2; i++) {
                subQueueTwo.add(subQueueOne.remove());
            }
            subTasks.add(new CheckCollisionAction(subQueueOne));
            subTasks.add(new CheckCollisionAction(subQueueTwo));
            return subTasks;
        }

        void processSubTask(ConcurrentLinkedQueue<Ship> ships) {
            for (Ship ship : ships) {
                for (Bullet bullet : match.getBullets()) {
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
}
