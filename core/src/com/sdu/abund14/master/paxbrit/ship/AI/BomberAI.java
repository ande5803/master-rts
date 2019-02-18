package com.sdu.abund14.master.paxbrit.ship.AI;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.ship.Bomber;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.ship.ShipType;
import com.sdu.abund14.master.paxbrit.util.ShipsUtil;

import java.util.List;
import java.util.Random;

public class BomberAI extends CombatShipAI {

    private List<Ship> ships;
    private float range = 200;

    public BomberAI(Bomber bomber) {
        super(bomber);
    }

    @Override
    public void update(float delta) {
        ships = PaxBritannicaGame.currentMatch.getAllShips();
        if (target == null || !ships.contains(target)) {
            target = ShipsUtil.getNearestEnemyShipWithTypePriorities(
                    ship,
                    ShipType.FRIGATE,
                    ShipType.FACTORY
            );
            Random r = new Random();
            target = PaxBritannicaGame.currentMatch.getAllShips().get(r.nextInt(PaxBritannicaGame.currentMatch.getAllShips().size()));
        }

        engage(target, range, delta);
    }
}
