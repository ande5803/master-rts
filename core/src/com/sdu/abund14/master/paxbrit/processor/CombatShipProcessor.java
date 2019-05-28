package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CombatShipProcessor implements Processor {
    @Override
    public void process(float delta) {
        removeDeadShips();
        NautilusGame.forkJoinPool.submit(
                new ProcessCombatShipAction(NautilusGame.currentMatch.getCombatShips(), delta)
        );
    }

    private void removeDeadShips() {
        NautilusGame.currentMatch.getFighters().removeIf(Ship::isDead);
        NautilusGame.currentMatch.getBombers().removeIf(Ship::isDead);
        NautilusGame.currentMatch.getFrigates().removeIf(Ship::isDead);
    }

    class ProcessCombatShipAction extends RecursiveAction {

        ConcurrentLinkedQueue<CombatShip> ships;
        float delta;
        int shipThreshold = 20;

        ProcessCombatShipAction(ConcurrentLinkedQueue<CombatShip> ships, float delta) {
            this.ships = ships;
            this.delta = delta;
        }

        @Override
        protected void compute() {
            if (ships.size() > shipThreshold) {
                ForkJoinTask.invokeAll(createSubTasks());
            } else {
                processSubTask(ships);
            }
        }

        List<ProcessCombatShipAction> createSubTasks() {
            List<ProcessCombatShipAction> subTasks = new LinkedList<>();
            ConcurrentLinkedQueue<CombatShip> subQueueOne = ships;
            ConcurrentLinkedQueue<CombatShip> subQueueTwo = new ConcurrentLinkedQueue<>();
            for (int i = 0; i < ships.size() / 2; i++) {
                subQueueTwo.add(subQueueOne.remove());
            }
            subTasks.add(new ProcessCombatShipAction(subQueueOne, delta));
            subTasks.add(new ProcessCombatShipAction(subQueueTwo, delta));
            return subTasks;
        }

        void processSubTask(ConcurrentLinkedQueue<CombatShip> ships) {
            for (CombatShip ship : ships) {
                ship.ai.update(delta);
                if (ship.shotCooldownTimeLeft > 0) {
                    ship.shotCooldownTimeLeft -= delta;
                } else {
                    ship.shotCooldownTimeLeft = 0;
                }
                if (ship.reloadTimeLeft > 0) {
                    ship.reloadTimeLeft -= delta;
                } else if (ship.ammoLeft <= 0) {
                    ship.reload();
                }
            }
        }
    }
}
