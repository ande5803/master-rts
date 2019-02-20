package com.sdu.abund14.master.paxbrit.opponent;

import com.sdu.abund14.master.paxbrit.GameSettings;
import com.sdu.abund14.master.paxbrit.ship.FactoryShip;

public class Opponent {

    private ProductionChoice currentChoice = ProductionChoice.NONE;
    private float productionTimeLeft;
    private FactoryShip ship;
    private OpponentAI ai;

    public Opponent(FactoryShip ship) {
        this.ship = ship;
        ai = new OpponentAI(ship.getPlayerNumber(), GameSettings.difficulty);
    }

    public void triggerProduction() {
        switch (currentChoice) {
            case FIGHTER:
                ship.spawnFighter();
                break;
            case BOMBER:
                ship.spawnBomber();
                break;
            case FRIGATE:
                ship.spawnFrigate();
                break;
            case UPGRADE:
                ship.upgrade();
            case NONE:
            default:
                System.out.println("Error in Opponent.triggerProduction: No production choice made");
        }
    }

    public OpponentAI getAi() {
        return ai;
    }

    public ProductionChoice getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(ProductionChoice currentChoice) {
        this.currentChoice = currentChoice;
    }

    public float getProductionTimeLeft() {
        return productionTimeLeft;
    }

    public void setProductionTimeLeft(float productionTimeLeft) {
        this.productionTimeLeft = productionTimeLeft;
    }

    public FactoryShip getShip() {
        return ship;
    }
}
