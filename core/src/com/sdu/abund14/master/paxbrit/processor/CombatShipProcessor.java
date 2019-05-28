package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.*;

import java.util.Iterator;

public class CombatShipProcessor implements Processor {
    @Override
    public void process(float delta) {
        removeDeadShips();
        for (CombatShip ship : NautilusGame.currentMatch.getCombatShips().values()) {
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
        Iterator<Fighter> fighterListIterator = NautilusGame.currentMatch.getFighters().values().iterator();
        while (fighterListIterator.hasNext()) {
            if (fighterListIterator.next().isDead()) fighterListIterator.remove();
        }
         Iterator<Bomber> bomberListIterator = NautilusGame.currentMatch.getBombers().values().iterator();
        while (bomberListIterator.hasNext()) {
            if (bomberListIterator.next().isDead()) bomberListIterator.remove();
        }
        Iterator<Frigate> frigateListIterator = NautilusGame.currentMatch.getFrigates().values().iterator();
        while (frigateListIterator.hasNext()) {
            if (frigateListIterator.next().isDead()) frigateListIterator.remove();
        }
        Iterator<CombatShip> combatShipListIterator = NautilusGame.currentMatch.getCombatShips().values().iterator();
        while (combatShipListIterator.hasNext()) {
            if (combatShipListIterator.next().isDead()) combatShipListIterator.remove();
        }
        Iterator<Ship> shipListIterator = NautilusGame.currentMatch.getAllShips().values().iterator();
        while (shipListIterator.hasNext()) {
            if (shipListIterator.next().isDead()) shipListIterator.remove();
        }
    }
}
