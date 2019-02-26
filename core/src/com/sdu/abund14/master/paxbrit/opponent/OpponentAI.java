package com.sdu.abund14.master.paxbrit.opponent;

import com.badlogic.gdx.math.MathUtils;
import com.sdu.abund14.master.paxbrit.GameSettings;
import com.sdu.abund14.master.paxbrit.ship.ShipType;
import com.sdu.abund14.master.paxbrit.util.MathUtil;
import com.sdu.abund14.master.paxbrit.util.ShipsUtil;

import java.util.Random;

public class OpponentAI {

    private int playerNumber;
    private float primaryOpportunityThreatWeight;
    private float secondaryOpportunityThreatWeight;

    public OpponentAI(int playerNumber, int difficulty) {
        this.playerNumber = playerNumber;

        switch (difficulty) {
            case GameSettings.DIFFICULTY_NORMAL:
                primaryOpportunityThreatWeight = 1;
                secondaryOpportunityThreatWeight = 0;
                break;
            case GameSettings.DIFFICULTY_HARD:
                primaryOpportunityThreatWeight = 2;
                secondaryOpportunityThreatWeight = 1;
                break;
            case GameSettings.DIFFICULTY_LEGENDARY:
                primaryOpportunityThreatWeight = 3;
                secondaryOpportunityThreatWeight = 2;
        }
    }

    public ProductionChoice makeProductionChoice() {
        int fighterCount = ShipsUtil.getEnemyShipCount(ShipType.FIGHTER, playerNumber);
        int bomberCount = ShipsUtil.getEnemyShipCount(ShipType.BOMBER, playerNumber);
        int frigateCount = ShipsUtil.getEnemyShipCount(ShipType.FRIGATE, playerNumber);
        int factoryCount = ShipsUtil.getEnemyShipCount(ShipType.FACTORY, playerNumber);

        Random random = new Random();
        int fighterScore = random.nextInt(100);
        int bomberScore = random.nextInt(100);
        int frigateScore = random.nextInt(100);
        int upgradeScore = random.nextInt(100);

        fighterScore += bomberCount * primaryOpportunityThreatWeight;
        fighterScore += fighterCount * secondaryOpportunityThreatWeight;
        fighterScore -= frigateCount * primaryOpportunityThreatWeight;
        fighterScore -= fighterCount * secondaryOpportunityThreatWeight;

        bomberScore += frigateCount * primaryOpportunityThreatWeight;
        bomberScore += factoryCount * secondaryOpportunityThreatWeight;
        bomberScore -= fighterCount * primaryOpportunityThreatWeight;
        bomberScore -= frigateCount * secondaryOpportunityThreatWeight;

        frigateScore += fighterCount * primaryOpportunityThreatWeight;
        frigateScore += bomberCount * secondaryOpportunityThreatWeight;
        frigateScore -= bomberCount * primaryOpportunityThreatWeight;

        upgradeScore -= 80;

        int winnerScore = MathUtil.max(fighterScore, bomberScore, frigateScore, upgradeScore);
        if (winnerScore == fighterScore) {
            return ProductionChoice.FIGHTER;
        } else if (winnerScore == bomberScore) {
            return ProductionChoice.BOMBER;
        } else if (winnerScore == frigateScore) {
            return ProductionChoice.FRIGATE;
        } else if (winnerScore == upgradeScore) {
            return ProductionChoice.UPGRADE;
        }
        return ProductionChoice.NONE;
    }
}
