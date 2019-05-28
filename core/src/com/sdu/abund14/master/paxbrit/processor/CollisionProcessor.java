package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.Match;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollisionProcessor implements Processor {

    private Match match = NautilusGame.currentMatch;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private ExecutorCompletionService<Runnable> ecs = new ExecutorCompletionService<Runnable>(threadPool);

    @Override
    public void process(float delta) {
        int tasks = 0;
        for (final Bullet bullet : match.getBullets()) {
            tasks++;
            ecs.submit(new Runnable() {
                @Override
                public void run() {
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
            }, null);
        }
        try {
            for (int i = 0; i < tasks; i++) {
                ecs.take();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
