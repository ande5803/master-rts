package com.sdu.abund14.master.paxbrit.ship.AI;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.ship.Bomber;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;
import com.sdu.abund14.master.paxbrit.util.ShipsUtil;

import java.util.List;

public class BomberAI extends CombatShipAI {

    private float shootingRange = 50;
    private Bomber bomber;

    public BomberAI(Bomber bomber) {
        super(bomber);
        this.bomber = bomber;
    }

    @Override
    public void update(float delta) {
        List<Ship> ships = PaxBritannicaGame.currentMatch.getAllShips();
        if (target == null || !ships.contains(target)) {
            target = ShipsUtil.getNearestEnemyShipWithTypePriorities(
                    ship,
                    ShipType.FRIGATE,
                    ShipType.FACTORY
            );
        }
        if (target != null) bomber.engage(target, shootingRange, delta);
    }
}
