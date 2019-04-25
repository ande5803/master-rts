package com.sdu.abund14.master.paxbrit.processor;

import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.*;

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
        ListIterator<Fighter> fighterListIterator = PaxBritannicaGame.currentMatch.getFighters().listIterator();
        while (fighterListIterator.hasNext()) {
            if (fighterListIterator.next().isDead()) fighterListIterator.remove();
        }
        ListIterator<Bomber> bomberListIterator = PaxBritannicaGame.currentMatch.getBombers().listIterator();
        while (bomberListIterator.hasNext()) {
            if (bomberListIterator.next().isDead()) bomberListIterator.remove();
        }
        ListIterator<Frigate> frigateListIterator = PaxBritannicaGame.currentMatch.getFrigates().listIterator();
        while (frigateListIterator.hasNext()) {
            if (frigateListIterator.next().isDead()) frigateListIterator.remove();
        }
        ListIterator<CombatShip> combatShipListIterator = PaxBritannicaGame.currentMatch.getCombatShips().listIterator();
        while (combatShipListIterator.hasNext()) {
            if (combatShipListIterator.next().isDead()) combatShipListIterator.remove();
        }
        ListIterator<Ship> shipListIterator = PaxBritannicaGame.currentMatch.getAllShips().listIterator();
        while (shipListIterator.hasNext()) {
            if (shipListIterator.next().isDead()) shipListIterator.remove();
        }
    }
}
