package com.sdu.abund14.master.paxbrit.ship.AI;

import com.sdu.abund14.master.paxbrit.ship.CombatShip;
import com.sdu.abund14.master.paxbrit.ship.Ship;

public abstract class CombatShipAI {

    CombatShip ship;
    Ship target;

    CombatShipAI(CombatShip ship) {
        this.ship = ship;
    }

    public abstract void update(float delta);
}
