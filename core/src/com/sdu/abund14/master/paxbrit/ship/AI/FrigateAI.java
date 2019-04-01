package com.sdu.abund14.master.paxbrit.ship.AI;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.ship.Frigate;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;
import com.sdu.abund14.master.paxbrit.util.ShipsUtil;

import java.util.List;

public class FrigateAI extends CombatShipAI {

    public FrigateAI(Frigate frigate) {
        super(frigate);
        this.frigate = frigate;
    }

    private Frigate frigate;
    private float shootingRange = 300;

    @Override
    public void update(float delta) {
        List<Ship> ships = PaxBritannicaGame.currentMatch.getShips();
        if (target == null || !ships.contains(target)) {
            target = ShipsUtil.getNearestEnemyShipWithTypePriorities(
                    ship,
                    ShipType.FIGHTER,
                    ShipType.BOMBER,
                    ShipType.FRIGATE,
                    ShipType.FACTORY
            );
        } else if (target.isOffScreen()) {
            target = null;
        }
        if (target != null) frigate.engage(target, shootingRange, delta);
    }
}
