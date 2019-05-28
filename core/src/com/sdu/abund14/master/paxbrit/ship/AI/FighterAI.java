package com.sdu.abund14.master.paxbrit.ship.AI;

import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.ship.Fighter;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;
import com.sdu.abund14.master.paxbrit.util.ShipsUtil;

import java.util.List;

public class FighterAI extends CombatShipAI {
    private List<Ship> ships;
    private float shootingRange = 200;
    private Fighter fighter;

    public FighterAI(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
    }

    @Override
    public void update(float delta) {
        ships = NautilusGame.currentMatch.getAllShips();
        if (target == null || !ships.contains(target)) {
            target = ShipsUtil.getNearestEnemyShipWithTypePriorities(
                    ship,
                    ShipType.BOMBER,
                    ShipType.FIGHTER,
                    ShipType.FRIGATE,
                    ShipType.FACTORY
            );
        } else if (target.isOffScreen()) {
            target = null;
        }
        if (target != null) fighter.engage(target, shootingRange, delta);
    }
}
