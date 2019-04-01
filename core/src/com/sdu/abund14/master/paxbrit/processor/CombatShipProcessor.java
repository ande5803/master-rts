package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Bomber;
import com.sdu.abund14.master.paxbrit.ship.CombatShip;
import com.sdu.abund14.master.paxbrit.ship.Fighter;
import com.sdu.abund14.master.paxbrit.ship.Frigate;

import java.util.ListIterator;

public class CombatShipProcessor implements Processor {
    @Override
    public void process(float delta) {
        removeDeadShips();
        for (CombatShip ship : PaxBritannicaGame.currentMatch.getCombatShips()) {
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

    private void removeDeadShips() {
        ListIterator<CombatShip> iterator = PaxBritannicaGame.currentMatch.getCombatShips().listIterator();
        while (iterator.hasNext()) {
            CombatShip ship = iterator.next();
            if (ship.isDead()) {
                PaxBritannicaGame.currentMatch.removeShip(ship);
                iterator.remove();
            }
        }
    }
}
