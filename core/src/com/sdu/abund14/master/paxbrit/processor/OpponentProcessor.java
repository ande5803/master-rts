package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.opponent.Opponent;
import com.sdu.abund14.master.paxbrit.ship.FactoryShip;

import java.util.LinkedList;
import java.util.List;


public class OpponentProcessor implements Processor {

    private List<Opponent> opponents;
    private float aiProductionDelay = 0.5f;

    private void init() {
        //Instantiate opponents and make first choice
        opponents = new LinkedList<Opponent>();
        for (FactoryShip ship : PaxBritannicaGame.currentMatch.getFactories()) {
            if (ship.isPlayerControlled()) continue;
            opponents.add(new Opponent(ship));
        }
        for (Opponent opponent : opponents) {
            opponent.setCurrentChoice(opponent.getAi().makeProductionChoice());
        }
    }

    @Override
    public void process(float delta) {
        if (opponents == null) init();
        for (Opponent opponent : opponents) {
            if (opponent.getShip().isDead()) {
                continue;
            }
            opponent.setProductionTimeLeft(opponent.getProductionTimeLeft() - delta);
            if (opponent.getProductionTimeLeft() <= 0) {
                opponent.triggerProduction();
                opponent.setCurrentChoice(opponent.getAi().makeProductionChoice());
                float newProductionTime = 0;
                switch (opponent.getCurrentChoice()) {
                    case FIGHTER:
                        newProductionTime = opponent.getShip().getFighterSpawnTime();
                        break;
                    case BOMBER:
                        newProductionTime = opponent.getShip().getBomberSpawnTime();
                        break;
                    case FRIGATE:
                        newProductionTime = opponent.getShip().getFrigateSpawnTime();
                        break;
                    case UPGRADE:
                        newProductionTime = opponent.getShip().getUpgradeTime();
                        break;
                }
                opponent.setProductionTimeLeft(newProductionTime + aiProductionDelay);
            }
        }
    }
}
