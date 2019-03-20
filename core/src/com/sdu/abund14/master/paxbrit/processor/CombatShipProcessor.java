package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.Bomber;
import com.sdu.abund14.master.paxbrit.ship.CombatShip;
import com.sdu.abund14.master.paxbrit.ship.Fighter;
import com.sdu.abund14.master.paxbrit.ship.Frigate;

import java.util.Iterator;
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
        Iterator<Fighter> fighterListIterator = PaxBritannicaGame.currentMatch.getFighters().iterator();
        while (fighterListIterator.hasNext()) {
            if (fighterListIterator.next().isDead()) fighterListIterator.remove();
        }
        Iterator<Bomber> bomberListIterator = PaxBritannicaGame.currentMatch.getBombers().iterator();
        while (bomberListIterator.hasNext()) {
            if (bomberListIterator.next().isDead()) bomberListIterator.remove();
        }
        Iterator<Frigate> frigateListIterator = PaxBritannicaGame.currentMatch.getFrigates().iterator();
        while (frigateListIterator.hasNext()) {
            if (frigateListIterator.next().isDead()) frigateListIterator.remove();
        }
    }
}
