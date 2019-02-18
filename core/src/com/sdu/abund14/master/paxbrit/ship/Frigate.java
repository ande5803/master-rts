package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.FrigateAI;

public class Frigate extends CombatShip {
    Frigate(int playerNumber, float x, float y) {
        super("frigate", playerNumber, x, y);
        ai = new FrigateAI(this);
        type = ShipType.FRIGATE;
        movementSpeed = 30;
        magazineSize = 10;
        fireRate = 0.2f;
        reloadTime = 3f;
    }
}
