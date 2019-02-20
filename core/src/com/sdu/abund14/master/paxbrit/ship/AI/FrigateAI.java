package com.sdu.abund14.master.paxbrit.ship.AI;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.ship.Frigate;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;
import com.sdu.abund14.master.paxbrit.util.ShipsUtil;

import java.util.List;
import java.util.Random;

public class FrigateAI extends CombatShipAI {

    public FrigateAI(Frigate frigate) {
        super(frigate);
    }

    private List<Ship> ships;
    private float shootingRange = 300;

    @Override
    public void update(float delta) {
        ships = PaxBritannicaGame.currentMatch.getAllShips();
        if (target == null || !ships.contains(target)) {
            target = ShipsUtil.getNearestEnemyShipWithTypePriorities(
                    ship,
                    ShipType.FIGHTER,
                    ShipType.BOMBER,
                    ShipType.FRIGATE,
                    ShipType.FACTORY
            );
        }

        if (target != null) engage(target, shootingRange, delta);
    }
}
