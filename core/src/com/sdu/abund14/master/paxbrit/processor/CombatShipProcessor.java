package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.NautilusGame;
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
        ListIterator<Fighter> fighterListIterator = NautilusGame.currentMatch.getFighters().listIterator();
        while (fighterListIterator.hasNext()) {
            if (fighterListIterator.next().isDead()) fighterListIterator.remove();
        }
        ListIterator<Bomber> bomberListIterator = NautilusGame.currentMatch.getBombers().listIterator();
        while (bomberListIterator.hasNext()) {
            if (bomberListIterator.next().isDead()) bomberListIterator.remove();
        }
        ListIterator<Frigate> frigateListIterator = NautilusGame.currentMatch.getFrigates().listIterator();
        while (frigateListIterator.hasNext()) {
            if (frigateListIterator.next().isDead()) frigateListIterator.remove();
        }
    }
}
