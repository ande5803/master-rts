package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.CombatShip;

public class CombatShipProcessor implements Processor {
    @Override
    public void process(float delta) {
        removeDeadShips();
        for (CombatShip ship : NautilusGame.currentMatch.getCombatShips()) {
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
        for (CombatShip ship : NautilusGame.currentMatch.getCombatShips()) {
            if (ship.isDead()) {
                NautilusGame.currentMatch.removeShip(ship);
            }
        }
    }
}
